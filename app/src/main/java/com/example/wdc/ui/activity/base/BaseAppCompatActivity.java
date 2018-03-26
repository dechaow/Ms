

package com.example.wdc.ui.activity.base;

import android.app.ActivityManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.example.wdc.loading.VaryViewHelperController;
import com.example.wdc.ms.R;
import com.example.wdc.receiver.net.INetWorkStateChange;
import com.example.wdc.receiver.net.NetWorkReceiver;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.utils.PrefUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wdc on 2016/7/20.
 * lifeCycle提供了一些接口帮助我们管理生命周期
 * 内部使用两个枚举来跟踪生命周期状态
 * 我们可以使用addObserver方法传入LifeCycleObserver对象来监听生命周期
 * 被观察者实现LifecycleOwner接口
 * <p>
 * 如果要管理整个应用的生命周期应使用ProcessLifecycleOwner
 * 我们继承AppCompatActivity 默认已经帮我们实现了LifeCycleOwner接口
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements INetWorkStateChange{

    //创建我们自定义的LifeCycle管理
    protected LifecycleRegistry mLifecycleRegistry;

    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    /**
     * 获得手机屏幕宽高和密度
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * context
     */
    protected Context mContext = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    protected  NetWorkReceiver mNetReceiver;

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (!toggleOverridePendingTransition()) {
//            switch (getOverridePendingTransitionMode()) {
//                case LEFT:
//                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
//                    break;
//                case RIGHT:
//                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
//                    break;
//                case TOP:
//                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
//                    break;
//                case BOTTOM:
//                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
//                    break;
//                case SCALE:
//                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
//                    break;
//                case FADE:
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    break;
//            }
//        }
        super.onCreate(savedInstanceState);
        setTaskDesc();

        mLifecycleRegistry = new LifecycleRegistry(this);
        //标记当前状态
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        //设置theme
        setTheme(PrefUtil.getTheme());
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);

        //获得屏幕宽高和密度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        //注册网络变化的广播
        mNetReceiver = new NetWorkReceiver();
        mNetReceiver.registerReceiver(this,this);

        initViewsAndEvents();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
//        if (!toggleOverridePendingTransition()) {
//            switch (getOverridePendingTransitionMode()) {
//                case LEFT:
//                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
//                    break;
//                case RIGHT:
//                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
//                    break;
//                case TOP:
//                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
//                    break;
//                case BOTTOM:
//                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
//                    break;
//                case SCALE:
//                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
//                    break;
//                case FADE:
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    break;
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除mNetChangeReceiver
        mNetReceiver.unregisterReceiver(this,this);
    }

    /**
     * get bundle data
     * 接收Intent传值
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * bind layout resource file
     * 绑定id setContentView
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * when event comming
     *  eventbus事件提交管理方法 ，但是感觉并没有啥用，在需要的地方在写也是可以的
     * @param eventCenter
     */
//    protected abstract void onEventComming(EventCenter eventCenter);

    /**
     * get loading target view
     * 需要切换显示的view
     */
    protected abstract View getLoadingTargetView();

    /**
     * init all views and add events
     * 在oncreate中 加载view和一些事件的操作写在这个方法中
     */
    protected abstract void initViewsAndEvents();

    /**
     * network connected
     * 网络连接
     */
//    protected abstract void onNetworkConnected();

    /**
     * network disconnected
     * 网络连接断开
     */
//    protected abstract void onNetworkDisConnected();

    /**
     * is applyStatusBarTranslucency
     * 是否需要设置状态栏颜色
     *
     * @return
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * toggle overridePendingTransition
     * 界面切换是否需要动画
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     * 界面切换的动画
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    protected abstract Boolean isAddStatusHeight();

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        //防止遮盖虚拟按键
//        if (null != msg && !CommonUtils.isEmpty(msg)) {
        Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();
//        }
    }

    /**
     * toggle show loading
     * 界面加载动画
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     * 显示错误的信息
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     * 出现错误时将显示erro view
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     * 网络连接异常调用的方法
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(this.getClass().getSimpleName() + "BaseAppCompatActivity.onStart");
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);

        getTasks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(this.getClass().getSimpleName() + "BaseAppCompatActivity.onPause");
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    /**
     * 设置进程任务栏的样式
     */
    protected void setTaskDesc(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Drawable drawable = ContextCompat.getDrawable(this,getApplicationInfo().icon);
            Bitmap bitmap = drawable2Bitmap(drawable);
            this.setTaskDescription(new ActivityManager.TaskDescription(getResources().getString(R.string.app_name),bitmap));
            bitmap.recycle();
        }
    }

    private Bitmap drawable2Bitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    private void getTasks(){
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(10);
        String className = null;
        if (info != null && !info.isEmpty()){
            for (int i = 0; i < info.size(); i++) {
                ActivityManager.RunningTaskInfo curInfo = info.get(i);
                System.out.println("BaseAppCompatActivity.getTasks  " + TAG_LOG + "     " + info.get(i).topActivity.getClassName() + "   " + curInfo);
            }
        }else{
            System.out.println("BaseAppCompatActivity.getTasks  " + info);
        }

    }

}
