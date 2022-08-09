package com.baz.lealtad.service;

import com.baz.lealtad.utils.CifradorAesUtil;

public class CifrarDesifrarAesService {

    CifradorAesUtil cifradorAes = new CifradorAesUtil();

    public String cifrar (String texto, String aes, String hmac){
        String textoCifrado = cifradorAes.encryptAes(aes,hmac,texto);
        return textoCifrado;
    }

    public String decifrar (String texto, String aes, String hmac){
        String textoDecifrado = cifradorAes.decryptAes(aes,hmac,texto);
        return textoDecifrado;
    }

}
