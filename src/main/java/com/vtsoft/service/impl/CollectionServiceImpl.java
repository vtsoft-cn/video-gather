package com.vtsoft.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.XML;
import cn.hutool.system.SystemUtil;
import com.google.common.collect.Lists;
import com.vtsoft.bo.VideoDetailBo;
import com.vtsoft.bo.VideoListBo;
import com.vtsoft.conf.CollectResourcesConfig;
import com.vtsoft.dao.VideoTypeRepository;
import com.vtsoft.dao.VideoVodRepository;
import com.vtsoft.entity.VideoTypeEntity;
import com.vtsoft.entity.VideoVodEntity;
import com.vtsoft.service.CollectionService;
import com.vtsoft.utils.HttpClientUtils;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * description
 * 视频资源采集
 *
 * @author Garden
 * @version 1.0 create 2023/2/23 22:31
 */
@Slf4j
@Service
public class CollectionServiceImpl implements CollectionService {

    /**
     * 视频分类数据
     */
    private final VideoTypeRepository videoTypeRepository;

    /**
     * 视频详细数据
     */
    private final VideoVodRepository videoVodRepository;

    /**
     * 资源采集配置
     */
    private final CollectResourcesConfig collectResourcesConfig;

    /**
     * 线程池
     */
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final JdbcTemplate jdbcTemplate;

