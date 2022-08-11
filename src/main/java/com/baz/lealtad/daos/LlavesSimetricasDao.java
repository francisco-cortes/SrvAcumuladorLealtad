package com.baz.lealtad.daos;

import com.baz.lealtad.utils.ConstantesUtil;
import org.apache.log4j.Logger;
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

public class LlavesSimetricasDao {
    private static final Logger logger = Logger.getLogger(LlavesAsimetricasDao.class);

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

    public String[] getLlavesSimetricas(String token, String id) throws IOException, InterruptedException {

        String[] llavesSimetricas = new String[2];

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(35))
                .sslContext(insecureContext())
                .build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ConstantesUtil.SIMETRICAS_URL + id))
                .headers("Authorization","Bearer " + token)
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if(response.statusCode() >= 200 && response.statusCode() < 300){
            logger.info("Llaves simetricas obtenidas");

            JSONObject asimetricasResponse = new JSONObject(response.body());

            llavesSimetricas[0] = asimetricasResponse.getJSONObject("resultado").getString("accesoSimetrico");
            llavesSimetricas[1] = asimetricasResponse.getJSONObject("resultado").getString("codigoAutentificacionHash");

            return llavesSimetricas;
        }else {
            logger.error("Error al obtener llaves Simetricas, Validar");
            llavesSimetricas[0] = "";
            llavesSimetricas[1] = "";
            return llavesSimetricas;
        }

    }
}
