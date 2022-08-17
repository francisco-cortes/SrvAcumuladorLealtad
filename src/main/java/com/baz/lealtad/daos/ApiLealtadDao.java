package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;
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

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        respuesta[1] = response.toString();
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

