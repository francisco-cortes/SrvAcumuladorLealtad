package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.InSslUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class TokenDao {

    private static final Logger LOGGER = Logger.getLogger(TokenDao.class);

    public String getToken() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection connection = null;

        String token = "";

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", ParametrerConfiguration.CONSUMER_SECRET);
        parameters.put("client_secret", ParametrerConfiguration.CONSUMER_KEY);

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
                .encodeToString((ParametrerConfiguration.CONSUMER_SECRET
                        + ":" + ParametrerConfiguration.CONSUMER_KEY).getBytes());

        URL url = new URL(ParametrerConfiguration.TOKEN_URL);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setHostnameVerifier((hostname, session) -> false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization","Basic " + encoded);
        connection.setRequestProperty("Accept","*/*");
        connection.setRequestProperty("Content-Length",String.valueOf(form.length()));
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);;

        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(form);
        wr.close();

        if(connection.getResponseCode() > 299){

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();

            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line).append('\r');
            }
            errorReader.close();
            connection.disconnect();

            LOGGER.error(connection.getResponseCode() + " Error en Token: " + errorResponse);
        }else {

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

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
