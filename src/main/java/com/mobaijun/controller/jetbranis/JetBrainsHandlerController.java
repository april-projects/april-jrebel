package com.mobaijun.controller.jetbranis;

import com.mobaijun.service.JetBrainsService;
import com.mobaijun.util.IPUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

/**
 * Description: [JetBrains 控制器]
 * Author: [mobaijun]
 * Date: [2024/2/22 15:01]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class JetBrainsHandlerController {

    private final JetBrainsService jetBrainsService;

    /**
     * 处理来自JetBrains的Ping请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/rpc/ping.action")
    public void pingHandler(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jetBrainsService.pingHandler(request, response);
    }

    /**
     * 处理来自JetBrains的获取票据请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/rpc/obtainTicket.action")
    public void obtainTicketHandler(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jetBrainsService.obtainTicketHandler(request, response);
    }

    /**
     * 处理来自JetBrains的释放票据请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/rpc/releaseTicket.action")
    public void releaseTicketHandler(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        jetBrainsService.releaseTicketHandler(request, response);
    }

    /**
     * 生成并返回一个随机的GUID
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     */
    @PostMapping("/guid")
    public void guid(HttpServletRequest request, HttpServletResponse response) {
        log.info("ip : {} ", IPUtil.getRemoteIp(request) + " -> " + request.getRequestURI());
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String body = UUID.randomUUID().toString();
        try {
            response.getWriter().print(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}