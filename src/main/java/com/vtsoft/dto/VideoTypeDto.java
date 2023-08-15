package com.vtsoft.dto;

import lombok.Data;

/**
 * description
 * 视频分类
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 18:13
 */
@Data
public class VideoTypeDto {

    /**
     * id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 分类名称英文
     */
    private String typeNameEn;

    /**
     * 分类排序
     */
    private Integer typeSort;

    /**
     * 所属分类（对应视频采集网站分类id）
     */
    private Integer typeMid;

    /**
     * 父类id
     */
    private Integer typePid;

    /**
     * 图标
     */
    private String icon;

    /**
     * 分类是否有选中显示 如果有 则 值为 active
     */
    private String classStyle;

}
