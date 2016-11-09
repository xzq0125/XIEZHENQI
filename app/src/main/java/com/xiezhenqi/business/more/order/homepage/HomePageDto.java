package com.xiezhenqi.business.more.order.homepage;

import java.util.List;

/**
 * C端首页数据实体
 * Created by sean on 2016/1/15.
 */
public class HomePageDto {
    public int bid;
    public String title;//块标题  (有可能没有标题，例如广告)

    /**
     * adver：广告
     * store：餐厅列表
     * dish：美食列表
     * 广告：当items中只有一个项目时，不需要轮播；有多个项目时，以轮播的形式展现
     * 轮播频率为固定值，暂定为5秒。
     * 如果遇到不认识的type，请跳过此块（block）的展示，主要考虑旧版本兼容性
     */
    public String type;//块类型

    /**
     * 如果是单张广告，那么items中size = 1
     * APP需要判定，如果items.size <= 0，直接忽略当前块（block），一般不会出现
     */
    public List<ItemDto> items;//块中的数据项

    public String action;//点击块标题的动作 h5：打开html5页面 index：前往瀑布流 空字符串，表示标题不可点击
    public String url;//打开 html5 页面的地址  当action=h5时有值注意：不是直接打开此url，而是APP将封签信息追加到url，再打开地址


    /**
     * 1：当action=index时：前往瀑布流页面，默认选中的tag列表；多个以英文逗号分隔
     * 瀑布流列表（刷新、分页）接口中的tags参数。
     * 例子：10001,20001,20005
     * 2：当action=h5，并且canShare=true时：表示 获取分享具体内容 中的 type参数值
     * 固定值：newIndexBlockHtml5
     * 同时bid 要作为 id 上传
     */
    public String actionParams;//点击标题，所附带的参数值不同的动作，作用不一样

    public short canShare;//是否可分享 是否可分享 1:是 0:否	 当action=h5时，才需要用到，指示打开html5页面的右上角，是否出现分享按钮其他target类型请忽略此值

    public boolean needScrollToTop = true;
}
