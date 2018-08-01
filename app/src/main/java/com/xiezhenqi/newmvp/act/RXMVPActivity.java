package com.xiezhenqi.newmvp.act;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseListActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.business.h5help.H5HelpActivity;
import com.xiezhenqi.newmvp.HomePageBean;
import com.xiezhenqi.newmvp.IAdapter;

public class RXMVPActivity extends BaseListActivity<RXMVPPresenter, HomePageBean.Datas> implements RXMVPContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, RXMVPActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected RXMVPPresenter createPresenter() {
        return new RXMVPPresenter(this);
    }

    @Override
    protected String getPageTitle() {
        return "分页加载";
    }

    @Override
    protected IAdapter<HomePageBean.Datas> getPageAdapter() {
        return new MyAdapter(this);
    }

    private final class MyAdapter extends BaseLoadMoreAdapter<HomePageBean.Datas, MyViewHolder> {

        public MyAdapter(OnLoadMoreCallback loadMoreCallback) {
            super(loadMoreCallback);
        }

        @Override
        public MyViewHolder onCreateNormalViewHolder(View itemView) {
            return new MyViewHolder(itemView);
        }

        @Override
        public void onConvert(MyViewHolder holder, int position) {
            holder.setData(getDataAt(position), position);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_song_list;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }
    }

    private final class MyViewHolder extends BaseLoadMoreViewHolder implements View.OnClickListener {


        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
            tv.setOnClickListener(this);
        }

        public void setData(HomePageBean.Datas data, int position) {
            tv.setTag(data.link);
            tv.setText(position + "\t" + data.title + "\t" + data.niceDate);
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof String)
                H5HelpActivity.start(v.getContext(), (String) v.getTag());
        }
    }

}
