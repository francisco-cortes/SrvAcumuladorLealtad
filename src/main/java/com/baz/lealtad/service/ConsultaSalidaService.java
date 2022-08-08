package com.baz.lealtad.service;

import com.baz.lealtad.controllers.MainController;
import com.baz.lealtad.daos.EjecutarSpSalidaDao;
import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.models.CursorSpSalidaModel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConsultaSalidaService {

    private static final Logger logger = Logger.getLogger(ConsultaSalidaService.class);
    private EjecutarSpSalidaDao baseSp = new EjecutarSpSalidaDao();

    public List<CursorSpSalidaModel> consulta(){
        logger.info("Consulta del primer SP");
        List<CursorSpSalidaModel> respuestaSp;
        respuestaSp = baseSp.ejecutarSpSalida();
        return respuestaSp;
    }
}
