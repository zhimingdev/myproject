package com.zzmfaster.myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.zzmfaster.myapplication.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private View mContentView;
    protected ImmersionBar mImmersionBar;

    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        unbinder = ButterKnife.bind(this, mContentView);
        initView();
        initData();
//        if (isImmersionBarEnabled()){
//            initImmersionBar(false);
//        }else {
            initImmersionBar(true);
//        }
        return mContentView;
    }



    public abstract int setLayoutResourceID();

    public abstract void initView();

    public abstract void initData();

    /** * 打开一个Activity 默认 不关闭当前activity */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(mActivity, clz);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            mActivity.finish();
        }
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
                        .statusBarDarkFont(true)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarColor(R.color.colorPrimary)
                        .fitsSystemWindows(true)
                        .statusBarDarkFont(true)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

