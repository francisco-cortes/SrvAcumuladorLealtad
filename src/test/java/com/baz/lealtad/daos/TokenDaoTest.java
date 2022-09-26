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

public class TokenDaoTest {

  @BeforeAll
  static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
    ParametrerConfiguration.setTokenUrl("https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token");
    ParametrerConfiguration.setConsumerSecret("RqV58GRGKromtjdWohnlCqAKy8dt3Cn1");
    ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3FL");

  }

  @DisplayName("Prueba unitaria para obtener token del api de seguridad")
  @Test
  public void testGetToken() {
    LogServicio log = new LogServicio();
    TokenDao tokenDao = new TokenDao();
    String respuesta = "";
    boolean respBol = false;
    try {
      respuesta = tokenDao.getToken(log);
    } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }
    respBol = !respuesta.isEmpty();
    assertEquals(true, respBol);
  }

}
