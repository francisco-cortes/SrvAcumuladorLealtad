package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class LlavesAsimetricasDao {

    private static final Logger LOGGER = Logger.getLogger(LlavesAsimetricasDao.class);

    public String[] getLlavesAsimetricas(String token) throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {
        HttpsURLConnection connection = null;

        final int idAcesso = 0, accesoPublic = 1, accesoPrivado = 2;
        final String jsonParametro = "resultado";
        String[] asimetricas = new String[3];

        FileInputStream fis = new FileInputStream(ParametrerConfiguration.CERT_FILE_PATH);
        X509Certificate ca = (X509Certificate) CertificateFactory.getInstance(
                "X.509").generateCertificate(new BufferedInputStream(fis));

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry(Integer.toString(1), ca);

        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext contextSsl = SSLContext.getInstance("TLS");
        contextSsl.init(null, tmf.getTrustManagers(), null);

        URL url = new URL(ParametrerConfiguration.asimetricasUrl);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);

        connection.setSSLSocketFactory(contextSsl.getSocketFactory());

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization","Bearer " + token);
        connection.setRequestProperty("Accept","*/*");

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();

            String line;
            while ((line = errorReader.readLine()) != null) {

                errorResponse.append(line).append('\r');

            }
            errorReader.close();
            connection.disconnect();

            LOGGER.error(connection.getResponseCode() + " Error en Asimetricas " + errorResponse);
        }
        else {

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());

            asimetricas[idAcesso] = jsonResponse.getJSONObject(jsonParametro).getString("idAcceso");
            asimetricas[accesoPublic] = jsonResponse.getJSONObject(jsonParametro).getString("accesoPublico");
            asimetricas[accesoPrivado] = jsonResponse.getJSONObject(jsonParametro).getString("accesoPrivado");

        }
        return asimetricas;
    }

}
