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

import de.bk_alsdorf.pcaapp.R;

/**
 * Created by Anja Möller on 04.10.2016.
 */

public class ResultFragment extends Fragment {

    //Fragments to calculate with values from other fragments
    private PharmacyFragment pharmacyFragment;
    private PumpFragment pumpFragment;

    private TextView resultDate;

    public ResultFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initializeResultView(inflater, container);
    }

    public void setPharmacyFragment(PharmacyFragment pharmacyFragment) {
        this.pharmacyFragment = pharmacyFragment;
    }

    public void setPumpFragment(PumpFragment pumpFragment) {
        this.pumpFragment = pumpFragment;
    }

    private View initializeResultView(LayoutInflater inflater, ViewGroup container) {
        final View resultView = inflater.inflate(R.layout.activity_result, container, false);
        final View pumpView = inflater.inflate(R.layout.activity_pump, null);
        final View pharmacyView = inflater.inflate(R.layout.activity_pharmacy, null);

        resultDate = (TextView) resultView.findViewById(R.id.resultDate);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        resultDate.setText(date);

        return resultView;
    }
}
