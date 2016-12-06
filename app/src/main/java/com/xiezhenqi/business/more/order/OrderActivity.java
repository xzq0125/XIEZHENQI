package com.xiezhenqi.business.more.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.mazing.IDActivity;
import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;
import com.xiezhenqi.business.more.order.homepage.JsonDto;
import com.xiezhenqi.utils.EntitySerializer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    @Bind(android.R.id.title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));

    }

    public void onClick(View view) {

        TextView textView = (TextView) view;

        switch (textView.getText().toString()) {
            case "体验1":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.TASTY, 0);
                break;
            case "体验2":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.TASTY, 1);
                break;
            case "推荐1":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.STAR, 0);
                break;
            case "推荐2":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.STAR, 1);
                break;
            case "喜欢1":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.LIKE, 0);
                break;
            case "喜欢2":
                IDActivity.start(this, MainFragmentPagerAdapter.PagerType.LIKE, 1);
                break;
        }

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
