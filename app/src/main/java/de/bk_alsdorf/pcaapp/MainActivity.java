package de.bk_alsdorf.pcaapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.bk_alsdorf.pcaapp.views.PharmacyFragment;
import de.bk_alsdorf.pcaapp.views.PumpFragment;
import de.bk_alsdorf.pcaapp.views.ResultFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText basalRateInput;
    private Spinner cartridgeSpinner;
    private SeekBar durationSeekBar;
    private TextView durationSeekBarCurrentValue;
    private EditText indrigendQuantityInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTabLayout();
        initializePharmacyView();
    }

    private void setTabLayout() {
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById( R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initializePharmacyView() {
        setContentView(R.layout.activity_pharmacy);

        basalRateInput = (EditText)findViewById(R.id.basalRateInput);
        cartridgeSpinner = (Spinner)findViewById(R.id.cartridgeSpinner);
        durationSeekBar = (SeekBar) findViewById(R.id.durationSeekBar);
        durationSeekBarCurrentValue = (TextView) findViewById(R.id.durationSeekBarCurrentValue);
        indrigendQuantityInput = (EditText)findViewById(R.id.indrigendQuantityInput);

        basalRateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateAgentAmountPerTank();
            }
        });
        durationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateAgentAmountPerTank();
                durationSeekBarCurrentValue.setText(String.valueOf(durationSeekBar.getProgress()));
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PharmacyFragment(), "Berechnung Apotheker");
        adapter.addFragment(new PumpFragment(), "Berechnung Pumpe");
        adapter.addFragment(new ResultFragment(), "Ergebnis");
        viewPager.setAdapter(adapter);
    }

    private void updateAgentAmountPerTank() {
        BigDecimal agentPerHour = new BigDecimal(0);
        if(basalRateInput.getText().toString().length() > 0) {
            agentPerHour = new BigDecimal(basalRateInput.getText().toString());
        }
        int runtime = durationSeekBar.getProgress();

        indrigendQuantityInput.setText(Calculation.getAgentAmountPerTank(agentPerHour, runtime).toString());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}