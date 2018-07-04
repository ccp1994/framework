package com.tfxk.framework.ui.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.tfxk.framework.R;
import com.tfxk.framework.ui.pulltorefresh.PullToRefreshBase;

/**
 * Created by chenchunpeng on 2017/6/7.
 */

public class SelfLoadingLayout extends LoadingLayout {

    static final int FLIP_ANIMATION_DURATION = 150;
    private static final long ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation, mResetRotateAnimation;
    private final RotateAnimation mLoadingRotateAnimation;
    private final int mLoadingDrawable;
    private Drawable mImageDrawable;


    public SelfLoadingLayout(Context context, final PullToRefreshBase.Mode mode, final PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        final int rotateAngle = mode == PullToRefreshBase.Mode.PULL_FROM_START ? -180 : 180;

        mRotateAnimation = new RotateAnimation(0, rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
        mRotateAnimation.setFillAfter(true);

        mResetRotateAnimation = new RotateAnimation(rotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mResetRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
        mResetRotateAnimation.setFillAfter(true);

        mLoadingRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mLoadingRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mLoadingRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mLoadingRotateAnimation.setRepeatCount(Animation.INFINITE);
        mLoadingRotateAnimation.setRepeatMode(Animation.RESTART);
        mLoadingDrawable = attrs.getResourceId(R.styleable.PullToRefresh_ptrProgressAnimationDrawable, getDefaultLoadingDrawableResId());
        mHeaderProgress.setIndeterminateDrawable(getResources().getDrawable(mLoadingDrawable));
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        if(imageDrawable!=null) {
            mImageDrawable=imageDrawable;
            makeImageRotate(imageDrawable);
        }
    }

    private void makeImageRotate(Drawable imageDrawable) {
        if (null != imageDrawable) {
            final int dHeight = imageDrawable.getIntrinsicHeight();
            final int dWidth = imageDrawable.getIntrinsicWidth();

            /**
             * We need to set the width/height of the ImageView so that it is
             * square with each side the size of the largest drawable dimension.
             * This is so that it doesn't clip when rotated.
             */
            ViewGroup.LayoutParams lp = mHeaderImage.getLayoutParams();
            lp.width = lp.height = Math.max(dHeight, dWidth);
            mHeaderImage.requestLayout();

            /**
             * We now rotate the Drawable so that is at the correct rotation,
             * and is centered.
             */
            mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate((lp.width - dWidth) / 2f, (lp.height - dHeight) / 2f);
            matrix.postRotate(getDrawableRotationAngle(), lp.width / 2f, lp.height / 2f);
            mHeaderImage.setImageMatrix(matrix);
        }
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        // NO-OP
    }

    @Override
    protected void pullToRefreshImpl() {
        // Only start reset Animation, we've previously show the rotate anim
        if (mRotateAnimation == mHeaderImage.getAnimation()) {
            mHeaderImage.startAnimation(mResetRotateAnimation);
        }
    }

    @Override
    protected void refreshingImpl() {
        mHeaderImage.clearAnimation();
        mHeaderImage.setVisibility(View.INVISIBLE);
        if (mAsProgressBar)
            mHeaderProgress.setVisibility(View.VISIBLE);
        else
            progressImageLoading(true);
    }

    @Override
    protected void releaseToRefreshImpl() {
        mHeaderImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mHeaderImage.startAnimation(mRotateAnimation);
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        if (mAsProgressBar)
            mHeaderProgress.setVisibility(View.GONE);
        else
            progressImageLoading(false);
        mHeaderImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_flip;
    }

    private void progressImageLoading(boolean show) {
        mHeaderProgress.setVisibility(show ? VISIBLE : GONE);
        if (show) {
            mHeaderImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            mHeaderProgressImage.startAnimation(mLoadingRotateAnimation);
        } else {
            mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
//            mHeaderProgressImage.clearAnimation();
        }
    }

    private float getDrawableRotationAngle() {
        float angle = 0f;
        switch (mMode) {
            case PULL_FROM_END:
                if (mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL) {
                    angle = 90f;
                } else {
                    angle = 180f;
                }
                break;

            case PULL_FROM_START:
                if (mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL) {
                    angle = 270f;
                }
                break;

            default:
                break;
        }

        return angle;
    }

    public int getDefaultLoadingDrawableResId() {
        return R.drawable.default_ptr_rotate;
    }
}
