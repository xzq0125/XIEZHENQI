package com.xiezhenqi.business.more;

import android.content.Context;
import android.content.Intent;

import com.xiezhenqi.business.more.contextlist.ContextMenuRecyclerViewActivity;
import com.xiezhenqi.business.more.drag.DragActivity;
import com.xiezhenqi.business.more.dragview.DragViewActivity;
import com.xiezhenqi.business.more.galleryviewpager.GalleryViewPagerActivity;
import com.xiezhenqi.business.more.lazyload.LazyLoadingActivity;
import com.xiezhenqi.business.more.live.phone.PhoneLiveListActivity;
import com.xiezhenqi.business.more.mazing.IDActivity;
import com.xiezhenqi.business.more.moveview.MoveViewActivity;
import com.xiezhenqi.business.more.order.OrderActivity;
import com.xiezhenqi.business.more.progress.ProgressBarActivity;
import com.xiezhenqi.business.more.toast.ToastActivity;
import com.xiezhenqi.business.more.watermark.WatermarkActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItemList
 * Created by sean on 2016/11/2.
 */
public class ItemList {

    private List<Map<String, Object>> items;
    private final List<Item> itemList = new ArrayList<>();

    public List<Map<String, Object>> init() {
        if (items == null)
            items = new ArrayList<>();

        initItemCount();

        for (Item item : itemList) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", item.title);
            map.put("item", item);
            items.add(map);
        }

        return items;
    }

    public static class Item {
        public final String title;
        public final Class<?> cls;

        public Item(String title, Class<?> cls) {
            this.title = title;
            this.cls = cls;
        }

        public void startActivity(Context context) {
            context.startActivity(new Intent(context, cls).putExtra("title", title));
        }
    }

    private void initItemCount() {
        itemList.add(new Item("上下文RecyclerView", ContextMenuRecyclerViewActivity.class));
        itemList.add(new Item("Drag(拖拽)Activity", DragActivity.class));
        itemList.add(new Item("ProgressBarActivity", ProgressBarActivity.class));
        itemList.add(new Item("WatermarkActivity", WatermarkActivity.class));
        itemList.add(new Item("Fragment懒加载", LazyLoadingActivity.class));
        itemList.add(new Item("IDActivity", IDActivity.class));
        itemList.add(new Item("ID导航", OrderActivity.class));
        itemList.add(new Item("MoveViewActivity", MoveViewActivity.class));
        itemList.add(new Item("GalleryViewPagerActivity", GalleryViewPagerActivity.class));
        itemList.add(new Item("ToastActivity", ToastActivity.class));
        itemList.add(new Item("手机直播列表", PhoneLiveListActivity.class));
        itemList.add(new Item("DragViewActivity", DragViewActivity.class));
    }

}
