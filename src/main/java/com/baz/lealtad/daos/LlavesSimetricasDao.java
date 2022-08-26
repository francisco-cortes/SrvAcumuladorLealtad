package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.GetCertUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class LlavesSimetricasDao {

    private static final GetCertUtil certGetter = new GetCertUtil();

    private static final Logger LOGGER = Logger.getLogger(LlavesSimetricasDao.class);

    public String[] getLlavesSimetricas(String token, String idAcceso) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        HttpsURLConnection connection = null;
        TrustManagerFactory tmf = null;

        final int accesoSimetrico = 0, codigoHash = 1;
        final String jsonName = "resultado";
        String[] simetricas = new String[2];

        try {
            tmf = certGetter.getCert();
        }
        catch (Exception e){
            LOGGER.error("No se pudo obtener el certificado: " + e);
        }

        SSLContext contextSsl = SSLContext.getInstance("TLSv1.2");
        assert tmf != null;
        contextSsl.init(null, tmf.getTrustManagers(), null);

        URL url = new URL(ParametrerConfiguration.simetricasUrl + idAcceso);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setSSLSocketFactory(contextSsl.getSocketFactory());

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
