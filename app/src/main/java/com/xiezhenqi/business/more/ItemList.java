package com.xiezhenqi.business.more;

import android.content.Context;
import android.content.Intent;

import com.xiezhenqi.business.more.date.DateActivity;

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
        itemList.add(new Item("DateActivity", DateActivity.class));
    }

}