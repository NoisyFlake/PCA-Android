package de.bk_alsdorf.pcaapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private EditText basalRateInput;
    private Spinner cartridgeSpinner;
    private EditText indrigendQuantityInput;

    public PumpFragment() {

    }

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

    private View initializePumpView(LayoutInflater inflater, ViewGroup container) {
        final View pumpView = inflater.inflate(R.layout.activity_pump, container, false);
        final View pharmacyView = inflater.inflate(R.layout.activity_pharmacy, null);

        //Pump Viewelements
        bolusAmountInput = (EditText) pumpView.findViewById(R.id.bolusAmountInput);
        bolusSpinner = (Spinner) pumpView.findViewById(R.id.bolusSpinner);

        //Pharmacy Viewelements
        basalRateInput = (EditText) pharmacyView.findViewById(R.id.basalRateInput);
        cartridgeSpinner = (Spinner) pharmacyView.findViewById(R.id.cartridgeSpinner);
        indrigendQuantityInput = (EditText) pharmacyView.findViewById(R.id.indrigendQuantityInput);

        bolusAmountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //updateBolusAmountInput();
            }
        });
        return pumpView;
    }

    private void updateBolusAmountInput() {
        String choosedScaleUnit = bolusSpinner.getSelectedItem().toString();
        BigDecimal agentPerHour = new BigDecimal(Integer.parseInt(basalRateInput.getText().toString()));
        BigDecimal agentAmount = new BigDecimal(Integer.parseInt(indrigendQuantityInput.getText().toString()));
        int tankVolume = Integer.parseInt(cartridgeSpinner.getSelectedItem().toString());
        if(choosedScaleUnit.equals("mg")) {
            bolusAmountInput.setText(Calculation.convertMlToMg(agentPerHour, agentAmount, tankVolume).toString());
        }
        if(choosedScaleUnit.equals("ml")) {
            bolusAmountInput.setText(Calculation.convertMgToMl(agentPerHour, agentAmount, tankVolume).toString());
        }
    }
}
