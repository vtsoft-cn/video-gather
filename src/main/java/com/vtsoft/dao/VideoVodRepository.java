package com.vtsoft.dao;

import com.vtsoft.entity.VideoVodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * createDate: 2023/03/07 <br>
 * description:  <br>
 * 视频数据详情查询
 *
 * @author : YuanWenHao <br>
 * @version : v2.1.1 <br>
 */
public interface VideoVodRepository extends JpaRepository<VideoVodEntity, Integer> {

    /**
     * 获取现在最新更新的视频
     *
     * @return 最新更新的视频
     */
    @Query(value = "select * from VIDEO_VOD where VOD_LANG = '国语' order by VOD_YEAR desc, LAST_UPDATE desc limit 100", nativeQuery = true)
    List<VideoVodEntity> getVideoVodHot();

    /**
     * 根据视频 分类 id 查找所有视频
     *
     * @param typeId 分类  id
     * @return 查询到的视频数据
     */
    @Query(value = "select * from VIDEO_VOD where TYPE_ID in (:typeId) order by VOD_YEAR desc, LAST_UPDATE desc limit 100", nativeQuery = true)
    List<VideoVodEntity> findAllByTypeIdIn(List<Integer> typeId);

    /**
     * 模糊查询 视频名称是 wd 的视频数据
     *
     * @param wd 模糊查询的视频名称
     * @return 查询到的视频数据
     */
    @Query(value = "select * from VIDEO_VOD where VOD_NAME like :wd order by VOD_YEAR desc, LAST_UPDATE desc limit 100", nativeQuery = true)
    List<VideoVodEntity> findAllByVodNameIsLike(@Param("wd") String wd);

}
