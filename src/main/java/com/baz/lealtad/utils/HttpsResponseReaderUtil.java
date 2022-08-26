package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpsResponseReaderUtil {

  public String responseReader(HttpsURLConnection connection) throws IOException {
    BufferedReader br;
    if(connection.getResponseCode() > ParametrerConfiguration.OK_STATUS_CODE_LIMIT){
      br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    }
    else {
      br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
    StringBuilder sb = new StringBuilder();

    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }

    br.close();

    return sb.toString();
  }

}
