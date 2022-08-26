package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
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
    private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();

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

        String sb = responseReader.responseReader(connection);

        if (connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
            LOGGER.error(connection.getResponseCode() + " Error en Token: " + sb);
        }
        else {

            JSONObject jsonResponse = new JSONObject(sb);
            token = jsonResponse.getString("access_token");
            LOGGER.info("Token: " + token);
        }

        connection.disconnect();

        return token;

    }
}
