package com.vtsoft.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description
 * 视频数据采集配置
 *
 * @author Garden
 * @version 1.0 create 2023/2/23 22:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "video-properties")
public class CollectResourcesConfig {
    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 采集地址
     */
    private String collectionAddress;
}
