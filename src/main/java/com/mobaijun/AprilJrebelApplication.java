package com.mobaijun;

import com.mobaijun.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * AprilJrebelApplication是启动类，用于启动Spring Boot应用程序。
 */
@Slf4j
@SpringBootApplication
public class AprilJrebelApplication {

    /**
     * main方法是应用程序的入口点，它启动Spring Boot应用程序。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AprilJrebelApplication.class);
        Environment env = app.run(args).getEnvironment();

        String port = env.getProperty("server.port");
        String hostname = IPUtil.getLocalIPv4Address();

        log.info("Service is running at: http://" + hostname + ":" + port);
    }
}