package com.xiezhenqi.business.more.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.widget.progressbar.NumberProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgressBarActivity extends BaseActivity implements Runnable {

    @Bind(R.id.progress1)
    NumberProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress_bar;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        progressBar.setMax(100);
    }

    boolean isStarted = false;

    public void onButtonClick(View view) {

        if (isStarted) {
            isStarted = false;
            ((TextView) view).setText("开始");
            progressBar.removeCallbacks(this);

        } else {
            isStarted = true;
            ((TextView) view).setText("停止");
            progressBar.postDelayed(this, 0);
        }
    }

    int progress = 0;

    @Override
    public void run() {
        ++progress;
        progressBar.setProgress(progress);
        if (progress > 100)
            progress = 0;
        progressBar.postDelayed(this, 10);
    }
}
