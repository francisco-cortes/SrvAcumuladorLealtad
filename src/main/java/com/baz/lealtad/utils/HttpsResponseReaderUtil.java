package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * HttpSResponseReaderUtil
 * Descrpcion: obtiene las respuestas de las peticiones http
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class HttpsResponseReaderUtil {
  /**
   * responseReader
   * Descrpcion: obtiene la salida json de una api y la convierte en string para su manejo en JSONObject
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: HttpsURLConnection
   * returns: String
   **/
  public String responseReader(HttpsURLConnection connection) throws IOException {
    /*
    inicio del buffer
     */
    BufferedReader br;
    /*
    si el codigo de conexion es mayor a 299, obtiene el objeto errorStream para respuesta contraldos
     */
    if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    }
    /*
    si no obtiene el objeto inputStream para respuesta positivas
     */
    else {
      br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
    /*
    uso de string builder para manejo del buferedRead
     */
    StringBuilder sb = new StringBuilder();

    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    /*
    cierra el buferedReader
     */
    br.close();

    return sb.toString();
  }

}
