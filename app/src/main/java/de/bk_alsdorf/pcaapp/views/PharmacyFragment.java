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

public class PharmacyFragment extends Fragment {

    public PharmacyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_pharmacy, container, false);
    }
}
