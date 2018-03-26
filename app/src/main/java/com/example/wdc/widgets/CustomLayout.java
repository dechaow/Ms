package com.example.wdc.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dechao on 2018/3/22.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class CustomLayout extends ViewGroup{

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * AT_MOST     有上限
     * EXACTLY     固定
     * UPSPECIFIED 无上限
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //循环遍历计算子View的大小

        //获取当前View的宽高模式和尺寸
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize= MeasureSpec.getSize(heightMeasureSpec);

        int childHeight = 0;
        int childWidth = 0;

        //计算子View的尺寸
        for (int i = 0; i < getChildCount(); i++) {

            View childView = getChildAt(i);

            int childWidthMeasureSpec;
            int childHeightMeasureSpec;

            LayoutParams childParams = childView.getLayoutParams();

            widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

            heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSize= MeasureSpec.getSize(heightMeasureSpec);

            //计算子View的MeasureSpec，分三种情况
            switch (childParams.width){
                case LayoutParams.MATCH_PARENT:
                    //根据父View的MeasureSpec来处理
                    if (widthMeasureMode == MeasureSpec.EXACTLY || widthMeasureMode == MeasureSpec.AT_MOST){
                        //这里怎么根据子View的大小给呢？又不确定
                        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(10,MeasureSpec.EXACTLY);
                        widthMeasureSize -= 10;
                        childWidth += 10;
                    }else{
                        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                        widthMeasureSize -= 0;
                    }
                    break;
                case LayoutParams.WRAP_CONTENT:
                    if (widthMeasureMode == MeasureSpec.EXACTLY || widthMeasureMode == MeasureSpec.AT_MOST){
                        //这里怎么根据子View的大小给呢？又不确定
                        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(10,MeasureSpec.AT_MOST);
                        widthMeasureSize -= 10;
                        childWidth += 10;
                    }else{
                        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                        widthMeasureSize -= 0;
                    }
                    break;
                default:
                    //固定大小
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childParams.width,MeasureSpec.EXACTLY);
                    widthMeasureSize -= childParams.width;
                    childWidth += childParams.width;
                    break;
            }

            switch (childParams.height){
                case LayoutParams.MATCH_PARENT:
                    if (heightMeasureMode == MeasureSpec.EXACTLY || heightMeasureMode == MeasureSpec.AT_MOST){
                        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(10,MeasureSpec.EXACTLY);
                        heightMeasureSize -= 10;
                        childHeight += 10;
                    }else{
                        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                        heightMeasureSize -= 0;
                    }
                    break;
                case LayoutParams.WRAP_CONTENT:
                    if (heightMeasureMode == MeasureSpec.EXACTLY || heightMeasureMode == MeasureSpec.AT_MOST){
                        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(10,MeasureSpec.AT_MOST);
                        heightMeasureSize -= 10;
                        childHeight += 10;
                    }else{
                        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                        heightMeasureSize -= 0;
                    }
                    break;
                default:
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childParams.height,MeasureSpec.EXACTLY);
                    heightMeasureSize -= childParams.height;
                    childHeight += childParams.height;
                    break;
            }
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);
        }

        //计算当前view的size
        switch (widthMeasureMode){
            case MeasureSpec.AT_MOST:
                //warp 有上限
//                widthMeasureSize = childWidth;
                System.out.println("CustomLayout.onMeasure  widthSpec AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                //match 固定值 所以直接按原来的尺寸就好
                System.out.println("CustomLayout.onMeasure  widthSpec EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                //无上限
                System.out.println("CustomLayout.onMeasure  widthSpec UNSPECIFIED");
                widthMeasureSize = 0;
                break;
        }

        switch (heightMeasureMode){
            case MeasureSpec.AT_MOST:
                System.out.println("CustomLayout.onMeasure  heightSpec AT_MOST");
                heightMeasureSize = childHeight;
                break;
            case MeasureSpec.EXACTLY:
                System.out.println("CustomLayout.onMeasure  heightSpec EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                System.out.println("CustomLayout.onMeasure  heightSpec UNSPECIFIED");
                heightMeasureSize = 0;
                break;
        }
        System.out.println("CustomLayout.onMeasure  " + widthMeasureSize + "   " + heightMeasureSize);
        setMeasuredDimension(widthMeasureSize,heightMeasureSize);


    }

    //为每个子View进行布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
        }
    }
}
