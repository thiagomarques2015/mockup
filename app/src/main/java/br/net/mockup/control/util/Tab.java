package br.net.mockup.control.util;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Tab para multiplos itens carregando fragmentos
 * Created by Thiago on 28/10/2015.
 */
public class Tab {
    private ArrayList<Map<String, Fragment>> tabs;
    private ArrayList<Integer> icons;
    private ViewPager pager;
    private FragmentManager fragmentManager;
    private Activity activity;
    private LocalBroadcastManager localBroadcastManager;
    private Intent intent;
    private boolean noChangeToolbarTitle;
    private TabIndex tabIndex;
    private TabLayout tabLayout;


    public Tab(ArrayList<Map<String, Fragment>> tabs) {
        this.tabs = tabs;
        noChangeToolbarTitle = false;
    }

    public Tab into(TabLayout tabLayout, ViewPager pager) {
        this.tabLayout = tabLayout;
        this.pager = pager;
        return this;
    }

    public Tab setLocalBroadcastManager(LocalBroadcastManager localBroadcastManager, Intent intent) {
        this.localBroadcastManager = localBroadcastManager;
        this.intent = intent;
        return this;
    }

    public Tab icons(ArrayList<Integer> icons) {
        this.icons = icons;
        return this;
    }

    public Tab with(FragmentManager fragmentManager, Activity context) {
        this.fragmentManager = fragmentManager;
        this.activity = context;
        return this;
    }

    public Tab setTabIndex(TabIndex tabIndex) {
        this.tabIndex = tabIndex;
        return this;
    }

    public Tab noChangeToolbarTitle() {
        this.noChangeToolbarTitle = true;
        return this;
    }

    @SuppressWarnings("deprecation")
    public void init(){
        // init view pager
        pager.setOnPageChangeListener(onPageChangeListener);
        setupViewPager(pager);
        tabLayout.setupWithViewPager(pager);
        setupTabIcons();
        recoveryLastTabSelected();
        tabLayout.setOnTabSelectedListener(onTabSelected);
    }

    private void recoveryLastTabSelected() {
        if(tabIndex != null){
            int tab = tabIndex.getTabSelected();
            tabLayout.getTabAt(tab).select();
        }
    }

    private TabLayout.OnTabSelectedListener onTabSelected = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if(tabIndex != null){
                tabIndex.onTabSelected(tab.getPosition());
            }
            pager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(tabLayout != null){

                if(tabIndex != null)
                    tabIndex.onTabSelected(position);

                pager.setCurrentItem(position);

                // Seta o titulo
                String title = tabs.get(position).keySet().toString().replaceAll("\\[|\\].*", "");

                if(!noChangeToolbarTitle)
                    activity.setTitle(title);

                if(localBroadcastManager != null){
                    intent.putExtra("title", title);
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, true);
        for(Map<String, Fragment> item : tabs){
            String key = item.entrySet().toString();
            Fragment fragment = item.values().iterator().next();
            adapter.addFrag(fragment, key);
        }
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        for(int i=0, total=icons.size(); i<total; i++){
            int icon = icons.get(i);
            /*TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.item_custom_tab, null);
            tab.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);*/
            tabLayout.getTabAt(i).setIcon(icon);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private boolean onlyIcons;

        public ViewPagerAdapter(FragmentManager fm, boolean onlyIcons) {
            super(fm);
            this.onlyIcons = onlyIcons;
        }

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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (onlyIcons)? null : mFragmentTitleList.get(position);
        }
    }

    public interface TabIndex{
        void onTabSelected(int position);
        int getTabSelected();
    }
}
