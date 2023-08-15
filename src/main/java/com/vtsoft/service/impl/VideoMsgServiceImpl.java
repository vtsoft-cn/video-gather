package com.vtsoft.service.impl;

import cn.hutool.json.JSONUtil;
import com.vtsoft.constant.Constant;
import com.vtsoft.dao.VideoTypeRepository;
import com.vtsoft.dao.VideoVodRepository;
import com.vtsoft.dto.VideoDetailDto;
import com.vtsoft.entity.VideoTypeEntity;
import com.vtsoft.mapper.VideoMsgMapper;
import com.vtsoft.mapper.VideoTypeMapper;
import com.vtsoft.service.VideoMsgService;
import com.vtsoft.utils.FileUtils;
import com.vtsoft.utils.M3U8Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 17:38
 */
@Slf4j
@Service
public class VideoMsgServiceImpl implements VideoMsgService {

    /**
     * 视频详细数据
     */
    private final VideoVodRepository videoVodRepository;

    /**
     * 视频分类数据
     */
    private final VideoTypeRepository videoTypeRepository;

    public VideoMsgServiceImpl(VideoVodRepository videoVodRepository, VideoTypeRepository videoTypeRepository) {
        this.videoVodRepository = videoVodRepository;
        this.videoTypeRepository = videoTypeRepository;
    }

    /**
     * 获取视频数据 通过 id
     *
     * @param videoId 视频 id
     * @param model   返回的数据
     * @return 跳转地址
     */
    @Override
    public String getVideoDetailMsg(Integer videoId, Model model) {
        model.addAllAttributes(this.getVideoDetailBaseMsg(videoId));
        return Constant.Skip.VOD_DETAIL;
    }

    /**
     * 获取视频数据
     *
     * @param videoId 视频 id
     * @return 视频数据
     */
    private Map<String, Object> getVideoDetailBaseMsg(Integer videoId) {
        VideoDetailDto videoDetail = VideoMsgMapper.INSTANCE.convert(videoVodRepository.findById(videoId).orElse(null), false, true);
        log.info("查看视频：{}", videoDetail.getVodName());
        Integer typeId = this.getTopTypeId(videoDetail.getTypeId());
        List<VideoTypeEntity> videoTypeEntities = videoTypeRepository.findAllByTypePidEqualsOrderByTypeSort(BigDecimal.ZERO.scale());
        return Map.of(
                Constant.Base.VIDEO_DETAIL, videoDetail,
                Constant.Base.VIDEO_TYPES, VideoTypeMapper.INSTANCE.convert(videoTypeEntities, typeId),
                Constant.Base.IS_HOME, Boolean.FALSE
        );
    }

    /**
     * 查找最顶层视频分类 id
     */
    private Integer getTopTypeId(Integer typeId) {
        VideoTypeEntity videoTypeEntity = videoTypeRepository.findAll().stream().filter(item -> item.getTypeMid().equals(typeId)).findFirst().orElseThrow();
        if (videoTypeEntity.getTypePid() != 0) {
            return this.getTopTypeId(videoTypeEntity.getTypePid());
        }
        return videoTypeEntity.getId();
    }

    /**
     * 获取视频播放信息数据
     *
     * @param videoId 视频 id
     * @param episode 第几集
     * @param model   返回的数据
     * @return 跳转地址
     */
    @Override
    public String getVideoPlayData(Integer videoId, Integer episode, Model model) {
        Map<String, Object> videoDetailBaseMsg = this.getVideoDetailBaseMsg(videoId);
        Map<String, Object> videoPlayData = new HashMap<>(4) {{
            putAll(videoDetailBaseMsg);
            put(Constant.Play.EPISODE, episode);
        }};
        model.addAllAttributes(videoPlayData);
        return Constant.Skip.VOD_PLAY;
    }

    /**
     * 通过视频id 以及 视频第几集 获取播放数据
     *
     * @param videoId 视频id
     * @param episode 第几集
     * @return 播放数据 - 视频播放 URL
     */
    @Override
    public String getVideoDataMsg(Integer videoId, Integer episode) {
        VideoDetailDto videoDetail = VideoMsgMapper.INSTANCE.convert(videoVodRepository.findById(videoId).orElse(null), true, false);
        log.info("播放视频：{}", videoDetail.getVodName());
        // 拿到视频的播放地址
        Object url = videoDetail.getUrls().get(episode);
        String convert = M3U8Converter.convert(String.valueOf(url));
        return JSONUtil.toJsonStr(Map.of(Constant.URL, convert));
    }

    /**
     * 视频模糊查询
     *
     * @param wd    查询关键字
     * @param model 返回的数据
     * @return 跳转到搜索完成页面
     */
    @Override
    public String searchVideoMsg(String wd, Model model) {
        log.info("查询视频：{}", wd);
        model.addAllAttributes(
                Map.of(
                        Constant.Base.IS_HOME, Boolean.FALSE,
                        Constant.Base.SEARCH_NAME, wd,
                        Constant.Base.VIDEO_TYPES, VideoTypeMapper.INSTANCE.convert(videoTypeRepository.findAllByTypePidEqualsOrderByTypeSort(BigDecimal.ZERO.scale())),
                        Constant.Base.VOD_LIST, VideoMsgMapper.INSTANCE.convert(videoVodRepository.findAllByVodNameIsLike(String.format("%%%s%%", wd)), false, false)
                )
        );
        return Constant.Skip.VOD_SEARCH;
    }

    /**
     * 获取视频播放数据
     *
     * @param fileName 视频文件名
     * @return 播放数据 - 视频播放 URL
     */
    @Override
    public String getVideoPlayData(String fileName) {
        String filePath = FileUtils.M3U8_LOCAL_FILE_PATH + fileName;
        return FileUtils.readFileToString(filePath);
    }
}
