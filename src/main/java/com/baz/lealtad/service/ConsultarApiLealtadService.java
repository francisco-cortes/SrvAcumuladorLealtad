package com.baz.lealtad.service;

import com.baz.lealtad.daos.ApiLealtadDao;
import org.apache.log4j.Logger;

import java.util.Map;


public class ConsultarApiLealtadService {

    private static final Logger LOGGER = Logger.getLogger(ConsultarApiLealtadService.class);
    private static final ApiLealtadDao leal = new ApiLealtadDao();

    public String[] consultaApi (String idAcceso, String token, Map<String, Object> params){

        String[] respuestaApi = new String[3];

        try {
            respuestaApi = leal.getAcumulaciones(idAcceso, token, params);
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Error: " + e );
        }
        return respuestaApi;
    }

}
