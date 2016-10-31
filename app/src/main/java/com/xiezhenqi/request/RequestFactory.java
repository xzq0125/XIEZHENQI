package com.xiezhenqi.request;


import com.google.gson.reflect.TypeToken;
import com.xiezhenqi.entity.lyric.LyricDto;
import com.xiezhenqi.entity.ResponseDtoEntity;
import com.xiezhenqi.entity.musicpavilion.MusicPavilionDto;
import com.xiezhenqi.entity.search.SearchSongDto;

/**
 * 请求工厂
 * Created by Tse on 2016/10/19.
 */
public class RequestFactory {

    public static final String ADDRESS_SEARCH = "/213-1";//搜索歌曲的地址
    public static final String ADDRESS_GET_LYRIC = "/213-2";//获取歌词
    public static final String ADDRESS_GET_TOPS = "/213-4";//获取排行榜

    /**
     * 搜索歌曲
     *
     * @return 请求
     */
    public static RequestHelper searchSong(String keyword, int page) {
        RequestUrlBuilder urlMaker = new RequestUrlBuilder(ADDRESS_SEARCH);
        urlMaker.addUrlData("keyword", keyword);
        urlMaker.addUrlData("page", page);
        return new RequestHelper(urlMaker, new TypeToken<ResponseDtoEntity<SearchSongDto>>() {
        });
    }

    /**
     * 获取歌词
     *
     * @return 请求
     */
    public static RequestHelper getLyric(int musicId) {
        RequestUrlBuilder urlMaker = new RequestUrlBuilder(ADDRESS_GET_LYRIC);
        urlMaker.addUrlData("musicId", musicId);
        return new RequestHelper(urlMaker, new TypeToken<ResponseDtoEntity<LyricDto>>() {
        });
    }

    /**
     * 获取排行榜
     *
     * @return 请求
     */
    public static RequestHelper getTops(String topid) {
        RequestUrlBuilder urlMaker = new RequestUrlBuilder(ADDRESS_GET_TOPS);
        urlMaker.addUrlData("topid", topid);
        return new RequestHelper(urlMaker, new TypeToken<ResponseDtoEntity<MusicPavilionDto>>() {
        });
    }
}
