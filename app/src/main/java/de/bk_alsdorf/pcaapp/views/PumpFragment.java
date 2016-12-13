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
import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class PumpFragment extends Fragment {
    private EditText bolusAmountInput;
    private Spinner bolusSpinner;
    private EditText bolusLockInput;
    private boolean updateInProgress;
    private EditText boliPerHourInput;

    public PumpFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializePumpView(inflater, container);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if(!updateInProgress) {
                updatePharmacyInputsByBolusAmountInput();
            }
        }
    }

    private View initializePumpView(LayoutInflater inflater, ViewGroup container) {
        final View pumpView = inflater.inflate(R.layout.activity_pump, container, false);

        bolusAmountInput = (EditText) pumpView.findViewById(R.id.bolusAmountInput);
        bolusSpinner = (Spinner) pumpView.findViewById(R.id.bolusSpinner);
        bolusLockInput = (EditText) pumpView.findViewById(R.id.bolusLockInput);
        boliPerHourInput = (EditText) pumpView.findViewById(R.id.boliPerHourInput);


        bolusAmountInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBolusAmount(bolusAmountInput.getText().toString());
            }
        });

        bolusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data.setBolusUnit(bolusSpinner.getSelectedItem().toString());
                updatePharmacyInputsByBolusAmountInput();
            }
        });

        bolusLockInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBolusLock(bolusLockInput.getText().toString());
            }
        });

        boliPerHourInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBoliPerHour(boliPerHourInput.getText().toString());
            }
        });

        return pumpView;
    }

    //Update agentPerHour and agentAmount in pharmacy view if bolus amount input is changed
    private void updatePharmacyInputsByBolusAmountInput() {
        updateInProgress = true;
        String choosedScaleUnit = Data.getBolusUnit();
        String bolusAmount = Data.getBolusAmount();
        int tankVolume = Integer.parseInt(Data.getCartridge());
        if(bolusAmount.length()<1) {
            bolusAmount = "0";
        }
        if(choosedScaleUnit.equals("mg")) {
            Data.setIngredientQuantity(bolusAmount);
        }
        if(choosedScaleUnit.equals("ml")) {
            Data.setBasalRate(Calculation.convertBasalrateFromBolusAmountMl(new BigDecimal(bolusAmount), tankVolume).toString());
        }

        updateInProgress = false;
    }
}
