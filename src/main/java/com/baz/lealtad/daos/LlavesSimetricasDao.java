package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.InSslUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class LlavesSimetricasDao {

    private static final Logger LOGGER = Logger.getLogger(LlavesSimetricasDao.class);

    public String[] getLlavesSimetricas(String token, String idAcceso) throws IOException {

        final int accesoSimetrico = 0, codigoHash = 1;
        final String jsonName = "Resultado";
        String[] simetricas = new String[2];


        HttpsURLConnection connection;

        URL url = new URL(ParametrerConfiguration.SIMETRICAS_URL + idAcceso);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(Objects.requireNonNull(InSslUtil.insecureContext()).getSocketFactory());
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
            simetricas[accesoSimetrico] = jsonResponse.getJSONObject(jsonName).getString("accesoSimetrico");
            simetricas[codigoHash] = jsonResponse.getJSONObject(jsonName).getString("codigoAutentificacionHash");
        }

        return simetricas;
    }
}
