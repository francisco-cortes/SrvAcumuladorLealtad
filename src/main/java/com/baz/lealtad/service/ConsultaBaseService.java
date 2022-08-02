package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpDao;
import com.baz.lealtad.dtos.DatabaseResponseDto;

public class ConsultaBaseService {

    EjecutarSpDao baseSp = new EjecutarSpDao();

    public DatabaseResponseDto consulta(String ejemplo){
        DatabaseResponseDto respuestaSp = new DatabaseResponseDto();
        //respuestaSp = baseSp.ejecutarSp();
        respuestaSp.setRespuestaXD(ejemplo);
        return respuestaSp;
    }
}
