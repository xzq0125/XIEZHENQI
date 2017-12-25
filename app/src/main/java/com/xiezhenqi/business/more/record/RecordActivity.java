package com.xiezhenqi.business.more.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {

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
    AudioRecorder audioRecorder;
    private MediaPlayer player;
    private File file;

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

    private boolean isPause = false;
    private boolean isAgain = false;

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

                if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                        checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                            checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        recording.setVisibility(View.VISIBLE);
                        recordDone.setVisibility(View.VISIBLE);
                        if (audioRecorder == null) {
                            audioRecorder = AudioRecorder.getInstance();
                            audioRecorder.createDefaultAudio("xzq");
                        }
                        if (isAgain)
                            audioRecorder.createDefaultAudio("xzq");
                        isAgain = false;
                        audioRecorder.startRecord(null);
                    } else {
                        startRecord.setVisibility(View.VISIBLE);
                        //提示用户开户权限音频
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(this, perms, 12);
                    }
                } else {
                    startRecord.setVisibility(View.VISIBLE);
                    //提示用户开户权限音频
                    String[] perms = {"android.permission.RECORD_AUDIO"};
                    ActivityCompat.requestPermissions(this, perms, 11);
                }


                break;

            case R.id.recording://正在录音按钮,点击之后会停止
                startRecord.setVisibility(View.VISIBLE);
                audioRecorder.pauseRecord();
                break;

            case R.id.play://正在播放
                stopPlay.setVisibility(View.VISIBLE);
                recordAgain.setVisibility(View.VISIBLE);
                recordUpload.setVisibility(View.VISIBLE);
                String path = audioRecorder.getDestinationPath();
                file = new File(path);
                if (player == null) {
                    player = MediaPlayer.create(this, Uri.parse(file.getAbsolutePath()));
                    player.setOnCompletionListener(this);
                }
                if (isPause) {
                    player.start();//开始播放
                } else {
                    play();
                }
                isPause = false;
                break;

            case R.id.record_done: {//完成
                play.setVisibility(View.VISIBLE);
                recordAgain.setVisibility(View.VISIBLE);
                recordUpload.setVisibility(View.VISIBLE);
                AudioRecorder.Status status = audioRecorder.getStatus();
                if (!(status == AudioRecorder.Status.STATUS_NO_READY || status == AudioRecorder.Status.STATUS_READY))
                    audioRecorder.stopRecord();
            }
            break;

            case R.id.stop_play://停止播放
                play.setVisibility(View.VISIBLE);
                recordAgain.setVisibility(View.VISIBLE);
                recordUpload.setVisibility(View.VISIBLE);
                player.pause();
                isPause = true;
                break;

            case R.id.record_again://重新录制
                isAgain = true;
                startRecord.setVisibility(View.VISIBLE);
                break;

            case R.id.record_upload://上传
                finish();
                break;
        }

    }

    //播放音乐的方法
    public void play() {
        try {
            player.reset();
            player.setDataSource(file.getAbsolutePath());//重新设置要播放的音频
            player.prepare();//预加载音频
            player.start();//开始播放
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 11:
                boolean albumAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!albumAccepted) {
                    ToastUtils.showToast(this, "请开启应用录音权限");
                } else {
                    if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.
                            checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(this, perms, 12);
                    }
                }
                break;

            case 12:
                boolean albumAccepted2 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!albumAccepted2) {
                    ToastUtils.showToast(this, "请开启应用内部存储权限");
                }
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play.setVisibility(View.VISIBLE);
        stopPlay.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            if (player.isPlaying())
                player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
