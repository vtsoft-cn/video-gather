package com.vtsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/7/2 1:04
 */
@Controller
@RequestMapping("videoType")
public class VideoTypeController {

    /**
     * 视频年份
     *
     * @param year 视频年份
     */
    @RequestMapping("year")
    public String year(@RequestParam String year) {
        return "vod/play";
    }

    /**
     * 视频归属
     *
     * @param area 视频归属
     */
    @RequestMapping("attribution")
    public String attribution(@RequestParam String area) {
        return "vod/play";
    }

    /**
     * 视频类别
     *
     * @param clas 视频类别
     */
    @RequestMapping("category")
    public String category(@RequestParam String clas) {
        return "vod/play";
    }

    /**
     * 视频语言
     *
     * @param lang 视频语言
     */
    @RequestMapping("language")
    public String language(@RequestParam String lang) {
        return "vod/play";
    }

}
