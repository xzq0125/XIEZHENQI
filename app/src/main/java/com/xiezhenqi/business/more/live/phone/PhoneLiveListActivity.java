package com.xiezhenqi.business.more.live.phone;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xiezhenqi.base.list.activity.BaseListActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.utils.EntitySerializer;
import com.xiezhenqi.utils.ToastUtils;

public class PhoneLiveListActivity extends BaseListActivity implements BaseLoadMoreAdapter.OnItemClickListener {

    private final PhoneLiveListAdapter adapter = new PhoneLiveListAdapter(null);

    @Override
    protected void initRecyclerView(RecyclerView recyclerView, ViewGroup container) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.addOnItemClickListener(this);
    }

    @Override
    protected void loadData(boolean loadFirstPage) {
        try {
            PhoneLiveDto phoneLiveDto = EntitySerializer.deserializerEntity(JSON.JSON_PHONE, PhoneLiveDto.class);
            adapter.setData(phoneLiveDto.data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showNormal();
    }

    @Override
    protected String getMyTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    public void onItemClick(Object dto, int position) {
        if (dto instanceof PhoneLiveDto.DataDto) {
            ToastUtils.showToast(this, ((PhoneLiveDto.DataDto) dto).room_name);
            PhoneLiveActivity.start(this);
        }
    }
}
