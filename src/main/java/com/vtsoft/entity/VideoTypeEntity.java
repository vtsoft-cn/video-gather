package com.vtsoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


/**
 * description
 * 视频分类
 *
 * @author Garden
 * @version 1.0 create 2023/3/5 22:36
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@Comment("视频分类")
@Table(name = "VIDEO_TYPE")
@AllArgsConstructor
public class VideoTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("分类名称")
    @Column(name = "TYPE_NAME", columnDefinition = "VARCHAR")
    private String typeName;

    @Comment("分类名称英文")
    @Column(name = "TYPE_NAME_EN", columnDefinition = "VARCHAR")
    private String typeNameEn;

    @Comment("分类排序")
    @Column(name = "TYPE_SORT", columnDefinition = "TINYINT")
    private Integer typeSort;

    @Comment("所属分类（对应视频采集网站分类id）")
    @Column(name = "TYPE_MID", columnDefinition = "INT")
    private Integer typeMid;

    @Comment("父类id")
    @Column(name = "TYPE_PID", columnDefinition = "INT")
    private Integer typePid;

    @Comment("图标")
    @Column(name = "ICON", columnDefinition = "VARCHAR")
    private String icon;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        VideoTypeEntity that = (VideoTypeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
