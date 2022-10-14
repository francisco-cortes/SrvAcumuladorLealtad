package com.baz.lealtad.logger;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.HashMap;

public class LogServicio {

  private static final Logger LOGGER = LogManager.getLogger(LogServicio.class);
  private static final String TIEMPO_CON = "tiempo";

  private StringBuilder sb;
  private HashMap<String, BeanLog> hs;
  /**
   * Solo en caso de ser necesario excluir una palabra que genere falsos positivos en respuestas exitosas,
   * sera necesario agregarla a la lista.
   * */
  private static final String[] arrExclusions = { "WARN", "ERROR", "SEVERE", "EXCEPTION" };

  /**
   *
   */

  public LogServicio() {
    sb = new StringBuilder();
    hs = new HashMap<>();
  }

  /**
   * Funcion para inicializar tiempo de consumo.
   * @param serviceName: Nombre del servicio que se consume.
   * @param systemName : Nombre del sistema.
   * @return: void.
   */
  public void setBegTimeMethod(String serviceName, String systemName) {
    hs.put(serviceName, new BeanLog(serviceName
      ,systemName
      ,System.currentTimeMillis())
    );
  }

  /**
   * Funcion para finalizar tiempo de consumo.
   * @param serviceName: Nombre del servicio que se consume.
   * @return: void.
   */
  public void setEndTimeMethod(String serviceName) {
    if (null != hs.get(serviceName)) {

      long tiempoService;

      tiempoService  = System.currentTimeMillis() - hs.get(serviceName).getTime();

      hs.get(serviceName).setTime(tiempoService);
      hs.get(serviceName).setTimeTotal(tiempoService);
      hs.get(serviceName).setTerminateTime(true);
    }
  }

  /**
   * Funcion para obtener el arreglo de consumo de servicios en el formato BAZ.
   * @return: Cadena con lista de servicios consumidos durante la transacción.
   */
  public String getServiceFotmat() {

    for (BeanLog bl : hs.values()) {
      sb.append("\",\"Sistema\":\"");
      sb.append(ParametrerConfiguration.SYSTEM_NAME);
      sb.append("\",\"Tiempo\":");
      sb.append(bl.getTime());
      if (hs.get( (hs.keySet().toArray())[ hs.size()-1 ])== bl) {
        sb.append("}");
      }
      else{
        sb.append("},");
      }
    }
    return sb.toString();
  }

  /**
   * Funcion para obtener el tiempo totald e la transacción al finalizar el proceso general.
   * @return: Tiempo expresado en milisegundos total de la transacción.
   */
  public String getTimeTotal(String servicio) {

    long timeTotal = 0L;

    for (BeanLog value : hs.values()) {
      if (!value.getServicio().equals(servicio)) {
        timeTotal += value.getTime();
      }
    }

    Long aux = hs.get(servicio).getTime();
    hs.get(servicio).setTime(aux - timeTotal);

    timeTotal = timeTotal + hs.get(servicio).getTime();

    return timeTotal+"";
  }


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
          error.append(msg == null ? "" : " MENSAJE -->> " + msg.trim());
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
