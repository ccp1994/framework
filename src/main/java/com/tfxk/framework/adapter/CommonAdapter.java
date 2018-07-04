package com.tfxk.framework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.tfxk.framework.GlobalApplication;
import com.tfxk.framework.R;
import com.tfxk.framework.adapter.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenchunpeng on 2016/3/28.
 */
public class CommonAdapter<T> extends BaseAdapter {
    private final Context mContext;
    private final Glide mPicasso;
    private final int mLayoutId;
    private final ICommonAdapter mICommonAdapter;
    private List<T> mList;
    private ViewHolder mHolder;
    private int mPlaceHolder;
    private int count;
    public CommonAdapter(Context context, int layoutId, ICommonAdapter iCommonAdapter) {
        this(context, layoutId, GlobalApplication.getInstance().getPicasso(), R.mipmap.ic_launcher, iCommonAdapter);
    }

    public CommonAdapter(Context context, int layoutId, int placeHolderImageRes, ICommonAdapter iCommonAdapter) {
        this(context, layoutId, GlobalApplication.getInstance().getPicasso(), placeHolderImageRes, iCommonAdapter);
    }

    public CommonAdapter(Context context, int layoutId, Glide picasso, int placeHolderImageRes, ICommonAdapter iCommonAdapter) {
        this.mContext = context;
        this.mPicasso = picasso;
        this.mLayoutId = layoutId;
        this.mPlaceHolder = placeHolderImageRes;
        this.mICommonAdapter = iCommonAdapter;
        this.mList = new ArrayList();
    }
    //设置listview里面的长度
    public void setCount(int count){
        this.count=count;
        notifyDataSetChanged();
    }
    //获得原来list的长度
    public int getOriginalCount(){
        return mList.size();
    }
    @Override
    public int getCount() {
        if(count==0){
            return  mList.size();
        }else{
            return count;
        }

    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, mLayoutId, null);
            mHolder = new ViewHolder(mContext, mPicasso, view, mPlaceHolder);
            view.setTag(mHolder);
        } else mHolder = (ViewHolder) view.getTag();
        mHolder.setPosition(i);
        mHolder.setCount(getCount());
        T item = mList.get(i);
        mICommonAdapter.getView(mHolder, item);
        return view;
    }

    public void setList(List<T> list) {
        if (list == null)
            return;
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<T> listData) {
        if (mList.size() == 0)
            mList = listData;
        else
            this.mList.addAll(listData);
        notifyDataSetChanged();
    }
    public void addObject(T object){
        mList.add(object);
        notifyDataSetChanged();
    }
    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void update() {
        notifyDataSetChanged();
    }

    public void remove(Object object) {
        mList.remove(object);
        notifyDataSetChanged();
    }

    public List getList() {
        return mList;
    }

    public void resetList() {
        mList=new ArrayList();
        notifyDataSetChanged();
    }


    public interface ICommonAdapter {
        void getView(ViewHolder viewHolder, Object item);

    }
}
