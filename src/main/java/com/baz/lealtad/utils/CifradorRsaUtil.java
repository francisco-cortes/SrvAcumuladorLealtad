package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
/**
 * CifradorRsaUtil
 * Descrpcion: Clase para manejar cifrado y decifrado RSA
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class CifradorRsaUtil {
  /**
   * decrypt
   * Descrpcion: descifra texto en RSA
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/
  public String decrypt(String txt, String llavePrivada) throws NoSuchAlgorithmException,
    InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException,
    BadPaddingException, InvalidKeyException {
    /*
    inicia
     */
    String cadDesencriptada = "";
    /*
    validad entrada llenas
     */
    if(!"".equals(txt)){
      final int OP_MODE = 2;
      // decadifica usando llave privada de api seguridad baz
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(llavePrivada));
      // genera instacia de RSA
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      // genera una llave privada
      PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
      Cipher cipher = Cipher.getInstance(ParametrerConfiguration.RSA_PADDING_SCHEME);
      //inicializa clase cipher
      cipher.init(OP_MODE, privateKey);
      cadDesencriptada = new String(cipher.doFinal(Base64.decodeBase64(txt)));
    }
    //regresa cadena des encriptada
    return cadDesencriptada;
  }
}
