package com.vtsoft.controller;

import com.vtsoft.service.VideoMsgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/2/18 15:19
 */
@RestController
@RequestMapping("api")
public class DataApiController {

    private final VideoMsgService videoMsgService;

    public DataApiController(VideoMsgService videoMsgService) {
        this.videoMsgService = videoMsgService;
    }

    /**
     * 视频a的详细播放数据-即视频播放地址
     */
    @GetMapping("videoMsg")
    public String videoMsg(@RequestParam Integer videoId, @RequestParam Integer episode) {
        return videoMsgService.getVideoDataMsg(videoId, episode);
    }

    /**
     * 视频播放数据
     */
    @GetMapping(value = "videoPlayData", produces = "application/x-mpegurl")
    public String videoPlayData(@RequestParam String fileName) {
        return videoMsgService.getVideoPlayData(fileName);
    }

}
