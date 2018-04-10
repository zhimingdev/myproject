package com.zzmfaster.myapplication.ui.mine;

import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.base.BaseFragment;

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }
}
