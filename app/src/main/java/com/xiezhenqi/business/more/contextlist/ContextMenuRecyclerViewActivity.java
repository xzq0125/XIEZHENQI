package com.xiezhenqi.business.more.contextlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.business.dialogs.AddTextDialog;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;
import com.xiezhenqi.widget.menurecyclerview.ContextMenuRecyclerView;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayoutHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 上下文RecyclerView
 * 有两个地方需要注意：
 * 1.onBindViewHolder中给ItemView添加Tag;
 * 2.设置ItemView的LongClickable为true，不然不会出现上下文菜单（具体原因见ContextMenu原理分析）；
 * holder.itemView.setLongClickable(true);
 */
public class ContextMenuRecyclerViewActivity extends BaseActivity implements
        BaseLoadMoreAdapter.OnItemClickListener,
        AddTextDialog.TextCallback,
        RefreshLayout.OnRefreshListener {

    private static final String ACTION_ADD = "add";
    private static final String ACTION_UPDATE = "update";
    private int position;
    private AddSingerDialog mAddSingerDialog;
    private final MyAdapter mAdapter = new MyAdapter(null);

    @BindView(android.R.id.title)
    TextView tvTitle;

    @BindView(R.id.rl)
    RefreshLayout pullDownRefresh;

    @BindView(android.R.id.list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_context_list;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_common_horizontal)));
        RefreshLayoutHeader header = new RefreshLayoutHeader(this);
        pullDownRefresh.addOnRefreshListener(header);
        pullDownRefresh.addOnRefreshListener(this);
        pullDownRefresh.setRefreshHeader(header);
        pullDownRefresh.autoRefresh();
        mAdapter.addOnItemClickListener(this);
        registerForContextMenu(rvList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ContextMenuRecyclerView.RecyclerContextMenuInfo contextMenuInfo = (ContextMenuRecyclerView.RecyclerContextMenuInfo) menuInfo;
        String item = String.valueOf(contextMenuInfo.mRecycleItem.getItem());
        position = mAdapter.indexOf(item);

        menu.setHeaderTitle("标题");
        menu.add(Menu.NONE, 0, Menu.NONE, "添加");
        menu.add(Menu.NONE, 1, Menu.NONE, "移除");
        menu.add(Menu.NONE, 2, Menu.NONE, "查询");
        menu.add(Menu.NONE, 3, Menu.NONE, "修改");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 0:
                if (mAddSingerDialog == null)
                    mAddSingerDialog = new AddSingerDialog(this, this);
                mAddSingerDialog.setTag(ACTION_ADD);
                mAddSingerDialog.show();
                break;

            case 1:
                mAdapter.remove(position);
                break;

            case 2:
                ToastUtils.showToastAtCenter(this, mAdapter.getDataAt(position));
                break;

            case 3:
                if (mAddSingerDialog == null)
                    mAddSingerDialog = new AddSingerDialog(this, this);
                mAddSingerDialog.setText(mAdapter.getDataAt(position));
                mAddSingerDialog.setTag(ACTION_UPDATE);
                mAddSingerDialog.show();
                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(rvList);
    }

    @Override
    public void onItemClick(Object dto, int position) {
        ToastUtils.showToast(this, "长按item弹出上下文菜单 position = " + position);
    }

    @Override
    public void callbackText(AddTextDialog addTextDialog, String text) {
        if (ACTION_ADD.equals(addTextDialog.getTag())) {

            if (mAdapter.isDataExist(text))
                ToastUtils.showToast(this, "歌手已存在");
            else
                mAdapter.addData(position, text);

        } else if (ACTION_UPDATE.equals(addTextDialog.getTag())) {

            mAdapter.update(position, text);

        }

    }

    @Override
    public void onRefresh() {
        List<String> data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.singer_list)));
        mAdapter.setData(data);
        pullDownRefresh.refreshComplete();
    }

    class MyAdapter extends BaseLoadMoreAdapter<String, MyViewHolder> {

        public MyAdapter(OnLoadMoreCallback loadMoreCallback) {
            super(loadMoreCallback);
        }

        @Override
        public MyViewHolder onCreateNormalViewHolder(View itemView) {
            return new MyViewHolder(itemView);
        }

        @Override
        public void onConvert(MyViewHolder holder, int position) {
            holder.setData(getDataAt(position));
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_drag_list;
        }

        @Override
        public void onViewRecycled(MyViewHolder holder) {
            super.onViewRecycled(holder);
            holder.itemView.setOnCreateContextMenuListener(null);
        }

        public void addData(int position, String newData) {
            int toPos = position + 1;
            mData.add(toPos, newData);
            notifyItemInserted(toPos);
        }

        public boolean isDataExist(String text) {
            for (String s : mData)
                if (TextUtils.equals(s, text))
                    return true;
            return false;
        }

        public void remove(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }

        public void update(int position, String text) {
            mData.remove(position);
            mData.add(position, text);
            notifyItemChanged(position);
        }
    }

    class MyViewHolder extends BaseLoadMoreViewHolder {

        final ContextMenuRecyclerView.RecyclerItem item = new ContextMenuRecyclerView.RecyclerItem();

        @BindView(R.id.tv_item)
        TextView tvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvItem.setCompoundDrawables(null, null, null, null);
            itemView.setLongClickable(true);
        }

        public void setData(String data) {
            itemView.setTag(itemView.getId(), item.setItem(data));
            itemView.setTag(data);
            tvItem.setText(data);
        }
    }

}
