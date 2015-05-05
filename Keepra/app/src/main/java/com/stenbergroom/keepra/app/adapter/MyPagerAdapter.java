package com.stenbergroom.keepra.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.stenbergroom.keepra.app.CardFragment;

/**
 * Created by Sten on 05.05.2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final String[] TABS_TITLES = {"Все кипы", "Выполненные", "Календарь", "Список"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TABS_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return TABS_TITLES.length;
    }
}
