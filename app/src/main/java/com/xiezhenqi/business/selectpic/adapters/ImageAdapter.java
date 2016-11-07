package com.xiezhenqi.business.selectpic.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiezhenqi.R;

import java.util.ArrayList;


/**
 * 照片Adapter
 *
 * @author Mofer
 */
public class ImageAdapter extends BaseAdapter {

    private ArrayList<String> paths = new ArrayList<>();

    @Override
    public int getCount() {
        return paths.size() + 1;
    }

    @Override
    public String getItem(int position) {
        return position == 0 ? null : paths.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        paths.clear();
    }

    public void addAll(ArrayList<String> paths) {
        this.paths.clear();
        this.paths.addAll(paths);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ImageView image;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_selectpicture_picture, null);
            image = (ImageView) convertView.findViewById(R.id.item_iv_picture);
            convertView.setTag(image);
        } else {
            image = (ImageView) convertView.getTag();
        }
        if (position == 0) {
            image.setBackgroundResource(R.color.themeColor);
            image.setImageResource(R.drawable.ic_camera);
        } else {
            image.setBackgroundResource(0);
            image.setImageResource(R.mipmap.ic_launcher);
            Glide.with(parent.getContext()).load("file://" + getItem(position)).into(image);
        }
        return convertView;
    }
}
