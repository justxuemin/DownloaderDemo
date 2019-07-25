package com.coocoo.downloaderdemo.downloader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class DownloadProgressBar extends View {

    private final Paint mPaint;
    private int mRoundWidth = 10;
    private int mCircleColor = Color.parseColor("#D2E4E8");
    private int mNumberColor = Color.parseColor("#E6D8E4");
    private float mNumberSize = 40;
    private int mProgress;
    private int mProgressColor = Color.parseColor("#8691A5");

    public DownloadProgressBar(Context context) {
        this(context, null);
    }

    public DownloadProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int circleRadius = center - mRoundWidth / 2;

        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setAntiAlias(true);

        canvas.drawCircle(center, center, circleRadius, mPaint);

        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF oval = new RectF(center - circleRadius, center - circleRadius, center
                + circleRadius, center + circleRadius);
        if (mProgress != 0) {
            canvas.drawArc(oval, 0, 360 * mProgress / 100, true, mPaint);
        }


        mPaint.setStrokeWidth(0);
        mPaint.setColor(mNumberColor);
        mPaint.setTextSize(mNumberSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float textWidth = mPaint.measureText(mProgress + "%");
        canvas.drawText(mProgress + "%", center - textWidth / 2, center + textWidth / 2, mPaint);

    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }
}
