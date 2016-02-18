package br.net.mockup.control.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Thiago on 10/08/2015.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private final int total;
    private ArrayList<Map<String, Fragment>> fragments;

    public TabAdapter(FragmentManager supportFragmentManager, ArrayList<Map<String, Fragment>> fragments, int total) {
        super(supportFragmentManager);
        this.total = total;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        try {
            Map<String, Fragment> f = fragments.get(position);
            fragment = f.values().iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return total;
    }
}
