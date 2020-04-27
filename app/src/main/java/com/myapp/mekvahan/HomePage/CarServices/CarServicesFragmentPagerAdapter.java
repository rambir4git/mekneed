package com.myapp.mekvahan.HomePage.CarServices;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CarServicesFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public CarServicesFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return "Grocery & Staples";
            case 1: return "Fruit & Vegetables";
            case 2: return "Household Items";
            case 3: return "Dairy Essentials";
            case 4: return "Medicines";
            case 5: return "Other";
        }

        return super.getPageTitle(position);
    }


}
