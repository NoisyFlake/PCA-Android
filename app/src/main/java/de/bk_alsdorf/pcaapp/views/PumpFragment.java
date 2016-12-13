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
import android.widget.TextView;

import java.math.BigDecimal;

import de.bk_alsdorf.pcaapp.Calculation;
import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class PumpFragment extends Fragment {
    private EditText bolusAmountInput;
    private Spinner bolusSpinner;
    private EditText bolusLockTimeInput;
    private boolean updateInProgress;
    private EditText boliPerHourInput;
    private TextView dosageResult;

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
                updateBolusAmountInputByBolusUnit();
                dosageResult.setText(Data.getDosage());
            }
        }
    }

    private View initializePumpView(LayoutInflater inflater, ViewGroup container) {
        final View pumpView = inflater.inflate(R.layout.activity_pump, container, false);

        bolusAmountInput = (EditText) pumpView.findViewById(R.id.bolusAmountInput);
        bolusSpinner = (Spinner) pumpView.findViewById(R.id.bolusSpinner);
        boliPerHourInput = (EditText) pumpView.findViewById(R.id.boliPerHourInput);
        bolusLockTimeInput = (EditText) pumpView.findViewById(R.id.bolusLockInput);
        dosageResult = (TextView) pumpView.findViewById(R.id.dosageResult);

        bolusAmountInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Data.getBolusUnit().equals("mg")) {
                    Data.setBolusAmount(bolusAmountInput.getText().toString());
                    Data.setBasalRate(bolusAmountInput.getText().toString());
                } else {
                    Data.setBolusAmount(Calculation.convertBolusAmountMlToMg(new BigDecimal(Data.getBasalRate()),
                            new BigDecimal(Data.getIngredientQuantity()), Integer.parseInt(Data.getCartridge())).toString());
                    Data.setBasalRate(Calculation.convertBolusAmountMlToMg(new BigDecimal(Data.getBasalRate()),
                            new BigDecimal(Data.getIngredientQuantity()), Integer.parseInt(Data.getCartridge())).toString());
                }
            }
        });


        bolusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data.setBolusUnit(bolusSpinner.getSelectedItem().toString());
                updateBolusAmountInputByBolusUnit();
            }
        });

        boliPerHourInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBoliPerHour(boliPerHourInput.getText().toString());
                if (!updateInProgress) {
                    updateBolusLock();
                }
            }
        });

        bolusLockTimeInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBolusLock(bolusLockTimeInput.getText().toString());
                if (!updateInProgress) {
                    updateBolusLockTime();
                }
            }
        });

        return pumpView;

    }

    //Update bolus amount depending on unit
    private void updateBolusAmountInputByBolusUnit() {
        updateInProgress = true;

        if(Data.getBolusUnit().equals("mg")) {
            bolusAmountInput.setText(Data.getBolusAmount());
        } else {
            bolusAmountInput.setText(Calculation.convertBolusAmountMgToMl(new BigDecimal(Data.getBasalRate()),
                    new BigDecimal(Data.getIngredientQuantity()), Integer.parseInt(Data.getCartridge())).toString());
        }

        updateInProgress = false;
    }

    //Update agentPerHour and agentAmount in pharmacy view if bolus amount input is changed
    private void updatePharmacyInputsInMl(){
        updateInProgress = true;
        String choosedScaleUnit = Data.getBolusUnit();
        String bolusAmount = Data.getBolusAmount();
        int tankVolume = Integer.parseInt(Data.getCartridge());
        if(Double.parseDouble(bolusAmount) == 0) {
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

    private void updateBolusLock(){
        updateInProgress = true;

        if (boliPerHourInput.getText().length() > 0) {
            BigDecimal bolusLock = new BigDecimal(boliPerHourInput.getText().toString());
            bolusLockTimeInput.setText(Calculation.BolusLockTime(bolusLock).toString());
        }

        updateInProgress = false;
    }

    private void updateBolusLockTime(){
        updateInProgress = true;

        if (bolusLockTimeInput.getText().length() > 0){
            BigDecimal bolusLockTime = new BigDecimal(bolusLockTimeInput.getText().toString());
            BigDecimal help = new BigDecimal(5);
            if (bolusLockTime.compareTo(help) == 0){
                boliPerHourInput.setText(Calculation.BolusLock(bolusLockTime).toString());
            } else {
                bolusLockTime = new BigDecimal(5);
                bolusLockTimeInput.setText("5");
                boliPerHourInput.setText(Calculation.BolusLock(bolusLockTime).toString());
            }
        }

        updateInProgress = false;
    }
}
