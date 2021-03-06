package com.xiezhenqi.business.more;

import android.content.Context;
import android.content.Intent;

import com.xiezhenqi.business.more.cling.ClingBarActivity;
import com.xiezhenqi.business.more.cling2.suspensionbar.MainActivity;
import com.xiezhenqi.business.more.cling2.suspensionbar.MultiActivity;
import com.xiezhenqi.business.more.contextlist.ContextMenuRecyclerViewActivity;
import com.xiezhenqi.business.more.drag.DragActivity;
import com.xiezhenqi.business.more.dragview.DragViewActivity;
import com.xiezhenqi.business.more.fit.CalculateDpiActivity;
import com.xiezhenqi.business.more.fit.FitActivity;
import com.xiezhenqi.business.more.galleryviewpager.GalleryViewPagerActivity;
import com.xiezhenqi.business.more.inflate.InflateActivity;
import com.xiezhenqi.business.more.largedialog.LargeDialogActivity;
import com.xiezhenqi.business.more.lazyload.LazyLoadingActivity;
import com.xiezhenqi.business.more.live.phone.PhoneLiveListActivity;
import com.xiezhenqi.business.more.mazing.IDActivity;
import com.xiezhenqi.business.more.moveview.MoveViewActivity;
import com.xiezhenqi.business.more.mvp.MvpActivity;
import com.xiezhenqi.business.more.order.OrderActivity;
import com.xiezhenqi.business.more.permission.MPermissionActivity;
import com.xiezhenqi.business.more.progress.ProgressBarActivity;
import com.xiezhenqi.business.more.pulldownrefresh.PullDownRefreshActivity;
import com.xiezhenqi.business.more.record.RecordActivity;
import com.xiezhenqi.business.more.richtext.RichTextActivity;
import com.xiezhenqi.business.more.selectcity.SelectCityActivity;
import com.xiezhenqi.business.more.toast.ToastActivity;
import com.xiezhenqi.business.more.watermark.WatermarkActivity;
import com.xiezhenqi.business.more.zxing.ZxingActivity;
import com.xiezhenqi.newmvp.act.RXMVPActivity;

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
        itemList.add(new Item("PhoneLiveListActivity", PhoneLiveListActivity.class));
        itemList.add(new Item("DragViewActivity", DragViewActivity.class));
        itemList.add(new Item("RichTextActivity", RichTextActivity.class));
        itemList.add(new Item("ClingBarActivity", ClingBarActivity.class));
        itemList.add(new Item("RecyclerView吸附栏1", MainActivity.class));
        itemList.add(new Item("RecyclerView吸附栏2", MultiActivity.class));
        itemList.add(new Item("RecordActivity", RecordActivity.class));
        itemList.add(new Item("LargeDialogActivity", LargeDialogActivity.class));
        itemList.add(new Item("PullDownRefreshActivity", PullDownRefreshActivity.class));
        itemList.add(new Item("SelectCityActivity", SelectCityActivity.class));
        itemList.add(new Item("ZxingActivity", ZxingActivity.class));
        itemList.add(new Item("MPermissionActivity", MPermissionActivity.class));
        itemList.add(new Item("MvpActivity", MvpActivity.class));
        itemList.add(new Item("FitActivity", FitActivity.class));
        itemList.add(new Item("RXMVPActivity", RXMVPActivity.class));
        itemList.add(new Item("CalculateDpiActivity", CalculateDpiActivity.class));
        itemList.add(new Item("InflateActivity", InflateActivity.class));
    }

}
