package nnr.app.search.com.searchapp2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import nnr.app.search.com.searchapp2.Fragment.SampleFragment;
import nnr.app.search.com.searchapp2.Fragment.SoonFragment;

public class FragmentHolder  extends FragmentActivity {
    TabLayout baseTabLayout;
    ViewPager fragmentPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        FirebaseApp.initializeApp(FragmentHolder.this);
    }

    private void initViews(){
        setContentView(R.layout.activity_tablayout);
        baseTabLayout = findViewById(R.id.switch_tablayout);
        fragmentPager = findViewById(R.id.fragment_holder);
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("Search");
        tabNames.add("Ambulance Tracker");
        tabNames.add("Locate hospital");
        tabNames.add("Offline help");
        tabNames.add("Privacy");
        tabNames.add("Settings");
        for(String name: tabNames) {
            baseTabLayout.addTab(baseTabLayout.newTab().setText(name));
        }
        baseTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        baseTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        baseTabLayout.setSelectedTabIndicatorHeight(10);
//        baseTabLayout.addOnTabSelectedListener(tabSelectedListener);
        fragmentPager.setAdapter(new fragmetSwitcherAdapter(getSupportFragmentManager(),tabNames));
        baseTabLayout.setupWithViewPager(fragmentPager);

    }


    public class fragmetSwitcherAdapter extends FragmentPagerAdapter {
        ArrayList<String> itemList= new ArrayList<>();
        @Override
        public Fragment getItem(int position) {
            Fragment myFragment = null;
            Log.i(AppConstants.APP_LOG_TAG,"The position selected is "+position);
            switch (position) {
                case 0:
                    return new SampleFragment();
                default:
                    Log.i(AppConstants.APP_LOG_TAG,"Did not find any tab for the selected item");
                    return new SoonFragment();
            }
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        public fragmetSwitcherAdapter(FragmentManager fm){
            super(fm);
        }

        public fragmetSwitcherAdapter(FragmentManager fm,ArrayList itemList){
            super(fm);
            this.itemList = itemList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return itemList.get(position);
        }
    }
}
