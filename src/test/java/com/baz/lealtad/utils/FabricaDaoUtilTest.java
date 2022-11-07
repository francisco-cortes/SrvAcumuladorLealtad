package com.baz.lealtad.utils;

import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
  public void testObtenerConexion(){
    LogServicio logServicio = new LogServicio();
    FabricaDaoUtil fabricaDaoUtil = new FabricaDaoUtil();
    try {
      assertNotNull(fabricaDaoUtil.obtenerConexion());
    } catch (ClassNotFoundException e) {
      logServicio.exepcion(e,"Error en test fabricaDao");
    } catch (SQLException e) {
      logServicio.exepcion(e,"Error en test fabricaDao");
    }
  }
}
