package com.vtsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * description
 * 视频数据
 *
 * @author Garden
 * @version 1.0 create 2023/3/5 22:50
 */
@Data
@Entity
@Builder
@Table(name = "VIDEO_VOD")
@AllArgsConstructor
@NoArgsConstructor
public class VideoVodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE_ID", columnDefinition = "INT comment '视频所属分类id'")
    private Integer typeId;

    @Column(name = "VOD_NAME", columnDefinition = "VARCHAR comment '视频名称'")
    private String vodName;

    @Column(name = "VOD_CLASS", columnDefinition = "VARCHAR comment '视频分类'")
    private String vodClass;

    @Column(name = "VOD_PIC", columnDefinition = "VARCHAR comment '视频图片'")
    private String vodPic;

    @Column(name = "VOD_ACTOR", columnDefinition = "VARCHAR comment '演员'")
    private String vodActor;

    @Column(name = "VOD_DIRECTOR", columnDefinition = "VARCHAR comment '导演'")
    private String vodDirector;

    @Column(name = "VOD_REMARKS", columnDefinition = "VARCHAR comment '备注（更新至第几集 | HD | 已完结）'")
    private String vodRemarks;

    @Column(name = "VOD_AREA", columnDefinition = "VARCHAR comment '区域（视频发布地）'")
    private String vodArea;

    @Column(name = "VOD_LANG", columnDefinition = "VARCHAR comment '语言'")
    private String vodLang;

    @Column(name = "VOD_YEAR", columnDefinition = "VARCHAR comment '年份'")
    private String vodYear;

    @Column(name = "VOD_CONTENT", columnDefinition = "VARCHAR comment '视频描述内容'")
    private String vodContent;

    @Column(name = "VOD_PLAY_URL", columnDefinition = "VARCHAR comment '播放地址信息'")
    private String vodPlayUrl;

    @Column(name = "LAST_UPDATE", columnDefinition = "VARCHAR comment '最后更新时间'")
    private Date lastUpdate;

}
