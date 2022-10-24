package com.baz.lealtad.service;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * ConsultaSalidaServiceTest
 * Descrpcion: consulta de sp a trevez del service
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 * params:
 * returns:
 **/
public class ConsultaSalidaServiceTest {

  /**
   * stup
   * Descrpcion: configura las propiedades nesesarias para el primer SP atravez de su clase service
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba unitaria del pay load primer SP")
  @Test
  public void testConsulta(){
    SetterTestParams.setAllRequiredParams();
    final int EMPTY = 0;
    LogServicio log = new LogServicio();
    /*
    objeto de clase
     */
    ConsultaSalidaService consultaSalidaService = new ConsultaSalidaService();
    /*
    Guarda la respuesta en una lista de la fortma CursorSpSalidaModel
     */
    List<CursorSpSalidaModel> cursorSpSalidaModelList = consultaSalidaService.consulta(log);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, "se obtuvieron " + cursorSpSalidaModelList.size() + " registros del primer sp");
    /*
    acierta si la el tamano es mayor a 0
     */
    assertTrue(cursorSpSalidaModelList.size()>EMPTY);
  }

}
