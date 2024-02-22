package com.mobaijun.util.jrebel;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Description: [jrebel 破解工具类]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class LicenseServer2ToJRebelPrivateKey {

    /**
     * Base64编码的私钥字符串
     */
    private static final String PRIVATE_KEY_BASE64 = "MIICXAIBAAKBgQDQ93CP6SjEneDizCF1P/MaBGf582voNNFcu8oMhgdTZ/N6qa6O7XJDr1FSCyaDdKSsPCdxPK7Y4Usq/fOPas2kCgYcRS/iebrtPEFZ/7TLfk39HLuTEjzo0/CNvjVsgWeh9BYznFaxFDLx7fLKqCQ6w1OKScnsdqwjpaXwXqiulwIDAQABAoGATOQvvBSMVsTNQkbgrNcqKdGjPNrwQtJkk13aO/95ZJxkgCc9vwPqPrOdFbZappZeHa5IyScOI2nLEfe+DnC7V80K2dBtaIQjOeZQt5HoTRG4EHQaWoDh27BWuJoip5WMrOd+1qfkOtZoRjNcHl86LIAh/+3vxYyebkug4UHNGPkCQQD+N4ZUkhKNQW7mpxX6eecitmOdN7Yt0YH9UmxPiW1LyCEbLwduMR2tfyGfrbZALiGzlKJize38shGC1qYSMvZFAkEA0m6psWWiTUWtaOKMxkTkcUdigalZ9xFSEl6jXFB94AD+dlPS3J5gNzTEmbPLc14VIWJFkO+UOrpl77w5uF2dKwJAaMpslhnsicvKMkv31FtBut5iK6GWeEafhdPfD94/bnidpP362yJl8Gmya4cI1GXvwH3pfj8S9hJVA5EFvgTB3QJBAJP1O1uAGp46X7Nfl5vQ1M7RYnHIoXkWtJ417Kb78YWPLVwFlD2LHhuy/okT4fk8LZ9LeZ5u1cp1RTdLIUqAiAECQC46OwOm87L35yaVfpUIjqg/1gsNwNsj8HvtXdF/9d30JIM3GwdytCvNRLqP35Ciogb9AO8ke8L6zY83nxPbClM=";
    private static final byte[] PRIVATE_KEY_BYTES;
    private static final BouncyCastleProvider SECURITY_PROVIDER;

    static {
        // 静态初始化块，将 Base64 编码的私钥字符串解码为字节数组
        PRIVATE_KEY_BYTES = ByteUtil.decodeBase64(PRIVATE_KEY_BASE64);
        SECURITY_PROVIDER = new BouncyCastleProvider();
    }

    /**
     * 获取 RSA 私钥对象的工具方法
     *
     * @return RSA 私钥对象，如果发生异常则返回 null
     */
    private static PrivateKey getPrivateKey() {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(PRIVATE_KEY_BYTES);
            // 使用指定的安全提供程序实例化 KeyFactory
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", SECURITY_PROVIDER);
            // 生成私钥对象
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            // 捕获 NoSuchAlgorithmException 和 InvalidKeySpecException 异常
            // 在异常情况下，记录异常信息并返回 null
            log.error("Error occurred while obtaining private key: {}", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 对输入字节数组进行签名
     *
     * @param array 要签名的字节数组
     * @return 签名后的字节数组
     */
    public static byte[] sign(byte[] array) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA", SECURITY_PROVIDER);
            signature.initSign(getPrivateKey());
            signature.update(array);
            return signature.sign();
        } catch (GeneralSecurityException ex) {
            // 在发生安全异常时，抛出运行时异常并记录错误信息
            throw new RuntimeException("License Server installation error 0000000F2", ex);
        }
    }
}
