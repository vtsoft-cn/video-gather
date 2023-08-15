package com.vtsoft.service;

import org.springframework.ui.Model;

/**
 * description
 * 视频信息数据获取
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 17:37
 */
public interface VideoMsgService {
    /**
     * 获取视频数据 通过 id
     *
     * @param videoId 视频 id
     * @param model   返回的数据
     * @return 跳转地址
     */
    String getVideoDetailMsg(Integer videoId, Model model);

    /**
     * 获取视频播放信息数据
     *
     * @param videoId 视频 id
     * @param episode 第几集
     * @param model   返回的数据
     * @return 跳转地址
     */
    String getVideoPlayData(Integer videoId, Integer episode, Model model);

    /**
     * 通过视频id 以及 视频第几集 获取播放数据
     *
     * @param videoId 视频id
     * @param episode 第几集
     * @return 播放数据 - 视频播放 URL
     */
    String getVideoDataMsg(Integer videoId, Integer episode);

    /**
     * 视频模糊查询
     *
     * @param wd    查询关键字
     * @param model 返回的数据
     * @return 搜索出的视频名称
     */
    String searchVideoMsg(String wd, Model model);

    /**
     * 获取视频播放数据
     *
     * @param fileName 视频文件名
     * @return 播放数据 - 视频播放 URL
     */
    String getVideoPlayData(String fileName);
}
