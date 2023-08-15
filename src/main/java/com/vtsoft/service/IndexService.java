package com.vtsoft.service;

import org.springframework.ui.Model;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 18:05
 */
public interface IndexService {
    /**
     * 处理首页 model 数据
     *
     * @param model       model
     * @param jumpAddress 跳转地址 - 即当前前端访问地址
     * @return 页面跳转地址
     */
    String handlerModelAttribute(Model model, String jumpAddress);
}
