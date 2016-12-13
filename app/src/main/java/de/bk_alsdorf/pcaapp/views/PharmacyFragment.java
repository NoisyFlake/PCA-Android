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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.math.BigDecimal;

import de.bk_alsdorf.pcaapp.Calculation;
import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class PharmacyFragment extends Fragment {
    private EditText basalRateInput;
    private Spinner cartridgeSpinner;
    private SeekBar durationSeekBar;
    private TextView durationSeekBarCurrentValue;
    private EditText ingredientQuantityInput;
    private TextView dosageResult;
    private boolean updateInProgress = false;

    public PharmacyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializePharmacyView(inflater, container);
    }

    private View initializePharmacyView(LayoutInflater inflater, ViewGroup container) {
        final View pharmacyView = inflater.inflate(R.layout.activity_pharmacy, container, false);

        basalRateInput = (EditText) pharmacyView.findViewById(R.id.basalRateInput);
        cartridgeSpinner = (Spinner) pharmacyView.findViewById(R.id.cartridgeSpinner);
        durationSeekBar = (SeekBar) pharmacyView.findViewById(R.id.durationSeekBar);
        durationSeekBarCurrentValue = (TextView) pharmacyView.findViewById(R.id.durationSeekBarCurrentValue);
        ingredientQuantityInput = (EditText) pharmacyView.findViewById(R.id.ingredientQuantityInput);
        dosageResult = (TextView) pharmacyView.findViewById(R.id.dosageResult);

        basalRateInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setBasalRate(basalRateInput.getText().toString());
                if (!updateInProgress) {
                    updateAgentAmountPerTank();
                }
            }
        });

        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {}
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Data.setDuration(String.valueOf(durationSeekBar.getProgress() + 1));
                durationSeekBarCurrentValue.setText(String.valueOf(durationSeekBar.getProgress()+1));
                updateAgentAmountPerTank();

            }
        });


        cartridgeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) {}

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data.setCartridge(cartridgeSpinner.getSelectedItem().toString());
                updateDosageResult();
            }
        });


        ingredientQuantityInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Data.setIngredientQuantity(ingredientQuantityInput.getText().toString());
                if (!updateInProgress) {
                    updateAgentPerHour();
                    updateDosageResult();
                }
            }
        });

        return pharmacyView;
    }

    private void updateAgentAmountPerTank() {
        updateInProgress = true;

        BigDecimal agentPerHour = new BigDecimal(Data.getBasalRate());
        int runtime = Integer.parseInt(Data.getDuration());

        ingredientQuantityInput.setText(Calculation.getAgentAmountPerTank(agentPerHour, runtime).toString());
        updateDosageResult();
        updateInProgress = false;
    }

    private void updateDosageResult() {
        BigDecimal agentAmountPerTank = new BigDecimal(Data.getIngredientQuantity());
        int tankVolume = Integer.parseInt(Data.getCartridge());

        Data.setDosage(Calculation.getConcentration(agentAmountPerTank, tankVolume).toString());
        dosageResult.setText(Data.getDosage());
    }

    private void updateAgentPerHour() {
        updateInProgress = true;

        BigDecimal agentAmountPerTank = new BigDecimal(Data.getIngredientQuantity());
        int runtime = Integer.parseInt(Data.getDuration());

        basalRateInput.setText(Calculation.getAgentPerHour(agentAmountPerTank,runtime).toString());

        updateInProgress = false;
    }
}
