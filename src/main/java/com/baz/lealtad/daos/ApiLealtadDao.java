package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.ConectorHttpsUtil;
import com.baz.lealtad.utils.GetCertUtil;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Map;

public class ApiLealtadDao {

    private static final GetCertUtil certGetter = new GetCertUtil();
    private static final ConectorHttpsUtil con = new ConectorHttpsUtil();

    private static final String OK = "0";
    private static final String BAD = "1";
    private static final int MENSAJE = 0;
    private static final int FOLIO = 1;
    private static final int FLAG = 2;

    public String[] getAcumulaciones(String idAcceso, String token, Map<String, Object> parameters, LogServicio log)
            throws Exception {
        log.setBegTimeMethod("ApiLealtadDao.getAcumulaciones", ParametrerConfiguration.SYSTEM_NAME);

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

        connection = con.crearConexion("POST", ParametrerConfiguration.getApiAcumulacionesUrl(),log);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-idAcceso", idAcceso);
        connection.setRequestProperty("Authorization","Bearer " + token);

        connection.setUseCaches(false);
        connection.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(params);
        wr.close();

        if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){

            bandera = BAD;
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;

            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();

            connection.disconnect();

            int i = errorResponse.indexOf("{");
            String errorString = errorResponse.substring(i);
            log.mensaje("ApiLealtadDao.getAcumulaciones", "ERROR: con el idCliente: " + parameters.get("idCliente") +
              " forma parseada: " + parameters.get("idClienteParseado") + "\n" + errorString +
              "\n ERROR con La peticion: " + params);

            JSONObject jsonResponse = new JSONObject(errorString.trim());
            respuesta[MENSAJE] = jsonResponse.getString("mensaje");
            respuesta[FOLIO] = jsonResponse.getString("folio");
            respuesta[FLAG] = bandera;

        }else {

            bandera = OK;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());

            respuesta[MENSAJE] = jsonResponse.getString("mensaje");
            respuesta[FOLIO] = jsonResponse.getString("folio");
            respuesta[FLAG] = bandera;
            //LOGGER.info(sb);
            //LOGGER.error("el id cliente" + parameters.get("idCliente")
              //+ "La peticion: " + params + "Se envio Correctamente");
        }
        log.setEndTimeMethod("ApiLealtadDao.getAcumulaciones");
        return respuesta;

        }

    }

