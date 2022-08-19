package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.InSslUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class ApiLealtadDao {

    private static final Logger LOGGER = Logger.getLogger(ApiLealtadDao.class);

    public String[] getAcumulaciones(String idAcceso, String token, Map<String, Object> parameters)
            throws IOException, InterruptedException {


        String[] respuesta = new String[3];
        String bandera;

        String params = "{" +
                "\"idTipoCliente\":" + parameters.get("idTipoCliente") + "," +
                "\"idCliente\":" + "\"" + parameters.get("idClienteCifrado") + "\"" + "," +
                "\"importe\":" + "\"" + parameters.get("importeCifrado") + "\"" + "," +
                "\"sucursal\":" + parameters.get("sucursal") + "," +
                "\"idOperacion\":" + parameters.get("idOperacion") + "," +
                "\"folioTransaccion\":" + "\"" + parameters.get("folioTransaccion") + "\"" +
                "}";

        HttpsURLConnection connection;
        URL url = new URL(ParametrerConfiguration.API_ACUMULACIONES_URL);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.Time_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(Objects.requireNonNull(InSslUtil.insecureContext()).getSocketFactory());
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

        LOGGER.info(connection.getResponseCode() + "\n" + connection.getResponseMessage());

        if(connection.getResponseCode() > 299){
            bandera = "1";
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();
            connection.disconnect();
            LOGGER.error(errorResponse);
            JSONObject jsonResponse = new JSONObject(errorReader.toString());
            respuesta[0] = jsonResponse.getString("mensaje");
            respuesta[1] = jsonResponse.getString("folio");
            respuesta[2] = bandera;
        }else {
            bandera = "0";
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            connection.disconnect();
            JSONObject jsonResponse = new JSONObject(sb.toString());
            LOGGER.info(jsonResponse);
            respuesta[0] = jsonResponse.getString("mensaje");
            respuesta[1] = jsonResponse.getString("folio");
            respuesta[2] = bandera;
        }

        return respuesta;

        }
    }

