package com.xiezhenqi.business.more.galleryviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.galleryviewpager.adapters.GuideAdapter;
import com.xiezhenqi.business.more.galleryviewpager.transformer.RotateYTransformer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 画廊效果ViewPager
 */
public class GalleryViewPagerActivity extends BaseActivity {

    private final int[] pics = {
            R.drawable.image_01,
            R.drawable.image_02,
            R.drawable.image_03,
            R.drawable.image_04};

    @Bind(R.id.vp)
    ViewPager viewPager;
    @Bind(android.R.id.title)
    TextView tvTitle;
    private GuideAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery_view_pager;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
        viewPager.setPageMargin(40);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter = new GuideAdapter(pics));

//        viewPager.setPageTransformer(true, new AlphaPageTransformer());
//        viewPager.setPageTransformer(true, new RotateDownPageTransformer());
//        viewPager.setPageTransformer(true, new RotateUpPageTransformer());
        viewPager.setPageTransformer(true, new RotateYTransformer(45));
//        viewPager.setPageTransformer(true, NonPageTransformer.INSTANCE);
//        viewPager.setPageTransformer(true, new AlphaPageTransformer());
//        viewPager.setPageTransformer(true, new ScaleInTransformer());
//        viewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
//        viewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
    }

}
