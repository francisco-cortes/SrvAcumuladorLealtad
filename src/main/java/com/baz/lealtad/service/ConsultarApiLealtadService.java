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
        // idtipoCleinte y idoperacion set in 3 default
        try {
            respuestaApi = leal.getAcumulaciones(idAcceso, token, idTipoCliente,
                    idCliente, importe, sucursal, idOperacion, folioTransaccion);
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
