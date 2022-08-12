package com.baz.lealtad.daos;

import com.baz.lealtad.utils.ConstantesUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;

public class ApiLealtadDao {

    private static final Logger logger = Logger.getLogger(ApiLealtadDao.class);

    public static SSLContext insecureContext(){
        TrustManager[] noopTrustManager = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] xcs, String string) {}
                    public void checkServerTrusted(X509Certificate[] xcs, String string) {}
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopTrustManager, null);
            return sc;
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            return null;
        }
    }

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

        System.out.println(params);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(35))
                .sslContext(insecureContext())
                .build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ConstantesUtil.API_ACUMULACIONES_URL))
                .headers("x-idAcceso", idAcceso,
                        "Authorization","Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(params)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() >= 200 && response.statusCode() < 300){
            JSONObject puntosResponse = new JSONObject(response.body());

            logger.info(puntosResponse.getString("mensaje") + " Codigo: " +response.statusCode());

            respuesta[0] = puntosResponse.getString("mensaje");
            respuesta[1] = puntosResponse.getString("folio");
            respuesta[2] = "0";

            /*
            respuesta[2] = puntosResponse.getJSONObject("resultado").getString("idUsuarioTpremia");
            respuesta[3] = String.valueOf(puntosResponse.getJSONObject("resultado").getInt("idOperacion"));
            respuesta[4] = puntosResponse.getJSONObject("resultado").getString("FolioOperacion");
            respuesta[5] = puntosResponse.getJSONObject("resultado").getString("fechaHoraOperacion");
            respuesta[6] = puntosResponse.getJSONObject("resultado").getJSONObject("balanceLealtad").getString("tipo");
            JSONArray balanceLealtad = puntosResponse.getJSONObject("resultado").getJSONArray("balanceLealtad");
            respuesta[7] = String.valueOf(balanceLealtad.getJSONObject(0).getInt("puntosRedimir"));
            respuesta[8] = String.valueOf(balanceLealtad.getJSONObject(0).getInt("totalPuntosAnterior"));
            respuesta[9] = String.valueOf(balanceLealtad.getJSONObject(0).getInt("totalPuntosFinal"));
            respuesta[10] = String.valueOf(balanceLealtad.getJSONObject(0).getInt("puntosRecompensa"));

            System.out.println(balanceLealtad.getJSONObject(0).getInt("puntosRecompensa"));
            */

            return respuesta;
        }else {
            JSONObject puntosResponse = new JSONObject(response.body());
            JSONArray arreglodetalles = puntosResponse.getJSONArray("detalles");
            String detalles = "";
            for(int i = 0; i < arreglodetalles.length(); i++){
                detalles = detalles + arreglodetalles.getString(i) + "\n";
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
            respuesta[2] = "1";

            return respuesta;
        }
    }

}
