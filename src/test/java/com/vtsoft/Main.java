package com.vtsoft;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/2/19 15:40
 */
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        /*
         * ac: list
         * flag:
         * url: https://cj.lziapi.com/api.php/provide/vod/from/lzm3u8/at/xml/
         * h:
         * t:
         * ids:
         * wd: 狂飙
         * type: 1
         * mid: 1
         * opt: 0
         * filter: 0
         * filter_from:
         * param:
         * page: 1
         * limit:
         */
        HttpClient httpClient = HttpClient.newHttpClient();
        /*
         * 列表接收参数：
         * ac=list
         * t=类别ID
         * pg=页码
         * wd=搜索关键字
         * h=几小时内的数据
         * 例如： http://域名/api.php/provide/vod/?ac=list&t=1&pg=5   分类ID为1的列表数据第5页
         */
        HttpResponse<String> videoList = httpClient.send(
                HttpRequest
                        .newBuilder()
                        .GET()
                        .uri(URI.create("https://cj.lziapi.com/api.php/provide/vod/from/lzm3u8/at/xml?ac=list"))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        /*
         * 内容接收参数：
         * 参数 ids=数据ID，多个ID逗号分割。
         *      t=类型ID
         *      pg=页码
         *      h=几小时内的数据
         *
         * 例如:   http://域名/api.php/provide/vod/?ac=detail&ids=123,567     获取ID为123和567的数据信息
         *         http://域名/api.php/provide/vod/?ac=detail&h=24     获取24小时内更新数据信息
         */
        HttpResponse<String> videoDetail = httpClient.send(
                HttpRequest
                        .newBuilder()
                        .GET()
                        .uri(URI.create("https://cj.lziapi.com/api.php/provide/vod/from/lzm3u8/at/xml?ac=detail&t=30&pg=2"))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println();
    }
}
