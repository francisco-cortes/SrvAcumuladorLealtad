package com.baz.lealtad.jobs;

import com.baz.lealtad.dtos.DatabaseResponseDto;
import com.baz.lealtad.service.ConsultaBaseService;

public class MainJob {

    private static ConsultaBaseService dbService = new ConsultaBaseService();

    public static void main(String[] args){
        DatabaseResponseDto responseDb = new DatabaseResponseDto();
        System.out.println("hello world");
        responseDb = dbService.consulta("bye");
        System.out.println(responseDb.getRespuestaXD());
    }

}
