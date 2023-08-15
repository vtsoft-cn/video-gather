package com.vtsoft.dao;

import com.vtsoft.entity.VideoTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * createDate: 2023/03/07 <br>
 * description:  <br>
 * 视频分类数据查询
 *
 * @author : YuanWenHao <br>
 * @version : v2.1.1 <br>
 */
public interface VideoTypeRepository extends JpaRepository<VideoTypeEntity, Integer> {

    /**
     * 根据 父 id 查找 子分类数据
     *
     * @param pId 父分类 id
     * @return 子分类数据
     */
    List<VideoTypeEntity> findAllByTypePidEqualsOrderByTypeSort(Integer pId);

    /**
     * 根据不是一级分类，查找子分类
     *
     * @param pId 父分类 id
     * @return 查询到的视频数据
     */
    List<VideoTypeEntity> findAllByTypePidNotIn(List<Integer> pId);
}
