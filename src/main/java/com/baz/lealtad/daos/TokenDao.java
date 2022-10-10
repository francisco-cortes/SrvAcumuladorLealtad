package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TokenDao
 * Descrpcion: Clase para obtener el Token de api seguridad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class TokenDao {
  /*
  Objetos
   */
  private static final ConectorHttpsUtil con = new ConectorHttpsUtil();
  private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();
  /*
  Constantes Globales
   */
  private static final String SERVICE_NAME = "TokenDao.getToken";
  /**
   * Metodo getToken
   * Descrpcion: meneja la conexion para obtener el token
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: log(LogServicio)
   **/
  public String getToken(LogServicio log)
    throws IOException, NoSuchAlgorithmException, KeyManagementException {

    log.setBegTimeMethod(SERVICE_NAME,ParametrerConfiguration.SYSTEM_NAME);
    /*
    mapa de strings para parametros de autenticacion tipo json
     */
    Map<String, String> parameters = new HashMap<>();
    parameters.put("grant_type", "client_credentials");
    parameters.put("client_id", ParametrerConfiguration.getConsumerSecret());
    parameters.put("client_secret", ParametrerConfiguration.getConsumerKey());
    /*
    codificacion para el mapa de strings parameters
     */
    String form = parameters.keySet().stream()
      .map(key -> {
        try {

          return key + "=" + URLEncoder.encode(parameters.get(key),
            String.valueOf(StandardCharsets.UTF_8));

        }
        catch (UnsupportedEncodingException e) {

          log.exepcion(e,"ERROR de Encoder Token");

        }

        return key;

      })
      .collect(Collectors.joining("&"));
    /*
    Encoder base 64 para el header basic
     */
    String encoded = Base64.getEncoder()
      .encodeToString((ParametrerConfiguration.getConsumerSecret()
        + ":" + ParametrerConfiguration.getConsumerKey()).getBytes());
    /*
    headder auth basic
     */
    final String AUTH = "Basic " + encoded;
    /*
    creacion de conexxion
     */
    HttpsURLConnection connection;
    connection = con.crearConexion("POST", ParametrerConfiguration.getTokenUrl(),log);
    /*
    manejo de headers
     */
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setRequestProperty("Authorization",AUTH);
    connection.setRequestProperty("Accept","*/*");
    connection.setRequestProperty("Content-Length",String.valueOf(form.length()));
    // sin cache
    connection.setUseCaches(false);
    // obtiene input
    connection.setDoInput(true);
    // genera output
    connection.setDoOutput(true);
    /*
    envia la peticion en un data stream
     */
    DataOutputStream wr = new DataOutputStream(
      connection.getOutputStream());
    wr.writeBytes(form);
    wr.close();
    /*
    obtiene la respuesta en un String
     */
    String sb = responseReader.responseReader(connection);
    /*
    valida el codigo de respuesta de la peticion
     */
    String token = "";
    if (connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      log.mensaje(SERVICE_NAME,connection.getResponseCode()
        + " ERROR en Token: " + sb);
    }
    else {
      /*
      crea un objeto json con la salida
       */
      JSONObject jsonResponse = new JSONObject(sb);
      /*
      obtiene los objetos nesesarios en el jar
       */
      token = jsonResponse.getString("access_token");
    }
    /*
    desconectar
     */
    connection.disconnect();

    log.setEndTimeMethod(SERVICE_NAME);
    /*
    retorna token
     */
    return token;

  }
}
