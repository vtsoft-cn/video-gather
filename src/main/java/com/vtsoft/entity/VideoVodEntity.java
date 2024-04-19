package com.vtsoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.h2.expression.Parameter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

/**
 * description
 * 视频数据
 *
 * @author Garden
 * @version 1.0 create 2023/3/5 22:50
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@Comment("视频数据")
@Table(name = "VIDEO_VOD")
@AllArgsConstructor
public class VideoVodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("视频所属分类id")
    @Column(name = "TYPE_ID", columnDefinition = "INT")
    private Integer typeId;

    @Comment("视频名称")
    @Column(name = "VOD_NAME", columnDefinition = "VARCHAR")
    private String vodName;

    @Comment("视频分类")
    @Column(name = "VOD_CLASS", columnDefinition = "VARCHAR")
    private String vodClass;

    @Comment("视频图片")
    @Column(name = "VOD_PIC", columnDefinition = "VARCHAR")
    private String vodPic;

    @Comment("演员")
    @Column(name = "VOD_ACTOR", columnDefinition = "VARCHAR")
    private String vodActor;

    @Comment("导演")
    @Column(name = "VOD_DIRECTOR", columnDefinition = "VARCHAR")
    private String vodDirector;

    @Comment("备注（更新至第几集 | HD | 已完结）")
    @Column(name = "VOD_REMARKS", columnDefinition = "VARCHAR")
    private String vodRemarks;

    @Comment("区域（视频发布地）")
    @Column(name = "VOD_AREA", columnDefinition = "VARCHAR")
    private String vodArea;

    @Comment("语言")
    @Column(name = "VOD_LANG", columnDefinition = "VARCHAR")
    private String vodLang;

    @Comment("年份")
    @Column(name = "VOD_YEAR", columnDefinition = "VARCHAR")
    private String vodYear;

    @Comment("视频描述内容")
    @Column(name = "VOD_CONTENT", columnDefinition = "VARCHAR")
    private String vodContent;

    @Comment("播放地址信息")
    @Column(name = "VOD_PLAY_URL", columnDefinition = "VARCHAR")
    private String vodPlayUrl;

    @Comment("最后更新时间")
    @Column(name = "LAST_UPDATE", columnDefinition = "VARCHAR")
    private Date lastUpdate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        VideoVodEntity that = (VideoVodEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
