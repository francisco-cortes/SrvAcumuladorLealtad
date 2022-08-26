package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.GetCertUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class TokenDao {


    private static final ConectorHttpsUtil con = new ConectorHttpsUtil();

    private static final Logger LOGGER = Logger.getLogger(TokenDao.class);

    public String getToken() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        HttpsURLConnection connection;

        String token = "";

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

        final String authorization = "Basic " + encoded;

        connection = con.crearConexion("POST", ParametrerConfiguration.tokenUrl);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization",authorization);
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
            LOGGER.info("Token: " + token);
        }

        return token;

    }
}
