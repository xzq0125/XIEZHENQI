package com.xiezhenqi.business.more.selectcity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class SelectCityActivity extends BaseActivity
        implements SideLetterBar.OnLetterChangedListener,
        CityViewHolder.OnItemClickListener {

    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.asc_city_list)
    RecyclerView ascCityList;
    @BindView(R.id.asc_side_letter_bar)
    SideLetterBar ascSideLetterBar;
    private CityAdapter myAdapter;
    private List<CityDto> list;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("选择城市");
        ascSideLetterBar.setOnLetterChangedListener(this);

        ascCityList.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        myAdapter = new CityAdapter(this);
        ascCityList.setAdapter(myAdapter);
        ascCityList.addItemDecoration(new StickyItemDecoration(this));
        ascCityList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_common_horizontal)));

        list = DBHelper.getInstance(this).getAllCities();
        list.add(0, new CityDto("热门", "#"));
        list.add(0, new CityDto("定位", "!"));
        list.add(0, new CityDto("", "搜"));
        myAdapter.setData(list);
    }

    @Override
    public void onLetterChanged(String letter) {
        LogUtils.debug("XZQ", "letter = " + letter);
        int dPos = myAdapter.getSelectedPosition(letter);
        LogUtils.debug("XZQ", "dPos = " + dPos);
        if (dPos >= 0) {
            layoutManager.scrollToPositionWithOffset(dPos, 0);
            layoutManager.setStackFromEnd(true);
        }
    }

    @Override
    public void onCityClick(CityDto city, int position) {
        LogUtils.debug("XZQ", "position = " + position);
        ToastUtils.showToast(this, city.name);
    }

    @Override
    public void onSearchClick(int position) {
        ToastUtils.showToast(this, "搜索");
    }
}
