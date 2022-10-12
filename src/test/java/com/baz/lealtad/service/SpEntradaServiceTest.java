package com.baz.lealtad.service;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpEntradaServiceTest {
  /**
   * testEjecutaSpEntrada
   * Descrpcion: clase test envia datos al segundo sp
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params:
   * returns:
   **/
  @DisplayName("Prueba Unitaria sobre Segundo SPm a travez de service")
  @Test
  public void testEjecutarSpEntrada(){
    SetterTestParams.setAllRequiredParams();
    /*
    objetos
     */
    LogServicio log = new LogServicio();
    log.setBegTimeMethod(ParametrerConfiguration.SYSTEM_NAME_TEST,ParametrerConfiguration.SYSTEM_NAME);
    SpEntradaService spEntradaService = new SpEntradaService();
    /*
    constantes
     */
    final String FOLIO_PREMIA = "49f9b6cf-1b50-";
    final String MENSAJE = "Operacion exitosa";
    final String BANDERA = "0";
    final int ID_TIPO_CLIENTE = 3;
    final String ID_CLIENTE = "0101-0127-";
    final Double IMPORTE = 1802.25;
    final int SUCURSAL = 127;
    final int ID_OPERACION = 3;
    final int ORIGEN_TRANS = 1;
    final int PAIS_ID = 1;
    final String MTCN = "MC-";
    final Date FECHA = new java.sql.Date(System.currentTimeMillis());
    final int MIN = 100000;
    final int MAX = 999999;
    final int CMIN = 1000;
    final int CMAX = 9999;
    int random_int = (int)Math.floor(Math.random()*(MAX-MIN+1)+MIN);
    int random_int2 = (int)Math.floor(Math.random()*(CMAX-CMIN+1)+CMIN);
    String randomMTNC = MTCN + random_int;
    String randomID = ID_CLIENTE + random_int2 + "-1";
    String randomFolio = FOLIO_PREMIA + random_int2 + "-b0ee-f2ae5bcb360";
    /*
    datos de prueba inventados
     */
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("idTipoCliente", ID_TIPO_CLIENTE);
    parameters.put("idCliente", randomID);
    parameters.put("importe", IMPORTE);
    parameters.put("fechaOperacion",FECHA);
    parameters.put("negocio","Dex");
    parameters.put("tipoOperacion", "E");
    parameters.put("origenTransaccion",ORIGEN_TRANS);
    parameters.put("paisId", PAIS_ID);
    parameters.put("sucursal", SUCURSAL);
    parameters.put("idOperacion", ID_OPERACION);
    parameters.put("folioTransaccion", randomMTNC);
    /*
    Operacion exitosa
     */
    log.setEndTimeMethod(ParametrerConfiguration.SYSTEM_NAME_TEST);
    assertEquals("OPERACION EXITOSA",
      spEntradaService.guardarBase(parameters,randomFolio,MENSAJE,BANDERA,log));

  }
}
