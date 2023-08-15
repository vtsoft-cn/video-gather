package com.vtsoft.controller;

import com.vtsoft.service.IndexService;
import com.vtsoft.service.VideoMsgService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description
 * 视频数据通用数据处理
 *
 * @author Garden
 * @version 1.0 create 2023/2/18 13:31
 */
@Controller
@RequestMapping("video")
public class VideoController {
    /**
     * 首页加载所需基本数据处理
     */
    private final IndexService indexService;

    /**
     * 视频数据
     */
    private final VideoMsgService videoMsgService;

    public VideoController(IndexService indexService, VideoMsgService videoMsgService) {
        this.indexService = indexService;
        this.videoMsgService = videoMsgService;
    }

    /**
     * 视频分类首页
     */
    @GetMapping("{type}")
    public String index(@PathVariable String type, Model model) {
        return indexService.handlerModelAttribute(model, type);
    }

    /**
     * 视频详情页
     *
     * @param videoId 视频 id
     * @param model   页面展示所需数据
     */
    @RequestMapping("detail")
    public String videoDetail(@RequestParam Integer videoId, Model model) {
        return videoMsgService.getVideoDetailMsg(videoId, model);
    }

    /**
     * 视频模糊搜索
     *
     * @return 查询出来的视频信息，进行返回
     */
    @GetMapping(value = "search")
    public String videoSearch(@RequestParam String wd, Model model) {
        return videoMsgService.searchVideoMsg(wd, model);
    }

    /**
     * 视频播放页
     */
    @RequestMapping("play")
    public String videoIndex(@RequestParam Integer videoId, @RequestParam Integer episode, Model model) {
        return videoMsgService.getVideoPlayData(videoId, episode, model);
    }

}
