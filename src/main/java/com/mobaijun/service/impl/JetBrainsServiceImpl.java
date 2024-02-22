package com.mobaijun.service.impl;

import com.mobaijun.service.JetBrainsService;
import com.mobaijun.util.RSAUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description: [jetbrains 实现]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:10]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JetBrainsServiceImpl implements JetBrainsService {

    private static final String DATE_FORMAT_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

    /**
     * 处理释放票据请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    @Override
    public void releaseTicketHandler(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应内容类型和状态码
        response.setContentType("text/html; charset=utf-8");
        setResponseOK(response);

        // 获取请求参数
        String salt = request.getParameter("salt");

        // 如果salt为空，则设置状态码为403（禁止访问）
        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 构建XML内容
        String xmlContent = buildXmlContent(salt);

        // 将XML内容写入响应
        writeToResponse(response, xmlContent);
    }

    /**
     * 处理ping请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    @Override
    public void pingHandler(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应状态码为200（OK）
        setResponseOK(response);

        // 获取请求参数
        String salt = request.getParameter("salt");

        if (salt == null) {
            // 如果salt为空，则设置状态码为403（禁止访问）
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 构建XML内容
        String xmlContent = buildXmlContent(salt);

        // 将XML内容写入响应
        writeToResponse(response, xmlContent);
    }

    /**
     * 处理获取票据请求
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    @Override
    public void obtainTicketHandler(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应内容类型和状态码
        response.setContentType("text/html; charset=utf-8");
        setResponseOK(response);

        // 获取请求参数
        String salt = request.getParameter("salt");
        String username = request.getParameter("userName");
        response.setHeader("Date", getCurrentDate());

        // 如果salt或username为空，则设置状态码为403（禁止访问）
        if (salt == null || username == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 构建XML内容
        String xmlContent = buildXmlContent(salt, username);

        // 将XML内容写入响应
        writeToResponse(response, xmlContent);
    }

    /**
     * 构建XML内容
     *
     * @param salt     盐值
     * @param username 用户名
     * @return 构建的XML内容
     */
    private String buildXmlContent(String salt, String username) {
        return """
                <ObtainTicketResponse>
                    <message></message>
                    <prolongationPeriod>607875500</prolongationPeriod>
                    <responseCode>OK</responseCode>
                    <salt>%s</salt>
                    <ticketId>1</ticketId>
                    <ticketProperties>licensee=%s licenseType=0</ticketProperties>
                </ObtainTicketResponse>
                """.formatted(salt, username);
    }

    /**
     * 构建XML内容
     *
     * @param salt 盐值
     * @return 构建的XML内容
     */
    private String buildXmlContent(String salt) {
        return """
                   <ReleaseTicketResponse>
                        <message></message>
                        <responseCode>OK</responseCode>
                        <salt>%s</salt>
                    </ReleaseTicketResponse>
                """.formatted(salt);
    }

    /**
     * 将XML内容写入响应
     *
     * @param response   HttpServletResponse对象
     * @param xmlContent XML内容
     * @throws RuntimeException 如果写入响应时发生IOException
     */
    private void writeToResponse(HttpServletResponse response, String xmlContent) {
        // 使用RSA算法对XML内容进行签名
        String xmlSignature = RSAUtil.sign(xmlContent);

        // 构建响应体
        String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;

        try {
            // 将响应体写入输出流
            response.getWriter().print(body);
            log.info("response: {}", body);
        } catch (IOException e) {
            // 发生 IOException 时，抛出 RuntimeException
            throw new RuntimeException("Error writing JSON response", e);
        }
    }

    /**
     * 获取当前日期并以指定格式格式化
     *
     * @return 格式化后的日期字符串
     */
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.ENGLISH);
        return dateFormat.format(new Date()) + " GMT";
    }

    /**
     * 设置响应状态码为200（OK）
     *
     * @param response HttpServletResponse对象
     */
    private void setResponseOK(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
