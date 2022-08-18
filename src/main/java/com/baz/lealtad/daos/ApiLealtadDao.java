package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiLealtadDao {

    private static final Logger logger = Logger.getLogger(ApiLealtadDao.class);

    public String[] getAcumulaciones(String idAcceso, String token, int idTipoCliente,
                                     String idCliente, String importe, int sucursal,
                                     int idOperacion, String folioTransaccion) throws IOException, InterruptedException {
        String[] respuesta = new String[3];
        String params = "{" +
                "\"idTipoCliente\":" + idTipoCliente + "," +
                "\"idCliente\":" + "\"" + idCliente + "\"" + "," +
                "\"importe\":" + "\"" + importe + "\"" + "," +
                "\"sucursal\":" + sucursal + "," +
                "\"idOperacion\":" + idOperacion + "," +
                "\"folioTransaccion\":" + "\"" + folioTransaccion + "\"" +
                "}";
        HttpURLConnection connection = null;
        URL url = new URL(ParametrerConfiguration.API_ACUMULACIONES_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("x-idAcceso", idAcceso);
        connection.setRequestProperty("Authorization","Bearer " + token);

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(params);
        wr.close();

        logger.info(connection.getResponseCode() + "\n" + connection.getResponseMessage());

        if(connection.getResponseCode() > 299){
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line).append('\r');
            }
            errorReader.close();
            connection.disconnect();
            logger.error(errorResponse);
            JSONObject jsonResponse = new JSONObject(errorReader.toString());
            respuesta[0] = jsonResponse.getString("mensaje");
            respuesta[1] = jsonResponse.getString("folio");
            respuesta[2] = "1";
        }else {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            connection.disconnect();
            JSONObject jsonResponse = new JSONObject(sb.toString());
            logger.info(jsonResponse);
            respuesta[0] = jsonResponse.getString("mensaje");
            respuesta[1] = jsonResponse.getString("folio");
            respuesta[2] = "0";
        }

        return respuesta;

        /*HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(35))
                .sslContext(insecureContext())
                .build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ParametrerConfiguration.API_ACUMULACIONES_URL))
                .headers("x-idAcceso", idAcceso,
                        "Authorization","Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(params)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() >= 200 && response.statusCode() < 300){
            JSONObject puntosResponse = new JSONObject(response.body());

            respuesta[0] = puntosResponse.getString("mensaje");
            respuesta[1] = puntosResponse.getString("folio");
            respuesta[2] = "0";

            return respuesta;
        }else {
            JSONObject puntosResponse = new JSONObject(response.body());
            JSONArray arreglodetalles = puntosResponse.getJSONArray("detalles");
            StringBuilder detalles = new StringBuilder();
            for(int i = 0; i < arreglodetalles.length(); i++){
                detalles.append(arreglodetalles.getString(i)).append("\n");
            }
            logger.error("Error al consumir api lealtad puntos. Codigo: " + response.statusCode() +
                    "\n Cuerpo de respuesta: " +
                    "\n Codigo: " +puntosResponse.getString("codigo") +
                    "\n Mensaje: " +puntosResponse.getString("mensaje") +
                    "\n Folio: " +puntosResponse.getString("folio") +
                    "\n Info: " +puntosResponse.getString("info") +
                    "\n Detalles: " + detalles);


            respuesta[0] = puntosResponse.getString("mensaje");
            respuesta[1] = puntosResponse.getString("folio");
            respuesta[2] = "1";*/

        }
    }

