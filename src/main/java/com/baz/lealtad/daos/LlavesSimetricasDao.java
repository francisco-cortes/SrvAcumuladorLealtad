package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * LlavesSimetricasDao.java
 * Descrpcion: Clase para obtener las llaves simetricas del api de seguridad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class LlavesSimetricasDao {
  /*
  objetos para clases y metodos
   */
  private static final ConectorHttpsUtil con = new ConectorHttpsUtil();
  private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();
  /*
  cosntantes globales
   */
  private static final String SERVICE_NAME = "LlavesSimetricasDao.getLlavesSimetricas";
  /**
   * Metodo get llavesSimetricas
   * Descrpcion: obtiene las llaves simtricas a travez de fabrica dao util
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: token(String), idAcceso(String), log(LogServicio)
   * returns: String Arrays
   **/
  public String[] getLlavesSimetricas(String token, String idAcceso, LogServicio log)
    throws IOException, NoSuchAlgorithmException, KeyManagementException {

    /*
    inicializacion de conexion
     */
    HttpsURLConnection connection;
    /*
    constantes
     */
    final int ACCESO_SIMETRICO = 0;
    final int CODIGO_HASH = 1;
    final int TAMANO_RESPUESTA = 2;
    final String PARAMETRO_JSON = "resultado";
    String[] simetricas = new String[TAMANO_RESPUESTA];
    /*
    obtencion de conexion atravezz de conectyor HTTP util
     */
    connection = con.crearConexion("GET", ParametrerConfiguration.getSimetricasUrl() + idAcceso,log);
    connection.setRequestProperty("Authorization","Bearer " + token);
    connection.setRequestProperty("Accept","*/*");
    /*
    obtencion de respuesta en String
     */
    String sb = responseReader.responseReader(connection);
    /*
    validacion de codigo de respuesta
     */
    if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      /*
      respuesta negativa al log
       */
      log.mensaje(SERVICE_NAME,
        connection.getResponseCode() + "ERROR en Simetricas " + sb);
    }
    else {
      /*
      Creacion de json apartir de string obtenida
       */
      JSONObject jsonResponse = new JSONObject(sb);
      /*
      obtencion de parametros relevantes del json
       */
      simetricas[ACCESO_SIMETRICO] = jsonResponse.getJSONObject(PARAMETRO_JSON).getString("accesoSimetrico");
      simetricas[CODIGO_HASH] = jsonResponse.getJSONObject(PARAMETRO_JSON).getString("codigoAutentificacionHash");

    }
    /*
    desconectar
     */
    connection.disconnect();
    /*
    retorna accesos simetrica, codigo hash
     */
    return simetricas;
  }

}
