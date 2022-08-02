package com.baz.lealtad.service;

import com.baz.lealtad.dtos.DatabaseResponseModel;

public class ConsultaBaseService {

    public DatabaseResponseModel consulta(String ejemplo){
        DatabaseResponseModel ejemplor = new DatabaseResponseModel();
        ejemplor.setRespuestaXD(ejemplo);
        return ejemplor;
    }
}
