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

    public String encrypt(String txt, String llavePublica ) throws Exception {

        String cadEncriptada = "";
        Cipher cipher = null;

        if(!txt.equals("")){
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(llavePublica));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            cipher = Cipher.getInstance(ParametrerConfiguration.RSA_PADDING_SCHEME);
            cipher.init(1, publicKey);
            cadEncriptada = Base64.encodeBase64String(cipher.doFinal(txt.getBytes(StandardCharsets.UTF_8.toString())));
        }

        return cadEncriptada;

    }

    public String decrypt(String txt, String llavePrivada) throws Exception {

        String cadDesencriptada = "";

        if(!txt.equals("")){
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(llavePrivada));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(ParametrerConfiguration.RSA_PADDING_SCHEME);
            cipher.init(2, privateKey);
            cadDesencriptada = new String(cipher.doFinal(Base64.decodeBase64(txt)));
        }

        return cadDesencriptada;
    }
}
