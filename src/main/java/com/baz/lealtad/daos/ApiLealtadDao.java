package com.baz.lealtad.daos;

/**
 * ApiLealtadDao.java
 * Descrpcion: Clase de maejo de conexion al APi Lealtad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ApiLealtadDao {
  /*
  Objetos de clases y metedos
   */
  private static final ConectorHttpsUtil con = new ConectorHttpsUtil();
  /*
  Constantes
   */
  private static final String OK = "0";
  private static final String BAD = "1";
  private static final String SERVICE_NAME = "ApiLealtadDao.getAcumulaciones";
  private static final int MENSAJE = 0;
  private static final int FOLIO = 1;
  private static final int FLAG = 2;
  private static final int NUMERO_RESPUESTAS = 3;

  /**
   * metodo getAcumulaciones
   * Descrpcion: Con los parametros de entrada construye la peticion y conexion al endpoint AcumulacionesLealtad
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * Params: idAcceso(String), token(String), parameters(Map), log(LogServicio)
   * Returns: String Arrays
   **/
  public String[] getAcumulaciones(String idAcceso, String token, Map<String, Object> parameters, LogServicio log)
    throws NoSuchAlgorithmException, IOException, KeyManagementException {
    /*
    arreglo que aloja la respuesta de acumulaciones
     */
    String[] respuesta = new String[NUMERO_RESPUESTAS];
    /*
    la bandera indica si es una operacion negativa o positiva
     */
    String bandera;

    /*
    Json formado con String
     */
    String params = "{" +
      "\"idTipoCliente\":" + parameters.get("idTipoCliente") + "," +
      "\"idCliente\":" + "\"" + parameters.get("idClienteCifrado") + "\"" + "," +
      "\"importe\":" + "\"" + parameters.get("importeCifrado") + "\"" + "," +
      "\"sucursal\":" + parameters.get("sucursal") + "," +
      "\"idOperacion\":" + parameters.get("idOperacion") + "," +
      "\"folioTransaccion\":" + "\"" + parameters.get("folioTransaccion") + "\"" +
      "}";

    /*
    Creacion de objeto conexion
     */
    HttpsURLConnection connection;
    connection = con.crearConexion("POST", ParametrerConfiguration.getApiAcumulacionesUrl(),log);
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestProperty("x-idAcceso", idAcceso);
    connection.setRequestProperty("Authorization","Bearer " + token);
    /*
    sin cache y crea un output
     */
    connection.setUseCaches(false);
    connection.setDoOutput(true);

    /*
    Envio de peticion atraves de outputStream
     */
    DataOutputStream wr = new DataOutputStream(
      connection.getOutputStream());
    wr.writeBytes(params);
    wr.close();

    if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      /*
      manejo de solicitud negativa
       */
      bandera = BAD;
      BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
      StringBuilder errorResponse = new StringBuilder();
      String line;

      while ((line = errorReader.readLine()) != null) {
        errorResponse.append(line);
      }
      errorReader.close();
      /*
      desconectar
       */
      connection.disconnect();
      /*
      manejo de respuesta de error con interacion debido a que el json objet detecta un espacio al inicio de la
      respuesta faja
       */
      int i = errorResponse.indexOf("{");
      String errorString = errorResponse.substring(i);
      /*
      se imprime al loger la peticion que fallo
      asi como el id cliente en su forma parseada y cifrada
       */
      log.mensaje(SERVICE_NAME, "ERROR: con el idCliente: " + parameters.get("idCliente") +
        " forma parseada: " + parameters.get("idClienteParseado") + errorString +
        " ERROR con La peticion: " + params);
      /*
      crecion de paramtro de retorno
       */
      JSONObject jsonResponse = new JSONObject(errorString.trim());
      respuesta[MENSAJE] = jsonResponse.getString("mensaje");
      respuesta[FOLIO] = jsonResponse.getString("folio");
      /*
      bandera igual a 1
       */
      respuesta[FLAG] = bandera;
    }
    else {
      /*
      manejo de respuesta positiva
       */
      bandera = OK;
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      br.close();
      /*
      desconecta
       */
      connection.disconnect();
      /*
      parseo de StringBuilder a string
      JSON solo accepta Strings
       */
      JSONObject jsonResponse = new JSONObject(sb.toString());
      /*
      creacion de objeto respuesta
       */
      respuesta[MENSAJE] = jsonResponse.getString("mensaje");
      respuesta[FOLIO] = jsonResponse.getString("folio");
      /*
      bandera igual a 0
       */
      respuesta[FLAG] = bandera;
    }
    /*
    retorna arreglo con mensaje, folio y bandera
     */
    return respuesta;

  }

}

