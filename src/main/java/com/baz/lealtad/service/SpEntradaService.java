package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpEntradaDao;
import com.baz.lealtad.logger.LogServicio;

import java.util.Map;
/**
 * SPEntradaService
 * Descrpcion: Clase para invocar el SP para guardar las respuesta del api lealtad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class SpEntradaService {
  /*
  objetos
   */
  private static final EjecutarSpEntradaDao baseEntradaSp = new EjecutarSpEntradaDao();
  /**
   * guardarBase
   * Descrpcion: ejecuta sp 2
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: params(maps), folio premmia(String), comentarios(String), bandera(String), log(LogServicio)
   **/
  public String guardarBase (Map<String, Object> params, String folioPremia,
                           String comentarios, String bandera, LogServicio log){
    /*
    consulta segundo sp
     */
    return baseEntradaSp.ejecutarSpEntrada(params, folioPremia,comentarios,bandera, log);

  }
}
