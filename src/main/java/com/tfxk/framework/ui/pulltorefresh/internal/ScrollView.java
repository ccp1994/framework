package com.tfxk.framework.ui.pulltorefresh.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Chenchunpeng on 2015/8/20.
 */
public class ScrollView extends android.widget.ScrollView {
    private OnScrollListener mScrollListener;
    private OnFooterListener mOnFooterListener;
    private OnHeaderListener mOnHeaderListener;

    public ScrollView(Context context) {
        super(context);

    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null)
            mScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        if (mOnFooterListener != null) {
            View view = (View) getChildAt(0);
            int d = view.getBottom();
            d -= (getHeight() + getScrollY());
            if (d == 0) {
                //you are at the end of the list in scrollview
                //do what you wanna do here
                mOnFooterListener.onFooterListener();
            }

        }
//        Printer.println("scrollY:"+getScrollY()+",t:"+t+",oldt:"+oldt);
        if (getScrollY() == 0 && t ==0) {
            if (mOnHeaderListener != null)
                mOnHeaderListener.onHeaderRefresh();
        }

    }

    public void setOnFooterListener(OnFooterListener onFooterListener) {
        this.mOnFooterListener = onFooterListener;
    }

    public void setOnHeaderListener(OnHeaderListener onHeaderListener) {
        this.mOnHeaderListener = onHeaderListener;
    }

    public interface OnFooterListener {
        public void onFooterListener();
    }

    public interface OnHeaderListener {
        /**
         */
        public void onHeaderRefresh();
    }

    public interface OnScrollListener {
        /**
         * WindowManager windowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
         * Display display = windowManager.getDefaultDisplay();
         * DisplayMetrics metrics=new DisplayMetrics();
         * display.getMetrics(metrics);
         * int height=metrics.heightPixels;
         * float alphaA=(height-y)*scale/height;//scale=1.5
         *
         * @param scrollView
         * @param x
         * @param y
         * @param oldX
         * @param oldY
         */
        public void onScrollChanged(ScrollView scrollView, int x, int y, int oldX, int oldY);
    }
}
