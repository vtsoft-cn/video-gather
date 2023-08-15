package com.vtsoft.constant;

/**
 * description
 * 常量池
 *
 * @author Garden
 * @version 1.0 create 2023/6/27 23:28
 */
public class Constant {

    public static final String URL = "url";
    public static final String NOT_FOUNT = "notFount";

    /**
     * 首页页面所需数据
     */
    public static class Base {
        /**
         * 视频详情数据
         */
        public static final String VIDEO_DETAIL = "videoDetail";
        /**
         * 视频分类
         */
        public static final String VIDEO_TYPES = "videoTypes";
        /**
         * 是否是首页
         */
        public static final String IS_HOME = "isHome";
        /**
         * 幻灯片数据
         */
        public static final String SLIDE = "slide";
        /**
         * 当前访问的是什么类型的分类
         */
        public static final String SITE_NAME = "siteName";
        /**
         * 搜索的名称
         */
        public static final String SEARCH_NAME = "searchName";
        /**
         * 视频列表数据
         */
        public static final String VOD_LIST = "vodList";
    }

    /**
     * 跳转地址
     */
    public static class Skip {
        /**
         * 首页
         */
        public static final String INDEX = "index";
        /**
         * 每个视频类别对应的视频数据
         */
        public static final String VOD_SHOW = "vod/show";
        /**
         * 当前视频详情页面
         */
        public static final String VOD_DETAIL = "vod/detail";
        /**
         * 视频播放地址
         */
        public static final String VOD_PLAY = "vod/play";

        /**
         * 视频播放地址
         */
        public static final String VOD_SEARCH = "vod/search";
        /**
         * 未找到该页面
         */
        public static final String NOT_FOUNT = "public/404";
    }

    /**
     * 视频播放信息
     */
    public static class Play {
        /**
         * 第几集
         */
        public static final String EPISODE = "episode";
    }

}
