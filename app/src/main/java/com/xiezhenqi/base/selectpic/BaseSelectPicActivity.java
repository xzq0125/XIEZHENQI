package com.xiezhenqi.base.selectpic;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.base.crop.Crop;
import com.xiezhenqi.base.selectpic.adapters.ImageAdapter;
import com.xiezhenqi.utils.FileManager;
import com.xiezhenqi.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 选取图片界面
 * Created by Tse on 2016/9/12.
 */
public class BaseSelectPicActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private static final String EXTRA_WIDTH = "width";
    private static final String EXTRA_HEIGHT = "height";
    private static final String EXTRA_PATH = "pic_path";
    private static final int REQUEST_CAMERA = 999;
    private BaseSelectPicActivity me = this;
    private ImageAdapter mAdapter;
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
    private File photo;
    private int width = 0;
    private int height = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectpicture;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        width = getIntent().getIntExtra(EXTRA_WIDTH, 0);
        height = getIntent().getIntExtra(EXTRA_HEIGHT, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.select_toolbar);
        GridView gvPictures = (GridView) findViewById(R.id.select_gv_pictures);
        mAdapter = new ImageAdapter();
        gvPictures.setAdapter(mAdapter);
        gvPictures.setOnItemClickListener(me);
        getLoaderManager().initLoader(0, null, me);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES,
                null, null, MediaStore.Images.Media._ID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> galleryList = new ArrayList<>();
        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                int dataColumnIndex = data
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                String item = data.getString(dataColumnIndex);
                galleryList.add(item);
            }
        }
        Collections.reverse(galleryList);//集合取反
        mAdapter.addAll(galleryList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {//相机拍照返回
            if (photo.exists()) {
                beginCrop(photo.getPath());
            }
        } else if (requestCode == Crop.REQUEST_CROP) {//截图返回
            if (resultCode == RESULT_OK) {
                handlePhoto(Crop.getOutput(data).getPath());
            } else if (resultCode == Crop.RESULT_ERROR) {
                ToastUtils.showToast(me, "图片解析出错");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            photo = FileManager.createPhoto(me);
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            startActivityForResult(intent, REQUEST_CAMERA);
        } else {
            beginCrop(mAdapter.getItem(position));
        }
    }

    /**
     * 开始截取图片
     *
     * @param path 图片路径
     */
    private void beginCrop(String path) {
        Uri source = Uri.fromFile(new File(path));
        Uri destination = Uri.fromFile(FileManager.getCropHeadFile(this));

        if (width == height) {
            Crop.of(source, destination).asSquare().start(this);
        } else {
            Crop.of(source, destination).withAspect(width, height).start(this);
        }

    }

    /**
     * 处理图片
     *
     * @param photoPath 图片路径
     */
    protected void handlePhoto(String photoPath) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_PATH, photoPath));
        finish();
    }

    /**
     * 获取图片路径
     *
     * @param intent setResult返回的intent
     * @return 图片路径
     */
    public static String getPath(Intent intent) {
        return intent.getStringExtra(EXTRA_PATH);
    }

    /**
     * 启动图片选取界面,本启动方式将截取方形图片
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void start(Activity activity, int requestCode) {
        Intent starter = new Intent(activity, BaseSelectPicActivity.class);
        activity.startActivityForResult(starter, requestCode);
    }

    /**
     * 启动图片选取界面,本启动方式可以截取矩形或者方形，当宽和高相等时截取的是方形，否则是矩形
     *
     * @param activity    Activity
     * @param requestCode 请求码
     * @param width       要截取的宽
     * @param height      要截取的高
     */
    public static void start(Activity activity, int requestCode, int width, int height) {
        Intent starter = new Intent(activity, BaseSelectPicActivity.class);
        starter.putExtra(EXTRA_WIDTH, width);
        starter.putExtra(EXTRA_HEIGHT, height);
        activity.startActivityForResult(starter, requestCode);
    }
}
