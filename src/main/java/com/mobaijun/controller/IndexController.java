package com.mobaijun.controller;

import com.mobaijun.util.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.UUID;

/**
 * Description: [主页控制器]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:35]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestController
@RequestMapping("/")
public class IndexController {

    /**
     * 处理根路径请求，返回主页视图
     *
     * @param request HTTP请求对象
     * @return 主页视图模型
     */
    @RequestMapping({"/", ""})
    public ModelAndView index(HttpServletRequest request) {
        // 获取licenseUrl和protocol环境变量，若不存在则使用request中的信息
        String licenseUrl = System.getenv().getOrDefault("licenseUrl", request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()));
        String protocol = System.getenv().getOrDefault("protocol", "https://");
        // 创建用于渲染主页的数据模型
        HashMap<String, Object> model = new HashMap<>();
        model.put("licenseUrl", licenseUrl);
        model.put("protocol", protocol);
        model.put("uuid", UUID.randomUUID().toString());
        model.put("mail", "%s@gmail.com".formatted(RandomUtil.next(10000, 999999999)));
        log.info("licenseUrl: {},mail: {}", model.get("licenseUrl"), model.get("mail"));
        // 返回包含数据模型的主页视图
        return new ModelAndView("index", model);
    }
}
