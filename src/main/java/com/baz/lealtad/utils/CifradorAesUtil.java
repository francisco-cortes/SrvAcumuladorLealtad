package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
/**
 * CifradorAesUtil
 * Descrpcion: utilidad para el cifrado en AES
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class CifradorAesUtil {
  /*
  constantes globales
   */
  private static final String SERVICE_NAME = "CifradorAesUtil.encryptAes";
  /**
   * EncryptAes
   * Descrpcion: encripta texto con el metodo AES
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: String, String, String, LogServicio
   * returns: String
   **/
  public String encryptAes(String aesKeyBase64, String hmacKeyBase64, String valorCampo, LogServicio log) {
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);

    try {
      /*
      genere un secret key AES
       */
      SecretKey aesKey = new SecretKeySpec(Base64.getDecoder()
        .decode(aesKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
        ParametrerConfiguration.AES_KEY);
      /*
      genera un secret key HMAC
       */
      SecretKey hmacKey = new SecretKeySpec(Base64.getDecoder()
        .decode(hmacKeyBase64.getBytes(ParametrerConfiguration.ENCODING_UTF8)),
        ParametrerConfiguration.ALGORITHM_HMAC);
      /*
      genera un byte array aleatorio seguro
       */
      byte[] bitesiv = new byte[ParametrerConfiguration.IV_SIZE];
      SecureRandom secureRandom = new SecureRandom();
      secureRandom.nextBytes(bitesiv);
      /*
      paramtro iv
       */
      IvParameterSpec iv = new IvParameterSpec(bitesiv);
      /*
      inizialsa el cipher para ejecutar el modo encrypt
       */
      Cipher cipher = Cipher.getInstance(ParametrerConfiguration.ALGORITHM_AES);
      cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
      /*
      genera un byte array apartir del texto a encritptar
       */
      byte[] plainText = valorCampo.getBytes(ParametrerConfiguration.ENCODING_UTF8);
      /*
      cifra
       */
      byte[] cipherText = cipher.doFinal(plainText);
      /*
      concatena bytes
       */
      byte[] ivCipherText = concatenateBytes(bitesiv, cipherText);
      /*
      genera un HMAC con el arraye Bytes concatenado
       */
      byte[] hmac = generarHMAC(hmacKey, ivCipherText);
      byte[] ivCipherTextHmac = concatenateBytes(ivCipherText, hmac);
      /*
      genera la encriptacion en base 64
       */
      byte[] ivCipherTextHmacBase64 = Base64.getEncoder().encode(ivCipherTextHmac);
      return new String(ivCipherTextHmacBase64, ParametrerConfiguration.ENCODING_UTF8);

    }
    catch (InvalidAlgorithmParameterException |
      UnsupportedEncodingException | NoSuchPaddingException | IllegalBlockSizeException |
      NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
      log.exepcion(e,"ERROR al cifrar en AES");
    }

    return valorCampo;

  }

  /**
   * concatenateByte
   * Descrpcion: concatene arreglos de Bytes
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: byteArray, byteArray
   * returns: byteArray
   **/

  private static byte[] concatenateBytes(byte[] first, byte[] second) {

    byte[] concatBytes = new byte[first.length + second.length];
    System.arraycopy(first, 0, concatBytes, 0, first.length);
    System.arraycopy(second, 0, concatBytes, first.length, second.length);
    return concatBytes;

  }

  /**
   * generarHMAC
   * Descrpcion: genera un key HMAC
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: SecretKey, byteArray
   * returns: byteArray
   **/

  private byte[] generarHMAC(SecretKey key, byte[] hmacInput) throws NoSuchAlgorithmException, InvalidKeyException {

    Mac hmac = Mac.getInstance(ParametrerConfiguration.ALGORITHM_HMAC);
    hmac.init(key);
    return hmac.doFinal(hmacInput);

  }

}
