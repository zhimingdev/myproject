package com.zzmfaster.myapplication.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollview extends ScrollView {
    public ScrvllViewlistener scrvllViewlistener = null;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrvllViewlistener.onScrollViewChange(l, t, oldl, oldt);
    }

    public interface ScrvllViewlistener {
        void onScrollViewChange(int x, int y, int oldx, int oldy);
    }

    public void setScrvllViewlistener(ScrvllViewlistener scrvllViewlistener) {
        this.scrvllViewlistener = scrvllViewlistener;
    }
}
