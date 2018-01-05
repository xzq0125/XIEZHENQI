package com.xiezhenqi.business.more.selectcity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * DBHelper
 * Created by Wesley on 2018/1/5.
 */

public class DBHelper {
    private static final String ASSETS_NAME = "city.db";
    private static final String DB_NAME = "city.db";
    private static final String TABLE_NAME = "city";
    private static final String CITY_ID = "city_id";
    private static final String NAME = "name";
    private static final String PINYIN = "pinyin";
    private static final String FIRST_LETTER = "first_letter";
    private static final String PROVINCE_ID = "province_id";
    private static final int BUFFER_SIZE = 1024 * 2;
    private String DB_PATH;
    private Context mContext;

    public static DBHelper getInstance(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.copyDBFile();
        return dbHelper;
    }

    private DBHelper(Context context) {
        this.mContext = context;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void copyDBFile() {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CityDto> getAllCities() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        List<CityDto> result = query(cursor);
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    public List<CityDto> searchCity(final String keyword) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where name like \"%" + keyword
                + "%\" or pinyin like \"%" + keyword + "%\"", null);
        List<CityDto> result = query(cursor);
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    private List<CityDto> query(Cursor cursor) {
        List<CityDto> result = new ArrayList<>();
        CityDto city;
        while (cursor.moveToNext()) {
            String city_id = cursor.getString(cursor.getColumnIndex(CITY_ID));
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            String first_letter = cursor.getString(cursor.getColumnIndex(FIRST_LETTER));
            String province_id = cursor.getString(cursor.getColumnIndex(PROVINCE_ID));
            city = new CityDto(city_id, name, pinyin, first_letter, province_id);
            result.add(city);
        }
        cursor.close();
        return result;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<CityDto> {
        @Override
        public int compare(CityDto o1, CityDto o2) {
            String a = o1.first_letter;
            String b = o2.first_letter;
            return a.compareTo(b);
        }
    }
}
