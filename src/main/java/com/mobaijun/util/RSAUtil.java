package com.mobaijun.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/2/22 11:11]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class RSAUtil {

    public final static String KEY22 = "MIIBOgIBAAJBALecq3BwAI4YJZwhJ+snnDFj3lF3DMqNPorV6y5ZKXCiCMqj8OeOmxk4YZW9aaV9"
            + "ckl/zlAOI0mpB3pDT+Xlj2sCAwEAAQJAW6/aVD05qbsZHMvZuS2Aa5FpNNj0BDlf38hOtkhDzz/h"
            + "kYb+EBYLLvldhgsD0OvRNy8yhz7EjaUqLCB0juIN4QIhAOeCQp+NXxfBmfdG/S+XbRUAdv8iHBl+"
            + "F6O2wr5fA2jzAiEAywlDfGIl6acnakPrmJE0IL8qvuO3FtsHBrpkUuOnXakCIQCqdr+XvADI/UTh"
            + "TuQepuErFayJMBSAsNe3NFsw0cUxAQIgGA5n7ZPfdBi3BdM4VeJWb87WrLlkVxPqeDSbcGrCyMkC"
            + "IFSs5JyXvFTreWt7IQjDssrKDRIPmALdNjvfETwlNJyY";

    public final static String KEY33 = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAt5yrcHAAjhglnCEn"
            + "6yecMWPeUXcMyo0+itXrLlkpcKIIyqPw546bGThhlb1ppX1ySX/OUA4jSakHekNP"
            + "5eWPawIDAQABAkBbr9pUPTmpuxkcy9m5LYBrkWk02PQEOV/fyE62SEPPP+GRhv4Q"
            + "Fgsu+V2GCwPQ69E3LzKHPsSNpSosIHSO4g3hAiEA54JCn41fF8GZ90b9L5dtFQB2"
            + "/yIcGX4Xo7bCvl8DaPMCIQDLCUN8YiXppydqQ+uYkTQgvyq+47cW2wcGumRS46dd"
            + "qQIhAKp2v5e8AMj9ROFO5B6m4SsVrIkwFICw17c0WzDRxTEBAiAYDmftk990GLcF"
            + "0zhV4lZvztasuWRXE+p4NJtwasLIyQIgVKzknJe8VOt5a3shCMOyysoNEg+YAt02"
            + "O98RPCU0nJg=";

    /**
     * 对内容进行签名，使用ASN1格式的私钥
     *
     * @param content 要签名的内容
     * @return 签名后的十六进制字符串
     */
    public static String sign(String content) {
        // 调用通用的签名方法，使用ASN1格式的私钥
        return sign(content.getBytes(), KEY22, true);
    }

    /**
     * 对内容进行签名，使用PKCS#8格式的私钥
     *
     * @param content 要签名的内容
     * @return 签名后的十六进制字符串
     */
    public static String sign2(String content) {
        // 调用通用的签名方法，使用PKCS#8格式的私钥
        return sign(content.getBytes(), KEY33, false);
    }

    /**
     * 对内容进行签名
     *
     * @param content    要签名的内容
     * @param privateKey 私钥，可以是ASN1格式或PKCS#8格式的base64编码字符串
     * @param isASN      指定私钥格式，true表示ASN1格式，false表示PKCS#8格式
     * @return 签名后的十六进制字符串
     */
    public static String sign(byte[] content, String privateKey, boolean isASN) {
        try {
            PrivateKey priKey;
            if (isASN) {
                byte[] keynote = Base64.decode(privateKey);
                try (ASN1InputStream in = new ASN1InputStream(keynote)) {
                    ASN1Primitive obj = in.readObject();
                    PrivateKeyInfo pki = PrivateKeyInfo.getInstance(obj);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pki.getEncoded());
                    priKey = keyFactory.generatePrivate(spec);
                }
            } else {
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
                KeyFactory keyf = KeyFactory.getInstance("RSA");
                priKey = keyf.generatePrivate(priPKCS8);
            }
            java.security.Signature signature = java.security.Signature.getInstance("MD5WithRSA");
            signature.initSign(priKey);
            signature.update(content);
            byte[] signed = signature.sign();
            return Hex.bytesToHexString(signed);
        } catch (Exception e) {
            // 使用日志记录方式输出异常信息
            log.error("Failed to sign content {}", e.getMessage());
            // 在异常情况下返回空字符串，也可以根据具体情况做其他处理
            return "";
        }
    }
}
