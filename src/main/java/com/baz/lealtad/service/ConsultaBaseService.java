package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSp;
import com.baz.lealtad.dtos.DatabaseResponseDto;

public class ConsultaBaseService {

    EjecutarSp baseSp = new EjecutarSp();

    public DatabaseResponseDto consulta(String ejemplo){
        DatabaseResponseDto respuestaSp = new DatabaseResponseDto();
        respuestaSp = baseSp.ejecutarSp();
        respuestaSp.setRespuestaXD(ejemplo);
        return respuestaSp;
    }
}
