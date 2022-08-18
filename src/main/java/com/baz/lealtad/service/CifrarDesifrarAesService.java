package com.baz.lealtad.service;

import com.baz.lealtad.utils.CifradorAesUtil;

public class CifrarDesifrarAesService {

    private final CifradorAesUtil cifradorAes = new CifradorAesUtil();

    public String cifrar (String texto, String aes, String hmac){
        return cifradorAes.encryptAes(aes,hmac,texto);
    }

    public String decifrar (String texto, String aes, String hmac){
        return cifradorAes.decryptAes(aes,hmac,texto);
    }

}
