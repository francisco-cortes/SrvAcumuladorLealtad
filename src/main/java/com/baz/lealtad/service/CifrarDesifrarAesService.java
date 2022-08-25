package com.baz.lealtad.service;

import com.baz.lealtad.utils.CifradorAesUtil;

public class CifrarDesifrarAesService {

    private static final CifradorAesUtil cifradorAes = new CifradorAesUtil();

    public String cifrar (String texto, String aes, String hmac){

        return cifradorAes.encryptAes(aes,hmac,texto);

    }

}
