package de.bk_alsdorf.pcaapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.bk_alsdorf.pcaapp.Calculation;
import de.bk_alsdorf.pcaapp.R;

/**
 * Created by Anja Möller on 04.10.2016.
 */

public class PharmacyFragment extends Fragment {
    private EditText basalRateInput;
    private Spinner cartridgeSpinner;
    private SeekBar durationSeekBar;
    private TextView durationSeekBarCurrentValue;
    private EditText indrigendQuantityInput;
    private TextView dosageResult;
    private boolean updateInProgress;

    //Fragments to calculate with values from other fragments
    private PumpFragment pumpFragment;
    private ResultFragment resultFragment;

    public PharmacyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initializePharmacyView(inflater, container);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
            Log.d("Pharmacy", "Fragment is visible.");

        else
            Log.d("Pharmacy", "Fragment is not visible.");
    }

    public EditText getBasalRateInput() {
        return basalRateInput;
    }

    public EditText getIndrigendQuantityInput() {
        return indrigendQuantityInput;
    }

    public Spinner getCartridgeSpinner() {
        return cartridgeSpinner;
    }

    public void setPumpFragment(PumpFragment pumpFragment) {
        this.pumpFragment = pumpFragment;
    }

    public void setResultFragment (ResultFragment resultFragment) {
        this.resultFragment = resultFragment;
    }

    private View initializePharmacyView(LayoutInflater inflater, ViewGroup container) {
        final View pharmacyView = inflater.inflate(R.layout.activity_pharmacy, container, false);

        basalRateInput = (EditText) pharmacyView.findViewById(R.id.basalRateInput);
        cartridgeSpinner = (Spinner) pharmacyView.findViewById(R.id.cartridgeSpinner);
        durationSeekBar = (SeekBar) pharmacyView.findViewById(R.id.durationSeekBar);
        durationSeekBarCurrentValue = (TextView) pharmacyView.findViewById(R.id.durationSeekBarCurrentValue);
        indrigendQuantityInput = (EditText) pharmacyView.findViewById(R.id.indrigendQuantityInput);
        dosageResult = (TextView) pharmacyView.findViewById(R.id.dosageResult);

        basalRateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updateInProgress) {
                    updateAgentAmountPerTank();
                }
            }
        });

        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateAgentAmountPerTank();
                durationSeekBarCurrentValue.setText(String.valueOf(durationSeekBar.getProgress()+1));
            }
        });


        cartridgeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDosageResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });


        indrigendQuantityInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updateInProgress) {
                    updateAgentPerHour();
                }
            }
        });

        return pharmacyView;
    }

    private void updateAgentAmountPerTank() {

        updateInProgress = true;

        BigDecimal agentPerHour = new BigDecimal(0);
        if(basalRateInput.getText().toString().length() > 0) {
            agentPerHour = new BigDecimal(basalRateInput.getText().toString());
        }
        int runtime = durationSeekBar.getProgress() + 1;

        indrigendQuantityInput.setText(Calculation.getAgentAmountPerTank(agentPerHour, runtime).toString());
        updateDosageResult();
        updateInProgress = false;
    }

    private void updateDosageResult() {
        BigDecimal agentAmountPerTank = new BigDecimal(0);
        int tankVolume = 0;

        if (indrigendQuantityInput.getText().toString().length() > 0) {
            agentAmountPerTank = new BigDecimal(indrigendQuantityInput.getText().toString());
        }
        if (cartridgeSpinner.getSelectedItem().toString().length() > 0) {
            tankVolume = Integer.parseInt(cartridgeSpinner.getSelectedItem().toString());
        }

        dosageResult.setText(Calculation.getConcentration(agentAmountPerTank, tankVolume).toString());
    }

    private void updateAgentPerHour() {

        updateInProgress = true;
        BigDecimal agentAmountPerTank = new BigDecimal(0);
        BigDecimal agentPerHour;

        if(indrigendQuantityInput.getText().toString().length() > 0) {
            agentAmountPerTank = new BigDecimal(indrigendQuantityInput.getText().toString());
        }
        int runtime = durationSeekBar.getProgress() + 1;

        basalRateInput.setText(Calculation.getAgentPerHour(agentAmountPerTank,runtime).toString());

        updateInProgress = false;
    }
}
