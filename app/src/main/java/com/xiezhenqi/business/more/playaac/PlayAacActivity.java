package com.xiezhenqi.business.more.playaac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.BindView;

public class PlayAacActivity extends BaseActivity {

    @BindView(android.R.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_aac;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("播放aac音频文件");
    }
}
