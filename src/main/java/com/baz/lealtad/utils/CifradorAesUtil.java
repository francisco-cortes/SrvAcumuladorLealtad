package com.baz.lealtad.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CifradorAesUtil {

    private String kyCipher                      = "B2A0A0R2N1Q8C0U7O2I0A1T6ZETCETCU";
    private String ivParameter                   = "02 A6 81 F1 19 8B 87 60 E6 56 81 C1 65 88 5D 34";
    private String ENCODE_UTF8                   = "UTF-8";
    private String ALGORITHM_METHOD_AES          = "AES";
    private String ALGORITHM_CYPHER_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private String CODE_RESULT                   = "C20015";
    private boolean flagError                    = false;
    private boolean activado                     = true;

    public String encrypt( String message ) throws Exception {

        if(message!=null){
            if(!message.equals("")){
                String cypherSecret = (kyCipher.length()>32?kyCipher.substring(0,32):kyCipher);
                SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(cypherSecret,32,"0").getBytes(ENCODE_UTF8), ALGORITHM_METHOD_AES);
                IvParameterSpec iv = new IvParameterSpec(xeh(ivParameter), 0, 16);

                // Instantiate the cipher
                Cipher cipher = Cipher.getInstance(ALGORITHM_CYPHER_PKCS5PADDING);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(message.getBytes(ENCODE_UTF8));

                message = new String(Base64.encodeBase64(encrypted));

            }
        }
        return message;
    }

    public String decrypt( String message ) throws Exception {

        if(message!=null){
            if(!message.equals("")){

                String cypherSecret = (kyCipher.length()>32?kyCipher.substring(0,32):kyCipher);
                SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(cypherSecret,32,"0").getBytes(ENCODE_UTF8), ALGORITHM_METHOD_AES);
                IvParameterSpec iv = new IvParameterSpec(xeh(ivParameter), 0, 16);

                // Instantiate the cipher
                Cipher cipher = Cipher.getInstance(ALGORITHM_CYPHER_PKCS5PADDING);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] decrypted = cipher.doFinal(Base64.decodeBase64(message.getBytes()));

                message = new String(decrypted);
            }
        }
        return message;
    }

    static byte[] xeh(String in) {
        in = StringUtils.leftPad(in.replaceAll(" ", ""),32,"0");
        int len = in.length() / 2;
        byte[] out = new byte[len];
        for (int i = 0; i < len; i++)
            out[i] = (byte) Integer.parseInt(in.substring(i * 2, i * 2 + 2), 16);
        return out;
    }

}
