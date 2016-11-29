package de.bk_alsdorf.pcaapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.bk_alsdorf.pcaapp.R;

/**
 * Created by Anja Möller on 04.10.2016.
 */

public class ResultFragment extends Fragment {

    //Fragments to calculate with values from other fragments
    private PharmacyFragment pharmacyFragment;
    private PumpFragment pumpFragment;

    public ResultFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_result, container, false);
    }

    public void setPharmacyFragment(PharmacyFragment pharmacyFragment) {
        this.pharmacyFragment = pharmacyFragment;
    }

    public void setPumpFragment(PumpFragment pumpFragment) {
        this.pumpFragment = pumpFragment;
    }
}
