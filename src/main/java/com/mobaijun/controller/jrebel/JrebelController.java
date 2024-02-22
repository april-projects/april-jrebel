package com.mobaijun.controller.jrebel;

import com.mobaijun.service.JrebelService;
import com.mobaijun.util.IPUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: [jrebel 控制器]
 * Author: [mobaijun]
 * Date: [2024/2/22 14:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class JrebelController {

    private final JrebelService jrebelService;

    /**
     * 处理 JRebel 租约信息请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/jrebel/leases")
    public void jrebelLeases(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jrebelService.jrebelLeasesHandler(request, response);
    }

    /**
     * 处理 JRebel 租约信息请求（1）
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/jrebel/leases/1")
    public void jrebelLeases1(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jrebelService.jrebelLeases1Handler(request, response);
    }

    /**
     * 处理代理租约信息请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/agent/leases")
    public void agentLeases(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jrebelService.jrebelLeasesHandler(request, response);
    }

    /**
     * 处理代理租约信息请求（1）
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/agent/leases/1")
    public void agentLeases1(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jrebelService.jrebelLeases1Handler(request, response);
    }

    /**
     * 验证 JRebel 连接
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/jrebel/validate-connection")
    public void jrebelValidateHandler(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jrebelService.jrebelValidateHandler(request, response);
    }
}
