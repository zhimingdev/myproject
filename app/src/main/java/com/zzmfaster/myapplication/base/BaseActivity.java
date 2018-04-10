package com.zzmfaster.myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager manager;
    private ImmersionBar mImmersionBar;
    private Context mContext;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mContext = this;
        unbinder = ButterKnife.bind(this);
        manager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar(false);
        //注册Eventbus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView(savedInstanceState);
        initData();
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     *携带数据的页面跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * EventBus处理事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(MessageEvent messageEvent) {
        if (messageEvent.className.contains(getClass().getName())) {
            initData();
        }
    }

    /**
     * EventBus更新UI
     */
    public void onChangeDataInUI(String className) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.className = className;
        EventBus.getDefault().post(messageEvent);
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar(boolean isChange) {
        //支持当前设备支状态栏字体设置为黑色
        mImmersionBar = ImmersionBar.with(this);
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            if (isChange) {
                mImmersionBar.statusBarDarkFont(false)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarDarkFont(true)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }
        }else {
            if (isChange) {
                mImmersionBar.statusBarColorTransformEnable(true)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarColor(R.color.colorPrimary)
                        .statusBarDarkFont(true)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册Eventbus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态   }
        }
        unbinder.unbind();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //监听整个页面，点击空白处隐藏输入法
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