    public CollectionServiceImpl(CollectResourcesConfig collectResourcesConfig, VideoTypeRepository videoTypeRepository, VideoVodRepository videoVodRepository, ThreadPoolTaskExecutor threadPoolTaskExecutor, JdbcTemplate jdbcTemplate) {
        this.collectResourcesConfig = collectResourcesConfig;
        this.videoTypeRepository = videoTypeRepository;
        this.videoVodRepository = videoVodRepository;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 开始采集视频资源
     */
    @SneakyThrows
    @Override
    @Synchronized
    public void collectionVideoMessages() {
        // key 分类id value 分类名称 并过滤掉顶级分类
        Map<Integer, String> videoTypes = videoTypeRepository.findAll().stream().filter(item -> item.getTypePid() != 0 || item.getTypeMid() == 25).collect(Collectors.toMap(VideoTypeEntity::getTypeMid, VideoTypeEntity::getTypeName));
        // 获取资源采集配置
        String resourceName = collectResourcesConfig.getResourceName();
        String collectionAddress = collectResourcesConfig.getCollectionAddress();
        log.info("正在执行视频资源采集：来自 {} 采集地址 {}", resourceName, collectionAddress);
        // 视频分类数据
        if (CollUtil.isEmpty(videoTypes)) {
            VideoListBo videoListBo = this.collectionVideoClassificationData(collectionAddress, "list", "视频分类采集", VideoListBo.class, Map.of());
            if (ObjUtil.isNotEmpty(videoListBo.getRss()) && ObjectUtil.isNotEmpty(videoListBo.getRss().getVideoClasses()) && CollUtil.isNotEmpty(videoListBo.getRss().getVideoClasses().getVideoClass())) {
                log.info("添加分类数据：暂不实现");
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(videoTypes.size());
        // 删除库中数据
        videoVodRepository.deleteAllInBatch();
        // 重置 id 自增
        jdbcTemplate.execute("alter table VIDEO_VOD alter column ID restart with 1;");
        // 采集数据
        videoTypes.forEach((key, value) -> threadPoolTaskExecutor.execute(() -> this.executeCollectionVideoData(collectionAddress, value, key, 1, countDownLatch)));
        boolean await = countDownLatch.await(1, TimeUnit.HOURS);
        if (await) {
            String initLock = String.format("%s%s", SystemUtil.getUserInfo().getHomeDir(), ".h2/initLock");
            // 重新写入时间
            FileUtil.del(initLock);
            FileUtil.writeUtf8String(DateUtil.now(), initLock);
        }
        log.info("---------- 采集成功？{} ----------", await);
    }

    /**
     * 视频分类数据收集
     *
     * @param collectionAddress 采集地址
     * @param paramVal          请求参数值
     * @param logMsg            正在执行 xxx
     */
    private <T, K, V> T collectionVideoClassificationData(String collectionAddress, String paramVal, String logMsg, Class<T> beanClass, Map<K, V> otherParams) {
        log.info("正在执行：{}", logMsg);
        HttpResponse<String> send = HttpClientUtils.send(RequestMethod.GET, String.format("%s?ac=%s%s", collectionAddress, paramVal, CollUtil.isEmpty(otherParams) ? "" : "&" + otherParams.entrySet().stream().map(param -> String.format("%s=%s", param.getKey(), param.getValue())).collect(Collectors.joining("&"))));
        log.info("{}：执行完成", logMsg);
        return JSONUtil.toBean(XML.toJSONObject(send.body()), beanClass);
    }

    /**
     * 视频资源详情，内容接收参数：
     * 参数 ids=数据ID，多个ID逗号分割。
     * t=类型ID
     * pg=页码
     * h=几小时内的数据
     * 例如:
     * http://域名/api.php/provide/vod/?ac=detail&ids=123,567     获取ID为123和567的数据信息
     * http://域名/api.php/provide/vod/?ac=detail&h=24     获取24小时内更新数据信息
     *
     * @param collectionAddress 采集地址
     * @param className         分类名称
     * @param classType         视频分类
     * @param page              第几页
     * @param countDownLatch    记录数据是否采集完成
     */
    private void executeCollectionVideoData(String collectionAddress, String className, Integer classType, Integer page, CountDownLatch countDownLatch) {
        log.info("---------- 正在采集：{} ，第 {} 页 ----------", className, page);
        VideoDetailBo videoDetailBo = this.collectionVideoClassificationData(collectionAddress, "detail", "视频详情数据采集", VideoDetailBo.class, Map.of("t", classType, "pg", page));
        if (page > videoDetailBo.getRss().getPageMsg().getPageCount()) {
            countDownLatch.countDown();
            return;
        }
        log.info("---------- 共查询到 {} 条视频数据 ----------", videoDetailBo.getRss().getPageMsg().getVideo().size());
        // 导出数据
        this.exportVideoData(videoDetailBo, collectionAddress, className, classType, page, countDownLatch);
    }

    /**
     * 导出视频数据
     *
     * @param videoDetailBo     视频数据
     * @param collectionAddress 采集地址
     * @param className         分类名称
     * @param classType         视频分类
     * @param page              第几页
     * @param countDownLatch    记录数据是否采集完成
     */
    private void exportVideoData(VideoDetailBo videoDetailBo, String collectionAddress, String className, Integer classType, Integer page, CountDownLatch countDownLatch) {
        log.info("第 {} 页数据导入中 ~~~~~~~~~~~", page);
        List<VideoVodEntity> videoVodEntities = videoDetailBo.getRss().getPageMsg().getVideo().stream()
                .filter(item -> StrUtil.isNotBlank(item.getVodName()) && StrUtil.isNotBlank(item.getPlayUrls()))
                .map(item -> VideoVodEntity.builder()
                        .typeId(classType)
                        .vodName(item.getVodName())
                        .vodClass(item.getVodClass())
                        .vodPic(item.getVodPic())
                        .vodActor(item.getVodActor())
                        .vodDirector(item.getVodDirector())
                        .vodRemarks(item.getVodRemarks())
                        .vodArea(item.getVodArea())
                        .vodLang(item.getVodLang())
                        .vodYear(item.getVodYear())
                        .vodContent(Optional.ofNullable(item.getVodContent()).orElse(StrUtil.EMPTY).replaceAll("/<[^>]+>/g", StrUtil.EMPTY))
                        .lastUpdate(item.getLastUpdate())
                        .vodPlayUrl(this.handlerPlayUrls(item.getPlayUrls()))
                        .build()
                ).filter(item -> StrUtil.isNotBlank(item.getVodPlayUrl())).collect(Collectors.toList());
        videoVodRepository.saveAll(videoVodEntities);
        log.info("第 {} 页数据导入完成！！！！！！", page);
        this.executeCollectionVideoData(collectionAddress, className, classType, ++page, countDownLatch);
    }

    /**
     * 处理视频播放地址数据
     *
     * @param playUrls 播放地址
     * @return 解析完成后的播放地址
     */
    private String handlerPlayUrls(String playUrls) {
        String[] split = playUrls.split("#");
        List<String> episodes = Lists.newArrayList();
        List<String> urls = Lists.newArrayList();
        for (String url : split) {
            String[] urlTemp = url.split("\\$");
            if (urlTemp.length == 1) {
                episodes.add("HD");
                String str = urlTemp[0];
                int httpIndex = str.indexOf("http");
                urls.add(httpIndex != -1 ? str.substring(httpIndex) : StrUtil.EMPTY);
            } else {
                episodes.add(urlTemp[0]);
                urls.add(urlTemp[1]);
            }
        }
        return JSONUtil.toJsonStr(Map.of("episodes", episodes, "urls", urls));
    }

}
