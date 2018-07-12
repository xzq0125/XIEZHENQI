package com.xiezhenqi.newmvp;

import java.util.List;

/**
 * ShopBean
 * Created by Wesley on 2018/7/10.
 */
public class ShopBean {

    /**
     * listCount : 4
     * list : [{"user_id":"209","shop_id":"969","face":"http://file.vipbendi.com/attachs/2017/10/21/thumb_59eab141a8131.jpeg","shop_logo":"http://file.vipbendi.com/attachs/2017/10/21/thumb_59eab0d067fb1.jpeg","view":"29909","cate_hangye_id":"716","cate_hangye":"IT互联网","cate_id":"826","cate_id_name":"互联网","contact":"本地网创始人-本地帮帮主","shop_name":"本地网创始人_本地帮帮主","name":"1 谭炯欢-本地网创始人_本地帮帮主","sex":"1","is_gang":"1","gang_level_name":"帮主","fans_count":"4","account_type":"4"},{"user_id":"1166","shop_id":"328","face":"http://file.vipbendi.com/attachs/2017/03/31/thumb_58dda991e7842.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/06/08/thumb_5939110c281e8.jpg","view":"2971","cate_hangye_id":"716","cate_hangye":"IT互联网","cate_id":"827","cate_id_name":"电子商务","contact":"本地网","shop_name":"本地网电子商务有限公司","name":"2 -本地网电子商务有限公司","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":"1","account_type":"4"},{"user_id":"291","shop_id":"1079","face":"http://file.vipbendi.com/attachs/2017/02/11/thumb_589e6620d52b7.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/02/25/thumb_58b0f5bc2d81d.jpg","view":"50","cate_hangye_id":"","cate_hangye":null,"cate_id":"396","cate_id_name":null,"contact":"席鹏6","shop_name":"飞利浦","name":"3 -飞利浦","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"313","shop_id":"1084","face":"http://file.vipbendi.com/attachs/2017/02/12/thumb_589fc420da9d5.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/02/25/thumb_58b1189b3f79c.jpg","view":"14","cate_hangye_id":"","cate_hangye":null,"cate_id":"396","cate_id_name":null,"contact":"富士山","shop_name":"富士山","name":"4 -富士山","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"315","shop_id":"1086","face":"http://file.vipbendi.com/attachs/2017/02/12/thumb_589fc8d30c41c.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/06/17/thumb_594501477cd00.jpg","view":"21","cate_hangye_id":"","cate_hangye":null,"cate_id":"393","cate_id_name":"家电","contact":"奥克斯","shop_name":"奥克斯","name":"5 -奥克斯","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"999","shop_id":"1294","face":"http://file.vipbendi.com/attachs/2017/07/08/thumb_596058c5384c3.JPG","shop_logo":"http://file.vipbendi.com/attachs/2017/04/16/thumb_58f3388aee2a1.jpg","view":"559","cate_hangye_id":"745","cate_hangye":"零售批发","cate_id":"680","cate_id_name":"批发商","contact":"（家家斛）铁皮石斛仿野生种植基地","shop_name":"（家家斛）铁皮石斛仿野生种植基地","name":"6 -（家家斛）铁皮石斛仿野生种植基地","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"1541","shop_id":"1683","face":"http://file.vipbendi.com/attachs/2017/10/30/thumb_59f6f8f7bc596.png","shop_logo":"http://file.vipbendi.com/attachs/2017/10/30/thumb_59f6f92a015c1.png","view":"331","cate_hangye_id":"","cate_hangye":null,"cate_id":"903","cate_id_name":"本地帮","contact":"爱客钉钉","shop_name":"爱客钉钉","name":"7 谭炯欢-爱客钉钉","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"2"},{"user_id":"13411","shop_id":"1771","face":"http://file.vipbendi.com/attachs/2017/09/12/59b792e2d2976.png","shop_logo":"http://file.vipbendi.com/attachs/2017/08/11/thumb_598d137198090.jpg","view":"623","cate_hangye_id":"716","cate_hangye":"IT互联网","cate_id":"828","cate_id_name":"营销推广","contact":"本地帮","shop_name":"本地帮管理运营中心","name":"8 谭炯欢-本地帮管理运营中心","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"1632","shop_id":"1763","face":"http://file.vipbendi.com/attachs/2017/07/09/thumb_59618c1f9d695.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/09/12/thumb_59b79a3143dd8.jpg","view":"41","cate_hangye_id":"","cate_hangye":null,"cate_id":"834","cate_id_name":"地方性","contact":"黄先生","shop_name":"长沙商会","name":"9 -长沙商会","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"},{"user_id":"1633","shop_id":"1764","face":"http://file.vipbendi.com/attachs/2017/07/09/thumb_596199ae8c4fc.jpg","shop_logo":"http://file.vipbendi.com/attachs/2017/07/09/thumb_59619ba13d1ef.jpg","view":"66","cate_hangye_id":"","cate_hangye":null,"cate_id":"837","cate_id_name":"工业园","contact":"李小姐","shop_name":"翠山湖工业园","name":"10 -翠山湖工业园","sex":"0","is_gang":"0","gang_level_name":"认证会员","fans_count":0,"account_type":"4"}]
     * ad : {"adListCount":3,"adList":[{"id":"217","image_url":"http://file.vipbendi.com/attachs/2017/03/24/58d4c44c3b61b.jpg","title":"商家分类广告图","link_url":"http://www.vipbendi.com/mshop/detail/shop_id/1886.html","app_param":{"module":"shop","value":"1886","account_type":2},"adListVersion":"1"},{"id":"218","image_url":"http://file.vipbendi.com/attachs/2017/10/30/59f6ddbf79abb.jpg","title":"只要你会写字-1部手机于疯互联网","link_url":"#","app_param":null,"adListVersion":"1"},{"id":"1117","image_url":"http://file.vipbendi.com/attachs/2017/10/30/59f6ddfd17d1b.jpg","title":"2分钟注册和认证，拥有互关网","link_url":"http://www.vipbendi.com/mindex/downloadpage.html","app_param":null,"adListVersion":"1"}]}
     */

