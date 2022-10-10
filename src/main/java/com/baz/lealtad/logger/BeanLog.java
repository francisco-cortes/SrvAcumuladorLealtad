package com.baz.lealtad.logger;

import lombok.Data;

/**
 * BeanLog
 * Descrpcion: DATA CLASS lombok
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

@Data
public class BeanLog {
  //nombre d clase o metodo
  private String servicio;
  // nombre del proyecto o app
  private String sistema;
  // tiempo
  private Long time;
  // tiempo menos tiempo
  private Long timeTotal;
  // se acabo el tiempo?
  private boolean terminateTime;
  /**
   * BeanLog
   * Descrpcion: constructor
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/
  public BeanLog(String servicio, String sistema, Long time) {
    this.servicio = servicio;
    this.sistema = sistema;
    this.time = time;
  }
}
