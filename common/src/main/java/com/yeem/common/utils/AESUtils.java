package com.yeem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

@Slf4j
public class AESUtils {

    // 加密模式
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 解密
     *
     * @param content 目标密文
     * @param key     秘钥
     * @param iv      偏移量
     * @return
     */
    public static String decrypt(@NotNull String content, @NotNull String key, @NotNull String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            byte[] sessionKey = java.util.Base64.getDecoder().decode(key);
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, AES_NAME);
            byte[] ivByte = java.util.Base64.getDecoder().decode(iv);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception e) {
            log.error("解密失败：", e);
        }
        return null;
    }

}
