package com.baz.lealtad.utils;

import java.util.Properties;

public class PropUtil {
    public void setProperties(){
        Properties systemProps = System.getProperties();
        systemProps.put("Dcom.sun.net.ssl.checkRevocation",Boolean.FALSE.toString());
        systemProps.put("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
        systemProps.put("oracle.jdbc.fanEnabled",Boolean.FALSE.toString());
        System.setProperties(systemProps);
    }
}
