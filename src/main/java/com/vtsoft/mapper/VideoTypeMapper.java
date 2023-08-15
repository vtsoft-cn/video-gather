package com.vtsoft.mapper;

import cn.hutool.core.util.StrUtil;
import com.vtsoft.dto.VideoTypeDto;
import com.vtsoft.entity.VideoTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 18:12
 */
@Mapper
public interface VideoTypeMapper {

    VideoTypeMapper INSTANCE = Mappers.getMapper(VideoTypeMapper.class);

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoTypes   VideoTypeEntity
     * @param activeByName 当前访问的视频分类名称
     * @return list
     */
    default List<VideoTypeDto> convert(List<VideoTypeEntity> videoTypes, String activeByName) {
        return videoTypes.stream().map(item -> this.convert(item, activeByName)).collect(Collectors.toList());
    }

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoTypes VideoTypeEntity
     * @return list
     */
    default List<VideoTypeDto> convert(List<VideoTypeEntity> videoTypes) {
        return videoTypes.stream().map(item -> this.convert(item, StrUtil.EMPTY)).collect(Collectors.toList());
    }

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoTypes VideoTypeEntity
     * @param typeId     视频所属分类id
     * @return list
     */
    default List<VideoTypeDto> convert(List<VideoTypeEntity> videoTypes, Integer typeId) {
        return videoTypes.stream().map(item -> this.convert(item, typeId)).collect(Collectors.toList());
    }

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoType VideoTypeEntity
     * @return VideoTypeDto
     */
    @Mapping(target = "classStyle", expression = "java(\"\")")
    VideoTypeDto convert(VideoTypeEntity videoType);

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoType    VideoTypeEntity
     * @param activeByName 当前访问的视频分类名称
     * @return VideoTypeDto
     */
    default VideoTypeDto convert(VideoTypeEntity videoType, String activeByName) {
        VideoTypeDto convert = this.convert(videoType);
        if (convert.getTypeNameEn().equals(activeByName)) {
            convert.setClassStyle("active");
        }
        return convert;
    }

    /**
     * VideoTypeEntity parser VideoTypeDto
     *
     * @param videoType VideoTypeEntity
     * @param typeId    视频所属分类id
     * @return VideoTypeDto
     */
    default VideoTypeDto convert(VideoTypeEntity videoType, Integer typeId) {
        VideoTypeDto convert = this.convert(videoType);
        if (convert.getId().equals(typeId)) {
            convert.setClassStyle("active");
        }
        return convert;
    }

}
