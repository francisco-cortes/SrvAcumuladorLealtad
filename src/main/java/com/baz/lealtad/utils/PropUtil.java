package com.baz.lealtad.utils;

import java.util.Properties;

public class PropUtil {
    public void setProperties(){
        Properties systemProps = System.getProperties();
        systemProps.put("Dcom.sun.net.ssl.checkRevocation","false");
        systemProps.put("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
        System.setProperties(systemProps);
    }
}
