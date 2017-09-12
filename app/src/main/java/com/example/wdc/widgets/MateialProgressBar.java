package com.example.wdc.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.wdc.ms.R;


/**
 * Created by wdc on 2016/7/13.
 */
public class MateialProgressBar extends View {

    private static final String TAG = MateialProgressBar.class.getSimpleName();

    private Paint arcPaint;
    private RectF arcRectF;
    private float startAngle = -45f;
    private float sweepAngle = -19f;
    private float incrementAngle = 0;
    private int size;
    private static float DEFULT_MAX_ANGLE = -305f;
    private static float DEFULT_MIN_ANGLE = -19f;
    private int DEFULT_DUARATION = 660;
    private int borderWidth;
    private final static int DEFULT_ARC_COLOR = Color.YELLOW;
    private int arcColor = DEFULT_ARC_COLOR;
    private AnimatorSet animatorSet;


    public MateialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs,defStyleAttr);
        init(context);
    }

    public MateialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs,0);
        init(context);
    }

    public MateialProgressBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        arcPaint = new Paint();
        arcPaint.setColor(arcColor);
        //设置画笔的空心宽度
        arcPaint.setStrokeWidth(borderWidth);
        //抗锯齿
        arcPaint.setAntiAlias(true);
        //设置空心
        arcPaint.setStyle(Paint.Style.STROKE);
        arcRectF = new RectF();
        animatorSet = new AnimatorSet();
    }

    private void initAttr(Context context,AttributeSet attrs,int defstyleAttr){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MateialProgressBar,defstyleAttr,0);
        arcColor = ta.getColor(R.styleable.MateialProgressBar_arc_color,DEFULT_ARC_COLOR);
        borderWidth = ta.getDimensionPixelSize(R.styleable.MateialProgressBar_pro_border_width,50);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一段弧线  指定弧线的外轮廓矩形  起始角度  扫过的角度  为true在绘制圆时将圆心包括在内  画笔属性
        canvas.drawArc(arcRectF,startAngle + incrementAngle,sweepAngle,false,arcPaint);
        if (animatorSet == null || !animatorSet.isRunning()){
            startAnimation();
        }

    }

    /**
     * 在view大小发生变化时调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置宽高相等
        size = ( w < h ) ? w : h;
        stupBound();
    }

    private void stupBound(){
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        //将矩形的坐标设为指定的值
        arcRectF.set(paddingLeft + borderWidth,paddingTop + borderWidth,size - paddingLeft - borderWidth, size - paddingTop - borderWidth);
    }

    private void startAnimation(){



        if (animatorSet != null || animatorSet.isRunning()){
            animatorSet.cancel();
        }
        animatorSet = new AnimatorSet();
        AnimatorSet set = circuAnimator();
        animatorSet.play(set);

        animatorSet.addListener(new Animator.AnimatorListener() {
            private boolean  isCancel = false ;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if ( !isCancel){
                    startAnimation();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isCancel = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private AnimatorSet circuAnimator(){
        //小圆到大圆
        ValueAnimator holdAnimator1 = ValueAnimator.ofFloat(incrementAngle + DEFULT_MIN_ANGLE,incrementAngle + 115f);
        holdAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                incrementAngle = (float) animation.getAnimatedValue();
            }
        });
        holdAnimator1.setDuration(DEFULT_DUARATION);
        holdAnimator1.setInterpolator(new LinearInterpolator());

        ValueAnimator expandAnimator = ValueAnimator.ofFloat(DEFULT_MIN_ANGLE,DEFULT_MAX_ANGLE);
        expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                incrementAngle -= sweepAngle;
                invalidate();
            }
        });
        expandAnimator.setDuration(DEFULT_DUARATION);
        expandAnimator.setInterpolator(new DecelerateInterpolator(2));

        //大圆到小圆
        ValueAnimator holdAnimator = ValueAnimator.ofFloat(startAngle,startAngle + 115f);
        holdAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (float) animation.getAnimatedValue();
            }
        });

        holdAnimator.setDuration(DEFULT_DUARATION);
        holdAnimator.setInterpolator(new LinearInterpolator());

        ValueAnimator norrowAnimator = ValueAnimator.ofFloat(DEFULT_MAX_ANGLE,DEFULT_MIN_ANGLE);
        norrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        norrowAnimator.setDuration(DEFULT_DUARATION);
        norrowAnimator.setInterpolator(new DecelerateInterpolator(2));

        AnimatorSet set = new AnimatorSet();
        set.play(holdAnimator1).with(expandAnimator);
        set.play(holdAnimator).with(norrowAnimator).after(holdAnimator1);

        return set;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void setBorderWidth(int width){
        this.borderWidth = width;
    }

    public void setArcColor(int arcColor){
        this.arcColor = arcColor;
    }

}
