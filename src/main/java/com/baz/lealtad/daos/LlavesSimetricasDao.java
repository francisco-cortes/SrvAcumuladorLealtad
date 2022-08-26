package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.HttpsResponseReaderUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class LlavesSimetricasDao {

    private static final ConectorHttpsUtil con = new ConectorHttpsUtil();

    private static final HttpsResponseReaderUtil responseReader = new HttpsResponseReaderUtil();

    private static final Logger LOGGER = Logger.getLogger(LlavesSimetricasDao.class);

    public String[] getLlavesSimetricas(String token, String idAcceso)
      throws IOException, NoSuchAlgorithmException, KeyManagementException {

        HttpsURLConnection connection;

        final int accesoSimetrico = 0, codigoHash = 1;
        final String jsonName = "resultado";
        String[] simetricas = new String[2];

        connection = con.crearConexion("GET", ParametrerConfiguration.simetricasUrl + idAcceso);
        connection.setRequestProperty("Authorization","Bearer " + token);
        connection.setRequestProperty("Accept","*/*");

        String sb = responseReader.responseReader(connection);

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){

            LOGGER.error(connection.getResponseCode() + " Error en Asimetricas " + sb);

        }
        else {

            JSONObject jsonResponse = new JSONObject(sb);
            simetricas[accesoSimetrico] = jsonResponse.getJSONObject(jsonName).getString("accesoSimetrico");
            simetricas[codigoHash] = jsonResponse.getJSONObject(jsonName).getString("codigoAutentificacionHash");
            LOGGER.info("Simetrico: " + simetricas[accesoSimetrico]);

        }

        connection.disconnect();

        return simetricas;
    }

}
