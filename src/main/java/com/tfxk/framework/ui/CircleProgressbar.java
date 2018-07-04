//package com.tfxk.framework.ui;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.tfxk.framework.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Chenchunpeng on 2015/9/7.
// */
//public class CircleProgressbar extends View {
//    private ArrayList<Integer> mOriginalColors;
//    private int mCircleInnerColor = getResources().getColor(R.color.app_color_yellow);
//    private int mProgressBarTextColor = getResources().getColor(R.color.main_theme_color);
//    private int mCircleRadius;
//    private float max = 100f;
//    private float progress = 0.0f;
//    private int mCenter;
//    private int mStrokeWidth;
//    private int mProgeressTextSize;
//    private int mProgressColor;
//    private int mStartAngle;
//    private int[] mColors;
//    private List<Float> mPercents;
//    private RectF circleRect;
//    private String mTotalCount = "";
//    private String mTotalMile = "";
//    private String mTotalFreque = "";
//
//    public CircleProgressbar(Context context) {
//        super(context);
//
//    }
//
//    public void setPercent(List<Float> percents) {
//        this.mPercents = percents;
//        mColors = new int[mPercents.size()];
//        genColors();
//        postInvalidate();
//    }
//
//    private void genColors() {
//        for (int i = 0; i < mPercents.size(); i++) {
//            mColors[i] = mOriginalColors.get(i);//Color.argb(Color.alpha(originalColor), Color.red(originalColor) >> 1, Color.green(originalColor) >> 1, Color.blue(originalColor) >> 1);
//        }
//    }
//
//    public CircleProgressbar(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }
//
//    public CircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }
//
//    private void init(Context context, AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressbar);
//        mStrokeWidth = (int) typedArray.getDimension(R.styleable.CircleProgressbar_circleStrokeWidth, 25);
//        mProgeressTextSize = (int) typedArray.getDimension(R.styleable.CircleProgressbar_progressTextSize, getResources().getDimension(R.dimen.circle_progress_bar_text_size));
//        mProgressBarTextColor = typedArray.getColor(R.styleable.CircleProgressbar_progressTextColor, getResources().getColor(R.color.white));
//        mCircleInnerColor = typedArray.getColor(R.styleable.CircleProgressbar_circleStrokeColor, getResources().getColor(R.color.app_color_yellow));
//        mProgressColor = typedArray.getColor(R.styleable.CircleProgressbar_progressColor, getResources().getColor(R.color.colorAccent));
//        mStartAngle = typedArray.getInt(R.styleable.CircleProgressbar_startAngle, 0);
//        this.progress = typedArray.getInt(R.styleable.CircleProgressbar_circleProgress, 0);
//
//        mPercents = new ArrayList<>();
//        mOriginalColors = new ArrayList();
//        Resources resource = context.getResources();
//        mOriginalColors.add(resource.getColor(R.color.circle_color1));
//        mOriginalColors.add(resource.getColor(R.color.circle_color2));
//        mOriginalColors.add(resource.getColor(R.color.circle_color3));
//        mOriginalColors.add(resource.getColor(R.color.circle_color4));
//        mOriginalColors.add(resource.getColor(R.color.circle_color5));
//        mOriginalColors.add(resource.getColor(R.color.circle_color6));
//        mOriginalColors.add(resource.getColor(R.color.circle_color7));
//        mOriginalColors.add(resource.getColor(R.color.circle_color8));
//        mOriginalColors.add(resource.getColor(R.color.circle_color9));
//        mOriginalColors.add(resource.getColor(R.color.circle_color10));
//        mOriginalColors.add(resource.getColor(R.color.circle_color11));
//
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        mCenter = getWidth() / 2;
//        mCircleRadius = (int) (mCenter - mStrokeWidth / 2f);
//        drawCircle(canvas);
//
//        drawProgressText(canvas);
//    }
//
//    private void drawProgressText(Canvas canvas) {
//        Paint progressTextPaint = new Paint();
//        progressTextPaint.setTextSize(mProgeressTextSize);
//        progressTextPaint.setColor(mProgressBarTextColor);
//
////        float progressPercent = getProgress() * 100 / getMax();
//        String text1 = mTotalCount;
//        String text2 = mTotalMile;
//        String text3 = mTotalFreque;
//        float width = progressTextPaint.measureText(text1);
//        float width2 = progressTextPaint.measureText(text2);
//        float width3 = progressTextPaint.measureText(text3);
//        Paint.FontMetrics fontMetrics = progressTextPaint.getFontMetrics();
//        float height = fontMetrics.descent - fontMetrics.ascent;
//        float centerHeight = mCenter + height / 2;
//
//        canvas.drawText(text1, mCenter - width / 2, centerHeight - height, progressTextPaint);
//        canvas.drawText(text2, mCenter - width2 / 2, centerHeight, progressTextPaint);
//        canvas.drawText(text3, mCenter - width3 / 2, centerHeight + height, progressTextPaint);
//    }
//
//    private void drawCircle(Canvas canvas) {
//
//        Paint circlePaintInner = new Paint();
//        circlePaintInner.setStyle(Paint.Style.STROKE);
//        circlePaintInner.setAntiAlias(true);
//        circlePaintInner.setStrokeWidth(mStrokeWidth);
//        circlePaintInner.setColor(Color.WHITE);
//        canvas.drawCircle(mCenter, mCenter, mCircleRadius, circlePaintInner);
//        int i = 0;
//        int offset;
//        mStartAngle = 0;
//        if (circleRect == null)
//            circleRect = new RectF(mCenter - mCircleRadius, mCenter - mCircleRadius, mCenter + mCircleRadius, mCenter + mCircleRadius);
//
//        for (float percent : mPercents) {
//            offset = (int) (percent * 360);
//            if (i == mPercents.size() - 1)
//                offset = 360 - mStartAngle;
//            System.out.println("=========percent:" + percent + ",startAngle:" + mStartAngle + ",offset:" + offset + "color:" + mColors[i]);
//            circlePaintInner.setColor(mColors[i]);
////            float progressPercent = getProgress() / getMax();
//            canvas.drawArc(circleRect, mStartAngle, offset, false, circlePaintInner);
//            mStartAngle = mStartAngle + offset;
//            i++;
//        }
//
//    }
//
//    public synchronized void setMax(float max) {
//        this.max = max;
//    }
//
//    public float getMax() {
//        return this.max;
//    }
//
//    public synchronized void setProgress(float progress) {
//        this.progress = progress;
//    }
//
//    public float getProgress() {
//        return this.progress;
//    }
//
//    public void setText(String totalCount, String totalMile, String totalFreque) {
//        mTotalCount = totalCount;
//        mTotalMile = totalMile;
//        mTotalFreque = totalFreque;
//        postInvalidate();
//    }
//
//    public int getColor(int curPosition) {
//        return mColors[curPosition];
//    }
//}
