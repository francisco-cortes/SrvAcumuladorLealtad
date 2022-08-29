package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;


public class LlavesAsimetricasDao {


    private static final ConectorHttpsUtil con = new ConectorHttpsUtil();
    private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();

    private static final Logger LOGGER = Logger.getLogger(LlavesAsimetricasDao.class);

    public String[] getLlavesAsimetricas(String token) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        HttpsURLConnection connection;

        final int idAcesso = 0;
        final int accesoPublic = 1;
        final int accesoPrivado = 2;
        final String jsonParametro = "resultado";
        String[] asimetricas = new String[3];

        connection = con.crearConexion("GET", ParametrerConfiguration.getAsimetricasUrl());
        connection.setRequestProperty("Authorization","Bearer " + token);
        connection.setRequestProperty("Accept","*/*");

        String sb = responseReader.responseReader(connection);

        if (connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
            LOGGER.error("Error en asimetricas: " + sb);
        }
        else {
            JSONObject jsonResponse = new JSONObject(sb);

            asimetricas[idAcesso] = jsonResponse.getJSONObject(jsonParametro).getString("idAcceso");
            asimetricas[accesoPublic] = jsonResponse.getJSONObject(jsonParametro).getString("accesoPublico");
            asimetricas[accesoPrivado] = jsonResponse.getJSONObject(jsonParametro).getString("accesoPrivado");
        }

        connection.disconnect();

        return asimetricas;

    }

}
