package com.tfxk.framework.abs.swip.app;


import com.tfxk.framework.abs.swip.SwipeBackLayout;

/**
 * @author Yrom
 */
public  interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
