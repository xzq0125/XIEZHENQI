package com.xiezhenqi.business.musicpavilion;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.fragment.BaseListFragment;
import com.xiezhenqi.business.musicpavilion.adapters.MusicPavilionAdapter;
import com.xiezhenqi.entity.musicpavilion.MusicPavilionDto;
import com.xiezhenqi.request.RequestError;
import com.xiezhenqi.request.RequestFactory;
import com.xiezhenqi.request.RequestTask;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

/**
 * MusicPavilionFragment
 * Created by Tse on 2016/10/19.
 */

public class MusicPavilionFragment extends BaseListFragment implements RequestTask.OnTaskListener {

    private final MusicPavilionAdapter mAdapter = new MusicPavilionAdapter();
    private int requestTaskCount;
    private int succeedCount;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView, ViewGroup container) {
        ButterKnife.bind(this, getView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration decoration = new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.divider_song_list));
        decoration.setItemNoDivider(0);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mAdapter);
        container.setBackgroundResource(R.color.common_bg);
    }

    @Override
    protected void loadData(boolean loadFirstPage) {
        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.top_list));
        requestTaskCount = 0;
        succeedCount = 0;
        mAdapter.setData(null);
        getData(list);
    }

    private void getData(List<String> list) {
        if (list == null)
            return;

        for (String s : list) {
            if (!s.contains("="))
                return;
            String[] str = s.split("=");
            String topId = str[0];
            String name = str[1];
            new RequestTask(this).execute(RequestFactory.getTops(topId).setTag(name));
            requestTaskCount++;
        }
    }

    @Override
    public void onFailed(RequestError error) {
        //加载失败处理
        showNormal();
        ToastUtils.showToast(getActivity(), error.getMessage());
    }

    @Override
    public void onSucceed(Object dto, Object tag) {

        //加载成功处理
        if (dto != null && dto instanceof MusicPavilionDto && tag instanceof String) {

            MusicPavilionDto pavilionDto = (MusicPavilionDto) dto;

            pavilionDto.pagebean.top_name = (String) tag;

            mAdapter.addData(pavilionDto.pagebean);

            succeedCount++;
        }

        if (requestTaskCount == succeedCount) {
            showNormal();
        }

    }

}
