package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpSalidaDao;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;


import java.util.List;

public class ConsultaSalidaService {

    private static final EjecutarSpSalidaDao baseSp = new EjecutarSpSalidaDao();

    public List<CursorSpSalidaModel> consulta(LogServicio log){

        List<CursorSpSalidaModel> respuestaSp;

        respuestaSp = baseSp.ejecutarSpSalida(log);

        return respuestaSp;

    }
}
