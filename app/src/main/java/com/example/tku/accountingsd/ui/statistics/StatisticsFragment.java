package com.example.tku.accountingsd.ui.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tku.accountingsd.R;

public class StatisticsFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;



    public StatisticsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_statistics,container,false);

        mTabLayout = (TabLayout) v.findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) v.findViewById(R.id.mViewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("圓餅圖"));
        mTabLayout.addTab(mTabLayout.newTab().setText("長條圖"));

        mViewPager.setAdapter(new pagerAdapter(getFragmentManager()));
        //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);

        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("財務分析");
    }

    class pagerAdapter extends FragmentStatePagerAdapter
    {

        public pagerAdapter(FragmentManager fragmentManager)
        {

            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return new PieChartFragment();
            else
                return new BarChartFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "圓餅圖";
            else
                return "長條圖";
        }
    }


}
