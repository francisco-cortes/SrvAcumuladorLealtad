package com.baz.lealtad.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * TEST FabricaDaoUtil
 * Descrpcion: Clase para test unitarios del ApiLealtadDaoTest
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 * params: byteArray, byteArray
 * returns: byteArray
 **/
public class FabricaDaoUtilTest {
  /**
   * TEST ApiLealtadDao
   * Descrpcion: Clase para test unitarios del ApiLealtadDaoTest
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: byteArray, byteArray
   * returns: byteArray
   **/
  @DisplayName("Prueba Unitaria sobre FabricaDao")
  @Test
  public void testObtenerConexion() throws Exception {
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    assertNotNull(fabricaDaoUtil.obtenerConexion());
  }
}
