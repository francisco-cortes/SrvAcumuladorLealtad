package com.baz.lealtad.logger;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.FabricaDaoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TEST FabricaDaoUtil
 * Descrpcion: Clase para test unitarios del ApiLealtadDaoTest
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 * params: byteArray, byteArray
 * returns: byteArray
 **/
public class LogServicioTest {
  /**
   * TEST ApiLealtadDao
   * Descrpcion: Clase para test unitarios del ApiLealtadDaoTest
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: byteArray, byteArray
   * returns: byteArray
   **/
  @DisplayName("Prueba Unitaria logger")
  @Test
  public void testAllLogger(){
    final String SERVICE_NAME = "AllmethodsLoger";
    LogServicio log = new LogServicio();
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    /*
    inicio de tiempo de ejecucion del metod
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    log.setBegTimeMethod(SERVICE_NAME,ParametrerConfiguration.SYSTEM_NAME);

    /*
    imprime un mensaje en log
     */
    log.mensaje(SERVICE_NAME,"Prueba de metodos LOGGER baz");
    /*
    muestra el formato del service
     */
    String serviceFormat = log.getServiceFotmat();
    /*
    el tiempo total de ejcucion
     */
    String timeTotal = log.getTimeTotal(SERVICE_NAME);
    /*
    imprime el tiempo y formato
     */
    log.mensaje(SERVICE_NAME, "Formato: " + serviceFormat + "Tiempo del metodo: " + timeTotal);
    /*
    esta parte siempre arroja una expecion
     */
    try {
      fabricaDaoUtil.cerrarConexionSinResult(null,null);
    } catch (Exception e) {
      /*
      manejo de exepcion con logger
       */
      log.exepcion(e,"Exepcion de prueba");
    }
     /*
    finaliza para un metodo anidado
     */
    /*
    identifica si se creo el archivo .log, CAMBIAR RUTA POR UNA LOCAL
     */
    File archivoLog = new File("/Users/fcortest/Desktop/SrvAcumuladorLealtad/log/" +
      "logSrvAcumuladorLealtad.log");
    log.setEndTimeMethod(SERVICE_NAME);
    assertTrue(archivoLog.exists());
  }

}
