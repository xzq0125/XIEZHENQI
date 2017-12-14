package com.xiezhenqi.business.more.dragview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DragViewActivity
 * Created by Tse on 2017/11/30.
 */

public class DragViewActivity extends BaseActivity {

    @BindView(android.R.id.title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_view;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, DragViewActivity.class);
        context.startActivity(starter);
    }
}
