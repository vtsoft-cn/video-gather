package com.vtsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * description <br/>
 * 启动类
 *
 * @author Garden
 * @version 1.0 create 2022/12/31 22:58
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class VtSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(VtSoftApplication.class, args);
    }

}
