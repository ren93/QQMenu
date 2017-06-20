package com.renny.qqmenu;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by LuckyCrystal on 2017/6/17.
 */

public class QQMenu extends FrameLayout {

    private View childView1, childView2;
    private float childView1X, childView1Y, childView2X, childView2Y;
    private float centerX, centerY;
    private boolean hasClick = false;
    private int normal1, click1;
    private int normal2, click2;
    private OnMenuClickListener OnMenuClickListener;

    public QQMenu(@NonNull Context context) {
        super(context);
    }

    public QQMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QQMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        childView1 = new ImageView(getContext());
        childView2 = new ImageView(getContext());
        addView(childView1);
        addView(childView2);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        centerX = getHeight() / 5;
        centerY = getWidth() / 5;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childView1 = getChildAt(0);
        childView2 = getChildAt(1);
        childView1X = childView1.getX();
        childView1Y = childView1.getY();
        childView2X = childView2.getX();
        childView2Y = childView2.getX();
    }

    public boolean isHasClick() {
        return hasClick;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.OnMenuClickListener = onMenuClickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void setImgages(@DrawableRes int normal1, @DrawableRes int click1) {
        this.normal1 = normal1;
        this.click1 = click1;

    }

    public void setImgages(@DrawableRes int normal1, @DrawableRes int click1, @DrawableRes int normal2, @DrawableRes int click2) {
        this.normal1 = normal1;
        this.click1 = click1;
        this.normal2 = normal2;
        this.click2 = click2;
        refreshDrawable();
    }

    private void refreshDrawable() {
        if (hasClick) {
            if (click1 != 0)
                childView1.setBackgroundResource(click1);
            if (click2 != 0)
                childView2.setBackgroundResource(click2);
        } else {
            if (normal1 != 0)
                childView1.setBackgroundResource(normal1);
            if (normal2 != 0)
                childView2.setBackgroundResource(normal2);
        }

    }

    public void setHasClick(boolean hasClick) {
        this.hasClick = hasClick;
        refreshDrawable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                changeWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                restorePosition();
                if (isContain(this, event.getRawX(), event.getRawY())) {
                    setHasClick(!hasClick);
                    if (OnMenuClickListener != null) {
                        OnMenuClickListener.onItemClick(this);
                    }
                }
                return true;
            }
        }
        return true;
    }

    private boolean isContain(View view, float x, float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        return x >= point[0] && x <= (point[0] + view.getWidth()) && y >= point[1] && y <= (point[1] + view.getHeight());
    }

    private void changeWhenMove(float x, float y) {
        if (y + centerY < -12 * centerY) {
            y = -12 * centerY - centerY;
        } else if (y - centerY > 12 * centerY) {
            y = 12 * centerY + centerY;
        }
        if (x + centerX < -12 * centerX) {
            x = -12 * centerX - centerX;
        } else if (x - centerX > 12 * centerX) {
            x = 12 * centerX + centerX;
        }
        childView1.setX(childView1X + (x - centerX) / 23);
        childView1.setY(childView1Y + (y - centerY) / 23);
        if (childView2 != null) {
            childView2.setX(childView2X + (x - centerX) / 10);
            childView2.setY(childView2Y + (y - centerY) / 10);
        }

    }

    private void restorePosition() {
        childView1.setX(childView1X);
        childView1.setY(childView1Y);
        childView2.setX(childView2X);
        childView2.setY(childView2Y);
    }

    public interface OnMenuClickListener {
        void onItemClick(View view);
    }

}
