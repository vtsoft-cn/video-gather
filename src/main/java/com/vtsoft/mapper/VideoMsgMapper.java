package com.vtsoft.mapper;

import com.vtsoft.dto.VideoDetailDto;
import com.vtsoft.entity.VideoVodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 17:56
 */
@Mapper
public interface VideoMsgMapper {

    VideoMsgMapper INSTANCE = Mappers.getMapper(VideoMsgMapper.class);

    /**
     * VideoVodEntity parser VideoDetailDto
     *
     * @param videoVodEntity videoVodEntity
     * @param isAddUrls      是否需要视频的播放地址
     * @param isAddEpisodes  是否需要视频的集数
     * @return VideoDetailDto
     */
    default VideoDetailDto convert(VideoVodEntity videoVodEntity, boolean isAddUrls, boolean isAddEpisodes) {
        VideoDetailDto convert = this.convert(videoVodEntity);
        if (!isAddUrls) {
            convert.getUrls().clear();
        }
        if (!isAddEpisodes) {
            convert.getEpisodes().clear();
        }
        return convert;
    }

    /**
     * VideoVodEntity parser VideoDetailDto
     *
     * @param videoVodEntity videoVodEntity
     * @return VideoDetailDto
     */
    @Mapping(target = "urls", expression = "java(cn.hutool.json.JSONUtil.parseObj(videoVodEntity.getVodPlayUrl()).get(\"urls\", java.util.List.class))")
    @Mapping(target = "episodes", expression = "java(cn.hutool.json.JSONUtil.parseObj(videoVodEntity.getVodPlayUrl()).get(\"episodes\", java.util.List.class))")
    VideoDetailDto convert(VideoVodEntity videoVodEntity);

    /**
     * VideoVodEntity parser VideoDetailDto
     *
     * @param videoVodList  VideoVodEntity
     * @param isAddUrls     是否需要视频的播放地址
     * @param isAddEpisodes 是否需要视频的集数
     * @return list
     */
    default List<VideoDetailDto> convert(List<VideoVodEntity> videoVodList, boolean isAddUrls, boolean isAddEpisodes) {
        return videoVodList.stream().map(item -> this.convert(item, isAddUrls, isAddEpisodes)).collect(Collectors.toList());
    }

}
