package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

/**
 * LlavesSimetricasDao.java
 * Descrpcion: Clase para el manejo de conexion y obtencion de llaves asimetricas
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class LlavesAsimetricasDao {
  /*
  objetos para clases y metodos
   */
  private static final ConectorHttpsUtil con = new ConectorHttpsUtil();
  private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();

  /**
   * Metodos getLllavesAsimetricas
   * Descrpcion: Clase  para cargar las configuraciones o propiedades par el funcionamiento del jar
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: toke(String), log(LogServicio)
   **/
  public String[] getLlavesAsimetricas(String token, LogServicio log)
    throws IOException, NoSuchAlgorithmException, KeyManagementException {
    /*
    conector https
     */
    HttpsURLConnection connection;
    /*
    constantes
     */
    final int ID_ACCESO = 0;
    final int ACCESO_PUBLICO = 1;
    final int ACCESO_PRIVADO = 2;
    final int TAMANO_RESPUESTA = 3;
    final String PARAMETRO_JSON = "resultado";
    String[] asimetricas = new String[TAMANO_RESPUESTA];
    /*
    creacion de conexion httos
    con conecotrHTTPSUtil
     */
    connection = con.crearConexion("GET", ParametrerConfiguration.getAsimetricasUrl(),log);
    connection.setRequestProperty("Authorization","Bearer " + token);
    connection.setRequestProperty("Accept","*/*");
    /*
    String Builder para la respuesta
     */
    String sb = responseReader.responseReader(connection);
    /*
    validacion de codigo de respuesta
     */
    if (connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      log.mensaje("LLavesAsimetricasDao ",
        "ERROR en asimetricas: " + sb);
    }
    else {
      /*
      objeto json para parseo de respuesta St6ring
       */
      JSONObject jsonResponse = new JSONObject(sb);
      /*
      obtencion de objetos relevantes del JSON de rerpuesta
       */
      asimetricas[ID_ACCESO] = jsonResponse.getJSONObject(PARAMETRO_JSON).getString("idAcceso");
      asimetricas[ACCESO_PUBLICO] = jsonResponse.getJSONObject(PARAMETRO_JSON).getString("accesoPublico");
      asimetricas[ACCESO_PRIVADO] = jsonResponse.getJSONObject(PARAMETRO_JSON).getString("accesoPrivado");
    }
    /*
    desconectar
     */
    connection.disconnect();
    /*
    retorna arreglo de string, id acceso, acceso publico, acceso privado
     */
    return asimetricas;

  }

}
