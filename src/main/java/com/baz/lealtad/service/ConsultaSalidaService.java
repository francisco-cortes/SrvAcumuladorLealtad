package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpSalidaDao;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;
import java.util.List;

/**
 * ConsultaSalidaService
 * Descrpcion: service para invocar el dao del primer sp
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class ConsultaSalidaService {
  /*
  objetos
   */
  private static final EjecutarSpSalidaDao baseSp = new EjecutarSpSalidaDao();
  /**
   * consulta
   * Descrpcion: obtiene el pay load del primer servicio
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: log(LogService)
   * returns: List of objets
   **/
  public List<CursorSpSalidaModel> consulta(LogServicio log){
    /*
    construye salida
     */
    List<CursorSpSalidaModel> respuestaSp;

    respuestaSp = baseSp.ejecutarSpSalida(log);

    return respuestaSp;

  }
}
