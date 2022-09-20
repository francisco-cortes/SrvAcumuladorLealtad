package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class LlavesSimetricasDao {

    private static final ConectorHttpsUtil con = new ConectorHttpsUtil();

    private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();


    public String[] getLlavesSimetricas(String token, String idAcceso, LogServicio log)
      throws IOException, NoSuchAlgorithmException, KeyManagementException {
        log.setBegTimeMethod("LlavesSimetricasDao.getLlavesSimetricas", ParametrerConfiguration.SYSTEM_NAME);
        HttpsURLConnection connection;

        final int accesoSimetrico = 0, codigoHash = 1;
        final String jsonName = "resultado";
        String[] simetricas = new String[2];

        connection = con.crearConexion("GET", ParametrerConfiguration.getSimetricasUrl() + idAcceso,log);
        connection.setRequestProperty("Authorization","Bearer " + token);
        connection.setRequestProperty("Accept","*/*");

        String sb = responseReader.responseReader(connection);

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
            log.mensaje("LlavesSimetricasDao.getLlavesSimetricas",
              connection.getResponseCode() + "ERROR en Simetricas " + sb);

        }
        else {

            JSONObject jsonResponse = new JSONObject(sb);
            simetricas[accesoSimetrico] = jsonResponse.getJSONObject(jsonName).getString("accesoSimetrico");
            simetricas[codigoHash] = jsonResponse.getJSONObject(jsonName).getString("codigoAutentificacionHash");

        }

        connection.disconnect();
        log.setEndTimeMethod("LlavesSimetricasDao.getLlavesSimetricas");
        return simetricas;
    }

}
