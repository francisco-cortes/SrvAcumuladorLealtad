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
    /*
    inicia un string builder para concatenar el parceo del error
     */
    StringBuilder error = new StringBuilder();

    /*
    valida si exite un stacktrace de la exepcion
     */
    if (e.getStackTrace() != null) {
      /*
      inicica el parceo de los elementos del estactrace
       */
      int sizeStk = e.getStackTrace().length;
      for (int i = 0; i < sizeStk; i++) {
        if (e.getStackTrace()[i].getClassName().contains("com.baz.lealtad")) {
          // imprime la clase que fallo
          error.append("(");
          error.append(e.getStackTrace()[i].getClassName());
          error.append(")");
          // imprime el metodo que fallo
          error.append(" METODO -->> ");
          error.append(e.getStackTrace()[i].getMethodName());
          // imprime la linea que fallo
          error.append(" LINEA -->> ");
          error.append(e.getStackTrace()[i].getLineNumber());
          error.append(" EXCEPTION -->> ");
          // remplaza saltos de linea, tabuladores, y comillas para ajustar a una sola linea
          error.append(e.toString().replace("\n", " ")
            .replace("\r", " ")
            .replace('\"', '\'').trim());
          error.append(" MENSAJE -->> " + msg.trim());
          break;
        }
      }
    }
    /*
    Si no se tiene un stacktrace
     */
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

    /*
    concatena el servicio con el mensaje eliminado los saltos de linea y tabuladores
     */
    LOGGER.info(servicio + msg.replace('\n', '_').replace('\r', '_')
      .replace("error", "incidencia")
      .replace("Error", "Incidencia"));

  }

}
