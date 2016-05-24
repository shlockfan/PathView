package com.app.fan.pathview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by fan on 2016/5/24.
 */
public class PathView extends RelativeLayout {
    private Paint mPaint;

    public PathView(Context context) {
        super(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1f);

    }
}
