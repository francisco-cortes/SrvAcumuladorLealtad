package com.baz.lealtad.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.HashMap;

public class LogServicio {

  private static final Logger serviceLogger = LogManager.getLogger(LogServicio.class);

  private StringBuilder sb = null;
  private HashMap<String, BeanLog> hs = null;
  /**
   * Solo en caso de ser necesario excluir una palabra que genere falsos positivos en respuestas exitosas, sera necesario agregarla a la lista.
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
   * @param service_name: Nombre del servicio que se consume.
   * @param system_name : Nombre del sistema.
   * @return: void.
   */
  public void setBegTimeMethod(String service_name, String system_name) {
    hs.put(service_name, new BeanLog(service_name
      ,system_name
      ,System.currentTimeMillis())
    );
  }

  /**
   * Funcion para finalizar tiempo de consumo.
   * @param service_name: Nombre del servicio que se consume.
   * @return: void.
   */
  public void setEndTimeMethod(String service_name) {
    if (null != hs.get(service_name)) {

      long tiempoService = 0L;

      tiempoService  = System.currentTimeMillis() - hs.get(service_name).getTime();

      hs.get(service_name).setTime(tiempoService);
      hs.get(service_name).setTimeTotal(tiempoService);
      hs.get(service_name).setTerminateTime(true);
    }
  }

  public void setEndTimeMethod(String service_name, String service_name_anidado) {
    if (null != hs.get(service_name) && null != hs.get(service_name_anidado)) {
      long tiempoServiceReal = 0L;
      long tiempototal = 0L;

      tiempoServiceReal = (System.currentTimeMillis() - hs.get(service_name).getTime()) - hs.get(service_name_anidado).getTimeTotal();
      tiempototal =  hs.get(service_name_anidado).getTimeTotal() + tiempoServiceReal;

      hs.get(service_name).setTime(tiempoServiceReal);
      hs.get(service_name).setTimeTotal(tiempototal);
      hs.get(service_name).setTerminateTime(true);
    } else {
      setEndTimeMethod(service_name);
    }
  }

  /**
   * Funcion para obtener el arreglo de consumo de servicios en el formato BAZ.
   * @return: Cadena con lista de servicios consumidos durante la transacción.
   */
  public String getServiceFotmat() {

    for (BeanLog bl : hs.values()) {


      sb.append("{\"servicio\":\"");
      sb.append(bl.getServicio());
      sb.append("\",\"Sistema\":\"");
      sb.append(bl.getSistema());
      sb.append("\",\"Tiempo\":");
      sb.append(bl.getTime());
      if (hs.get( (hs.keySet().toArray())[ hs.size()-1 ])== bl) {
        sb.append("}");
      }else{
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
  public void exepcion(Exception e, String msg) {

    ThreadContext.put("tiempo", "0");

    StringBuilder error = new StringBuilder();

    try {

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
            error.append(e.toString().replace("\n", " ").replace("\r", " ").replace('\"', '\'').trim());
            error.append(msg == null ? "" : " MENSAJE -->> " + msg.trim());
            break;
          }
        }
      } else {
        error.append("Exception: ");
        error.append(e.toString().replace('\n', '_').replace('\r', '_'));
        error.append(msg == null ? "" : " MENSAJE -->> " + msg.replace('\n', '_').replace('\r', '_').replace('\"', '\'').trim());
      }

    } catch (Exception e1) {

      serviceLogger.error(e1.toString().trim());
    }
    serviceLogger.error(error.toString().trim());
  }

  /**
   * Funcion para grabar en el log el detalle general del consumo de la función.
   * @param msg: comlemento informativo, para el mensaje final.
   * @return: void.
   */
  public void mensaje(String servicio, String msg) {

    for (BeanLog value : hs.values()) {
      if(!value.isTerminateTime()) {
        hs.get(value.getServicio()).setTime(System.currentTimeMillis() - hs.get(value.getServicio()).getTime());
        hs.get(value.getServicio()).setTerminateTime(true);
      }
    }

    for (String str : arrExclusions) {
      msg = msg.replace(str, " T_T");
    }

    ThreadContext.put("tiempo", getTimeTotal(servicio));
    ThreadContext.put("servicios", getServiceFotmat());
    serviceLogger.info(msg.replace('\n', '_').replace('\r', '_').replace("error", "incidencia").replace("Error", "Incidencia"));
    ThreadContext.clearAll();
  }


  /**
   *
   * @param servicio
   * @param msg
   */
  public void tramaSocio(String servicio, String msg) {

    ThreadContext.put("servicios",'\"'+ servicio + '\"');
    ThreadContext.put("tiempo", (System.currentTimeMillis() - hs.get(servicio).getTime())+"");
    serviceLogger.info(msg.replace('\n', '_').replace('\r', '_').replace('\"', '\''));

    ThreadContext.clearAll();
  }

}
