package com.xiezhenqi.business.more.fit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.StringUtils;

import butterknife.BindView;

/**
 * CalculateDpiActivity
 * Created by Wesley on 2018/6/7.
 */
public class CalculateDpiActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.edt_size)
    EditText edtSize;
    @BindView(R.id.edt_width_px)
    EditText edtWidthPx;
    @BindView(R.id.edt_height_px)
    EditText edtHeightPx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calc_dpi;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
    }

    public void calc(View view) {
        String size = edtSize.getText().toString();
        String widthPx = edtWidthPx.getText().toString();
        String heightPx = edtHeightPx.getText().toString();

        int wpx = StringUtils.convert2Int(widthPx, 0);
        int hpx = StringUtils.convert2Int(heightPx, 0);

        double anglesPx = Math.sqrt((wpx * wpx + hpx * hpx));

        int result = (int) (anglesPx / Double.parseDouble(size));

        String dpiStr = "";

        if (result >= 640) {
            dpiStr = "密度太高了";
        } else if (result >= 480) {
            dpiStr = "xxxhdpi";
        } else if (result >= 320) {
            dpiStr = "xxhdpi";
        } else if (result >= 240) {
            dpiStr = "xhdpi";
        } else if (result >= 160) {
            dpiStr = "hdpi";
        } else if (result >= 120) {
            dpiStr = "mdpi";
        } else if (result >= 0) {
            dpiStr = "ldpi";
        }

        tvResult.setText("dpi:" + String.valueOf(result) + "," + dpiStr);
    }
}
