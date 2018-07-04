package com.tfxk.framework.adapter.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewHolder {
    private final View mView;
    private final Glide mPicasso;
    private final Context mContext;
    private int position;
    private int mCount;
    private int mPlaceHoldImageRes;


    public ViewHolder(Context context, Glide picasso, View view) {
        this.mView = view;
        this.mContext = context;
        this.mPicasso = picasso;
    }

    public ViewHolder(Context context, Glide picasso, View view, int placeHolderImageRes) {
        this.mView = view;
        this.mContext = context;
        this.mPicasso = picasso;
        this.mPlaceHoldImageRes = placeHolderImageRes;
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

    public int getPosition() {
        return this.position;
    }

    public void setText(int textResId, String text) {
        TextView textView = (TextView) mView.findViewById(textResId);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }
    public void setText(int textResId,SpannableStringBuilder spannableStringBuilder){
        TextView textView = (TextView) mView.findViewById(textResId);
        textView.setVisibility(View.VISIBLE);
        textView.setText(spannableStringBuilder);
    }
    public void setText(int textResId, int textStringId) {
        setText(textResId, mContext.getResources().getString(textStringId));
    }

    public void setImageResResizeDimen(int imageResId, String imageUrl, int resizeWidthDimen, int resizeHeightDimen) {
        mPicasso.with(mContext).load(imageUrl).centerCrop().placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));
//        mPicasso.load(imageUrl)
//                .resizeDimen(resizeWidthDimen, resizeHeightDimen)
//                .centerCrop()
//                .placeholder(mPlaceHoldImageRes)
//                .into((ImageView) mView.findViewById(imageResId));
    }

    public void setImageRes(int imageResId, String imageUrl, int resizeWidth, int resizeHeight) {
//        mPicasso.load(imageUrl)
//                .resize(resizeWidth, resizeHeight)
//                .centerCrop()
//                .placeholder(mPlaceHoldImageRes)
//                .into((ImageView) mView.findViewById(imageResId));
        mPicasso.with(mContext).load(imageUrl).centerCrop().placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));

    }
    public void setImageRes(int imageResId, String imageUrl,boolean isCrop){
        if(isCrop){
            setImageRes(imageResId,imageUrl);
        }else{
            mPicasso.with(mContext).load(imageUrl).placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));
        }
    }

    public void setImageRes(int imageResId, String imageUrl) {
//        mPicasso.load(imageUrl)
//                .placeholder(mPlaceHoldImageRes)
//                .into((ImageView) mView.findViewById(imageResId));
        mPicasso.with(mContext).load(imageUrl).centerCrop().placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));

    }

    public void setImageRes(int imageResId, int imageDrawableId, int resizeWidth, int resizeHeight) {
//        mPicasso.load(imageDrawableId)
//                .resize(resizeWidth, resizeHeight)
//                .centerCrop()
//                .placeholder(mPlaceHoldImageRes)
//                .into((ImageView) mView.findViewById(imageResId));
        mPicasso.with(mContext).load(imageDrawableId).centerCrop().placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));

    }

    public void setImageRes(int imageResId, int imageDrawableId,boolean isCrop) {
//        mPicasso.load(imageDrawableId)
//                .placeholder(mPlaceHoldImageRes)
//                .into((ImageView) mView.findViewById(imageResId));
        if (isCrop){
            mPicasso.with(mContext).load(imageDrawableId).centerCrop().placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));
        }
        mPicasso.with(mContext).load(imageDrawableId).placeholder(mPlaceHoldImageRes).into((ImageView) mView.findViewById(imageResId));

    }

    public void setClickListener(int id, View.OnClickListener onClickListener) {
        mView.findViewById(id).setOnClickListener(onClickListener);
    }

    public void setSelected(int id, boolean b) {
        mView.findViewById(id).setSelected(b);
    }

    public View getView(int id) {
        return mView.findViewById(id);
    }

    public void setVisibility(int widgetId, boolean show) {
        mView.findViewById(widgetId).setVisibility(show?View.VISIBLE:View.GONE);
    }
}
