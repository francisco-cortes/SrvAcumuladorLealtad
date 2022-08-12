package com.baz.lealtad.daos;

import com.baz.lealtad.utils.ConstantesUtil;

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

public class LlavesAsimetricasDao {

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

    public HttpResponse<String> getLlavesAsimetricas(String token) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(35))
                .sslContext(insecureContext())
                .build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ConstantesUtil.ASIMETRICAS_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded",
                        "Authorization","Bearer " + token)
                .GET().build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
