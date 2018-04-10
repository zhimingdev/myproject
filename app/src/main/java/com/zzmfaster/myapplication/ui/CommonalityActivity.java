package com.zzmfaster.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzmfaster.myapplication.R;
import com.zzmfaster.myapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CommonalityActivity extends BaseActivity {
    @BindView(R.id.ib_close)
    ImageButton ibClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_share)
    ImageButton ibShare;
    @BindView(R.id.wv_content)
    WebView wvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commonality;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ib_close, R.id.ib_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_close:
                webViewGoBack();
                break;
            case R.id.ib_share:
                break;
        }
    }

    public void webViewGoBack() {
        if (wvContent.canGoBack()) {
            wvContent.goBack();
        } else {
            this.finish();
        }
    }
}
