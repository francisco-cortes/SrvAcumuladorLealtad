package com.baz.lealtad.service;

import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * <b>SpEntradaServiceTest</b>
 * @descripcion: testUnitario para verificar el segundo sp
 * @autor: Francisco Javier Cortes Torres, Desarrollador
 * @ultimaModificacion: 31/10/22
 */
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
    /*
    settea las variables nesesarias para los test
    simulo el properties
     */
    SetterTestParams.setAllRequiredParams();
    /*
    objetos
     */
    LogServicio log = new LogServicio();
    SpEntradaService spEntradaService = new SpEntradaService();
    /*
    constantes
     */
    //folio premia se obtiene de la api de lealtad
    final String FOLIO_PREMIA = "49f9b6cf-1b50-";
    //mensaje se  obtiene de la api de lealtad
    final String MENSAJE = "Operacion exitosa";
    //bandera 0 es una respuesta positiva
    final String BANDERA = "0";
    //id tipo cliente unico
    final int ID_TIPO_CLIENTE = 3;
    // id cliente de alguien
    final String ID_CLIENTE = "0101-0127-";
    // importe cantidad en pesos
    final Double IMPORTE = 1802.25;
    // donde se realizo
    final int SUCURSAL = 127;
    // id operacion es transaferencia
    final int ID_OPERACION = 3;
    // origine de la transaccion 1 ventanilla
    final int ORIGEN_TRANS = 1;
    // pais de la transaccion 1 mexicon
    final int PAIS_ID = 1;
    // mfolio de mtcn
    final String MTCN = "MC-";
    // fecha cuando se ejecuta el tests
    final Date FECHA = new java.sql.Date(System.currentTimeMillis());
    // valor minimo para generar un mtcn
    final int MIN = 100000;
    //valor maximo para generar un mtcn
    final int MAX = 999999;
    // valor minimo para generar un id cliente
    final int CMIN = 1000;
    // valor maximo para generar un id clieente
    final int CMAX = 9999;
    //genera un valor entre 100000 y 999999
    int random_int = (int)Math.floor(Math.random()*(MAX-MIN+1)+MIN);
    //genera un valor entre 1000 y 9999
    int random_int2 = (int)Math.floor(Math.random()*(CMAX-CMIN+1)+CMIN);
    // genera un mtcn aleatorio
    String randomMTNC = MTCN + random_int;
    // genera un id aleatorio
    String randomID = ID_CLIENTE + random_int2 + "-1";
    // genera un folio premia aleatorio
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
    assertEquals("OPERACION EXITOSA",
      spEntradaService.guardarBase(parameters,randomFolio,MENSAJE,BANDERA,log));

  }
}
