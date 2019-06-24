package com.example.pager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.base.BasePager;

public class VideoHomePager extends BasePager {
    private TextView textView;
    public VideoHomePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(35);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("VideoHomePager");
    }
}
