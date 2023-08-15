package com.vtsoft.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * description
 * 视频详情
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 17:43
 */
@Data
public class VideoDetailDto {

    /**
     * 视频 Id
     */
    private Integer id;

    /**
     * 视频所属分类id
     */
    private Integer typeId;

    /**
     * 视频名称
     */
    private String vodName;

    /**
     * 视频分类
     */
    private String vodClass;

    /**
     * 视频图片
     */
    private String vodPic;

    /**
     * 演员
     */
    private String vodActor;

    /**
     * 导演
     */
    private String vodDirector;

    /**
     * 备注（更新至第几集 | HD | 已完结）
     */
    private String vodRemarks;

    /**
     * 区域（视频发布地）
     */
    private String vodArea;

    /**
     * 语言
     */
    private String vodLang;

    /**
     * 年份
     */
    private String vodYear;

    /**
     * 视频描述内容
     */
    private String vodContent;

    /**
     * 播放信息, 第 x 集
     */
    private List<?> episodes;

    /**
     * 播放信息, 播放地址
     */
    private List<?> urls;

    /**
     * 最后更新时间
     */
    private Date lastUpdate;

}
