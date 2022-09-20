package com.baz.lealtad.service;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.daos.ApiLealtadDao;
import com.baz.lealtad.logger.LogServicio;

import java.util.Map;


public class ConsultarApiLealtadService {

    private static final ApiLealtadDao leal = new ApiLealtadDao();

    public String[] consultaApi (String idAcceso, String token, Map<String, Object> params, LogServicio log){
        log.setBegTimeMethod("ConsultarApiLealtadService.conultaApi", ParametrerConfiguration.SYSTEM_NAME);

        String[] respuestaApi = new String[3];

        try {
            respuestaApi = leal.getAcumulaciones(idAcceso, token, params, log);
        }
        catch (Exception e) {

            Thread.currentThread().interrupt();

            log.exepcion(e, "ERROR al consultar api lealtad");

        }
        log.setEndTimeMethod("ConsultarApiLealtadService.conultaApi");
        return respuestaApi;
    }

}
