package com.baz.lealtad.service;

import com.baz.lealtad.daos.ApiLealtadDao;
import com.baz.lealtad.logger.LogServicio;

import java.util.Map;

/**
 * ConsultarApiLealtadService
 * Descrpcion: services para invocar el DAo de api lealtad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class ConsultarApiLealtadService {
  /*
  objetos
   */
  private static final ApiLealtadDao leal = new ApiLealtadDao();
  /**
   * consultaApi
   * Descrpcion: preve los parametros para invocar la consulta al api lealtad
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: idAcceso(String), token(String), params(Map), log(LogServicio)
   * returns: String Array
   **/
  public String[] consultaApi (String idAcceso, String token, Map<String, Object> params, LogServicio log){
    /*
    cosntantes
     */
    final int TAMANO_RESPUESTA = 3;

    String[] respuestaApi = new String[TAMANO_RESPUESTA];

    try {
      /*
      consulta a api lealtad
       */
      respuestaApi = leal.getAcumulaciones(idAcceso, token, params, log);
    }
    catch (Exception e) {
      /*
      elimina el proceso en el hilo
       */
      Thread.currentThread().interrupt();
      log.exepcion(e, "ERROR al consultar api lealtad");
    }
    return respuestaApi;
  }

}
