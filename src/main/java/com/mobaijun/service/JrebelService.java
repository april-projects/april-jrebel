package com.mobaijun.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Description: [jrebel 接口]
 * Author: [mobaijun]
 * Date: [2024/2/22 10:45]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface JrebelService {

    /**
     * 处理 JRebel 租约信息请求
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    void jrebelLeasesHandler(HttpServletRequest request, HttpServletResponse response);

    /**
     * jrebel validate handler
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    void jrebelValidateHandler(HttpServletRequest request, HttpServletResponse response);

    /**
     * 返回JSON格式的响应
     *
     * @param response HttpServletResponse对象
     * @param request  HttpServletRequest
     */
    void jrebelLeases1Handler(HttpServletRequest request, HttpServletResponse response);
}
