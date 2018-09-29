package com.xiezhenqi.newmvp.act;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseListActivity;
import com.xiezhenqi.business.h5help.H5HelpActivity;
import com.xiezhenqi.newmvp.BaseRecyclerAdapter;
import com.xiezhenqi.newmvp.BaseRecyclerViewHolder;
import com.xiezhenqi.newmvp.HomePageBean;
import com.xiezhenqi.newmvp.IAdapter;

import java.util.List;

public class RXMVPActivity extends BaseListActivity<RXMVPPresenter, HomePageBean.Datas> implements RXMVPContract.View {

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
        return new MyAdapter();
    }

    private final class MyAdapter extends BaseRecyclerAdapter<HomePageBean.Datas, MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @Nullable View itemView, int viewType) {
            return new MyViewHolder(itemView);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_song_list;
        }

        @Override
        public void onConvert(@NonNull MyViewHolder holder, HomePageBean.Datas data,
                              int position, @NonNull List<Object> payload) {
            holder.setData(data);
        }

    }

    private final class MyViewHolder extends BaseRecyclerViewHolder<HomePageBean.Datas> implements View.OnClickListener {

        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
            tv.setOnClickListener(this);
        }

        @Override
        public void setData(HomePageBean.Datas data) {
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
