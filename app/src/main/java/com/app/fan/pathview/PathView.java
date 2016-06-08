package com.app.fan.pathview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by fan on 2016/5/24.
 */
public class PathView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float pathX;
    private float pathY;
    private Boolean isActionUp = false;
    private OnFinishListener listener;

    public PathView(Context context) {
        super(context);
        init(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        Log.d("path", "init");
        //设置画笔颜色
        mPaint.setColor(ContextCompat.getColor(context, R.color.halfgray));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5.0f);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isActionUp) {
            isActionUp = false;
            mPaint.setStyle(Paint.Style.STROKE);
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mPath.close();
                mPaint.setStyle(Paint.Style.FILL);
                RectF r = new RectF();
                mPath.computeBounds(r, true);
                if (listener != null)
                    listener.onFinish(mPath,r);
                break;

        }
        postInvalidate();
        return true;
    }

    private void touchMove(MotionEvent event) {
        float disX = Math.abs(event.getX() - pathX);
        float disy = Math.abs(event.getY() - pathY);
        if (disX > 5 || disy > 5) {
            mPath.lineTo(event.getX(), event.getY());
            pathX = event.getX();
            pathY = event.getY();
            Log.d("path", "touchMove" + pathX + ":" + pathY);

        }
    }

    private void touchDown(MotionEvent event) {
        mPath.reset();
        pathX = event.getX();
        pathY = event.getY();
        mPath.moveTo(pathX, pathY);
        isActionUp = true;
        Log.d("path", "touchDown" + pathX + ":" + pathY);
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    public interface OnFinishListener {
        public void onFinish(Path p,RectF r);
    }

}
