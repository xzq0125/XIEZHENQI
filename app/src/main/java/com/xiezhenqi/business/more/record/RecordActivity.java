package com.xiezhenqi.business.more.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity {

    @BindView(android.R.id.title)
    TextView tvTitle;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.start_record)
    ImageView startRecord;
    @BindView(R.id.stop_play)
    ImageView stopPlay;
    @BindView(R.id.recording)
    ImageView recording;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.record_done)
    Button recordDone;
    @BindView(R.id.record_again)
    Button recordAgain;
    @BindView(R.id.record_upload)
    Button recordUpload;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        tvTitle.setText("录音");
        startRecord.setVisibility(View.VISIBLE);
        stopPlay.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        recording.setVisibility(View.GONE);
        recordDone.setVisibility(View.GONE);
        recordAgain.setVisibility(View.GONE);
        recordUpload.setVisibility(View.GONE);

    }

    @OnClick({R.id.start_record, R.id.stop_play, R.id.recording, R.id.play,
            R.id.record_done, R.id.record_again, R.id.record_upload})
    public void onClick(View view) {
        startRecord.setVisibility(View.GONE);
        stopPlay.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        recording.setVisibility(View.GONE);
        recordDone.setVisibility(View.GONE);
        recordAgain.setVisibility(View.GONE);
        recordUpload.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.start_record://开始录音,点击之后会开始录音
                recording.setVisibility(View.VISIBLE);
                recordDone.setVisibility(View.VISIBLE);
                break;

            case R.id.recording://正在录音按钮,点击之后会停止
                startRecord.setVisibility(View.VISIBLE);
                break;

            case R.id.play://正在播放
                stopPlay.setVisibility(View.VISIBLE);
                recordAgain.setVisibility(View.VISIBLE);
                recordUpload.setVisibility(View.VISIBLE);
                break;

            case R.id.record_done://完成
            case R.id.stop_play://停止播放
                play.setVisibility(View.VISIBLE);
                recordAgain.setVisibility(View.VISIBLE);
                recordUpload.setVisibility(View.VISIBLE);
                break;

            case R.id.record_again://重新录制
                startRecord.setVisibility(View.VISIBLE);
                break;

            case R.id.record_upload://上传
                finish();
                break;
        }
    }
}
