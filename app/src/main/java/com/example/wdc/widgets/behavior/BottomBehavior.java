package com.example.wdc.widgets.behavior;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by dechao on 2018/2/11.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class BottomBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private int mChildHeight;

    private ViewOffsetHelper mViewOffsetHelper;

    public BottomBehavior(){
    }

    public BottomBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    /**
     *
     * @param parent 父布局，这里父布局必须是CoordinatorLayout
     * @param child 使用此Behavior的view
     * @param dependency 和此view有依赖关系的view，按照xml布局位置，在这child上方的view的就是dependency,如果上方没有就找最后一个
     * @return 返回true代表你使用其和此dependency依赖，则dependency的布局有变化的时候就会调用onDependentViewChanged
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
//        AppBarLayout.ScrollingViewBehavior
//        System.out.println("BottomBehavior.layoutDependsOn  parent = " + parent + "  child = " + child + "   dependency = " + dependency);
        mChildHeight = child.getHeight();
        System.out.println("BottomBehavior.layoutDependsOn   " + child.getHeight());
        return true;
    }

    /**
     * 如果layoutDependsOn返回true才会执行此方法，dependency的布局有变化的时候就会调用onDependentViewChanged
     * onDependentViewChanged在普通布局中不会被调用
     * @param parent CoordinatorLayout
     * @param child 拥有此Behavior属性的view
     * @param dependency 可以理解成为一个锚点，child以dependency为锚点
     * @return 如果Behavior需要改变此子View的大小or位置，则返回true
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
//        System.out.println("BottomBehavior.onDependentViewChanged  parent = " + parent + "  child = " + child + "   dependency = " + dependency);
        System.out.println("BottomBehavior.onDependentViewChanged    dependency.getTop " + dependency.getTop());
        return super.onDependentViewChanged(parent,child,dependency);
//        return true;
    }

    /**
     * dependency被移除的时候调用的方法了
     * @param parent
     * @param child
     * @param dependency
     */
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, V child, View dependency) {
        System.out.println("BottomBehavior.onDependentViewRemoved  parent = " + parent + "  child = " + child + "   dependency = " + dependency);
        super.onDependentViewRemoved(parent, child, dependency);
    }

    /**
     * 绘制child的时候调用
     * @param parent 同上
     * @param child 同上
     * @param layoutDirection  CoordinatorLayout布局解析的方法 0=ltr 1=rtl，因为有些国家是从左向右显示的
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        System.out.println("BottomBehavior.onLayoutChild  parent = " + parent + "  child = " + child + "   layoutDirection = " + layoutDirection);
        if (mViewOffsetHelper == null){
            mViewOffsetHelper = new ViewOffsetHelper(child);
        }
        mViewOffsetHelper.onViewLayout();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * 开始滑动的时候调用一次，手松开的时候调用一次
     * 返回true代表获取滑动事件，其他的scroll事件就会被触发
     * @param coordinatorLayout
     * @param child 使用此Behavior的View
     * @param directTargetChild 是target或是target的parent
     * @param target 处理滑动事件的view
     * @param axes  垂直滚动2 横向滚动1
     * @param type  滑动类型touch 0手指按下 1手指松开
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        System.out.println("BottomBehavior.onStartNestedScroll  coordinatorLayout = " + coordinatorLayout + "  child = " + child + "   directTargetChild = " + directTargetChild + "    target = " + target + "   axes = " + axes + "    type = " + type);
        //super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
        return true;
    }

    /**
     * dependencyView接受到触摸事件的时候调用一次
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     */
    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        System.out.println("BottomBehavior.onNestedScrollAccepted  coordinatorLayout = " + coordinatorLayout + "  child = " + child + "   directTargetChild = " + directTargetChild + "    target = " + target + "   axes = " + axes + "    type = " + type);
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    /**
     * 手指松开时，调用一次，滑动停止时调用一次
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param type
     */
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        System.out.println("BottomBehavior.onStopNestedScroll  coordinatorLayout = " + coordinatorLayout + "  child = " + child + "    target = " + target + "    type = " + type);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    /**
     * 页面滑动的时候调用
     * @param coordinatorLayout 同上
     * @param child 同上
     * @param target 同上
     * @param dxConsumed 水平滑动的实时距离
     * @param dyConsumed 竖直滑动的实时距离
     * @param dxUnconsumed view处于滚动状态，但是并不是由target消耗的滚动时候触发，这个是水平滚动的实时距离
     * @param dyUnconsumed view处于滚动状态，但是并不是由target消耗的滚动时候触发，这个是竖直滚动的实时距离
     * @param type 同上
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        System.out.println("BottomBehavior.onNestedScroll  coordinatorLayout = " + coordinatorLayout + "  child = " + child + "    target = " + target + "    type = " + type + "     dxConsumed = " + dxConsumed + "   dyConsumed = " + dyConsumed + "    dxUnconsumed = " + dxUnconsumed + "   dyUnconsumed = " + dyUnconsumed + "    type = " + type);
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    /**
     * 在target已消耗任何滚动距离之前，正在进行嵌套滚动更新时调用
     * @param coordinatorLayout 同上
     * @param child 同上
     * @param target 同上
     * @param dx 同上
     * @param dy 同上
     * @param consumed consumed[0]为消耗的x距离，consumed[1]为消耗的y距离
     * @param type 同上
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //dy对其没有影响
        //但是这里要处理，如果view已经移出，就需要停止这个事件,所以加判断 如果offset小于mChildHeight 则可以继续滑动，但是如果此时如果向上滑也会进入
        //向下滑并且偏移量没有达到view的高度
        int offsetHeight = mViewOffsetHelper.getOffsetHeight();

        if(dy > 0 &&  offsetHeight <= mChildHeight ){
            mViewOffsetHelper.setTopAndBottomOffset(2);
            mViewOffsetHelper.onViewLayout();
        }
        else if(dy < 0 && offsetHeight > 0){
            mViewOffsetHelper.setTopAndBottomOffset(-2);
            mViewOffsetHelper.onViewLayout();
        }

    }

    /**
     * 滑动时手指松开如果还继续滑动的时候调用一次
     * @param coordinatorLayout 同上
     * @param child 同上
     * @param target 同上
     * @param velocityX 水平加速度
     * @param velocityY 竖直加速度
     * @param consumed 同上
     * @return false不拦截 true则不会有惯性滑动，需要自己处理
     */
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        System.out.println("BottomBehavior.onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//        return true;
    }

    /**
     * 和onNestedFling一样，只不过会优先于onNestedFling方法调用
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        System.out.println("BottomBehavior.onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//        return true;
    }

    /**
     * 控制view显示状态的辅助类
     */
    class ViewOffsetHelper {

        //所监听的view
        private final View mView;

        private int mLayoutTop;
        private int mLayoutLeft;
        private int mOffsetTop;
        private int mOffsetLeft;

        private int mOffsetHeight;

        public ViewOffsetHelper(View view) {
            mView = view;
            System.out.println("ViewOffsetHelper.ViewOffsetHelper   " + mView.getTop());
        }

        public void onViewLayout() {
            // Now grab the intended top
            mLayoutTop = mView.getTop();
            mLayoutLeft = mView.getLeft();

            // And offset it as needed
            updateOffsets();
        }

        private void updateOffsets() {
//            System.out.println("ViewOffsetHelper.updateOffsets   " + (mOffsetTop - (mView.getTop() - mLayoutTop))  + "     " + mView.getTop() + "   " + mView.getHeight() + "    " + mOffsetTop + "    " + mView.getVisibility() + "   " + mView.getScrollY());
            mOffsetHeight+=mOffsetTop;
            System.out.println("ViewOffsetHelper.updateOffsets   " + mOffsetHeight);
            ViewCompat.offsetTopAndBottom(mView, mOffsetTop - (mView.getTop() - mLayoutTop));
            ViewCompat.offsetLeftAndRight(mView, mOffsetLeft - (mView.getLeft() - mLayoutLeft));
        }

        /**
         * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view.
         *
         * @param offset the offset in px.
         * @return true if the offset has changed
         */
        public boolean setTopAndBottomOffset(int offset) {
            if (mOffsetTop != offset) {
                mOffsetTop = offset;
                updateOffsets();
                return true;
            }
            return false;
        }

        /**
         * Set the left and right offset for this {@link ViewOffsetHelper}'s view.
         *
         * @param offset the offset in px.
         * @return true if the offset has changed
         */
        public boolean setLeftAndRightOffset(int offset) {
            if (mOffsetLeft != offset) {
                mOffsetLeft = offset;
                updateOffsets();
                return true;
            }
            return false;
        }

        public int getTopAndBottomOffset() {
            return mOffsetTop;
        }

        public int getLeftAndRightOffset() {
            return mOffsetLeft;
        }

        public int getLayoutTop() {
            return mLayoutTop;
        }

        public int getLayoutLeft() {
            return mLayoutLeft;
        }

        public int getOffsetHeight() {
            return mOffsetHeight;
        }
    }

    @Override
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V child, Rect rectangle, boolean immediate) {
        System.out.println("BottomBehavior.onRequestChildRectangleOnScreen");
        return super.onRequestChildRectangleOnScreen(coordinatorLayout, child, rectangle, immediate);
//        return true;
    }


    @Override
    public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull Rect rect) {
        System.out.println("BottomBehavior.getInsetDodgeRect");
        return super.getInsetDodgeRect(parent, child, rect);
    }

    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V child, WindowInsetsCompat insets) {
        System.out.println("BottomBehavior.onApplyWindowInsets");
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        System.out.println("BottomBehavior.onRestoreInstanceState");
        super.onRestoreInstanceState(parent, child, state);
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        System.out.println("BottomBehavior.onSaveInstanceState");
        return super.onSaveInstanceState(parent, child);
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        super.onAttachedToLayoutParams(params);
        System.out.println("BottomBehavior.onAttachedToLayoutParams");
    }

    @Override
    public void onDetachedFromLayoutParams() {
        System.out.println("BottomBehavior.onDetachedFromLayoutParams");
        super.onDetachedFromLayoutParams();
    }
}
