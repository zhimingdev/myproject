package com.zzmfaster.myapplication.ui.find;

import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.base.BaseFragment;

public class FindFragment extends BaseFragment {
    public static FindFragment newInstance() {
        return new FindFragment();
    }
    @Override
    public int setLayoutResourceID() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(false);
    }
}
