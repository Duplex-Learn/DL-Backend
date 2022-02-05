package com.duplexlearn.util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {
    private static final String ALGORITHM = "SHA1WithRSA";
    private static final int KEY_SIZE = 1024;

    public byte[] sign(byte[] data, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public String sign(String data, PrivateKey privateKey)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return Base64.getEncoder().encodeToString(sign(data.getBytes(StandardCharsets.UTF_8), privateKey));
    }

    public boolean verify(byte[] data, byte[] sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public boolean verify(String data, String sign, PublicKey publicKey)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return verify(data.getBytes(), Base64.getDecoder().decode(sign), publicKey);
    }

    public KeyPair createKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(KEY_SIZE, new SecureRandom());
        return keyGen.generateKeyPair();
    }

    public String getBase64KeyString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public PublicKey getPublicKey(String publicKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        return publicKey;
    }

    public PrivateKey getPrivateKey(String privateKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(priKeySpec);
        return privateKey;
    }
}
