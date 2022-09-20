package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class GetCertUtil {

  public TrustManagerFactory getCert (LogServicio log){
    log.setBegTimeMethod("GetCertUtil.getCert", ParametrerConfiguration.SYSTEM_NAME);
    FileInputStream fis = null;
    X509Certificate ca = null;
    KeyStore ks = null;
    TrustManagerFactory tmf = null;

    try{
      fis = new FileInputStream(ParametrerConfiguration.CERT_FILE_PATH);
      ca = (X509Certificate) CertificateFactory.getInstance(
        "X.509").generateCertificate(new BufferedInputStream(fis));

      ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(null, null);
      ks.setCertificateEntry(Integer.toString(1), ca);

      tmf = TrustManagerFactory
        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);

    }
    catch (Exception e){

      log.exepcion(e,"ERROR No se pudo obtener el certificado");

    }
    finally {

      try {

        if (fis != null) {
          fis.close();
        }

      }catch (Exception e){

        log.exepcion(e,"ERROR No se puede cerrar el fileInputStream de cert");

      }

    }
    log.setEndTimeMethod("GetCertUtil.getCert");
    return tmf;

  }

}
