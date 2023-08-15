package com.vtsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * description
 * 视频分类
 *
 * @author Garden
 * @version 1.0 create 2023/3/5 22:36
 */
@Data
@Entity
@Builder
@Table(name = "VIDEO_TYPE")
@AllArgsConstructor
@NoArgsConstructor
public class VideoTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE_NAME", columnDefinition = "VARCHAR comment '分类名称'")
    private String typeName;

    @Column(name = "TYPE_NAME_EN", columnDefinition = "VARCHAR comment '分类名称英文'")
    private String typeNameEn;

    @Column(name = "TYPE_SORT", columnDefinition = "TINYINT comment '分类排序'")
    private Integer typeSort;

    @Column(name = "TYPE_MID", columnDefinition = "INT comment '所属分类（对应视频采集网站分类id）'")
    private Integer typeMid;

    @Column(name = "TYPE_PID", columnDefinition = "INT comment '父类id'")
    private Integer typePid;

    @Column(name = "ICON", columnDefinition = "VARCHAR comment '图标'")
    private String icon;

}
