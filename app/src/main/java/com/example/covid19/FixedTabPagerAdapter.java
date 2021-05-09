package com.example.covid19;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class FixedTabPagerAdapter extends FragmentStatePagerAdapter {

    public FixedTabPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CovidDataFragment();
            case 1:
                return new RegisterFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "COVID19 Data";
            case 1:
                return "Register";

            default:
                return null;
        }
    }
}
