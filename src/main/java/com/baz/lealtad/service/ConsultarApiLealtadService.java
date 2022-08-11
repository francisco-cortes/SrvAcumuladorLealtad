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
            respuestaApi = leal.getAcumulaciones(idAcceso, token, idTipoCliente,
                    idCliente, importe, sucursal, idOperacion, folioTransaccion);
        } catch (IOException e) {
            respuestaApi[0] = "";
            respuestaApi[1] = "";
            respuestaApi[2] = "";
            logger.info("Error: " + e );
        } catch (InterruptedException e) {
            respuestaApi[0] = "";
            respuestaApi[1] = "";
            respuestaApi[2] = "";
            logger.info("Error: " + e );
        }
        return respuestaApi;
    }

}
