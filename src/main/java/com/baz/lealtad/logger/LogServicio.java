package com.baz.lealtad.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogServicio {

  private static final Logger LOGGER = LogManager.getLogger(LogServicio.class);

  /**
   * Funcion para grabar en el log la excepcion generada en el flujo aplicativo.
   * @param   e: Excepcion generada en la función que invoco.
   * @param msg: Comlemento informativo, para el mensaje final.
   * @return: void.
   */
  public void exepcion(Throwable e, String msg) {

    StringBuilder error = new StringBuilder();

    if (e.getStackTrace() != null) {
      int sizeStk = e.getStackTrace().length;
      for (int i = 0; i < sizeStk; i++) {
        if (e.getStackTrace()[i].getClassName().contains("com.baz.lealtad")) {
          error.append("(");
          error.append(e.getStackTrace()[i].getClassName());
          error.append(")");
          error.append(" METODO -->> ");
          error.append(e.getStackTrace()[i].getMethodName());
          error.append(" LINEA -->> ");
          error.append(e.getStackTrace()[i].getLineNumber());
          error.append(" EXCEPTION -->> ");
          error.append(e.toString().replace("\n", " ")
            .replace("\r", " ")
            .replace('\"', '\'').trim());
          error.append(" MENSAJE -->> " + msg.trim());
          break;
        }
      }
    }
    else {
      error.append("Exception: ");
      error.append(e.toString()
        .replace('\n', '_')
        .replace('\r', '_'));
      error.append(" MENSAJE -->> " + msg.replace('\n', '_')
              .replace('\r', '_')
              .replace('\"', '\'').trim());
    }
    LOGGER.error(error.toString().trim());
  }

  /**
   * Funcion para grabar en el log el detalle general del consumo de la función.
   * @param msg: comlemento informativo, para el mensaje final.
   * @return: void.
   */
  public void mensaje(String servicio, String msg) {

    LOGGER.info(servicio + msg.replace('\n', '_').replace('\r', '_')
      .replace("error", "incidencia")
      .replace("Error", "Incidencia"));

  }

}
