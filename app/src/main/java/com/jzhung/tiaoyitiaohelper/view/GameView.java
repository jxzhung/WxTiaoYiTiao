package com.jzhung.tiaoyitiaohelper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jzhung.tiaoyitiaohelper.util.DensityUtil;

/**
 * 获取触摸点
 * Created by jzhung on 2017/12/31.
 */

public class GameView extends View {
    private PointF mBeginPoint;//记录起始点
    private PointF mEndPoint;//记录结束点
    private Paint mPaint;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 8));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mBeginPoint == null) {
                mBeginPoint = new PointF(event.getX(), event.getY());
            } else if (mEndPoint == null) {
                mEndPoint = new PointF(event.getX(), event.getY());
            }
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBeginPoint != null) {
            canvas.drawPoint(mBeginPoint.x, mBeginPoint.y, mPaint);
        }
        if (mEndPoint != null) {
            canvas.drawPoint(mEndPoint.x, mEndPoint.y, mPaint);
        }
    }

    public void clear() {
        mBeginPoint = null;
        mEndPoint = null;
        invalidate();
    }

    public float getLength() {
        float x2 = Math.abs(mBeginPoint.x - mEndPoint.x);
        float y2 = Math.abs(mBeginPoint.y - mEndPoint.y);
        float length = (float) Math.sqrt(x2 * x2 + y2 * y2);
        return length;
    }

    public boolean ready(){
        if(mBeginPoint != null && mEndPoint != null){
            return true;
        }
        return false;
    }
}
