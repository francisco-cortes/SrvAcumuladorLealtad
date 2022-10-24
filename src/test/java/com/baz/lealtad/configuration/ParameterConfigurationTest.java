package com.baz.lealtad.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterConfigurationTest {
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
    boolean ok = ParametrerConfiguration.loadConfigs();
    assertTrue(ok);
  }

}
