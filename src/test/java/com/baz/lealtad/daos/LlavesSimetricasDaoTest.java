package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LlavesSimetricasDaoTest {
  @BeforeAll
  static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
    ParametrerConfiguration.setTokenUrl("https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token");
    ParametrerConfiguration.setAsimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves");
    ParametrerConfiguration.setSimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves-simetricas/");
    ParametrerConfiguration.setConsumerSecret("RqV58GRGKromtjdWohnlCqAKy8dt3Cn1");
    ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3FL");
  }

  @DisplayName("Prueba unitaria para obtener Llaves Simetricas del api de seguridad")
  @Test
  public void testGetSimetricas() {
    LogServicio log = new LogServicio();
    TokenDao tokenDao = new TokenDao();
    LlavesAsimetricasDao llavesAsimetricasDao = new LlavesAsimetricasDao();
    LlavesSimetricasDao llavesSimetricasDao = new LlavesSimetricasDao();
    String respuesta = "";
    String[] respuesta2 = new String[3];
    String[] respuesta3 = new String[2];
    boolean respBol = false;
    try {
      respuesta = tokenDao.getToken(log);
      respuesta2 = llavesAsimetricasDao.getLlavesAsimetricas(respuesta,log);
      respuesta3 = llavesSimetricasDao.getLlavesSimetricas(respuesta,respuesta2[0],log);
    } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }
    respBol = !respuesta3[0].isEmpty();
    assertEquals(true, respBol);
  }

}
