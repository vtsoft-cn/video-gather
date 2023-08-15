package com.vtsoft.utils;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * description
 * M3U8Converter m3u8 视频文件转化为m3u8下载url
 *
 * @author Garden
 * @version 1.0 create 2023/2/23 22:39
 */
@Slf4j
public class M3U8Converter {

    /**
     * m3u8 视频文件转化为m3u8下载url
     *
     * @param m3u8Url 视频地址
     */
    @SneakyThrows
    public static String convert(String m3u8Url) {
        String localHost = "/api/videoPlayData?fileName=";
        String localFile = HashUtil.bkdrHash(m3u8Url) + ".m3u8";
        String filePath = FileUtils.M3U8_LOCAL_FILE_PATH + localFile;
        File file = new File(filePath);
        if (file.exists()) {
            log.info("数据文件已存在");
            return localHost + localFile;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            boolean mkdir = new File(dirFile.getParent()).mkdir();
            if (mkdir) {
                log.info("开始执行视频数据转换任务~~~");
            }
        }
        URL url = new URL(m3u8Url);
        String body = HttpClientUtils.send(RequestMethod.GET, url.toString()).body();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        String tsM3u8Url = "";
        while ((line = reader.readLine()) != null) {
            if (line.contains(".m3u8")) {
                tsM3u8Url = line.trim();
                break;
            }
        }
        String startPath = url.getProtocol() + "://" + url.getHost() + url.getPath();
        startPath = startPath.substring(0, startPath.lastIndexOf('/') + 1);
        tsM3u8Url = tsM3u8Url.replace("\\", "/");
        String tsUrl = startPath + tsM3u8Url;
        String tBody = HttpClientUtils.send(RequestMethod.GET, tsUrl).body();
        BufferedReader tsReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(tBody.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String tsLine;
        int discontinuityCounter = 0;
        StringBuilder tsContentBuilder = new StringBuilder();
        String tsFullPath = tsUrl.substring(0, tsUrl.lastIndexOf('/') + 1) + "/";
        while ((tsLine = tsReader.readLine()) != null) {
            if (tsLine.endsWith(discontinuityCounter + ".ts")) {
                tsContentBuilder.append(tsFullPath).append(tsLine.trim()).append(StrUtil.C_LF);
                discontinuityCounter++;
            } else if (tsLine.startsWith("#")) {
                tsContentBuilder.append(tsLine.trim()).append(StrUtil.C_LF);
            }
        }
        tsReader.close();
        String tsContent = tsContentBuilder.toString();
        tsContent = tsContent.replace("\n\n", "");
        FileWriter writer = new FileWriter(filePath);
        writer.write(tsContent);
        writer.close();
        return localHost + localFile;
    }
}