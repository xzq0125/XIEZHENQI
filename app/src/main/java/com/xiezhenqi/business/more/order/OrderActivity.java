package com.xiezhenqi.business.more.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.order.homepage.JsonDto;
import com.xiezhenqi.utils.EntitySerializer;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    @Bind(android.R.id.title)
    TextView tvTitle;

    @Bind(R.id.rl)
    RefreshLayout pullDownRefresh;

    @Bind(android.R.id.list)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
        getData();
    }

    private JsonDto getData() {

        JsonDto jsonDto;
        try {
            jsonDto = EntitySerializer.deserializerEntity(Json.JSON, JsonDto.class);
        } catch (Exception e) {
            jsonDto = null;
        }

        return jsonDto;
    }
}
