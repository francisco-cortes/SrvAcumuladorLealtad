package com.baz.lealtad.controllers;

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
  /**
   * TEST testIdTipoClienteSetterClienteDEx
   * Descrpcion: id tipo clioente 5
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria comprobar id cliente dex")
  @Test
  public void testIdTipoClienteSetterClienteDex(){
    /*
    tipo de negocio dex debe un tipo cliente 5
     */
    final String NEGOCIO = "DEX";
    /*
    el id cliente debe ser numeroco de 7 a 10 digitos
     */
    final String ID_CLIENTE = "171834554";
    /*
    se espera un 5
     */
    final int TIPO_ESPERADO = 5;
    int tipocliente = MainController.idTipoClienteSetter(NEGOCIO,ID_CLIENTE);
    /*
    acierta si es igual
     */
    assertEquals(TIPO_ESPERADO,tipocliente);
  }

  /**
   * testIdTipoClienteSetterCliente
   * Descrpcion: test para cliente unico
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria comprobar id cliente unico")
  @Test
  public void testIdTipoClienteSetterCliente(){
    /*
    si no es dex el id cliente es 3
     */
    final String NEGOCIO = "MM";
    final String ID_CLIENTE = "171834554";
    /*
    se espera un 3
     */
    final int TIPO_ESPERADO = 3;
    int tipocliente = MainController.idTipoClienteSetter(NEGOCIO,ID_CLIENTE);
    /*
    acierta si es igual
     */
    assertEquals(TIPO_ESPERADO,tipocliente);
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
