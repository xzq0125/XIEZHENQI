package com.xiezhenqi.business.more.zxing;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.h5help.H5HelpActivity;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ZxingActivity extends BaseActivity {

    private static final int PERMS_REQUEST_CAMERA = 111;
    private static final int PERMS_REQUEST_STORAGE = 222;
    private static final int REQUEST_IMAGE = 333;
    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.btn_open_light)
    TextView btnOpenLight;
    private CaptureFragment captureFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("二维码");
        PermissionGen.with(this)
                .addRequestCode(PERMS_REQUEST_CAMERA)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @PermissionSuccess(requestCode = PERMS_REQUEST_CAMERA)
    public void requestCameraSuccess() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @PermissionFail(requestCode = PERMS_REQUEST_CAMERA)
    public void requestCameraFail() {
        finish();
    }

    @PermissionSuccess(requestCode = PERMS_REQUEST_STORAGE)
    public void requestStorageSuccess() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @PermissionFail(requestCode = PERMS_REQUEST_STORAGE)
    public void requestStorageFail() {

    }

    public static boolean isOpen = false;

    @OnClick(R.id.btn_open_light)
    public void onClick() {
        if (!isOpen) {
            CodeUtils.isLightEnable(true);
            isOpen = true;
            btnOpenLight.setText("关灯");
        } else {
            CodeUtils.isLightEnable(false);
            isOpen = false;
            btnOpenLight.setText("开灯");
        }
    }

    @OnClick(R.id.btn_open_photo)
    public void onOpenPhoto() {
        PermissionGen.with(this)
                .addRequestCode(PERMS_REQUEST_STORAGE)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(ZxingActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            ZxingActivity.this.finish();
                            H5HelpActivity.start(ZxingActivity.this, result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ZxingActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            //SecondActivity.this.setResult(RESULT_OK, resultIntent);
            // SecondActivity.this.finish();
            Toast.makeText(ZxingActivity.this, "扫描结果:" + result, Toast.LENGTH_LONG).show();
            ZxingActivity.this.finish();
            H5HelpActivity.start(ZxingActivity.this, result);
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            // SecondActivity.this.setResult(RESULT_OK, resultIntent);
            // SecondActivity.this.finish();
            Toast.makeText(ZxingActivity.this, "扫描二维码失败", Toast.LENGTH_LONG).show();
        }
    };
}
