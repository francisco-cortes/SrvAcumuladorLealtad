package com.baz.lealtad.jobs;

import com.baz.lealtad.dtos.DatabaseResponseModel;
import com.baz.lealtad.service.ConsultaBaseService;

public class mainjob {

    static ConsultaBaseService dbService = new ConsultaBaseService();

    public static void main(String[] args){
        DatabaseResponseModel responseDb = new DatabaseResponseModel();
        System.out.println("hello world");
        responseDb = dbService.consulta("bye");
        System.out.println(responseDb.getRespuestaXD());
    }

}
