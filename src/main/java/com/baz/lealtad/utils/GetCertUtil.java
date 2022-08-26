package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class GetCertUtil {

  private static final Logger LOGGER = Logger.getLogger(GetCertUtil.class);

  public TrustManagerFactory getCert (){

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

      LOGGER.error("No se pudo obtener el certificado: " + e);

    }
    finally {

      try {

        if (fis != null) {
          fis.close();
        }

      }catch (Exception e){

        LOGGER.error("No se pudo cerrar el fileInputStream de cert: " + e);

      }

    }

    return tmf;

  }

}
