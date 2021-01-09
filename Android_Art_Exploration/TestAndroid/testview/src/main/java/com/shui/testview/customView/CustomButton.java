package com.shui.testview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cyt.sdk_base.utils.LogUtil;

/**
 * Created by shui on 2020/10/11
 */
class CustomButton extends android.support.v7.widget.AppCompatButton {

    private static final String TAG = "CustomButton";

    private int mLastX;
    private int mLastY;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                LogUtil.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
        }
        return super.onTouchEvent(event);
    }
}
