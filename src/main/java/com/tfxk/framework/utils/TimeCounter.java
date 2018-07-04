package com.tfxk.framework.utils;

public class TimeCounter extends Thread {
    private int mInitCount;
    private int sleepTime;
    private int count;
    private boolean isCounterFinished;
    private boolean stop;
    private boolean isCounterStarted;

    private TimeCounter() {

    }

    private TimeCounter(int count) {
        this(count, 1000);
    }

    private TimeCounter(int count, int sleepTime) {
        mCount = count;
        this.mInitCount = count;

        this.sleepTime = sleepTime;
    }

    private int mCount = 60;
    private OnTimeListener mListener;

    public static TimeCounter getTimeCounter(int count) {
        return new TimeCounter(count);
    }

    public void setCount(int count) {
        mCount = count;
    }

    /**
     * a time counter instance
     *
     * @param count     the count of sleep
     * @param sleepTime
     * @return
     */
    public static TimeCounter getTimeCounter(int count, int sleepTime) {
        return new TimeCounter(count, sleepTime);
    }

    public int getRemainCount() {
        return count;
    }

    public void reset() {
        stop = false;
        mCount = mInitCount;
        count=0;
    }

    public boolean isCountFinished() {
        return isCounterFinished;
    }

    public void stopCounter() {
        stop = true;

    }

    public void resumeCounter() {
        stop = false;
    }

    public boolean isTimerGoing() {
        return isCounterStarted;
    }

    public boolean isTimerStarted() {
        return !(0 == count);
    }

    public interface OnTimeListener {
        public void onTimesUp();

        public void onTimeChange(int countRemain);
    }

    public TimeCounter setOnTimeListener(OnTimeListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    public void run() {
        super.run();
        count = 0;
        isCounterStarted = true;
        isCounterFinished = false;

        if (mListener != null && !stop)
            mListener.onTimeChange(mCount - count);
        while (count < mCount) {
            try {
                Thread.sleep(sleepTime);//每次sleep 1s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if (mListener != null && !stop)
                mListener.onTimeChange(mCount - count);
//            System.out.println("==============TimeCounter:count:" + count);
        }
        if (mListener != null && !stop) {
            mListener.onTimesUp();
            isCounterFinished = true;
        }
    }
}