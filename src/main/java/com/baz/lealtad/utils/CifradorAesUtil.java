package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CifradorAesUtil {

    public String encryptAes(String aesKeyBase64, String hmacKeyBase64, String valorCampo, LogServicio log) {
        log.setBegTimeMethod("CifradorAesUtil.encryptAes", ParametrerConfiguration.SYSTEM_NAME);
        try {

            SecretKey aesKey = new SecretKeySpec(java.util.Base64.getDecoder()
                    .decode(aesKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.AES_KEY);

            SecretKey hmacKey = new SecretKeySpec(java.util.Base64.getDecoder()
                    .decode(hmacKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.ALGORITHM_HMAC);

            byte[] bitesiv = new byte[ParametrerConfiguration.IV_SIZE];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(bitesiv);

            IvParameterSpec iv = new IvParameterSpec(bitesiv);

            Cipher cipher = Cipher.getInstance(ParametrerConfiguration.ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);

            byte[] plainText = valorCampo.getBytes(ParametrerConfiguration.ENCODING_UTF8);

            byte[] cipherText = cipher.doFinal(plainText);
            byte[] iv_cipherText = concatenateBytes(bitesiv, cipherText);
            byte[] hmac = generarHMAC(hmacKey, iv_cipherText);
            byte[] iv_cipherText_hmac = concatenateBytes(iv_cipherText, hmac);

            byte[] iv_cipherText_hmac_base64 = Base64.getEncoder().encode(iv_cipherText_hmac);
            return new String(iv_cipherText_hmac_base64, ParametrerConfiguration.ENCODING_UTF8);

        }
        catch (Exception e) {

            log.exepcion(e,"ERROR al cifrar en AES");

        }

        return valorCampo;

    }

    private byte[] concatenateBytes(byte[] first, byte[] second) {

        byte[] concatBytes = new byte[first.length + second.length];
        System.arraycopy(first, 0, concatBytes, 0, first.length);
        System.arraycopy(second, 0, concatBytes, first.length, second.length);
        return concatBytes;

    }

    private byte[] generarHMAC(SecretKey key, byte[] hmacInput) throws NoSuchAlgorithmException, InvalidKeyException {

        Mac hmac = Mac.getInstance(ParametrerConfiguration.ALGORITHM_HMAC);
        hmac.init(key);
        return hmac.doFinal(hmacInput);

    }

    private int obtenerHMACLength(SecretKey key) throws NoSuchAlgorithmException, InvalidKeyException {

        Mac hmac = Mac.getInstance(ParametrerConfiguration.ALGORITHM_HMAC);
        hmac.init(key);
        return hmac.getMacLength();

    }

}
