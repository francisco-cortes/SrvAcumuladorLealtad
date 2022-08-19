package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CifradorAesUtil {

    private static final Logger LOGGER = Logger.getLogger(CifradorAesUtil.class);

    public String encryptAes(String aesKeyBase64, String hmacKeyBase64, String valorCampo) {

        try {

            SecretKey aesKey = new SecretKeySpec(java.util.Base64.getDecoder()
                    .decode(aesKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.AES_KEY);

            SecretKey hmacKey = new SecretKeySpec(java.util.Base64.getDecoder()
                    .decode(hmacKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.ALGORITHM_HMAC);

            byte[] iv = generarInitializationVector();

            Cipher cipher = Cipher.getInstance(ParametrerConfiguration.ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));

            byte[] plainText = valorCampo.getBytes(ParametrerConfiguration.ENCODING_UTF8);

            byte[] cipherText = cipher.doFinal(plainText);
            byte[] iv_cipherText = concatenateBytes(iv, cipherText);
            byte[] hmac = generarHMAC(hmacKey, iv_cipherText);
            byte[] iv_cipherText_hmac = concatenateBytes(iv_cipherText, hmac);

            byte[] iv_cipherText_hmac_base64 = Base64.getEncoder().encode(iv_cipherText_hmac);
            return new String(iv_cipherText_hmac_base64, ParametrerConfiguration.ENCODING_UTF8);

        }
        catch (Exception e) {

            LOGGER.error("Incidente al cifrar el parametro : " + e);

        }

        return valorCampo;

    }

    public String decryptAes(String aesKeyBase64, String hmacKeyBase64, String valorCifrado) {

        try {

            SecretKey aesKey = new SecretKeySpec(Base64.getDecoder()
                    .decode(aesKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.AES_KEY);

            SecretKey hmacKey = new SecretKeySpec(Base64.getDecoder()
                    .decode(hmacKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
                    ParametrerConfiguration.ALGORITHM_HMAC);

            int macLength = obtenerHMACLength(hmacKey);

            byte[] iv_cipherText_hmac = Base64.getDecoder().decode(valorCifrado.getBytes(ParametrerConfiguration.ENCODING_UTF8));
            int cipherTextLength = iv_cipherText_hmac.length - macLength;

            byte[] iv = Arrays.copyOf(iv_cipherText_hmac, ParametrerConfiguration.IV_SIZE);
            byte[] cipherText = Arrays.copyOfRange(iv_cipherText_hmac, ParametrerConfiguration.IV_SIZE, cipherTextLength);

            Cipher cipher = Cipher.getInstance(ParametrerConfiguration.ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, ParametrerConfiguration.ENCODING_UTF8);


        }
        catch (Exception e) {

            LOGGER.error("Incidente al decifrar el parametro : " + e);

        }

        return valorCifrado;

    }

    private byte[] generarInitializationVector() {

        byte[] iv = new byte[ParametrerConfiguration.IV_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;

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
