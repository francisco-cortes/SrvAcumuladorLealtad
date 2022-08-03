package com.baz.lealtad.controllers;

import com.baz.lealtad.dtos.DatabaseResponseDto;
import com.baz.lealtad.service.ConsultaBaseService;
import com.baz.lealtad.utils.CifradorAesUtil;
import com.baz.lealtad.utils.CifradorRsaUtil;

public class MainController {

    private static ConsultaBaseService dbService = new ConsultaBaseService();
    private static CifradorRsaUtil cifrardor = new CifradorRsaUtil("","");

    public static void main(String[] args){
        String hola = "hola";
        DatabaseResponseDto responseDb = new DatabaseResponseDto();
        System.out.println("hello world");

        try {
            hola = cifrardor.encrypt("Hello");
            System.out.println(hola);
            hola = cifrardor.decrypt(hola);
            System.out.println(hola);
        } catch (Exception e) {
            e.printStackTrace();
        }

        responseDb = dbService.consulta("bye");
        System.out.println(responseDb.getRespuestaXD());


    }

}
