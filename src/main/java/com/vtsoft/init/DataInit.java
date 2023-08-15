package com.vtsoft.init;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.system.SystemUtil;
import com.vtsoft.service.CollectionService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * description
 * 首次运行程序数据初始化
 *
 * @author Garden
 * @version 1.0 create 2023/3/5 21:53
 */
@Slf4j
@Component
public class DataInit implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    private final CollectionService collectionService;

    public DataInit(JdbcTemplate jdbcTemplate, CollectionService collectionService) {
        this.jdbcTemplate = jdbcTemplate;
        this.collectionService = collectionService;
    }

    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        log.info("初始化数据.....................");
        String initLock = String.format("%s%s", SystemUtil.getUserInfo().getHomeDir(), ".h2/initLock");
        if (BooleanUtil.isFalse(FileUtil.exist(initLock))) {
            log.info("正在执行数据初始化.....................");
            jdbcTemplate.execute("delete from VIDEO_TYPE");
            log.info("视频分类数据添加中.....................");
            jdbcTemplate.execute(
                    "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT, ICON) values (1, 1, '电影', 'film', 0, 1,'icon-dy-o');" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT, ICON) values (2, 2, '电视剧', 'TV', 0, 2,'icon-tv-o');" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT, ICON) values (3, 25, '综艺', 'VarietyShow', 0, 3,'icon-zy-o');" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT, ICON) values (4, 4, '动漫', 'animation', 0, 4,'icon-dm-o');" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (6, 6, '动作片', 'ActionMovie', 1, 1);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (7, 7, '喜剧片', 'comedy', 1, 2);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (8, 8, '爱情片', 'RomanticFilm', 1, 3);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (9, 9, '科幻片', 'ScienceFictionFilm', 1, 4);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (10, 10, '恐怖片', 'HorrorFilm', 1, 5);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (11, 11, '剧情片', 'FeatureFilm', 1, 6);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (12, 12, '战争片', 'WarFilm', 1, 7);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (13, 13, '国产剧', 'DomesticDrama', 2, 1);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (14, 14, '香港剧', 'HongKongDrama', 2, 3);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (15, 20, '纪录片', 'documentary', 2, 6);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (16, 29, '国产动漫', 'DomesticAnimation', 4, 1);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (17, 30, '日本动漫', 'JapaneseAnime', 4, 2);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (18, 16, '欧美剧', 'Euro-americanDrama', 2, 5);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (19, 33, '海外动漫', 'OverseasAnimation', 4, 3);" +
                            "insert into VIDEO_TYPE (ID, TYPE_MID, TYPE_NAME, TYPE_NAME_EN, TYPE_PID, TYPE_SORT) values (21, 15, '韩国剧', 'KoreanDrama', 2, 4);"
            );
            log.info("分类数据添加完成.....................");
            log.info("视频资源采集中.....................");
            collectionService.collectionVideoMessages();
            log.info("视频资源采集完成......................");
            // 写入时间
            FileUtil.writeUtf8String(DateUtil.now(), initLock);
        }
        log.info("初始化数据已完成！！！！！！！！！！！！！！");
    }
}
