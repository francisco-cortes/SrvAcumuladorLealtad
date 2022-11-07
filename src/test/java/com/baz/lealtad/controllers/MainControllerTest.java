package com.baz.lealtad.controllers;

import com.baz.lealtad.configuration.SetterTestParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TEST TokenDaoTest
 * Descrpcion: Clase para test unitarios de token api seguridad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 * params:
 * returns:
 **/
public class MainControllerTest {
  @DisplayName("Prueba")
  @Test
  public void mainTest(){
    String[] args = new String[3];
    SetterTestParams.setAllRequiredParams();
    MainController.main(args);
  }
  /**
   * testImporteRedondeador
   * Descrpcion: revisa erl redondeo
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria para redondeo de importes")
  @Test
  public void testImporteRedondeador(){
    final Double IMPORTE = 108.4;
    final String ESPERADO = "108";
    String resp = MainController.importeRedondeador(IMPORTE);
    assertEquals(ESPERADO, resp);
  }
}
