package com.mobaijun.util.jrebel;

import java.util.StringJoiner;

/**
 * Description: [用于生成租约创建 JSON 并对其进行签名的实用工具类。]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class JrebelSign {

    /**
     * 初始化签名
     */
    private static String signature = null;

    /**
     * 生成租约创建 JSON 并对其进行签名。
     *
     * @param clientRandomness 客户端生成的随机数。
     * @param guid             安装相关的 GUID。
     * @param offline          表示安装是否脱机。
     * @param validFrom        租约的有效开始日期/时间。
     * @param validUntil       租约的有效结束日期/时间。
     */
    public void generateLeaseCreateJson(String clientRandomness, String guid,
                                        boolean offline, String validFrom, String validUntil) {
        // 服务器端的随机数；如果自动生成，则必须包含在 JSON 中作为 'serverRandomness'
        String serverRandomness = "H2ulzLlh7E0=";

        // 使用 StringJoiner 构建要签名的字符串
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(clientRandomness)
                .add(serverRandomness)
                .add(guid)
                .add(String.valueOf(offline));

        // 如果脱机，则包括 validFrom 和 validUntil
        if (offline) {
            joiner.add(validFrom)
                    .add(validUntil);
        }

        // 获取要签名的字符串
        String dataToSign = joiner.toString();

        // 对字符串进行签名，并获取签名结果
        byte[] signatureBytes = LicenseServer2ToJRebelPrivateKey.sign(dataToSign.getBytes());
        signature = ByteUtil.encodeBase64(signatureBytes);
    }

    /**
     * 获取为租约创建 JSON 生成的签名。
     *
     * @return 租约创建 JSON 的签名。
     */
    public String getSignature() {
        return signature;
    }
}
