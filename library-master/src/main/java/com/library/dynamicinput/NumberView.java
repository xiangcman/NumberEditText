package com.library.dynamicinput;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by xiangcheng on 16/8/29.
 * 右下角显示的文本情况的view
 */
public class NumberView extends View {

    private Paint mLeftPaint;
    private Paint mRightPaint;
    private Paint mLinePaint;
    private static final int mTextColor = Color.parseColor("#666666");
    private float mTextSize;
    private int mLeftNum = 10;
    private int mRightNum;
    private float mRightNumWidth;
    private float mleftNumWidth;
    private float mLineOffset;
    private Rect rect;

    public NumberView(Context context, int right) {
        super(context);
        this.mRightNum = right;
        initArgus();
    }

    private void initArgus() {
        mTextSize = Utils.sp2px(getContext(), 13);
        mLineOffset = Utils.dp2px(getContext(), 3);
        mLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftPaint.setColor(mTextColor);
        mLeftPaint.setTextSize(mTextSize);
        mLeftPaint.setStrokeWidth(6);
        mRightPaint = mLeftPaint;
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mTextColor);
        mLinePaint.setStrokeWidth(3);
        mRightNumWidth = mRightPaint.measureText(mRightNum + "");
        rect = new Rect();
    }

    private int getRightNumHeight() {
        mRightPaint.getTextBounds(mRightNum + "", 0, String.valueOf(mRightNum).length(), rect);
        return rect.height();
    }

    private int getLeftNumHeight() {
        mRightPaint.getTextBounds(mLeftNum + "", 0, String.valueOf(mLeftNum).length(), rect);
        return rect.height();
    }

    private int getNumHeight() {
        return Math.max(getRightNumHeight(), getLeftNumHeight());
    }

    public void setLeftNum(int left) {
        this.mLeftNum = left;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mleftNumWidth = mLeftPaint.measureText(mLeftNum + "");
        canvas.drawText(mLeftNum + "", mRightNumWidth - mleftNumWidth, getLeftNumHeight(), mLeftPaint);
        canvas.drawLine(mRightNumWidth + mLineOffset, getNumHeight(), mRightNumWidth + mLineOffset + 7.346f, 0, mLinePaint);
        canvas.drawText(mRightNum + "", mRightNumWidth + mLineOffset * 2 + 7.346f, getRightNumHeight(), mRightPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (mRightNumWidth * 2 + mLineOffset * 2 + 7.346f), getNumHeight());
    }

    public int getMeWidth() {
        return (int) (mRightNumWidth * 2 + mLineOffset * 2 + 7.346f);
    }

    public int getMeHeight() {
        return getNumHeight();
    }
}
