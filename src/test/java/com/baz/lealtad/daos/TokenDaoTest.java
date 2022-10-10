package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * TEST TokenDaoTest
 * Descrpcion: Clase para test unitarios de token api seguridad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 * params:
 * returns:
 **/
public class TokenDaoTest {
  /*
  constates glabales
   */
  private static final String SERVICE_NAME = "TokenDaoTEST";
  private static final LogServicio log = new LogServicio();
  private static final TokenDao tokenDao = new TokenDao();
  /**
   * testGetToken
   * Descrpcion: metodo que maneja el test tokenDAo
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria para obtener token del api de seguridad")
  @Test
  public void testGetToken() {
    SetterTestParams.setAllRequiredParams();
    /*
    contrasena consumer
     */
    String respuesta = "";
    String respuesta2 = "";
    boolean respBol;
    log.setBegTimeMethod(SERVICE_NAME,ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    try {
      /*
      metodo de obtencion token
       */
      ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3FL");
      respuesta = tokenDao.getToken(log);
      ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3F8");
      respuesta2 = tokenDao.getToken(log);
    }
    catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      log.exepcion(e,"Error en TEST tokenDAo");
    }
    if(!respuesta.isEmpty() && respuesta2.isEmpty()){
      respBol = true;
    }
    else{
      respBol = false;
    }
    log.mensaje(SERVICE_NAME, "Token: " + respuesta);
    log.setEndTimeMethod(SERVICE_NAME);
    assertTrue(respBol);
  }

}
