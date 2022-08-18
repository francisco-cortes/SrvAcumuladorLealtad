package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CifradorRsaUtil {

    private String base64PublicKey= "";
    private String base64PrivateKey= "";

    public CifradorRsaUtil(String base64PublicKey, String base64PrivateKey) {
        this.base64PrivateKey=base64PrivateKey;
        this.base64PublicKey=base64PublicKey;
    }

    public String encrypt(String txt ) throws Exception {
        String cadEncriptada = "";
        Cipher cipher = null;
        if(txt!=null){
            cipher = Cipher.getInstance(ParametrerConfiguration.RSA_PADDING_SCHEME);
            cipher.init(1, getPublicKey());
            cadEncriptada = Base64.encodeBase64String(cipher.doFinal(txt.getBytes(StandardCharsets.UTF_8.toString())));
        }
        return cadEncriptada;
    }

    public String decrypt(String txt) throws Exception {
        String cadDesencriptada = "";
        if(txt!=null){
            Cipher cipher = Cipher.getInstance(ParametrerConfiguration.RSA_PADDING_SCHEME);
            cipher.init(2, getPrivateKey());
            cadDesencriptada = new String(cipher.doFinal(Base64.decodeBase64(txt)));
        }
        return cadDesencriptada;
    }

    public PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey publicKey = null;
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey = null;
        KeyFactory keyFactory = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

}
