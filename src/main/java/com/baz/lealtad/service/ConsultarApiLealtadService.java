package com.baz.lealtad.service;

import com.baz.lealtad.daos.ApiLealtadDao;
import org.apache.log4j.Logger;

import java.io.IOException;


public class ConsultarApiLealtadService {

    private static final Logger logger = Logger.getLogger(ConsultarApiLealtadService.class);
    private static final ApiLealtadDao leal = new ApiLealtadDao();

    public String[] consultaApi (String idAcceso, String token, int idTipoCliente,
                                 String idCliente, String importe, int sucursal,
                                 int idOperacion, String folioTransaccion){
        String[] respuestaApi = new String[3];

        try {
            respuestaApi = leal.getAcumulaciones(idAcceso, token, 3,
                    idCliente, importe, sucursal, 3, folioTransaccion);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            respuestaApi[0] = "";
            respuestaApi[1] = "";
            respuestaApi[2] = "";
            logger.error("Error: " + e );
        }
        return respuestaApi;
    }

}
