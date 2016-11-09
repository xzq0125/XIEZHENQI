package com.xiezhenqi.business.more.order.homepage;

/**
 * C端首页数据块中的数据项
 * Created by sean on 2016/1/15.
 */
public class ItemDto {

    public String img;//此数据项显示的图片   每个图片末尾有 该图片的宽高，请自行适配 例如：http://xxx/aaa_750x750.jpg 宽高为 750 x 750

    /**
     * h5：打开html5页面
     * store：打开餐厅mini blog
     * dish：打开美食详情（卡片）
     * index：前往瀑布流
     * 注意事项：（target=store，打开id对应的餐厅mini blog target=dish，打开id对应的美食详情）
     */
    public String target;//当前项的目标类型
    public String name;//此数据项显示的名称 上层block中type=餐厅 | 美食，此列有值
    public String description;//此数据项显示的简介 上层block中type=餐厅 | 美食，此列有值
    public String logo;//此数据项的logo图片地址（如果有）	 上层block中type=餐厅，此列有值
    public String url;//打开 html5 页面的地址 当target=h5时有值 注意：不是直接打开此url，而是APP将封签信息追加到url，再打开地址
    /**
     * 1：当target=index时：前往瀑布流页面，默认选中的tag列表；多个以英文逗号分隔
     * 瀑布流列表（刷新、分页）接口中的tags参数。
     * 例子：10001,20001,20005
     * 2：当target=h5，并且canShare=1：表示 获取分享具体内容 中的 type参数值
     * 固定值：newIndexHtml5
     */
    public String targetParams;// 此数据项 附带的参数值 不同的target，作用不一样

    public long targetId;// 数据项的ID 当target=store | dish 时，有此值  target=store，指storeId  target=dish，指dishKey其他情况 等于 0
    public int itemId;//当前数据项的ID
    public int bid;

    public int unitFee;//单价
    public int price;//展示的金额
    public int originalPrice;//展示的原价 单位：分，小于等于0表示不显示‘原价’

    public boolean fav;//是否收藏了此数据项
    public int canShare;//是否可分享 当target=h5时，才需要用到，指示打开html5页面的右上角，是否出现分享按钮其他target类型请忽略此值

    public boolean underControl;

    //以下字段为客户端添加并赋值，赋值参数参照 HomePageDto  title和type
    public String clientTitle;//块标题  (有可能没有标题，例如广告)
    public String clientType;//块类型

}
