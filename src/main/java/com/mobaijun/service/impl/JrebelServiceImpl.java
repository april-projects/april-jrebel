package com.mobaijun.service.impl;

import com.mobaijun.service.JrebelService;
import com.mobaijun.util.JsonUtil;
import com.mobaijun.util.jrebel.JrebelSign;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: [jrebel 实现]
 * Author: [mobaijun]
 * Date: [2024/2/22 10:48]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JrebelServiceImpl implements JrebelService {

    /**
     * 打印日志
     *
     * @param message 请求响应参数
     */
    private static void logResponse(String message) {
        log.info("The request response parameter is:：{}", message);
    }

    /**
     * 生成 JRebel 租约信息的 JSON 响应
     *
     * @param offline    是否为离线模式
     * @param validFrom  有效起始时间
     * @param validUntil 有效截止时间
     * @return JSON 响应字符串
     */
    private String generateJsonResponse(boolean offline, String validFrom, String validUntil) {
        return """
                {
                    "serverVersion": "3.2.4",
                    "serverProtocolVersion": "1.1",
                    "serverGuid": "a1b4aea8-b031-4302-b602-670a990272cb",
                    "groupType": "managed",
                    "id": 1,
                    "licenseType": 1,
                    "evaluationLicense": false,
                    "signature": "OJE9wGg2xncSb+VgnYT+9HGCFaLOk28tneMFhCbpVMKoC/Iq4LuaDKPirBjG4o394/UjCDGgTBpIrzcXNPdVxVr8PnQzpy7ZSToGO8wv/KIWZT9/ba7bDbA8/RZ4B37YkCeXhjaixpmoyz/CIZMnei4q7oWR7DYUOlOcEWDQhiY=",
                    "serverRandomness": "H2ulzLlh7E0=",
                    "seatPoolType": "standalone",
                    "statusCode": "SUCCESS",
                    "offline": %s,
                    "validFrom": %s,
                    "validUntil": %s,
                    "company": "Administrator",
                    "orderId": "",
                    "zeroIds": [
                       \s
                    ],
                    "licenseValidFrom": 1490544001000,
                    "licenseValidUntil": %s
                }
                """.formatted(offline, validFrom, validUntil, System.currentTimeMillis());
    }

    @Override
    public void jrebelLeasesHandler(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应类型和状态
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 从请求参数中获取所需信息
        String clientRandomness = request.getParameter("randomness");
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");
        boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
        String validFrom = "null";
        String validUntil = "null";

        // 若为离线模式，则计算有效期
        if (offline) {
            String clientTime = request.getParameter("clientTime");
            long clientTimeUntil = Long.parseLong(clientTime) + 180L * 24 * 60 * 60 * 1000;
            validFrom = clientTime;
            validUntil = String.valueOf(clientTimeUntil);
        }

        // 生成 JSON 响应
        String jsonStr = generateJsonResponse(offline, validFrom, validUntil);

        // 若关键信息缺失，则返回 403 错误
        if (clientRandomness == null || username == null || guid == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            // 生成签名并添加到 JSON 中
            Map<String, Object> map = JsonUtil.toMap(jsonStr);
            JrebelSign jrebelSign = new JrebelSign();
            jrebelSign.generateLeaseCreateJson(clientRandomness, guid, offline, validFrom, validUntil);
            String signature = jrebelSign.getSignature();
            map.put("signature", signature);
            map.put("company", username);
            String body = JsonUtil.toJson(map);
            try {
                logResponse(body);
                response.getWriter().print(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final String JSON_RESPONSE_TEMPLATE = """
            {
                "serverVersion": "3.2.4",
                "serverProtocolVersion": "1.1",
                "serverGuid": "a1b4aea8-b031-4302-b602-670a990272cb",
                "groupType": "managed",
                "statusCode": "SUCCESS",
                "msg": null,
                "statusMessage": null
            }
            """;

    @Override
    public void jrebelValidateHandler(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String jsonResponse = """
                {
                    "serverVersion": "3.2.4",
                    "serverProtocolVersion": "1.1",
                    "serverGuid": "a1b4aea8-b031-4302-b602-670a990272cb",
                    "groupType": "managed",
                    "statusCode": "SUCCESS",
                    "company": "Administrator",
                    "canGetLease": true,
                    "licenseType": 1,
                    "evaluationLicense": false,
                    "seatPoolType": "standalone"
                }
                """;
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonResponse);
            logResponse(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON response", e);
        }
    }

    @Override
    public void jrebelLeases1Handler(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String username = request.getParameter("username");


        Map<String, Object> map = JsonUtil.toMap(JSON_RESPONSE_TEMPLATE);
        if (username != null) {
            map.put("company", username);
        }
        String body = JsonUtil.toJson(map);

        try (PrintWriter writer = response.getWriter()) {
            logResponse(body);
            writer.print(body);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON response", e);
        }
    }
}
