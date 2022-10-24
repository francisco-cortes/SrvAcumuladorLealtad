package com.baz.lealtad.service;

import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CifrarDesifrarAesServiceTest {

  private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
  private static final LogServicio log = new LogServicio();
  private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
  /*
    Constantes para  getLlaves
     */
  private static final int TOKEN = 0;
  private static final int IDACCESO = 1;
  private static final int SIMETRICA_1 = 2;
  private static final int SIMETRICA_2 = 3;
  /**
   * TEST crifradorAes con llaver
   * Descrpcion: Clase para test unitarios
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   **/
  @DisplayName("cifrar aes datos")
  @Test
  public void testObtenerConexion(){
    SetterTestParams.setAllRequiredParams();
    String[] llavesAes = obtenerLlaves.getLlaves(log);
    /*
    cifrado del id cliente como lo requiere la api
     */
    String idCliente = cifrarService.cifrar("100",
      llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);
    System.out.println(idCliente);
  }
}
