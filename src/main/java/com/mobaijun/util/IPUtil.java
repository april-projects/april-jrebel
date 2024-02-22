package com.mobaijun.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Description: [IP 工具类]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:24]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class IPUtil {

    private final static String[] HEADERS = {
            "X-FORWARDED-FOR",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    /**
     * 获取客户端真实 IP 地址
     *
     * @param request HTTP 请求对象
     * @return 客户端 IP 地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        for (String header : HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                int commaIndex = ip.indexOf(",");
                if (commaIndex != -1) {
                    ip = ip.substring(0, commaIndex);
                }
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
