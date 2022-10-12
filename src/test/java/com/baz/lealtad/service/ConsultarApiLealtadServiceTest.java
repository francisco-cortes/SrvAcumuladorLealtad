package com.baz.lealtad.service;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.configuration.SetterTestParams;
import com.baz.lealtad.daos.TestParamMaker;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsultarApiLealtadServiceTest {
  /*
    obejetos test
     */
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
  /*
    Constantes para la creacion del JSON
     */
  private static final int ID_TIPO_CLIENTE = 3;
  private static final int ID_TIPO_DEX = 5;
  private static final String ID_CLIENTE = "0101-0127-4888";
  private static final int IMPORTE = 1802;
  private static final int SUCURSAL = 127;
  private static final int ID_OPERACION = 3;
  private static final String MTCN = "MC-793246234";
  /*
  Constantes para la respueta api
   */
  private static final int MENSAJE = 0;
  private static final int FOLIO = 1;
  private static final int BANDERA = 2;
  private static final int RESPUESTA_TAMANO = 3;

  @DisplayName("Prueba unitaria para api de lealtad Service")
  @Test
  public void testApiLeatadService() {

    SetterTestParams.setAllRequiredParams();
    log.setBegTimeMethod(ParametrerConfiguration.SYSTEM_NAME_TEST, ParametrerConfiguration.SYSTEM_NAME);
    ConsultarApiLealtadService apiLealtadDao = new ConsultarApiLealtadService();
    String[] llavesAes = obtenerLlaves.getLlaves(log);
    String[] respuesta = new String[RESPUESTA_TAMANO];
    String[] respuestaNegativa = new String[RESPUESTA_TAMANO];
    boolean respBol;
    /*
    cifrado del id cliente como lo requiere la api
     */
    String idCliente = cifrarService.cifrar(ID_CLIENTE,
      llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);
    /*
    cifrado para el importe como lo requiere la api
     */
    String importe = cifrarService.cifrar(String.valueOf(IMPORTE),
      llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);
    /*
    construccion del mapa de objetos para construir json de entrada a la api
     */
    Map<String, Object> parameters = TestParamMaker.parameters(ID_TIPO_CLIENTE, idCliente,
      importe, SUCURSAL, ID_OPERACION, MTCN);
    Map<String, Object> parametersNeg = TestParamMaker.parameters(ID_TIPO_DEX, idCliente,
      importe, SUCURSAL, ID_OPERACION, MTCN);

    /*
    ejecucion de la consulta a la api
     */
    try {
      respuesta = apiLealtadDao.consultaApi(llavesAes[IDACCESO], llavesAes[TOKEN],
        parameters, log);
      respuestaNegativa = apiLealtadDao.consultaApi(llavesAes[IDACCESO], llavesAes[TOKEN],
        parametersNeg, log);
    }
    catch (Exception e) {
      log.exepcion(e, "Error en test unitario api lealtad");
    }
    /*
    imprime mensaje de salida
     */
    if((!respuesta[MENSAJE].isEmpty() && !respuestaNegativa[MENSAJE].isEmpty())){
      respBol = true;
    }
    else {
      respBol = false;
    }
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, " Mensaje: " + respuesta[MENSAJE] + " Folio: "
      + respuesta[FOLIO] + " Bandera: " + respuesta[BANDERA]);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, " Mensaje: " + respuestaNegativa[MENSAJE] + " Folio: "
      + respuestaNegativa[FOLIO] + " Bandera: " + respuestaNegativa[BANDERA]);

    log.setEndTimeMethod(ParametrerConfiguration.SYSTEM_NAME_TEST);

    assertEquals(true, respBol);
  }

}
