package com.vtsoft.utils;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * description
 * http请求处理
 *
 * @author Garden
 * @version 1.0 create 2023/2/23 22:39
 */
public class HttpClientUtils {

    /**
     * http 请求
     */
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * 发送请求
     *
     * @param method 请求方式
     * @param url    请求地址
     * @param body   请求 body
     * @return 请求结果
     */
    @SneakyThrows
    public static HttpResponse<String> send(RequestMethod method, String url, String... body) {
        HttpRequest.Builder httpRequest = HttpRequest.newBuilder();
        switch (method) {
            case GET:
                httpRequest = httpRequest.GET();
                break;
            case POST:
                httpRequest = httpRequest.POST(
                        HttpRequest.BodyPublishers.ofString(
                                Optional.of(Arrays.asList(body)).orElse(List.of("")).get(0)
                        )
                );
            default:
        }
        return HTTP_CLIENT.send(
                httpRequest
                        .uri(
                                URI.create(url)
                        )
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }
}
