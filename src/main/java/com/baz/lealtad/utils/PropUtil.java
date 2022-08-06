package com.baz.lealtad.utils;

import java.util.Properties;

public class PropUtil {
    public void setProperties(){
        Properties systemProps = System.getProperties();
        //systemProps.put("javax.net.ssl.keyStorePassword","passwordForKeystore");
        //systemProps.put("javax.net.ssl.keyStore","pathToKeystore.ks");
        //systemProps.put("javax.net.ssl.trustStore", "pathToTruststore.ts");
        //systemProps.put("javax.net.ssl.trustStorePassword","passwordForTrustStore");
        systemProps.put("Dcom.sun.net.ssl.checkRevocation","false");
        systemProps.put("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
        System.setProperties(systemProps);
    }
}
