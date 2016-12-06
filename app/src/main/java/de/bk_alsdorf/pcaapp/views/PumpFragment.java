package de.bk_alsdorf.pcaapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;

import de.bk_alsdorf.pcaapp.Calculation;
import de.bk_alsdorf.pcaapp.R;

/**
 * Created by Anja Möller on 04.10.2016.
 */

public class PumpFragment extends Fragment {
    private EditText bolusAmountInput;
    private Spinner bolusSpinner;
    private boolean updateInProgress;

    //Fragments to calculate with values from other fragments
    private PharmacyFragment pharmacyFragment;
    private ResultFragment resultFragment;

    public PumpFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initializePumpView(inflater, container);
    }

    public void setPharmacyFragment(PharmacyFragment pharmacyFragment) {
        this.pharmacyFragment = pharmacyFragment;
    }

    public void setResultFragment(ResultFragment resultFragment) {
        this.resultFragment = resultFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if(!updateInProgress) {
                updateBolusAmountInputByPharmacyFragment();
            }
        }
    }

    private View initializePumpView(LayoutInflater inflater, ViewGroup container) {
        final View pumpView = inflater.inflate(R.layout.activity_pump, container, false);

        //Pump Viewelements
        bolusAmountInput = (EditText) pumpView.findViewById(R.id.bolusAmountInput);
        bolusSpinner = (Spinner) pumpView.findViewById(R.id.bolusSpinner);

        bolusAmountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!updateInProgress) {
                    updatePharmacyInputsByBolusAmountInput();
                }
            }
        });

        bolusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateBolusAmountInputByPharmacyFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        return pumpView;
    }

    //Update bolus amount if view change to pump View or mg/ml dropdown is changed
    private void updateBolusAmountInputByPharmacyFragment() {
        updateInProgress = true;

        String choosedScaleUnit = bolusSpinner.getSelectedItem().toString();
        BigDecimal agentPerHour = new BigDecimal(0);
        BigDecimal agentAmount = new BigDecimal(0);
        int tankVolume = Integer.parseInt(pharmacyFragment.getCartridgeSpinner().getSelectedItem().toString());
        if(pharmacyFragment.getBasalRateInput().getText().toString().length() > 0) {
            agentPerHour = new BigDecimal(Double.parseDouble(pharmacyFragment.getBasalRateInput().getText().toString()));
        }
        if(pharmacyFragment.getIndrigendQuantityInput().getText().toString().length() > 0) {
            agentAmount = new BigDecimal(Double.parseDouble(pharmacyFragment.getIndrigendQuantityInput().getText().toString()));
        }
        if(choosedScaleUnit.equals("mg")) {
            bolusAmountInput.setText(Calculation.convertBolusAmountMlToMg(agentPerHour, agentAmount, tankVolume).toString());
        }
        if(choosedScaleUnit.equals("ml")) {
            bolusAmountInput.setText(Calculation.convertBolusAmountMgToMl(agentPerHour, agentAmount, tankVolume).toString());
        }

        updateInProgress = false;
    }

    //Update agentPerHour and agentAmount in pharmacy view if bolus amount input is changed
    private void updatePharmacyInputsByBolusAmountInput() {
        updateInProgress = true;
        String choosedScaleUnit = bolusSpinner.getSelectedItem().toString();
        if(choosedScaleUnit.equals("mg")) {
            pharmacyFragment.getBasalRateInput().setText("");
            pharmacyFragment.getIndrigendQuantityInput().setText("");
        }
        if(choosedScaleUnit.equals("ml")) {
            pharmacyFragment.getBasalRateInput().setText("");
            pharmacyFragment.getIndrigendQuantityInput().setText("");
        }

        updateInProgress = false;
    }
}
