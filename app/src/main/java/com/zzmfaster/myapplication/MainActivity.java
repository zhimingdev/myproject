package com.zzmfaster.myapplication;

import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zzmfaster.myapplication.base.BaseActivity;
import com.zzmfaster.myapplication.base.BaseFragment;
import com.zzmfaster.myapplication.custom.bottombar.BottomBarItem;
import com.zzmfaster.myapplication.custom.bottombar.BottomBarLayout;
import com.zzmfaster.myapplication.ui.decoration.DecorationFragment;
import com.zzmfaster.myapplication.ui.find.FindFragment;
import com.zzmfaster.myapplication.ui.home.HomeFragment;
import com.zzmfaster.myapplication.ui.mine.MineFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_fl)
    FrameLayout mainFl;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private DecorationFragment decorationFragment;
    private MineFragment mineFragment;
    private BaseFragment mFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initHomeFragment();
    }


    @Override
    public void initData() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                switch (currentPosition) {
                    case 0:
                        initHomeFragment();
                        break;
                    case 1:
                        initFindFragment();
                        break;
                    case 2:
                        initDecoraFragment();
                        break;
                    case 3:
                        initMineFragment();
                        break;
                }
                mBottomBarLayout.hideMsg(currentPosition);
                mBottomBarLayout.hideMsg(currentPosition);
                mBottomBarLayout.hideNotify(currentPosition);
                mBottomBarLayout.hideMsg(currentPosition);
            }
        });
    }


    private void initHomeFragment() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        switchContent(mFragment,homeFragment);
    }

    private void initFindFragment() {
        if (findFragment == null) {
            findFragment = FindFragment.newInstance();
        }
        switchContent(mFragment,findFragment);
    }

    private void initDecoraFragment() {
        if (decorationFragment == null) {
            decorationFragment = DecorationFragment.newInstance();
        }
        switchContent(mFragment,decorationFragment);
    }

    private void initMineFragment() {
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
        }
        switchContent(mFragment,mineFragment);
    }

    /**
     * Fragment 切换方法，
     *
     * @param from
     * @param to
     */
    public void switchContent(BaseFragment from, BaseFragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFragment == null) {
            mFragment = to;
            transaction.replace(R.id.main_fl, mFragment);
            transaction.commitAllowingStateLoss();
            return;
        }
        if (mFragment != to) {
            mFragment = to;
            //mFragmentMan.beginTransaction().setCustomAnimations(
            //android.R.anim.fade_in, R.anim.slide_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.main_fl, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 绑定物理返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // creatDialog();
            // 如果两次按键时间间隔大于2000毫秒，则不退出
            // ActivityManager.getInstance().cleanAllActivity();
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 记录exitTime
                exitTime = System.currentTimeMillis();
            } else {
                // 否则退出程序
                finish();
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
