package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FabricaDaoUtilTest {

  @BeforeAll
  static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
    ParametrerConfiguration.setOracleDatabaseIp("10.81.11.77");
    ParametrerConfiguration.setOracleDatabasePort("1521");
    ParametrerConfiguration.setOracleDatabaseName("SFBDDEV");
    ParametrerConfiguration.setOracleDatabaseP("BuSTxN4LMm");
    ParametrerConfiguration.setOracleDatabaseU("C3Multimarcas");
  }

  @DisplayName("Prueba Unitaria sobre FabricaDao")
  @Test
  public void testObtenerConexion() throws Exception {
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    assertNotNull(fabricaDaoUtil.obtenerConexion());
  }

  @DisplayName("Prueba Unitaria sobre exepcion FabricaDao")
  @Test
  void shouldThrowException() throws Exception {
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    assertThrows(Exception.class, () -> {
      fabricaDaoUtil.obtenerConexion();
    });
  }
}
