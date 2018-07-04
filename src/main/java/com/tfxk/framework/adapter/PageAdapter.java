package com.tfxk.framework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter<T> extends android.support.v4.view.PagerAdapter {
    private final Context mContext;
    private List<T> mList;

    private View.OnClickListener mClickListener;
    private AdapterView.OnItemClickListener mItemClickListener;
    private int mNewDataSize;

    private List <String>titles;


    public PageAdapter(Context context) {
        this.mContext = context;
        this.titles = new ArrayList();
        this.mList = new ArrayList<T>();
    }

    public PageAdapter(Context context, List<String>titles, List items) {
        this.mContext = context;
        this.titles = titles;
        this.mList = items;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = (View) mList.get(position);
        container.addView(view);
        return view;
    }


    public void setClickListener(View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void clear() {
        this.notifyDataSetChanged();
    }

    public T getView(int page) {
        return mList.get(page);
    }

    public void addPager(T view) {
        mList.add(view);
        notifyDataSetChanged();
    }
}