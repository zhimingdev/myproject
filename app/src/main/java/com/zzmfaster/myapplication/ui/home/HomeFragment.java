package com.zzmfaster.myapplication.ui.home;

import android.view.View;
import android.widget.TextView;

import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.base.BaseFragment;
import com.zzmfaster.myapplication.custom.MyScrollview;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.sv)
    MyScrollview sv;
    @BindView(R.id.searchBarLayout)
    View searchBarLayout;
    @BindView(R.id.searchTxt)
    TextView searchTxt;
    private int height,distance;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        //渐变过度的高度
        //alpha 0.0~1.0 换成高度的渐变就是 0~180
        height = (int) (180 * getResources().getDisplayMetrics().density);
        sv.setScrvllViewlistener(new MyScrollview.ScrvllViewlistener() {
            @Override
            public void onScrollViewChange(int x, int y, int oldx, int oldy) {
                System.out.println("两者的位置"+y+"------"+oldy);
                distance += oldy;
                if (oldy <= 0 || y <= 0) {
                    searchBarLayout.setAlpha(0f);
                }else if (distance > 0 && distance <= height){
                    searchBarLayout.setAlpha((float) distance / height);
                }else {
                    searchBarLayout.setAlpha(1.0f);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }

}
