package de.bk_alsdorf.pcaapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.R;

public class ResultFragment extends Fragment {

    private TextView resultDate;
    private TextView basalRateResult;
    private TextView cartridgeResult;
    private TextView durationResult;
    private TextView ingredientQuantityResult;
    private TextView dosageResult;
    private TextView bolusAmountResult;
    private TextView bolusLockResult;
    private TextView boliPerHourResult;

    public ResultFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializeResultView(inflater, container);
    }

    @Override
    public void setUserVisibleHint (boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) updateResults();
    }

    public void updateResults() {
        String basalRate = Data.getBasalRate() + " mg/h";
        basalRateResult.setText(basalRate);

        if (Data.getIngredientQuantity().length() > 0 && !Data.getIngredientQuantity().equals("0.0")) {
            String ingredientQuantity = Data.getIngredientQuantity() + " mg";
            ingredientQuantityResult.setText(ingredientQuantity);
        }

        String cartridge = Data.getCartridge() + " ml";
        cartridgeResult.setText(cartridge);

        String duration = Data.getDuration() + " Tage";
        durationResult.setText(duration);

        dosageResult.setText(Data.getDosage());

        if (Data.getBolusAmount().length() > 0 && !Data.getBolusAmount().equals("0.0")) {
            String bolusAmount = Data.getBolusAmount() + " " + Data.getBolusUnit();
            bolusAmountResult.setText(bolusAmount);
        }

        if (Data.getBolusLock().length() > 0 && !Data.getBolusLock().equals("0.0")) {
            String bolusLock = Data.getBolusLock() + " Minuten";
            bolusLockResult.setText(bolusLock);
        }

        if (Data.getBoliPerHour().length() > 0 && !Data.getBoliPerHour().equals("0.0")) {
            String boliPerHour = Data.getBoliPerHour() + " pro Stunde";
            boliPerHourResult.setText(boliPerHour);
        }
    }

    private View initializeResultView(LayoutInflater inflater, ViewGroup container) {
        final View resultView = inflater.inflate(R.layout.activity_result, container, false);

        resultDate = (TextView) resultView.findViewById(R.id.resultDate);
        basalRateResult = (TextView) resultView.findViewById(R.id.basalRateResult);
        cartridgeResult = (TextView) resultView.findViewById(R.id.cartridgeResult);
        durationResult = (TextView) resultView.findViewById(R.id.durationResult);
        ingredientQuantityResult = (TextView) resultView.findViewById(R.id.ingredientQuantityResult);
        dosageResult = (TextView) resultView.findViewById(R.id.dosageResult);
        bolusAmountResult = (TextView) resultView.findViewById(R.id.bolusAmountResult);
        bolusLockResult = (TextView) resultView.findViewById(R.id.bolusLockResult);
        boliPerHourResult = (TextView) resultView.findViewById(R.id.boliPerHourResult);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        resultDate.setText(date);

        return resultView;
    }
}
