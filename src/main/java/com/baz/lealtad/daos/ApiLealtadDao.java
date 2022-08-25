package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

public class ApiLealtadDao {

    private static final Logger LOGGER = Logger.getLogger(ApiLealtadDao.class);

    private static final String OK = "0";
    private static final String BAD = "1";
    private static final int MENSAJE = 0;
    private static final int FOLIO = 1;
    private static final int FLAG = 2;

    public String[] getAcumulaciones(String idAcceso, String token, Map<String, Object> parameters)
            throws Exception {

        FileInputStream fis = null;

        TrustManagerFactory tmf = null;

        String[] respuesta = new String[3];
        String bandera;

        try {

            fis = new FileInputStream(ParametrerConfiguration.CERT_FILE_PATH);
            X509Certificate ca = (X509Certificate) CertificateFactory.getInstance(
                    "X.509").generateCertificate(new BufferedInputStream(fis));

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            ks.setCertificateEntry(Integer.toString(1), ca);

            tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
        }
        catch (Exception e){

            LOGGER.error("Error al cargar el archivo de certificado" + e);

        }
        finally {

            if (fis != null) {
                fis.close();
            }

        }

        SSLContext contextSsl = SSLContext.getInstance("TLSv1.2");
        contextSsl.init(null, tmf.getTrustManagers(), null);

        String params = "{" +
                "\"idTipoCliente\":" + parameters.get("idTipoCliente") + "," +
                "\"idCliente\":" + "\"" + parameters.get("idClienteCifrado") + "\"" + "," +
                "\"importe\":" + "\"" + parameters.get("importeCifrado") + "\"" + "," +
                "\"sucursal\":" + parameters.get("sucursal") + "," +
                "\"idOperacion\":" + parameters.get("idOperacion") + "," +
                "\"folioTransaccion\":" + "\"" + parameters.get("folioTransaccion") + "\"" +
                "}";

        HttpsURLConnection connection;
        URL url = new URL(ParametrerConfiguration.apiAcumulacionesUrl);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(contextSsl.getSocketFactory());

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-idAcceso", idAcceso);
        connection.setRequestProperty("Authorization","Bearer " + token);

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(params);
        wr.close();

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){

            bandera = BAD;
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;

            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();

            connection.disconnect();

            int i = errorResponse.indexOf("{");
            String errorString = errorResponse.substring(i);
            LOGGER.error(errorString);

            JSONObject jsonResponse = new JSONObject(errorString.trim());
            respuesta[MENSAJE] = jsonResponse.getString("mensaje");
            respuesta[FOLIO] = jsonResponse.getString("folio");
            respuesta[FLAG] = bandera;

        }else {

            bandera = OK;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());

            respuesta[MENSAJE] = jsonResponse.getString("mensaje");
            respuesta[FOLIO] = jsonResponse.getString("folio");
            respuesta[FLAG] = bandera;
        }

        return respuesta;

        }

    }

