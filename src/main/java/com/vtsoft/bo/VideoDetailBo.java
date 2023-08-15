package com.vtsoft.bo;

import cn.hutool.core.annotation.Alias;
import cn.hutool.json.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/11 22:48
 */
@Data
public class VideoDetailBo {

    /**
     * 视频数据摘要
     */
    private Rss rss;

    @Data
    public static class Rss {
        /**
         * 版本
         */
        private BigDecimal version;
        /**
         * 分页数据
         */
        @Alias("list")
        private DataPageMsg pageMsg;

        @Data
        public static class DataPageMsg {
            /**
             * 当前第几页
             */
            private Integer page;
            /**
             * 每页数据个数
             */
            private Integer pagesize;
            /**
             * 总共有多少页
             */
            @Alias("pagecount")
            private Integer pageCount;
            /**
             * 视频数据
             */
            private List<Video> video;

            @Data
            public static class Video {
                /**
                 * 视频所属分类
                 */
                @Alias("tid")
                private Integer typeId;
                /**
                 * 视频名称
                 */
                @Alias("name")
                private String vodName;
                /**
                 * 视频类型
                 */
                @Alias("type")
                private String vodClass;
                /**
                 * 视频图片
                 */
                @Alias("pic")
                private String vodPic;
                /**
                 * 语言
                 */
                @Alias("lang")
                private String vodLang;
                /**
                 * 区域（视频发布地）
                 */
                @Alias("area")
                private String vodArea;
                /**
                 * 年份
                 */
                @Alias("year")
                private String vodYear;
                /**
                 * 备注（更新至第几集 | HD | 已完结）
                 */
                @Alias("note")
                private String vodRemarks;
                /**
                 * 演员
                 */
                @Alias("actor")
                private String vodActor;
                /**
                 * 导演
                 */
                @Alias("director")
                private String vodDirector;
                /**
                 * 视频播放地址
                 */
                private String playUrls;
                private Object dl;
                /**
                 * 视频描述
                 */
                @Alias("des")
                private String vodContent;
                /**
                 * 最后更新时间
                 */
                @Alias("last")
                private Date lastUpdate;

                public void setDl(Object dl) {
                    this.setPlayUrls((String) ((JSONObject) ((JSONObject) dl).get("dd")).get("content"));
                }

            }
        }
    }
}
