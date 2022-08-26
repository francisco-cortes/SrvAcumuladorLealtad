package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ConectorHttpsUtil {

  private static final GetCertUtil certGetter = new GetCertUtil();

  private static final Logger LOGGER = Logger.getLogger(ConectorHttpsUtil.class);

  public HttpsURLConnection crearConexion (String metodo, String link)
    throws NoSuchAlgorithmException, KeyManagementException, IOException {

    HttpsURLConnection connection = null;
    TrustManagerFactory tmf = null;

    try {
      tmf = certGetter.getCert();
    }catch (Exception e){
      LOGGER.error("no se pudo obtener el certificado: " + e);
    }

    SSLContext contextSsl = SSLContext.getInstance("TLSv1.2");
    assert tmf != null;
    contextSsl.init(null, tmf.getTrustManagers(), null);

    URL url = new URL(link);
    connection = (HttpsURLConnection) url.openConnection();

    connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
    connection.setSSLSocketFactory(contextSsl.getSocketFactory());
    connection.setRequestMethod(metodo);

    return connection;
  }

}
