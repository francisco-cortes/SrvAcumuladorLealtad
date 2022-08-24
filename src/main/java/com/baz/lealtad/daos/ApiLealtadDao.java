package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class ApiLealtadDao {

    private static final Logger LOGGER = Logger.getLogger(ApiLealtadDao.class);

    private static final String ok = "0", bad = "1";
    private static final int mensaje = 0, folio = 1, flag = 2;

    public String[] getAcumulaciones(String idAcceso, String token, Map<String, Object> parameters)
            throws Exception {


        String[] respuesta = new String[3];
        String bandera;

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new ApiLealtadDao.DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

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

        connection.setConnectTimeout(ParametrerConfiguration.TIME_OUT_MILLISECONDS);
        connection.setHostnameVerifier((hostname, session) -> false);
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

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
            bandera = bad;
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
            respuesta[mensaje] = jsonResponse.getString("mensaje");
            respuesta[folio] = jsonResponse.getString("folio");
            respuesta[flag] = bandera;

        }else {
            bandera = ok;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());

            respuesta[mensaje] = jsonResponse.getString("mensaje");
            respuesta[folio] = jsonResponse.getString("folio");
            respuesta[flag] = bandera;
        }

        return respuesta;

        }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    }

