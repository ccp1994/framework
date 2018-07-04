
package com.tfxk.framework.abs.swip.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tfxk.framework.abs.swip.SwipeBackLayout;
import com.tfxk.framework.utils.Log;


public abstract class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setHandleKitKatEnable(false);
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.setOnActivityFinishedListener(new OnActivityFinishedListener() {

            @Override
            public void onActivityFinished() {
                SwipeBackActivity.this.onActivityFinished();
            }
        });
        mHelper.onActivityCreate();
    }

    public void onActivityFinished() {
        Log.println("SwipeBack", "onActivityFinished:");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
//        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public static interface OnActivityFinishedListener {
        public void onActivityFinished();
    }
}
