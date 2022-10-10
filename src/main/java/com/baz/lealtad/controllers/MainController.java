package com.baz.lealtad.controllers;

/**
 * MainController.java
 * Descrpcion: Clase donde se aloja la seccion principal del codigo
 *
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.SpEntradaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ConsultarApiLealtadService;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ClienteUnicoParserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MainController {

  /*
  Objetos estaticos para clases y metodos
   */
  private static final ParametrerConfiguration configs = new ParametrerConfiguration();
  private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
  private static final SpEntradaService spEntrada = new SpEntradaService();
  private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
  private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
  private static final ConsultarApiLealtadService apiService = new ConsultarApiLealtadService();
  /*
  Cosntates
   */
  private static final int CLIENTE_DEX = 5;
  private static final int CLIENTE_UNICO = 3;
  private static final int ID_OPERACION = 3;
  /*
  Constantes para Indices
   */
  private static final int TOKEN = 0;
  private static final int IDACCESO = 1;
  private static final int SIMETRICA_1 = 2;
  private static final int SIMETRICA_2 = 3;
  private static final int MENSAJE = 0;
  private static final int FOLIO = 1;
  private static final int BANDERA = 2;
  /*
  Constantes para regex
   */
  private static final Pattern SEVEN_TEN_IS_DEX = Pattern.compile("(\\d{7,10})");

  /**
   * Metodo main
   * Descrpcion: punto inicila para la ejecucion del jar aqui se invoca los services necesarios
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * Params: args(String array)
   **/
  public static void main(String[] args){
    /*
    confing para el path de mmuuser home
     */
    final String MMUSER_HOME = System.getenv("MMUSER_HOME");
    final String SERVICE_NAME = "MainController.main";
    System.setProperty("MMUSER_HOME", MMUSER_HOME);
    /*
    creacion de log
     */
    LogServicio log = new LogServicio();
    /*
    carga de properties
     */
    configs.loadConfiguration();
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);

    /*
    Obtiene todas las llaves de api seguridad baz
     */
    String[] llavesAes = obtenerLlaves.getLlaves(log);
    /*
    obtiene un payload de la forma CursosSP
     */
    List<CursorSpSalidaModel> responseDb = salidaService.consulta(log);
    String[] respuestaApi;

    //---------------------------
    /*
    No se realiza ningina accion si el payload respose DB llega vacio
     */
    if (responseDb.size() > 0) {

      int fallidosLealtad = 0;
      int idTipoCliente;
      String idClienteParseado;

      for(int i = 0; i < responseDb.size(); i ++){
        /*
        id tipo cliente a trves de metodo
         */
        idTipoCliente = idTipoClienteSetter(responseDb.get(i).getFCNEGOCIO(),responseDb.get(i).getFCIDCLIENTE());

        /*
        parseo de id cliente a la forma xxxx-xxxx-xxxx-xxx, o numerica dependindo de su tipo cliente
         */
        idClienteParseado = ClienteUnicoParserUtil.parsear(responseDb.get(i).getFCIDCLIENTE().trim(), log);
        /*
        cifrado de datos sencibles id cliente e impiorte
         */
        String idCliente = cifrarService.cifrar(idClienteParseado,
          llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);
        /*
        redondeo de montos, api lealtad no acepta datos fraccionados
         */
        String importe = cifrarService.cifrar(importeRedondeador(responseDb.get(i).getFNIMPORTE()),
          llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);

        /*
        construccion de mapa de objetos para mejor manejos en los siguentes metods
         */

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idTipoCliente", idTipoCliente);
        parameters.put("idCliente", responseDb.get(i).getFCIDCLIENTE().trim());
        parameters.put("idClienteCifrado", idCliente);
        parameters.put("idClienteParseado", idClienteParseado);
        parameters.put("importe", responseDb.get(i).getFNIMPORTE());
        parameters.put("importeCifrado", importe);
        parameters.put("sucursal", responseDb.get(i).getFNSUCURSAL());
        parameters.put("idOperacion", ID_OPERACION);
        parameters.put("folioTransaccion", responseDb.get(i).getFCFOLIOTRANSACCION());
        parameters.put("fechaOperacion", responseDb.get(i).getFDFECHAOPERACION());
        parameters.put("negocio", responseDb.get(i).getFCNEGOCIO());
        parameters.put("tipoOperacion", responseDb.get(i).getFCTIPOOPERACION());
        parameters.put("origenTransaccion", responseDb.get(i).getFIORIGENTRANSACCION());
        parameters.put("paisId", responseDb.get(i).getFIPAISID());

        /*
        llamada al api de lealtad
         */
        respuestaApi = apiService.consultaApi(llavesAes[IDACCESO], llavesAes[TOKEN],
          parameters, log);
        /*
        guardado de datos con el sp2
         */
        spEntrada.guardarBase(parameters,respuestaApi[FOLIO]
          ,respuestaApi[MENSAJE], respuestaApi[BANDERA], log);
        /*
        contador de respuestas negativas de api lealtad
         */
        if("1".equals(respuestaApi[BANDERA])){
          fallidosLealtad ++;
        }

      }
      /*
      if contador de respuestas negativas del api lealtada
       */
      if (fallidosLealtad > 0){
        log.mensaje(SERVICE_NAME,
          "ERROR: Hubo " + fallidosLealtad + " respuestas negativas de API Lealtad");
        /*
        termina la ejecucion con status 2
         */
        System.exit(ParametrerConfiguration.ERROR_OR_EXCEPTION);
      }
      else {
        /*
        termina la ejcucion con estatus cero
         */
        System.exit(0);
      }
    }
    else {
      /*
      termina la ejecucion con estus 1
       */
      log.mensaje(SERVICE_NAME,
        "ERROR: Respuesta vacia del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD \n"+
          "No se realiza ninguna Accion");
      System.exit(ParametrerConfiguration.CANT_LOAD_SOMETHING);
    }
    log.setEndTimeMethod(SERVICE_NAME);
  }

  /**
   * Metodo idTipoClienteSetter
   * Descrpcion: identifica el valor del Id Tipo cliente (3 o 5 ) dependiendo de los parametros negocio e idCliente
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: negocio(String), IdCliente(String)
   * returns: int
   **/

  public static int idTipoClienteSetter(String negocio, String idCliente){
    if("dex".equalsIgnoreCase(negocio)
      && SEVEN_TEN_IS_DEX.matcher(idCliente).matches()){
      return CLIENTE_DEX;
    }
    else{
      return CLIENTE_UNICO;
    }
  }

  public static String importeRedondeador(Double importe){
    int importeRedondeado =  (int) Math.round(importe);
    return String.valueOf(importeRedondeado);
  }


}
