package com.vtsoft.bo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * description
 * 视频分类摘要数据
 *
 * @author Garden
 * @version 1.0 create 2023/3/11 22:34
 */
@Data
public class VideoListBo {
    /**
     * 视频分类摘要
     */
    private Rss rss;

    @Data
    public static class Rss {
        /**
         * 版本
         */
        private BigDecimal version;
        /**
         * 视频分类数据
         */
        @Alias("class")
        private VideoClasses videoClasses;

        @Data
        public static class VideoClasses {
            /**
             * 视频分类数据
             */
            @Alias("ty")
            private List<VideoClass> videoClass;

            @Data
            public static class VideoClass {
                /**
                 * 分类 id
                 */
                @Alias("id")
                private Integer typeMid;
                /**
                 * 分类名称
                 */
                @Alias("content")
                private String typeName;
            }
        }
    }
}
