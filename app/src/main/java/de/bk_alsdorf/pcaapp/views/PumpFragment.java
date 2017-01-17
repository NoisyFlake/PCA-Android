package de.bk_alsdorf.pcaapp.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import de.bk_alsdorf.pcaapp.Calculation;
import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class PumpFragment extends Fragment {
    private TextView basalRateValue;
    private Spinner basalRateUnitSpinner;
    private EditText bolusInput;
    private Spinner bolusUnitSpinner;
    private EditText bolusLockInput;
    private EditText boliPerHourInput;
    private TextView dosageResult;
    private TextView minimalRuntime;

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
            updateData();
        }
    }

    private View initializePumpView(LayoutInflater inflater, ViewGroup container) {
        final View pumpView = inflater.inflate(R.layout.activity_pump, container, false);

        basalRateValue = (TextView) pumpView.findViewById(R.id.basalRateValue);
        basalRateUnitSpinner = (Spinner) pumpView.findViewById(R.id.basalRateSpinner);
        bolusInput = (EditText) pumpView.findViewById(R.id.bolusInput);
        bolusUnitSpinner = (Spinner) pumpView.findViewById(R.id.bolusSpinner);
        boliPerHourInput = (EditText) pumpView.findViewById(R.id.boliPerHourInput);
        bolusLockInput = (EditText) pumpView.findViewById(R.id.bolusLockInput);
        dosageResult = (TextView) pumpView.findViewById(R.id.dosageResult);
        minimalRuntime = (TextView) pumpView.findViewById(R.id.minimalRuntimeValue);

        bolusUnitSpinner.setSelection(1);

        basalRateUnitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String unit = parent.getSelectedItem().toString();
               boolean isBasalRateInMl = unit.equals("ml") ? true : false;
               Data.setIsBasalRateInMl(isBasalRateInMl);
               updateData();
           }

           public void onNothingSelected(AdapterView<?> parent) {}
        });

        bolusInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                double bolusAmount;
                try {
                    bolusAmount = Double.parseDouble(v.getText().toString());
                } catch(NumberFormatException e) {
                    bolusAmount = 0;
                }

                if (Data.isBolusAmountInMl()) {
                    bolusAmount = Calculation.convertMlToMg(bolusAmount);
                }

                Data.setBolusAmount(bolusAmount);
                updateData();

                return false;
            }
        });

        bolusInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                bolusInput.setTextColor(Color.RED);
            }
        });

        bolusUnitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String unit = parent.getSelectedItem().toString();
                boolean isBolusAmountInMl = unit.equals("ml") ? true : false;
                Data.setIsBolusAmountInMl(isBolusAmountInMl);
                updateData();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        bolusLockInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int bolusLock;
                try {
                    bolusLock = Integer.parseInt(v.getText().toString());
                } catch(NumberFormatException e) {
                    bolusLock = 0;
                }

                Data.setBolusLock(bolusLock);
                updateData();

                return false;
            }
        });

        bolusLockInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                bolusLockInput.setTextColor(Color.RED);
                boliPerHourInput.setTextColor(Color.GRAY);
            }
        });

        boliPerHourInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int boliPerHour;
                try {
                    boliPerHour = Integer.parseInt(v.getText().toString());
                } catch(NumberFormatException e) {
                    boliPerHour = 0;
                }

                Data.setBoliPerHour(boliPerHour);
                updateData();

                return false;
            }
        });

        boliPerHourInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                boliPerHourInput.setTextColor(Color.RED);
                bolusLockInput.setTextColor(Color.GRAY);
            }
        });

        return pumpView;
    }

    private void updateData() {
        String basalRate = "0";
        if (Data.getBasalRate() > 0) {
            if (Data.isBasalRateInMl()) {
                basalRate = String.valueOf(Data.getBasalRateInMl());
            } else {
                basalRate = String.valueOf(Data.getBasalRate());
            }
        }
        basalRateValue.setText(basalRate);

        String bolusAmount = "";
        if (Data.getBolusAmount() > 0) {
            if (Data.isBolusAmountInMl()) {
                bolusAmount = String.valueOf(Data.getBolusAmountInMl());
            } else {
                bolusAmount = String.valueOf(Data.getBolusAmount());
            }
        }
        bolusInput.setText(bolusAmount);

        String bolusLock = Data.getBolusLock() > 0 ? String.valueOf(Data.getBolusLock()) : "";
        bolusLockInput.setText(bolusLock);

        String boliPerHour = Data.getBoliPerHour() > 0 ? String.valueOf(Data.getBoliPerHour()) : "";
        boliPerHourInput.setText(boliPerHour);

        String dosage = String.valueOf(Data.getDosage());
        dosageResult.setText(dosage);

        String minRuntime = String.valueOf(Data.getMinimalRuntime());
        minimalRuntime.setText(minRuntime);
        bolusInput.setTextColor(Color.BLACK);
        bolusLockInput.setTextColor(Color.BLACK);
        boliPerHourInput.setTextColor(Color.BLACK);
    }

}
