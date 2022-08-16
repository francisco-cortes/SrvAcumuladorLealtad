package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenDao {

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

    public HttpResponse<String> getToken() throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", ParametrerConfiguration.CONSUMER_SECRET);
        parameters.put("client_secret", ParametrerConfiguration.CONSUMER_KEY);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoded = Base64.getEncoder()
                .encodeToString((ParametrerConfiguration.CONSUMER_SECRET + ":" + ParametrerConfiguration.CONSUMER_KEY).getBytes());

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(35))
                .sslContext(insecureContext())
                .build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ParametrerConfiguration.TOKEN_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded",
                        "Authorization","Basic " + encoded)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
