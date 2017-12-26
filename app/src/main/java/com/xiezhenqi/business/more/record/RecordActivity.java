package com.xiezhenqi.business.more.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.DateUtils;
import com.xiezhenqi.utils.ToastUtils;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {

    @BindView(android.R.id.title)
    TextView tvTitle;
    @BindView(R.id.ar_tv_count_time)
    TextView tvCountTime;

    @BindView(R.id.ar_btn_start_record)
    View btnStartRecord;//开始录音
    @BindView(R.id.ar_btn_pause_record)
    View btnPauseRecord;//暂停录音

    @BindView(R.id.ar_btn_play_voice)
    View btnPlayVoice;//播放音频
    @BindView(R.id.ar_btn_pause_play_voice)
    View btnPausePlayVoice;//暂停播放音频

    @BindView(R.id.ar_btn_record_again)
    View btnRecordAgain;//重新录制音频
    @BindView(R.id.ar_btn_record_done)
    View btnRecordDone;//录制完毕
    @BindView(R.id.ar_btn_upload_voice)
    View btnRecordUpload;//上传录音

    private AudioRecorder mAudioRecorder;
    private MediaPlayer mVoicePlayer;
    private File file;
    private CountTimeHandler mCountTimeHandler;
    private boolean isPlayVoicePause = false;//播放音频是否暂停
    private boolean isRecordAgain = false;//是否重新录制
    private static final String FILE_NAME = "bendi_voice";//录音文件名
    private static final int PERMS_REQUEST_AUDIO = 998;
    private static final int PERMS_REQUEST_STORAGE = 999;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        tvTitle.setText("录音");
        hideAllButton();
        btnStartRecord.setVisibility(View.VISIBLE);
    }

    private void hideAllButton() {
        btnStartRecord.setVisibility(View.GONE);
        btnPausePlayVoice.setVisibility(View.GONE);
        btnPlayVoice.setVisibility(View.GONE);
        btnPauseRecord.setVisibility(View.GONE);
        btnRecordDone.setVisibility(View.GONE);
        btnRecordAgain.setVisibility(View.GONE);
        btnRecordUpload.setVisibility(View.GONE);
    }

    @OnClick({R.id.ar_btn_start_record,
            R.id.ar_btn_pause_record,
            R.id.ar_btn_play_voice,
            R.id.ar_btn_pause_play_voice,
            R.id.ar_btn_record_done,
            R.id.ar_btn_record_again,
            R.id.ar_btn_upload_voice})
    public void onClick(View view) {
        hideAllButton();
        switch (view.getId()) {
            case R.id.ar_btn_start_record: {// 点击开始录音
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                        checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                            checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        btnPauseRecord.setVisibility(View.VISIBLE);
                        btnRecordDone.setVisibility(View.VISIBLE);
                        if (mAudioRecorder == null) {
                            mAudioRecorder = AudioRecorder.getInstance();
                            mAudioRecorder.createDefaultAudio(FILE_NAME);
                        }
                        if (isRecordAgain)
                            mAudioRecorder.createDefaultAudio(FILE_NAME);
                        isRecordAgain = false;
                        mAudioRecorder.startRecord(null);
                        if (mCountTimeHandler == null)
                            mCountTimeHandler = new CountTimeHandler(tvCountTime);
                        mCountTimeHandler.startCount();
                    } else {
                        btnStartRecord.setVisibility(View.VISIBLE);
                        //申请文件权限
                        ActivityCompat.requestPermissions(this,
                                new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},
                                PERMS_REQUEST_STORAGE);
                    }
                } else {
                    btnStartRecord.setVisibility(View.VISIBLE);
                    //申请录音权限
                    ActivityCompat.requestPermissions(this,
                            new String[]{"android.permission.RECORD_AUDIO"},
                            PERMS_REQUEST_AUDIO);
                }
            }
            break;

            case R.id.ar_btn_pause_record://点击暂停
                btnStartRecord.setVisibility(View.VISIBLE);
                pauseRecord();
                mCountTimeHandler.pauseCountTime();
                break;

            case R.id.ar_btn_play_voice://点击播放音频
                btnPausePlayVoice.setVisibility(View.VISIBLE);
                btnRecordAgain.setVisibility(View.VISIBLE);
                btnRecordUpload.setVisibility(View.VISIBLE);
                String path = mAudioRecorder.getDestinationPath();
                file = new File(path);
                if (mVoicePlayer == null) {
                    mVoicePlayer = MediaPlayer.create(this, Uri.parse(file.getAbsolutePath()));
                    mVoicePlayer.setOnCompletionListener(this);
                }
                if (isPlayVoicePause) {//继续播放
                    mVoicePlayer.start();
                } else {//重新播放
                    resetCountTime();
                    playVoiceReset();
                }
                mCountTimeHandler.startCount();
                isPlayVoicePause = false;
                break;

            case R.id.ar_btn_record_done: {//点击了完成
                btnPlayVoice.setVisibility(View.VISIBLE);
                btnRecordAgain.setVisibility(View.VISIBLE);
                btnRecordUpload.setVisibility(View.VISIBLE);
                stopRecord();
                mCountTimeHandler.doneCountTime();
            }
            break;

            case R.id.ar_btn_pause_play_voice://点击了暂停播放
                btnPlayVoice.setVisibility(View.VISIBLE);
                btnRecordAgain.setVisibility(View.VISIBLE);
                btnRecordUpload.setVisibility(View.VISIBLE);
                mVoicePlayer.pause();
                mCountTimeHandler.pauseCountTime();
                isPlayVoicePause = true;
                break;

            case R.id.ar_btn_record_again://点击了重新录制
                isRecordAgain = true;
                btnStartRecord.setVisibility(View.VISIBLE);
                mCountTimeHandler.doneCountTime();
                if (mVoicePlayer.isPlaying())
                    mVoicePlayer.stop();
                resetCountTime();
                break;

            case R.id.ar_btn_upload_voice://点击了上传
                finish();
                break;
        }

    }

    /**
     * 计时复位
     */
    private void resetCountTime() {
        tvCountTime.setText("00:00");
    }

    /**
     * 重新播放
     */
    public void playVoiceReset() {
        try {
            mVoicePlayer.reset();
            mVoicePlayer.setDataSource(file.getAbsolutePath());//重新设置要播放的音频
            mVoicePlayer.prepare();//预加载音频
            mVoicePlayer.start();//开始播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        if (mAudioRecorder == null)
            return;
        AudioRecorder.Status status = mAudioRecorder.getStatus();
        if (!(status == AudioRecorder.Status.STATUS_NO_READY || status == AudioRecorder.Status.STATUS_READY))
            mAudioRecorder.stopRecord();
    }

    private void pauseRecord() {
        if (mAudioRecorder == null)
            return;
        AudioRecorder.Status status = mAudioRecorder.getStatus();
        if (status == AudioRecorder.Status.STATUS_START) {
            mAudioRecorder.pauseRecord();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMS_REQUEST_AUDIO:
                boolean isOpenAudio = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!isOpenAudio) {
                    ToastUtils.showToast(this, "请开启录音权限");
                } else {
                    if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.
                            checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(this, perms, 12);
                    }
                }
                break;

            case PERMS_REQUEST_STORAGE:
                boolean isOpenStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!isOpenStorage) {
                    ToastUtils.showToast(this, "请开启内部存储权限");
                }
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btnPlayVoice.setVisibility(View.VISIBLE);
        btnPausePlayVoice.setVisibility(View.GONE);
        if (mCountTimeHandler != null)
            mCountTimeHandler.doneCountTime();
    }

    @Override
    protected void onDestroy() {
        if (mVoicePlayer != null) {
            if (mVoicePlayer.isPlaying())
                mVoicePlayer.stop();
            mVoicePlayer.release();
        }
        if (mCountTimeHandler != null)
            mCountTimeHandler.doneCountTime();
        super.onDestroy();
    }

    /**
     * 计时处理器
     */
    private static class CountTimeHandler extends Handler {

        private final WeakReference<TextView> textViewWeakReference;
        private int count = 0;
        private static final int WHAT_COUNT = 1;
        private static final int DELAYED = 1000;//1000ms=1s

        CountTimeHandler(TextView textView) {
            textViewWeakReference = new WeakReference<>(textView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (textViewWeakReference.get() != null) {
                count++;
                String timeStr = DateUtils.getCalendarStr("mm:ss", count * 1000);
                textViewWeakReference.get().setText(timeStr);
                startCount();
            }
        }

        /**
         * 开启计时
         */
        void startCount() {
            sendEmptyMessageDelayed(WHAT_COUNT, DELAYED);
        }

        /**
         * 暂停计时
         */
        void pauseCountTime() {
            removeMessages(WHAT_COUNT);
        }

        /**
         * 计时完成,计时会重置
         */
        void doneCountTime() {
            pauseCountTime();
            count = 0;
        }
    }
}
