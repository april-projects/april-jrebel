package com.mobaijun.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Description: [json 工具类,实现 JSON 数据与 Java 对象之间的相互转换。]
 * Author: [mobaijun]
 * Date: [2024/2/22 10:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class JsonUtil {

    /**
     * ObjectMapper 实例，用于 JSON 转换。
     */
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param data   要转换的对象
     * @param format 是否格式化输出
     * @return 转换后的 JSON 字符串
     * @throws RuntimeException 如果转换失败
     */
    public static <T> String toJson(T data, boolean format) {
        try {
            if (format) {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            } else {
                return objectMapper.writeValueAsString(data);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }

    /**
     * 将对象转换为 JSON 字符串，默认不格式化输出。
     *
     * @param data 要转换的对象
     * @return 转换后的 JSON 字符串
     * @throws RuntimeException 如果转换失败
     */
    public static <T> String toJson(T data) {
        return toJson(data, false);
    }

    /**
     * 将 JSON 字符串转换为指定类型的对象。
     *
     * @param json 要转换的 JSON 字符串
     * @param cls  目标对象类型
     * @return 转换后的对象
     * @throws RuntimeException 如果转换失败
     */
    public static <T> T toObject(String json, Class<T> cls) {
        try {


            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to object", e);
        }
    }

    /**
     * 将JSON字符串转换为Map
     *
     * @param json JSON字符串
     * @param <T>  目标对象的类型
     * @return 转换后的Map
     * @throws RuntimeException 如果JSON解析失败
     */
    public static <T> Map<String, T> toMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    /**
     * 从文件中加载 JSON 字符串并将其转换为指定类型的对象。
     *
     * @param configName 文件名
     * @param cls        目标对象类型
     * @return 转换后的对象
     * @throws RuntimeException 如果加载或转换失败
     */
    public static <T> T loadToObject(String configName, Class<T> cls) {
        try (InputStream inputStream = JsonUtil.class.getClassLoader().getResourceAsStream(configName)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + configName);
            }
            byte[] bin = inputStream.readAllBytes();
            String json = new String(bin, StandardCharsets.UTF_8);
            return toObject(json, cls);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load or convert JSON file: " + configName, e);
        }
    }
}
