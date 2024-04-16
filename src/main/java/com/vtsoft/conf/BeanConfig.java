package com.vtsoft.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 * ssl 配置 http 自动跳转 https
 *
 * @author Garden
 * @version 1.0 create 2023/7/1 23:13
 */
@Slf4j
@Configuration
public class BeanConfig {

    @Value("${vtSoft.server.http-port}")
    private Integer httpPort;

    @Value("${server.port}")
    private Integer port;

    /**
     * http自动跳转https
     */
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        // 将http请求转换为https请求
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                // 默认为 NONE
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                // 所有的东西都https
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(this.httpsConnector());
        return tomcat;
    }

    /**
     * 强制将所有的http请求转发到https
     *
     * @return httpConnector
     */
    @Bean
    public Connector httpsConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        // connector监听的http端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        // 监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(port);
        return connector;
    }

}
