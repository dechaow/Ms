package com.example.wdc.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wdc on 2016/7/28.
 */
public class SplashView extends View {

    private Point sStartPoint;
    private Point sEndPoint;
    private Point sAssistPoint1;
    private Point sAssistPoint2;

    private Path mPath;
    private Paint mPaint;

    public SplashView(Context context) {
        super(context);
    }

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        //控件右上角位置
        sStartPoint = new Point(getRight(),getTop());
        //左下角
        sEndPoint = new Point(getLeft(),getBottom());
        //
        sAssistPoint1 = new Point(getLeft()-200,getTop() + getHeight()/4);

        sAssistPoint2 = new Point(getRight()+200,getTop() + getHeight()*3/4);

        mPath = new Path();

        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
        //画笔白色
        mPaint.setColor(Color.WHITE);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaint.setStrokeWidth(15);
        //重置路径
        mPath.reset();
        //画起始位置
        mPath.moveTo(sStartPoint.x - 8,sStartPoint.y + 4);
        //调用3阶的barier
        mPath.cubicTo(sAssistPoint1.x,sAssistPoint1.y,sAssistPoint2.x,sAssistPoint2.y,sEndPoint.x,sEndPoint.y);
        //画路径
        canvas.drawPath(mPath,mPaint);

    }

    /**
     *  MeasureSpec:
     *       Measure specification mode(测量规范模式)
     *      UNSPECFIED  父类没有强加约束，子类可以是任何大小
     *      EXACITY     父类已经确定大小，子类不能超过这个父类的大小
     *      AT_MOST     这个子类可以想和他要的一样大
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取mode
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        //从所提供的mode中获取大小
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int HeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int HeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(specSize,HeightSpecSize);

        init();
    }

}
