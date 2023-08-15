package com.vtsoft.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.vtsoft.constant.Constant;
import com.vtsoft.dao.VideoTypeRepository;
import com.vtsoft.dao.VideoVodRepository;
import com.vtsoft.dto.VideoTypeDto;
import com.vtsoft.mapper.VideoMsgMapper;
import com.vtsoft.mapper.VideoTypeMapper;
import com.vtsoft.service.IndexService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description
 * 视频首页数据处理
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 18:05
 */
@Service
public class IndexServiceImpl implements IndexService {

    /**
     * 分类
     */
    private final VideoTypeRepository videoTypeRepository;

    /**
     * 视频数据
     */
    private final VideoVodRepository videoVodRepository;

    public IndexServiceImpl(VideoTypeRepository videoTypeRepository, VideoVodRepository videoVodRepository) {
        this.videoTypeRepository = videoTypeRepository;
        this.videoVodRepository = videoVodRepository;
    }

    /**
     * 处理首页 model 数据
     *
     * @param model       model
     * @param jumpAddress 跳转地址 - 即当前前端访问地址
     * @return 页面跳转地址
     */
    @Override
    public String handlerModelAttribute(Model model, String jumpAddress) {
        boolean isHome = jumpAddress.equals(Constant.Skip.INDEX);
        List<VideoTypeDto> videoTypes = VideoTypeMapper.INSTANCE.convert(videoTypeRepository.findAllByTypePidEqualsOrderByTypeSort(BigDecimal.ZERO.scale()), jumpAddress);
        VideoTypeDto videoType = videoTypes.stream().filter(item -> jumpAddress.equals(item.getTypeNameEn())).findFirst().orElse(new VideoTypeDto());

        // 处理返回数据
        model.addAllAttributes(
                Map.of(
                        Constant.Base.IS_HOME, isHome,
                        Constant.Base.VIDEO_TYPES, videoTypes,
//                        Constant.Base.SLIDE, isHome ? List.of() : List.of(),
                        Constant.Base.SITE_NAME, Optional.ofNullable(videoType.getTypeName()).orElse(StrUtil.EMPTY),
                        Constant.Base.VOD_LIST, VideoMsgMapper.INSTANCE.convert(isHome ? videoVodRepository.getVideoVodHot() : videoVodRepository.findAllByTypeIdIn(this.getVideoChildrenTypeMid(videoType)), false, false)
                )
        );
        return isHome ? Constant.Skip.INDEX : Constant.Skip.VOD_SHOW;
    }

    /**
     * 获取 当前需要显示的子分类 的 id list
     *
     * @param videoType 父分类
     * @return 分类 id list : typeMid
     */
    private List<Integer> getVideoChildrenTypeMid(VideoTypeDto videoType) {
        // 非顶级级分类
        List<VideoTypeDto> nonTopLevelTypes = VideoTypeMapper.INSTANCE.convert(videoTypeRepository.findAllByTypePidNotIn(List.of(BigDecimal.ZERO.scale())));
        // 存储 id
        List<Integer> typeMid = Lists.newArrayList(videoType.getTypeMid());
        // 递归 获取当前显示的子分类
        this.recursiveCollectionMid(typeMid, nonTopLevelTypes, videoType.getId());
        return typeMid;
    }

    /**
     * 递归获取id
     *
     * @param typeMid          类型 id
     * @param nonTopLevelTypes 不是顶级类型集合
     * @param id               父分类 id
     */
    private void recursiveCollectionMid(List<Integer> typeMid, List<VideoTypeDto> nonTopLevelTypes, Integer id) {
        // 找到 typeMid 在 nonTopLevelTypes 中 所有的子分类
        List<VideoTypeDto> collect = nonTopLevelTypes.stream().filter(item -> item.getTypePid().equals(id)).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            return;
        }
        // 获取所有的分类
        Map<Integer, Integer> typeMidList = collect.stream().collect(Collectors.toMap(VideoTypeDto::getId, VideoTypeDto::getTypeMid));
        typeMid.addAll(typeMidList.values());
        // 遍历所有子分类
        typeMidList.keySet().forEach(item -> this.recursiveCollectionMid(typeMid, nonTopLevelTypes, item));
    }

}
