package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
/**
 * conectorHttpsUtil
 * Descrpcion: crea contexto para conexiones https
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class ConectorHttpsUtil {
  /*
  objetos
   */
  private static final GetCertUtil certGetter = new GetCertUtil();
  /**
   * crearConexion
   * Descrpcion: crea un objeto Https con el contexto de conexion para utlizar en los DAOS
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: metodo(String), link(String), log(LogServicio)
   * returns: HttpsUrlConnection
   **/
  public HttpsURLConnection crearConexion (String metodo, String link, LogServicio log)
    throws NoSuchAlgorithmException, KeyManagementException, IOException {
    /*
    inicia objetos de conexion
     */
    HttpsURLConnection connection = null;
    TrustManagerFactory tmf = certGetter.getCert(log);
    /*
    asegura una instancia TLSv1.2
     */
    SSLContext contextSsl = SSLContext.getInstance("TLSv1.2");
    /*
    convierte la url string en un objeto URL
     */
    URL url = new URL(link);
    /*
    abre la conexion con el conexto y la URL
     */
    connection = (HttpsURLConnection) url.openConnection();
    /*
    determina el tiempo maximo de espera en 32 segundos
     */
    connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
    /*
    settea el metodo de peticion
     */
    connection.setRequestMethod(metodo);
      /*
      si no encuentra el certificado o no lo puede cargar
      se lo salta en un insecureContext
       */
    contextSsl.init(null, tmf.getTrustManagers(), null);
    connection.setSSLSocketFactory(contextSsl.getSocketFactory());

    return connection;
  }

}
