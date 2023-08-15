package com.vtsoft.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Scanner;

/**
 * description <br/>
 *
 * @author Garden
 * @version 1.0 create 2023/7/1 16
 */
public class FileUtils {
    /**
     * M3U8 视频文件临时存储地址
     */
    public final static String M3U8_LOCAL_FILE_PATH = String.format("%s%s", SystemUtil.getUserInfo().getHomeDir(), ".h2/m3u8/");

    /**
     * 文件中的数据内容转换成字符串
     *
     * @param filePath 文件路径
     * @return String 内容
     */
    @SneakyThrows
    public static String readFileToString(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = FileUtil.file(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append(StrUtil.C_LF);
        }
        scanner.close();
        return stringBuilder.toString();
    }
}