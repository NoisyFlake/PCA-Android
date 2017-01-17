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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemSelectedListener;

import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class PharmacyFragment extends Fragment {
    private EditText basalRateInput;
    private EditText cartridgeInput;
    private SeekBar durationSeekBar;
    private TextView durationSeekBarCurrentValue;
    private EditText ingredientQuantityInput;
    private TextView dosageResult;
    private boolean init = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializePharmacyView(inflater, container);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && init) {
            updateData();
        }
    }

    private View initializePharmacyView(LayoutInflater inflater, ViewGroup container) {
        final View pharmacyView = inflater.inflate(R.layout.activity_pharmacy, container, false);

        basalRateInput = (EditText) pharmacyView.findViewById(R.id.basalRateInput);
        cartridgeInput = (EditText) pharmacyView.findViewById(R.id.cartridgeInput);
        durationSeekBar = (SeekBar) pharmacyView.findViewById(R.id.durationSeekBar);
        durationSeekBarCurrentValue = (TextView) pharmacyView.findViewById(R.id.durationSeekBarCurrentValue);
        ingredientQuantityInput = (EditText) pharmacyView.findViewById(R.id.ingredientQuantityInput);
        dosageResult = (TextView) pharmacyView.findViewById(R.id.dosageResult);

        basalRateInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            double basalRate;
            try {
                basalRate = Double.parseDouble(v.getText().toString());
            } catch(NumberFormatException e) {
                basalRate = 0;
            }

            Data.setBasalRate(basalRate);
            updateData();

            return false;
            }
        });

        basalRateInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                basalRateInput.setTextColor(Color.RED);
                ingredientQuantityInput.setTextColor(Color.GRAY);
            }
        });

        cartridgeInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int cartridge;
                try {
                    cartridge = Integer.parseInt(v.getText().toString());
                } catch(NumberFormatException e) {
                    cartridge = 0;
                }

                Data.setCartridge(cartridge);
                updateData();

                return false;
            }
        });

        cartridgeInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                cartridgeInput.setTextColor(Color.RED);
            }
        });

        durationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {}
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Data.setDuration(progress + 1);
                durationSeekBarCurrentValue.setText(String.valueOf(Data.getDuration()));
                updateData();
            }
        });

        ingredientQuantityInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                double ingredientQuantity;
                try {
                    ingredientQuantity = Double.parseDouble(v.getText().toString());
                } catch (NumberFormatException e) {
                    ingredientQuantity = 0;
                }

                Data.setIngredientQuantity(ingredientQuantity);
                updateData();

                return false;
            }
        });

        ingredientQuantityInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ingredientQuantityInput.setTextColor(Color.RED);
                basalRateInput.setTextColor(Color.GRAY);
            }
        });

        init = true;
        return pharmacyView;
    }

    private void updateData() {
        // Don't fill the fields if value is zero or less (except dosage)
        String basalRate = Data.getBasalRate() > 0 ? String.valueOf(Data.getBasalRate()) : "";
        String cartridge = Data.getCartridge() > 0 ? String.valueOf(Data.getCartridge()) : "";
        String ingredientQuantity = Data.getIngredientQuantity() > 0 ? String.valueOf(Data.getIngredientQuantity()) : "";

        String dosage = String.valueOf(Data.getDosage());

        basalRateInput.setText(basalRate);
        cartridgeInput.setText(cartridge);
        ingredientQuantityInput.setText(ingredientQuantity);
        dosageResult.setText(dosage);

        ingredientQuantityInput.setTextColor(Color.BLACK);
        cartridgeInput.setTextColor(Color.BLACK);
        basalRateInput.setTextColor(Color.BLACK);
    }

}