    public int listCount;
    public AdBean ad;
    public List<ListBean> list;

    public static class AdBean {
        /**
         * adListCount : 3
         * adList : [{"id":"217","image_url":"http://file.vipbendi.com/attachs/2017/03/24/58d4c44c3b61b.jpg","title":"商家分类广告图","link_url":"http://www.vipbendi.com/mshop/detail/shop_id/1886.html","app_param":{"module":"shop","value":"1886","account_type":2},"adListVersion":"1"},{"id":"218","image_url":"http://file.vipbendi.com/attachs/2017/10/30/59f6ddbf79abb.jpg","title":"只要你会写字-1部手机于疯互联网","link_url":"#","app_param":null,"adListVersion":"1"},{"id":"1117","image_url":"http://file.vipbendi.com/attachs/2017/10/30/59f6ddfd17d1b.jpg","title":"2分钟注册和认证，拥有互关网","link_url":"http://www.vipbendi.com/mindex/downloadpage.html","app_param":null,"adListVersion":"1"}]
         */

        public int adListCount;
        public List<AdListBean> adList;

        public static class AdListBean {
            /**
             * id : 217
             * image_url : http://file.vipbendi.com/attachs/2017/03/24/58d4c44c3b61b.jpg
             * title : 商家分类广告图
             * link_url : http://www.vipbendi.com/mshop/detail/shop_id/1886.html
             * app_param : {"module":"shop","value":"1886","account_type":2}
             * adListVersion : 1
             */

            public String id;
            public String image_url;
            public String title;
            public String link_url;
            public AppParamBean app_param;
            public String adListVersion;

            public static class AppParamBean {
                /**
                 * module : shop
                 * value : 1886
                 * account_type : 2
                 */

                public String module;
                public String value;
                public int account_type;
            }
        }
    }

    public static class ListBean {
        /**
         * user_id : 209
         * shop_id : 969
         * face : http://file.vipbendi.com/attachs/2017/10/21/thumb_59eab141a8131.jpeg
         * shop_logo : http://file.vipbendi.com/attachs/2017/10/21/thumb_59eab0d067fb1.jpeg
         * view : 29909
         * cate_hangye_id : 716
         * cate_hangye : IT互联网
         * cate_id : 826
         * cate_id_name : 互联网
         * contact : 本地网创始人-本地帮帮主
         * shop_name : 本地网创始人_本地帮帮主
         * name : 1 谭炯欢-本地网创始人_本地帮帮主
         * sex : 1
         * is_gang : 1
         * gang_level_name : 帮主
         * fans_count : 4
         * account_type : 4
         */

        public String user_id;
        public String shop_id;
        public String face;
        public String shop_logo;
        public String view;
        public String cate_hangye_id;
        public String cate_hangye;
        public String cate_id;
        public String cate_id_name;
        public String contact;
        public String shop_name;
        public String name;
        public String sex;
        public String is_gang;
        public String gang_level_name;
        public String fans_count;
        public String account_type;
    }
}
