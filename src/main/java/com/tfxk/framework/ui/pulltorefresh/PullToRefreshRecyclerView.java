package com.tfxk.framework.ui.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.tfxk.framework.R;

/**
 * Created by chenchunpeng on 2017/4/27.
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView;
        recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();

    }

    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<?> adapter = mRefreshableView.getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            int firstVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView.getChildAt(0));
            if (firstVisiblePosition == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView.getTop();
            }
        }

        return false;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        if (mRefreshableView == null) {
            return false;
        }
        try {
            int lastVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1));
            if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount() - 1) {
                return mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView.getBottom();
            }
        } catch (Exception e) {
        }
        return false;
    }

}