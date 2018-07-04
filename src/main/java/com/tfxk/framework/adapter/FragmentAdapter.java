package com.tfxk.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;
    private List<? extends Fragment> lists = new ArrayList<Fragment>();

    public FragmentAdapter(android.support.v4.app.FragmentManager fm, List<? extends Fragment> views, String[] titles) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.lists = views;
        this.mTitles = titles;
    }

    public void setList(List<? extends Fragment> views) {
        this.lists = views;
        notifyDataSetChanged();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles!=null)
        return mTitles[position];
        return "";
    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }
}
