package com.baz.lealtad.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CifradorAesUtil {

    public String encrypt( String message ) throws Exception {

        if(message!=null){
            if(!message.equals("")){
                String cypherSecret = (ConstantesUtil.KEY_CIPHER.length()>32?
                        ConstantesUtil.KEY_CIPHER.substring(0,32):
                        ConstantesUtil.KEY_CIPHER);
                SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(cypherSecret,32,"0").getBytes(ConstantesUtil.ENCODE_UTF8), ConstantesUtil.ALGORITHM_METHOD_AES);
                IvParameterSpec iv = new IvParameterSpec(xeh(ConstantesUtil.IV_PARAM), 0, 16);

                // Instantiate the cipher
                Cipher cipher = Cipher.getInstance(ConstantesUtil.ALGORITHM_CYPHER_PKCS5PADDING);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(message.getBytes(ConstantesUtil.ENCODE_UTF8));

                message = new String(Base64.encodeBase64(encrypted));

            }
        }
        return message;
    }

    public String decrypt( String message ) throws Exception {

        if(message!=null){
            if(!message.equals("")){

                String cypherSecret = (ConstantesUtil.KEY_CIPHER.length()>32?ConstantesUtil.KEY_CIPHER.substring(0,32):ConstantesUtil.KEY_CIPHER);
                SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(cypherSecret,32,"0").getBytes(ConstantesUtil.ENCODE_UTF8), ConstantesUtil.ALGORITHM_METHOD_AES);
                IvParameterSpec iv = new IvParameterSpec(xeh(ConstantesUtil.IV_PARAM), 0, 16);

                // Instantiate the cipher
                Cipher cipher = Cipher.getInstance(ConstantesUtil.ALGORITHM_CYPHER_PKCS5PADDING);
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
