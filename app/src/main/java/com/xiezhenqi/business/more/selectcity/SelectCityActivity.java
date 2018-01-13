package com.xiezhenqi.business.more.selectcity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.Address;
import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.SoftInputUtils;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class SelectCityActivity extends BaseActivity
        implements SideLetterBar.OnLetterChangedListener,
        CityViewHolder.OnItemClickListener, TextWatcher,
        SearchResultViewHolder.OnResultCityClickListener,
        MyLocationListener.OnGetBDLocationListener {

    private static final int PERMS_REQUEST_LOCATION = 99;
    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.asc_city_list)
    RecyclerView ascCityList;
    @BindView(R.id.asc_side_letter_bar)
    SideLetterBar ascSideLetterBar;
    @BindView(R.id.lcs_edt_text)
    EditText edtText;
    @BindView(R.id.lcs_btn_cancel)
    TextView btnCancel;
    @BindView(R.id.asc_tv_search_result_count)
    TextView tvResultCount;
    @BindView(R.id.asc_search_result_list)
    RecyclerView searchResultList;
    @BindView(R.id.asc_llyt_search)
    ViewGroup vgSearch;
    @BindView(R.id.asc_flyt_city)
    ViewGroup vgCity;
    private CityAdapter myAdapter;
    private SearchResultAdapter searchResultAdapter;
    private List<CityDto> list;
    private LinearLayoutManager layoutManager;
    private DBHelper dbHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("选择城市");

        vgCity.setVisibility(View.VISIBLE);
        vgSearch.setVisibility(View.GONE);

        ascSideLetterBar.setOnLetterChangedListener(this);
        ascCityList.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        ascCityList.setItemAnimator(null);
        myAdapter = new CityAdapter(this);
        ascCityList.setAdapter(myAdapter);
        ascCityList.addItemDecoration(new StickyItemDecoration(this));
        ascCityList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_common_horizontal)));

        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new SearchResultAdapter(this);
        searchResultList.setAdapter(searchResultAdapter);
        searchResultList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_common_horizontal)));


        dbHelper = DBHelper.getInstance(this);
        list = dbHelper.getAllCities();
        list.add(0, new CityDto("热门", "#"));
        list.add(0, new CityDto("定位", "!"));
        list.add(0, new CityDto("", "搜"));
        myAdapter.setData(list);

        edtText.addTextChangedListener(this);

        PermissionGen.with(this)
                .addRequestCode(PERMS_REQUEST_LOCATION)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                .request();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMS_REQUEST_LOCATION)
    public void requestLocationSuccess() {
        bdLocationUtils = new BDLocationUtils(this, this);
        bdLocationUtils.doLocation();
        bdLocationUtils.mLocationClient.start();
    }

    @PermissionFail(requestCode = PERMS_REQUEST_LOCATION)
    public void requestLocationFail() {
        ToastUtils.showToast(this, "请开启手机定位，以获取您当前的城市");
    }

    private BDLocationUtils bdLocationUtils;

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
        vgCity.setVisibility(View.GONE);
        vgSearch.setVisibility(View.VISIBLE);
        SoftInputUtils.show(this, edtText);
    }

    @OnClick(R.id.lcs_btn_cancel)
    public void onCancelClick() {
        vgCity.setVisibility(View.VISIBLE);
        vgSearch.setVisibility(View.GONE);
        SoftInputUtils.hide(this, edtText);
        searchResultAdapter.setData(null);
        edtText.setText(null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            searchResultAdapter.setData(null);
            tvResultCount.setVisibility(View.GONE);
            return;
        }
        List<CityDto> searchList = dbHelper.searchCity(keyword);
        searchResultAdapter.setData(searchList);
        tvResultCount.setVisibility(View.VISIBLE);
        String countFormatStr = String.format(Locale.getDefault(), format, searchList.size());
        tvResultCount.setText(countFormatStr);
    }

    public static final String format = "有%1$d个搜索结果";

    @Override
    public void onCityClick(CityDto city) {
        ToastUtils.showToast(this, city.name);
    }

    @Override
    protected void onDestroy() {
        if (bdLocationUtils != null)
            bdLocationUtils.mLocationClient.stop();
        super.onDestroy();
    }

    @Override
    public void onGetBDLocation(Address address) {
        myAdapter.setLocation(address.city);
    }
}
