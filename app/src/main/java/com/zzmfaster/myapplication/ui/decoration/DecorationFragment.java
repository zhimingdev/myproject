package com.zzmfaster.myapplication.ui.decoration;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DecorationFragment extends BaseFragment implements AMapLocationListener {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.banner_rv)
    DiscreteScrollView bannerRv;
    private BaseQuickAdapter adapter;
    private AMapLocationClient mLocationClient = null;////定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private String[] images = {"http://bpic.588ku.com/back_pic/02/51/47/12578201efb0f64.jpg",
            "http://bpic.588ku.com/back_pic/00/04/29/1356230ec0b1a60.jpg",
            "http://bpic.588ku.com/back_pic/00/02/75/41561a71adafd4a.jpg",
            "http://bpic.588ku.com/back_pic/03/64/71/8857ad8072cbb0a.jpg"};
    private List<String> list = new ArrayList<>();
    private Runnable autoRunnable;

    public static DecorationFragment newInstance() {
        return new DecorationFragment();
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.fragment_decoration;
    }

    @Override
    public void initView() {
        for (int i = 0 ;i<images.length;i++) {
            list.add(images[i]);
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(mActivity);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        loadLocation();
        bannerRv.setOrientation(DSVOrientation.HORIZONTAL);
        adapter =new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_imageview_card) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                Glide.with(mActivity).load(item)
                        .bitmapTransform(new RoundedCornersTransformation(mActivity, 20, 0))
                        .into((ImageView)helper.getView(R.id.image));
            }
        };

        bannerRv.setItemTransitionTimeMillis(150);
        bannerRv.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.9f)
                .build());

        adapter.setNewData(list);
        autoRunnable = new Runnable(){
            @Override
            public void run() {
                if (bannerRv == null) {
                    return;
                }
                if (bannerRv.SCROLL_STATE_DRAGGING != bannerRv.getScrollState()) {
                    int currentItem = bannerRv.getCurrentItem();
                    bannerRv.smoothScrollToPosition(++currentItem);
                }
                bannerRv.postDelayed(this,5000);
            }
        };
        bannerRv.setAdapter(InfiniteScrollAdapter.wrap(adapter));
        bannerRv.postDelayed(autoRunnable,5000);
    }

    @Override
    public void initData() {
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(MapActivity.class);
            }
        });
    }

    /**
     * 定位
     */
    private void loadLocation() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                String city = aMapLocation.getCity();
                String district = aMapLocation.getDistrict();//城区信息
                String street = aMapLocation.getStreet();//街道信息
                String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
                tvLocation.setText(street);
            } else {
                tvLocation.setText("定位失败");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bannerRv.removeCallbacks(autoRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

}


