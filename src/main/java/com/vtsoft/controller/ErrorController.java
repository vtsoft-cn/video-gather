package com.vtsoft.controller;

import com.vtsoft.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description
 * 异常页面跳转
 *
 * @author Garden
 * @version 1.0 create 2023/2/18 14:59
 */
@Controller
public class ErrorController {

    @GetMapping("notFount")
    public String index() {
        return Constant.Skip.NOT_FOUNT;
    }

}
