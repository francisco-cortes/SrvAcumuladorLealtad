package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * LlavesAsimetricasDaosTest
 * Descrpcion: Clase para test unitarios del ApiSeguridad llaves Asimetricas
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class LlavesAsimetricasDaoTest {
  /*
  Constantes globales
   */
  private static final String SERVICE_NAME = "LlavesAsimetricasDaoTEST";
  private static final int TAMANO_RESPUESTA_ASIMETICAS = 3;
  private static final int ID_ACCESO = 0;
  private static final int ACCESO_PUBLICO = 1;
  private static final int ACESOS_PRIVADO = 2;
  /*
    objetos
     */
  private static final LogServicio log = new LogServicio();
  private static final TokenDao tokenDao = new TokenDao();
  private static final LlavesAsimetricasDao llavesAsimetricasDao = new LlavesAsimetricasDao();
  /**
   * TEST GetAsimetricas
   * Descrpcion: obtiene las llaves asimetricas api seguridad
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria para obtener Llaves asimetricas del api de seguridad")
  @Test
  public void testGetAsimetricas() {
    SetterTestParams.setAllRequiredParams();
    log.setBegTimeMethod(SERVICE_NAME,ParametrerConfiguration.SYSTEM_NAME);
    String respuesta;
    String[] respuesta2 = new String[TAMANO_RESPUESTA_ASIMETICAS];
    boolean respBol;
    try {
      /*
      primero se obtiene el token
       */
      respuesta = tokenDao.getToken(log);
      /*
      despues la llaves Asimetricas
       */
      respuesta2 = llavesAsimetricasDao.getLlavesAsimetricas(respuesta,log);
    } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      log.exepcion(e, "Error en el TEST de Llaves Asimetricas");
    }
    log.mensaje(SERVICE_NAME, "Se obtuvo id accesos: " + respuesta2[ID_ACCESO] +
      " Publico: " + respuesta2[ACCESO_PUBLICO] + "Privado: " +
      respuesta2[ACESOS_PRIVADO]);
    /*
    acierta si el id acceso no esta vacio
     */
    respBol = !respuesta2[ID_ACCESO].isEmpty();
    log.setEndTimeMethod(SERVICE_NAME);
    assertEquals(true, respBol);
  }

  @DisplayName("Prueba unitaria para No obtener Llaves asimetricas del api de seguridad")
  @Test
  public void testNoGetAsimetricas() {
    SetterTestParams.setAllRequiredParams();
    log.setBegTimeMethod(SERVICE_NAME,ParametrerConfiguration.SYSTEM_NAME);
    //respuesta aaa
    String respuesta;
    String[] respuesta2 = new String[TAMANO_RESPUESTA_ASIMETICAS];
    try {
      /*
      primero se obtiene el token
       */
      respuesta = "";
      /*
      despues la llaves Asimetricas
       */
      respuesta2 = llavesAsimetricasDao.getLlavesAsimetricas(respuesta,log);
    } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      log.exepcion(e, "Error en el TEST de Llaves Asimetricas");
    }
    log.mensaje(SERVICE_NAME, "Se obtuvo id accesos: " + respuesta2[ID_ACCESO] +
      " Publico: " + respuesta2[ACCESO_PUBLICO] + "Privado: " +
      respuesta2[ACESOS_PRIVADO]);
    /*
    acierta si el id acceso no esta vacio
     */
    log.setEndTimeMethod(SERVICE_NAME);
    assertNull(respuesta2[ID_ACCESO]);
  }

}
