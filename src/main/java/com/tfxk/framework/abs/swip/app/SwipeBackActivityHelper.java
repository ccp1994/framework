package com.tfxk.framework.abs.swip.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.tfxk.framework.R;
import com.tfxk.framework.abs.swip.SwipeBackLayout;
import com.tfxk.framework.abs.swip.ViewDragHelper;
import com.tfxk.framework.utils.Log;


/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;
    private SwipeBackActivity.OnActivityFinishedListener mActivityFinishedListener;

    public SwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
    }

    @SuppressWarnings("deprecation")
    public void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity).inflate(R.layout.swipeback_layout, null);
        mSwipeBackLayout.addFinishListener(mActivityFinishedListener);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (state == ViewDragHelper.STATE_SETTLING) {
//                    if (scrollPercent < 0.3)
//                        return;

                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
//                Utils.convertActivityToTranslucent(mActivity);
            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setOnActivityFinishedListener(SwipeBackActivity.OnActivityFinishedListener onActivityFinishedListener) {
        this.mActivityFinishedListener = onActivityFinishedListener;
    }
}
