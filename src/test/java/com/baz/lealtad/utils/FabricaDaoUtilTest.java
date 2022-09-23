package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @DisplayName("Single test successful")
  @Test
  public void testObtenerConexion() throws Exception {
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    assertEquals(null, fabricaDaoUtil.obtenerConexion());
  }

  @Test
  void shouldThrowException() throws Exception {
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    assertThrows(Exception.class, () -> {
      fabricaDaoUtil.obtenerConexion();
    });
  }
}
