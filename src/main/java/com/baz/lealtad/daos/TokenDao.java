package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;


public class TokenDao {

    private static final Logger LOGGER = Logger.getLogger(TokenDao.class);

    public String getToken() throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {
        HttpsURLConnection connection = null;
        FileInputStream fis = null;
        TrustManagerFactory tmf = null;

        String token = "";

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
            System.exit(ParametrerConfiguration.CANT_LOAD_SOMETHING);
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }


        SSLContext contextSsl = SSLContext.getInstance("TLS");
        contextSsl.init(null, tmf.getTrustManagers(), null);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", ParametrerConfiguration.consumerSecret);
        parameters.put("client_secret", ParametrerConfiguration.consumerKey);

        String form = parameters.keySet().stream()
                .map(key -> {
                    try {
                        return key + "=" + URLEncoder.encode(parameters.get(key), String.valueOf(StandardCharsets.UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("Error de encoder Token: " + e);
                    }
                    return key;
                })
                .collect(Collectors.joining("&"));

        String encoded = Base64.getEncoder()
                .encodeToString((ParametrerConfiguration.consumerSecret
                        + ":" + ParametrerConfiguration.consumerKey).getBytes());

        URL url = new URL(ParametrerConfiguration.tokenUrl);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(contextSsl.getSocketFactory());
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization","Basic " + encoded);
        connection.setRequestProperty("Accept","*/*");
        connection.setRequestProperty("Content-Length",String.valueOf(form.length()));
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(form);
        wr.close();

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();

            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line).append('\r');
            }
            errorReader.close();
            connection.disconnect();

            LOGGER.error(connection.getResponseCode() + " Error en Token: " + errorResponse);
            System.exit(ParametrerConfiguration.CANT_LOAD_SOMETHING);

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
            token = jsonResponse.getString("access_token");
        }
        return token;
    }
}
