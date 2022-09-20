package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
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

    public String getToken(LogServicio log) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        log.setBegTimeMethod("TokenDao.getToken",ParametrerConfiguration.SYSTEM_NAME);
        HttpsURLConnection connection;

        String token = "";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", ParametrerConfiguration.getConsumerSecret());
        parameters.put("client_secret", ParametrerConfiguration.getConsumerKey());

        String form = parameters.keySet().stream()
                .map(key -> {
                    try {

                        return key + "=" + URLEncoder.encode(parameters.get(key), String.valueOf(StandardCharsets.UTF_8));

                    } catch (UnsupportedEncodingException e) {

                        log.exepcion(e,"ERROR de Encoder Token");

                    }

                    return key;

                })
                .collect(Collectors.joining("&"));

        String encoded = Base64.getEncoder()
                .encodeToString((ParametrerConfiguration.getConsumerSecret()
                        + ":" + ParametrerConfiguration.getConsumerKey()).getBytes());

        final String authorization = "Basic " + encoded;

        connection = con.crearConexion("POST", ParametrerConfiguration.getTokenUrl(),log);
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
            log.mensaje("TokenDao.getToken",connection.getResponseCode() + " ERROR en Token: " + sb);
        }
        else {

            JSONObject jsonResponse = new JSONObject(sb);
            token = jsonResponse.getString("access_token");
        }

        connection.disconnect();

        log.setEndTimeMethod("TokenDao.getToken");
        return token;

    }
}
