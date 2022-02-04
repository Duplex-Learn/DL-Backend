package com.duplexlearn.test;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SecretKeyTest {

    @Test
    public void testGenerateKey()
    {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("secretKey: {}",Encoders.BASE64.encode(secretKey.getEncoded()));
    }
}
