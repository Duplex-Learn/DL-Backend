package com.duplexlearn.test;

import com.duplexlearn.util.RSAUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@SpringBootTest
@Slf4j
public class SecretKeyTest {

    private RSAUtil rsaUtil;

    @Autowired
    public SecretKeyTest(RSAUtil rsaUtil) {
        this.rsaUtil = rsaUtil;
    }

    @Test
    public void testGenerateKey()
    {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("secretKey: {}",Encoders.BASE64.encode(secretKey.getEncoded()));
    }

    @Test
    public void testRSA() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        KeyPair keyPair = rsaUtil.createKeyPair();
        log.info("public: {}, private: {}",rsaUtil.getBase64KeyString(keyPair.getPublic()),rsaUtil.getBase64KeyString(keyPair.getPrivate()));
        String sign = rsaUtil.sign("1234",keyPair.getPrivate());
        Assertions.assertTrue(rsaUtil.verify("1234",sign,keyPair.getPublic()));
    }
    @Test
    public void sign() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        PrivateKey privateKey = rsaUtil.getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI9F4+RaA5naXNZlldaFrlPdABFEoXVymW5KaviBUe7FQYQ6ZPmBfa+szOLUcahm8hIS7DBOGE2hD/nytKpHnJ4wh5vzd9YrIGCQdT43+DOLWQ4llFPfDtxRFFl02lQcsllpQFYRN52qWs+VjVNgU74XMKG8tGPU0HB4OIqZy7IZAgMBAAECgYAdFQvAU4E9nrSPlIlIxVPwlACqgKrhg0SfvZ1fGij+da3p4EyU1PMlUMc6F/OGQndKseqdl4yZXWM30ktX8TdaGy6+KAByFORHaU3b4+IfkdOvPx45zgmCS2PhYyU6g20qIlivYvffGHqSBUU+WWwSBGAobomSg8GLNL6YnrodvQJBAMLTg5/WI9FlsQr9XcpN99w4GG7y2qcXOxzsMQAk9qRwfLkx8AUBK+L4Qd8xi5VeyRkBaDzJzHadu2FqB1GJt2sCQQC8Qm9LU3Mqq9fEo/DxyuGRAeE0DXiZBuI2DwK80oG7Tw1xDjsGeRqKQQCGFwyJu0tP2gk9/LLcaroMZkz9RRGLAkEAhZTO7928i0tlW3qjPx28b4MKxa+/2cck4czUQBwX/GGgMr6ZqmTGOYYsbCMlaJIn4fxEA0H97epyWvV+9Rou5wI/HuLHX5RRUK7gt2IuJX0jhKbKS7/qCTEX68aKrG/c2N4fVmz/QhCcShJgA5/EbtRLhs4+tey/a2oXG/Gsr+ClAkEAr8774hVI9oUUlrCTcVQFrNy24ysLKTniavhXYL01sQddJL11rvsZhWety0D6zd0Cs5YYm0djD1YdSYt9DfYiBg==");
        String sign = rsaUtil.sign("https://gitee.com/lovelonelytime/dl_project_markdown/",privateKey);
        log.info(sign);
    }
}
