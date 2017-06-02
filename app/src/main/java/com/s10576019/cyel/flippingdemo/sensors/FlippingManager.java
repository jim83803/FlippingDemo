package com.s10576019.cyel.flippingdemo.sensors;

import android.content.Context;
import android.os.Handler;

public class FlippingManager extends SimpleAccelerometerManager implements SimpleAccelerometerManager.AccelerometerListener {
    //接口
    public interface OnScreenFlipListener {
        int DIRECTION_UP = 1;
        int DIRECTION_DOWN = 2;

        void onScreenFlipped(int facingDirection);
    }

    //常數
    private static final double Z_EDGE = -3;

    //變數
    private boolean isWaitingForChecking = false;
    private int facingDirection = OnScreenFlipListener.DIRECTION_UP;
    private int newFacingDirection;

    //物件
    private OnScreenFlipListener onScreenFlipListener;

    //建構子
    public FlippingManager(Context context, int filterMode, double cutOffFrequency) {
        super(context, filterMode, cutOffFrequency);
    }

    //公開方法
    public void start(OnScreenFlipListener onScreenFlipListener) {
        this.onScreenFlipListener = onScreenFlipListener;
        super.start(this);
    }

    //實作接口
    @Override
    public void onNewValue(double x, double y, double z) {
        if (z >= Z_EDGE) {
            newFacingDirection = OnScreenFlipListener.DIRECTION_UP;
        } else {
            newFacingDirection = OnScreenFlipListener.DIRECTION_DOWN;
        }

        if (facingDirection != newFacingDirection && !isWaitingForChecking) {
            checkAgainDelayed(300);
        }
    }

    //內部方法
    private void checkAgainDelayed(int delayedMill) {
        isWaitingForChecking = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (newFacingDirection != facingDirection && onScreenFlipListener != null) {
                    facingDirection = newFacingDirection;
                    onScreenFlipListener.onScreenFlipped(newFacingDirection);
                }
                isWaitingForChecking = false;
            }
        }, delayedMill);
    }
}
