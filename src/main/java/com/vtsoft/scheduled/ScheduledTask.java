package com.vtsoft.scheduled;

import cn.hutool.core.io.FileUtil;
import com.vtsoft.service.CollectionService;
import com.vtsoft.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description
 * 视频定时采集
 *
 * @author Garden
 * @version 1.0 create 2023/7/1 12:20
 */
@Slf4j
@Component
public class ScheduledTask {

    private final CollectionService collectionService;

    public ScheduledTask(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * 每天凌晨执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        boolean del = FileUtil.del(FileUtils.M3U8_LOCAL_FILE_PATH);
        log.info("清理缓存视频文件 : {}", del);
        log.info("~~~~~~~~~定时任务开始执行视频资源采集中~~~~~~~~~");
        collectionService.collectionVideoMessages();
        log.info("~~~~~~~~~定时任务执行视频资源采集已完成~~~~~~~~~");
    }
}
