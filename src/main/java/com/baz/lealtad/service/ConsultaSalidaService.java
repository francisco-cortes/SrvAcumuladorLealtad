package com.baz.lealtad.service;

import com.baz.lealtad.controllers.MainController;
import com.baz.lealtad.daos.EjecutarSpSalidaDao;
import com.baz.lealtad.dtos.SpSalidaResponseDto;
import org.apache.log4j.Logger;

public class ConsultaSalidaService {

    private static final Logger logger = Logger.getLogger(ConsultaSalidaService.class);
    private EjecutarSpSalidaDao baseSp = new EjecutarSpSalidaDao();

    public SpSalidaResponseDto consulta(String ejemplo){
        logger.info("Consulta del primer SP");
        SpSalidaResponseDto respuestaSp = new SpSalidaResponseDto();
        respuestaSp = baseSp.ejecutarSpSalida(ejemplo);
        return respuestaSp;
    }
}
