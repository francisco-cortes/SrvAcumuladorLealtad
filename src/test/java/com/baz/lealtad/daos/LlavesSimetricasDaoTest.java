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
/**
 * TEST LlavesSimetricasDao
 * Descrpcion: Clase para test unitarios del API Seguridad llaves Simetricas
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class LlavesSimetricasDaoTest {
  /**
   * TEST ApiLealtadDao
   * Descrpcion: Clase para test unitarios del ApiLealtadDaoTest
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria para obtener Llaves Simetricas del api de seguridad")
  @Test
  public void testGetSimetricas() {
    SetterTestParams.setAllRequiredParams();
    /*
    objetos
     */
    LogServicio log = new LogServicio();
    TokenDao tokenDao = new TokenDao();
    LlavesAsimetricasDao llavesAsimetricasDao = new LlavesAsimetricasDao();
    LlavesSimetricasDao llavesSimetricasDao = new LlavesSimetricasDao();
    /*
    constantes
     */
    final int ID_ACCESO = 0;
    final int CANTIDAD_LLAVES_SIMETICAS = 2;
    final int ACCESO_SIMETRICO = 0;
    /*
    respuesta
     */
    String respuesta = "";
    String[] respuesta2;
    String[] respuesta3 = new String[CANTIDAD_LLAVES_SIMETICAS];
    boolean respBol;

    try {

      /*
      primero se obtien el token
       */
      respuesta = tokenDao.getToken(log);
      /*
      despues llaves Asiemtricas
       */
      respuesta2 = llavesAsimetricasDao.getLlavesAsimetricas(respuesta,log);
      /*
      depus llaves simetricas
       */
      respuesta3 = llavesSimetricasDao.getLlavesSimetricas(respuesta,respuesta2[ID_ACCESO],log);
    }
    catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      log.exepcion(e,"Error en Simetricas TEST");
    }
    StringBuilder aux = new StringBuilder("Llaves Simetricas: ");
    for(int i = 0; i < CANTIDAD_LLAVES_SIMETICAS-1; i++){
      aux.append(respuesta3[i]).append(" - ");
    }
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, aux.toString());
    respBol = !respuesta3[ACCESO_SIMETRICO].isEmpty();
    /*
    acierta si la respuesta de simetricas no esta vacia
     */
    assertEquals(true, respBol);
  }

}
