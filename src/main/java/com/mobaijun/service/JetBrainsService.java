package com.mobaijun.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Description: [JetBrainsService 接口]
 * Author: [mobaijun]
 * Date: [2024/2/22 10:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface JetBrainsService {

    /**
     * 处理释放票据的请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    void releaseTicketHandler(HttpServletRequest request, HttpServletResponse response);

    /**
     * 处理Ping请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    void pingHandler(HttpServletRequest request, HttpServletResponse response);

    /**
     * 处理获取票据请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    void obtainTicketHandler(HttpServletRequest request, HttpServletResponse response);
}
