package com.tfxk.framework.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tfxk.framework.R;

/**
 * Created by chenchunpeng on 2017/4/26.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private RequestManager mPicasso;
    private Context mContext;
    private int position;
    private int mCount;
    private int mPlaceHoldImageRes = R.mipmap.ic_launcher;
    public RecyclerViewHolder(Context context,View itemView) {
        super(itemView);
        this.mView = itemView;
        this.mContext = context;
        this.mPicasso = Glide.with(mContext);
    }

    public RecyclerViewHolder(Context context, int placeHolder,View itemView) {
        super(itemView);
        this.mView = itemView;
        this.mContext = context;
        this.mPlaceHoldImageRes=placeHolder;
        this.mPicasso = Glide.with(mContext);
    }

    public void setPlaceHoldImageRes(int placeHoldImageRes) {
        this.mPlaceHoldImageRes = placeHoldImageRes;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public View getRootView() {
        return this.mView;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCurPosition() {
        return position;
    }

    public void setText(int textResId, String text) {
        TextView textView = (TextView) mView.findViewById(textResId);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    public void setText(int textResId, int textStringId) {
        setText(textResId, mContext.getResources().getString(textStringId));
    }

    public void setImageResResizeDimen(int imageResId, String imageUrl, int resizeWidthDimen, int resizeHeightDimen) {
        if (!TextUtils.isEmpty(imageUrl))
            mPicasso.load(imageUrl)
                    .centerCrop()
                    .placeholder(mPlaceHoldImageRes)
                    .into((ImageView) mView.findViewById(imageResId));
    }

    public void setImageRes(int imageResId, String imageUrl, int resizeWidth, int resizeHeight) {
        if (!TextUtils.isEmpty(imageUrl))
            mPicasso.load(imageUrl)
                    .centerCrop()
                    .placeholder(mPlaceHoldImageRes)
                    .into((ImageView) mView.findViewById(imageResId));
    }

    public void setImageRes(int imageResId, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            mPicasso.load(imageUrl)
                    .placeholder(mPlaceHoldImageRes)
                    .into((ImageView) mView.findViewById(imageResId));
    }

    public void setImageRes(int imageResId, int imageDrawableId, int resizeWidth, int resizeHeight) {
        mPicasso.load(imageDrawableId)
                .centerCrop()
                .placeholder(mPlaceHoldImageRes)
                .into((ImageView) mView.findViewById(imageResId));
    }

    public void setImageRes(int imageResId, int imageDrawableId) {
        mPicasso.load(imageDrawableId)
                .placeholder(mPlaceHoldImageRes)
                .into((ImageView) mView.findViewById(imageResId));
    }

    public void setClickListener(int id, View.OnClickListener onClickListener) {
        mView.findViewById(id).setOnClickListener(onClickListener);
    }

    public void setClickListener(int id, View.OnClickListener onClickListener, Object extra) {
        View view = mView.findViewById(id);
        view.setTag(R.id.tag_extra, extra);
        view.setOnClickListener(onClickListener);
    }

    public void setSelected(int id, boolean b) {
        mView.findViewById(id).setSelected(b);
    }

    public View getView(int id) {
        return mView.findViewById(id);
    }

    public void setVisibility(int id, int visibility) {
        mView.findViewById(id).setVisibility(visibility);
    }
}
