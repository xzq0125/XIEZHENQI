package com.xiezhenqi.business.more.selectcity;

/**
 * CityDto
 * Created by Wesley on 2018/1/5.
 */

public class CityDto {
    public String city_id;//城市id
    public String name;//广州
    public String pinyin;//guangzhou
    public String first_letter;//首字母
    public String province_id;//省id

    public CityDto(String name, String first_letter) {
        this.name = name;
        this.first_letter = first_letter;
    }

    public CityDto(String city_id, String name, String pinyin,
                   String first_letter, String province_id) {
        this.city_id = city_id;
        this.name = name;
        this.pinyin = pinyin;
        this.first_letter = first_letter;
        this.province_id = province_id;
    }
}
