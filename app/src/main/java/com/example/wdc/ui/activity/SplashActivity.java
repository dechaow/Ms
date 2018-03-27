package com.example.wdc.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.wdc.ms.R;
import com.example.wdc.ms.databinding.ActivitySplashBinding;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.TitanicTextView.Titanic;
import com.example.wdc.widgets.TitanicTextView.TitanicTextView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by wdc on 2016/7/20.
 */
public class SplashActivity extends BaseAppCompatActivity {

    @BindView(R.id.titanic_tv)
    protected TitanicTextView myTitanicTextView;
    private Titanic titanic;
    //判读是否有权限
    private boolean mHavePermission;

    private ActivitySplashBinding mActivitySplashBinding;

    SplashBean bean;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mActivitySplashBinding = DataBindingUtil.setContentView(this, getContentViewLayoutID());
        bean = new SplashBean("Ms");
        mActivitySplashBinding.setSplash(bean);
        //申请权限
        applicationPermission();
        titanic = new Titanic();
        titanic.start(myTitanicTextView);
        new Thread(runnable).start();
    }

    @Override
    public void onNetworkConnected() {
    }

    @Override
    public void onNetworkDisConnected() {
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return false;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && mHavePermission) {
                readyGoThenKill(MainActivity.class);
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                handler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        titanic.cancel();
        handler.removeCallbacks(runnable);
    }

    protected void applicationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mHavePermission = false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            mHavePermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        Observable.just(requestCode).filter(new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer integer) throws Exception {
//                return integer == 1;
//            }
//        }).flatMap(new Function<Integer, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Integer integer) throws Exception {
//                return null;
//            }
//        });

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readyGoThenKill(MainActivity.class);
            } else {
                mHavePermission = false;
                Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
                Observable.fromArray(permissions).filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
                    }
                }).filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !shouldShowRequestPermissionRationale(s);
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //用户禁止提示权限申请框
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setTitle("权限申请被拒绝");
                        builder.setMessage("MS需要申请下列权限才能使用");
                        builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goAppSetting();
                            }
                        });
                        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog dialog = null;
                        if (dialog == null) {
                            dialog = builder.show();
                        }
                    }
                });

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void goAppSetting() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);

    }

    private void goNotificationSetting() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", this.getPackageName());
            intent.putExtra("app_uid", this.getApplicationInfo().uid);
        } else {
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        }
    }

}
