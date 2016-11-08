package com.xiezhenqi.business.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(android.R.id.title)
    TextView tvTitle;
    @Bind(android.R.id.list)
    ListView listView;
    private SimpleAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText("更多");

        adapter = new SimpleAdapter(this, new ItemList().init(),
                android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object data = adapter.getItem(position);
        if (data instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) data;
            Object item = map.get("item");
            if (item instanceof ItemList.Item) {
                ((ItemList.Item) item).startActivity(this);
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MoreActivity.class);
        context.startActivity(starter);
    }

}
