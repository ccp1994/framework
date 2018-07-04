package com.tfxk.framework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tfxk.framework.R;
import com.tfxk.framework.adapter.holder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenchunpeng on 2016/11/1.
 */
public class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final Context mContext;
    private final int mLayoutId;
    private final IRecyclerViewAdapter mICommonAdapter;
    private List items;
    protected View.OnClickListener mClickListener;
    private int mPlaceHolder = R.mipmap.ic_launcher;

    public CommonRecyclerViewAdapter(Context context, int layoutId, IRecyclerViewAdapter iCommonAdapter) {
        this.items = new ArrayList<>();
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mICommonAdapter = iCommonAdapter;
        if (iCommonAdapter instanceof IRecyclerViewAdpater2) {
            ((IRecyclerViewAdpater2) iCommonAdapter).attachToAdapter(this);
        }
    }

    public CommonRecyclerViewAdapter(Context context, int layoutId, int placeHolderResId, IRecyclerViewAdapter iCommonAdapter) {
        this.items = new ArrayList<>();
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mPlaceHolder = placeHolderResId;
        this.mICommonAdapter = iCommonAdapter;
        if (iCommonAdapter instanceof IRecyclerViewAdpater2) {
            ((IRecyclerViewAdpater2) iCommonAdapter).attachToAdapter(this);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new RecyclerViewHolder(mContext, mPlaceHolder, v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final T item = (T) items.get(position);
        holder.setPosition(position);
        mICommonAdapter.getView(holder, item);
    }

    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        return items.size();
    }

    public void add(Object item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Object item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }


    private static int setColorAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00ffffff);
    }

    public void setList(List list) {
        this.items = list;
        notifyDataSetChanged();
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return this.mClickListener;
    }

    public void update() {
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public void addList(List list) {
        if (list != null) {
            items.addAll(list);
            notifyDataSetChanged();
        }
    }

    public ArrayList getList() {
        return (ArrayList) items;
    }


    public interface IRecyclerViewAdapter {
        void getView(RecyclerViewHolder viewHolder, Object item);
    }

    public interface IRecyclerViewAdpater2 extends IRecyclerViewAdapter {
        void attachToAdapter(RecyclerView.Adapter adapter);
    }

}
