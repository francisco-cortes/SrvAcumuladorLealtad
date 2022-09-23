package com.baz.lealtad.logger;

import lombok.Data;

@Data
public class BeanLog {
  private String servicio;
  private String sistema;
  private Long time;
  private Long timeTotal;
  private boolean terminateTime = false;

  public BeanLog(String servicio, String sistema, Long time) {
    this.servicio = servicio;
    this.sistema = sistema;
    this.time = time;
  }
}
