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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TokenDao {

    private static final Logger LOGGER = Logger.getLogger(TokenDao.class);

    public String getToken() throws IOException {
        HttpsURLConnection connection = null;

        String token = "";

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

        LOGGER.info("Consumer : "  + ParametrerConfiguration.CONSUMER_SECRET
                + ":" + ParametrerConfiguration.CONSUMER_KEY);

        URL url = new URL(ParametrerConfiguration.TOKEN_URL);
        LOGGER.info("token url " + url);
        connection = (HttpsURLConnection) url.openConnection();
        LOGGER.info("con " + connection);

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(Objects.requireNonNull(InSslUtil.insecureContext()).getSocketFactory());
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization","Basic " + encoded);
        connection.setRequestProperty("Accept","*/*");
        connection.setRequestProperty("Content-Length",String.valueOf(form.length()));

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        LOGGER.info("conexxion:" + connection.getURL());
        LOGGER.info("conexxion:" + connection);

        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(form);
        LOGGER.info("dataOut:" + wr);
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
}
