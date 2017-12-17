package com.xiezhenqi.business.more.cling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClingBarActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.cling_bar)
    View vStateBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cling_bar;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        rv.addOnScrollListener(new StateScrollListener());
        rv.setLayoutManager(new LinearLayoutManager(this));
        ClingAdapter adapter = new ClingAdapter();
        rv.setAdapter(adapter);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        adapter.setData(list);
    }

    private class StateScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            checkStoreBar(recyclerView);
        }
    }


    /**
     * 检查StoreBar
     */
    private void checkStoreBar(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                vStateBar.setVisibility(View.INVISIBLE);
                return;
            }
            if (layoutManager.findFirstVisibleItemPosition() > 0) {
                vStateBar.setVisibility(View.VISIBLE);
                return;
            }
            View child = layoutManager.findViewByPosition(0);
            if (child == null) {
                vStateBar.setVisibility(View.INVISIBLE);
                return;
            }
            checkStoreBarLocation(child);
        }
    }

    /**
     * 检查
     */
    public void checkStoreBarLocation(View child) {
        View storeBar = child.findViewById(R.id.cling_bar);
        if (storeBar == null) {
            vStateBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (child.getTop() + storeBar.getTop() < 0) {
            vStateBar.setVisibility(View.VISIBLE);
        } else {
            vStateBar.setVisibility(View.INVISIBLE);
        }
    }

}
