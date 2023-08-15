package com.vtsoft.controller;

import com.vtsoft.constant.Constant;
import com.vtsoft.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description
 * 首页
 * @author Garden
 * @version 1.0 create 2023/2/18 13:44
 */
@Controller
public class IndexController {

    /**
     * 首页加载所需基本数据处理
     */
    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * 视频首页
     */
    @GetMapping("")
    public String index(Model model) {
        return indexService.handlerModelAttribute(model, Constant.Skip.INDEX);
    }

}
