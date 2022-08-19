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

public class LlavesAsimetricasDao {

    private static final Logger log = Logger.getLogger(TokenDao.class);

    public String[] getLlavesAsimetricas(String token) throws IOException {
        String[] asimetricas = new String[3];
        HttpsURLConnection connection;

        URL url = new URL(ParametrerConfiguration.ASIMETRICAS_URL);
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
            log.error(connection.getResponseCode() + " Error en Asimetricas " + errorResponse);
            asimetricas[0] = "";
            asimetricas[1] = "";
            asimetricas[2] = "";
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
            asimetricas[0] = jsonResponse.getJSONObject("resultado").getString("idAcceso");
            asimetricas[1] = jsonResponse.getJSONObject("resultado").getString("accesoPublico");
            asimetricas[2] = jsonResponse.getJSONObject("resultado").getString("accesoPrivado");
        }
        return asimetricas;
    }
}
