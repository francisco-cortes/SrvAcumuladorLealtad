package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
/**
 * GetCertutil
 * Descrpcion: Cierra y termina los procesos resultantes de la consulta a base de datos
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
@SuppressWarnings("squid:S2095")
public class GetCertUtil {

  /**
   * getCert
   * Descrpcion: crea un socket para un constexto http
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: LogServicio
   * returns: TrustManagerFactory
   **/
  public TrustManagerFactory getCert (LogServicio log){
    /*
    inicio de objetos necesarios
     */
    //FileInputStream fis = null;
    X509Certificate ca;
    KeyStore ks;
    TrustManagerFactory tmf = null;

    try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(ParametrerConfiguration.CERT_FILE_PATH))){
      /*
      obtiene archivo .cert
       */

      /*
      lee el archivo y obtiene instancio de certificado
       */
      ca = (X509Certificate) CertificateFactory.getInstance(
        "X.509").generateCertificate(bis);
      /*
      crea un keystore por default
       */
      ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(null, null);
      ks.setCertificateEntry(Integer.toString(1), ca);
      /*
      inicializa el trustManager factori con el keystore generado
       */
      tmf = TrustManagerFactory
        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);

    }
    catch (Exception e){
      log.exepcion(e,"ERROR No se pudo obtener el certificado"+ ParametrerConfiguration.VERSION);
    }

    return tmf;

  }

}
