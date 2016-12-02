package com.xiezhenqi.business.more.mazing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.mazing.adapters.IDFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.managers.IDTitleViewManager;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import am.widget.basetabstrip.BaseTabStrip;
import am.widget.gradienttabstrip.GradientTabStrip;
import am.widget.replacelayout.ReplaceLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IDActivity extends BaseActivity implements SmartTabLayout.TabProvider, AppBarLayout.OnOffsetChangedListener, BaseTabStrip.OnChangeListener {

    @Bind(R.id.vp)
    ViewPager vpId;

    @Bind(R.id.stl)
    SmartTabLayout stlId;

    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolBar)
    Toolbar toolbar;

    @Bind(R.id.tab)
    ViewGroup viewGroup;

    @Bind(R.id.gts)
    GradientTabStrip gtsTabs;

    @Bind(R.id.rl)
    ReplaceLayout rlTitles;

    private TitleViewManager mTitleViewManager;
    private MainFragmentPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);

        stlId.setCustomTabView(this);
        stlId.setViewPager(vpId);
        appBarLayout.addOnOffsetChangedListener(this);

        mTitleViewManager = new IDTitleViewManager();
        mPagerAdapter = new IDFragmentPagerAdapter(getSupportFragmentManager(), mTitleViewManager, getIntent().getBundleExtra("data"));
        mTitleViewManager.initManager(this, rlTitles);
        gtsTabs.addOnChangeListener(this);
        gtsTabs.bindViewPager(vpId);
        rlTitles.setAdapter(mPagerAdapter);
        vpId.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);
        vpId.setAdapter(mPagerAdapter);
        gtsTabs.setAdapter(mPagerAdapter);
        vpId.addOnPageChangeListener(mPagerAdapter);
    }


    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = getLayoutInflater().inflate(R.layout.item_custom_tab_view, container, false);
        TextView tabTitleView = (TextView) tabView.findViewById(R.id.tv_tab);
        TextView tabCount = (TextView) tabView.findViewById(R.id.tv_count);
        tabTitleView.setText(adapter.getPageTitle(position));
        tabCount.setText("5");
        return tabView;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.debug("WISH", "verticalOffset = " + verticalOffset);
        if (verticalOffset == 0) {
            toolbar.setVisibility(View.GONE);
            viewGroup.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            viewGroup.setVisibility(View.GONE);
        }

    }

    @Override
    public void jumpTo(int correct) {
        rlTitles.moveTo(correct);
    }

    @Override
    public void gotoLeft(int correct, int next, float offset) {
        rlTitles.moveLeft(correct, offset);
    }

    @Override
    public void gotoRight(int correct, int next, float offset) {
        rlTitles.moveRight(correct, offset);
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_ITEMS = 4;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num + 1);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);

            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(getActivity());
            textView.setPadding(16, 16, 16, 16);
            textView.setText("Fragment#" + mNum);
            textView.setLayoutParams(params);
            getListView().addHeaderView(textView);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, getActivity().getResources().getStringArray(R.array.song_name_list)));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }
}
