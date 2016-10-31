package com.xiezhenqi.entity.search;

import java.util.List;

/**
 * SearchSongDto
 * Created by Tse on 2016/10/19.
 */

public class SearchSongDto {

    public int ret_code;

    public PageBean pagebean;

    public static class PageBean {
        public String w;
        public int allPages;
        public int ret_code;
        public int currentPage;
        public String notice;
        public int allNum;
        public int maxResult;
        public List<ContentListBean> contentlist;

        public static class ContentListBean {
            public String m4a;
            public String media_mid;
            public int songid;
            public int singerid;
            public String albumname;
            public String downUrl;
            public String singername;
            public String songname;
            public String albummid;
            public String songmid;
            public String albumpic_big;
            public String albumpic_small;
            public int albumid;
        }
    }
}
