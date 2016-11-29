package de.bk_alsdorf.pcaapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import de.bk_alsdorf.pcaapp.views.PharmacyFragment;
import de.bk_alsdorf.pcaapp.views.PumpFragment;
import de.bk_alsdorf.pcaapp.views.ResultFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTabLayout();
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(generateFragmentsForApater(adapter));
    }

    private  ViewPagerAdapter generateFragmentsForApater(ViewPagerAdapter adapter) {
        PharmacyFragment pharmacyFragment = new PharmacyFragment();
        PumpFragment pumpFragment = new PumpFragment();
        ResultFragment resultFragment = new ResultFragment();
        pharmacyFragment.setPumpFragment(pumpFragment);
        pharmacyFragment.setResultFragment(resultFragment);
        pumpFragment.setPharmacyFragment(pharmacyFragment);
        pumpFragment.setResultFragment(resultFragment);
        resultFragment.setPharmacyFragment(pharmacyFragment);
        resultFragment.setPumpFragment(pumpFragment);

        adapter.addFragment(pharmacyFragment, "Berechnung Apotheker");
        adapter.addFragment(pumpFragment, "Berechnung Pumpe");
        adapter.addFragment(resultFragment, "Ergebnis");
        return adapter;
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