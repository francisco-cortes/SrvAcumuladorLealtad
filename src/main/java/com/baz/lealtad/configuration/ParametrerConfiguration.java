package com.baz.lealtad.configuration;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public final class ParametrerConfiguration {

    private static final Logger logger = Logger.getLogger(String.valueOf(ParametrerConfiguration.class));

    public final static String NOMBRE_JAR = "SrvAcumuladorLealtad";

    private static final String CONF_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty("file.separator")
            + NOMBRE_JAR + System.getProperty("file.separator") + "config" + System.getProperty("file.separator")
            + "SrvGeoBazDig.properties";

    public static String ORACLE_DATABASE_URL = "";

    public static String ORACLE_DATABASE_USERNAME = "";

    public static String ORACLE_DATABASE_PASSWORD = "";

    public static String ORACLE_DATABASE_STOREPROCEDURE = "";

    public static String ORACLE_DATABASE_IN_STOREPROCEDURE = "";

    public static String ENCODING_UTF8 = "";

    public static String AES_KEY = "";

    public static String ALGORITHM_AES = "";

    public static String ALGORITHM_HMAC = "";

    public static int IV_SIZE = 0;

    public static String TOKEN_URL = "";

    public static String CONSUMER_SECRET = "";

    public static String CONSUMER_KEY = "";

    public static String ASIMETRICAS_URL = "";

    public static String SIMETRICAS_URL = "";

    public static String API_ACUMULACIONES_URL = "";

    public static String Dcom_sun_net_ssl_checkRevocation = "";

    public static String jdk_internal_httpclient_disableHostnameVerification = "";

    public static String oracle_jdbc_fanEnabled = "";

    public void loadConfiguration(){
        try {
            Properties systemProps = System.getProperties();
            Properties properties = new Properties();
            properties.load(new FileInputStream(CONF_FILE_PATH));

            ORACLE_DATABASE_URL = properties.getProperty("BASEURL").trim();

            ORACLE_DATABASE_USERNAME = properties.getProperty("BASEUSR").trim();

            ORACLE_DATABASE_PASSWORD = properties.getProperty("BASEPWORD").trim();

            ORACLE_DATABASE_STOREPROCEDURE = properties.getProperty("BASEPROCEDURE").trim();

            ORACLE_DATABASE_IN_STOREPROCEDURE = properties.getProperty("BASEINPROCEDURE").trim();

            ENCODING_UTF8 = properties.getProperty("ENCODINGUTF8").trim();

            AES_KEY = properties.getProperty("AES").trim();

            ALGORITHM_AES = properties.getProperty("ALGORITHMAES").trim();

            ALGORITHM_HMAC = properties.getProperty("ALGORITHMHMAC").trim();

            IV_SIZE = Integer.parseInt(properties.getProperty("IVSIZE").trim());

            TOKEN_URL = properties.getProperty("TOKENURL").trim();

            CONSUMER_SECRET = properties.getProperty("CONSUMER").trim();

            CONSUMER_SECRET = properties.getProperty("CONSUMERKEY").trim();

            ASIMETRICAS_URL = properties.getProperty("ASIMETRICASSURL").trim();

            SIMETRICAS_URL = properties.getProperty("SIMETRICASSURL").trim();

            API_ACUMULACIONES_URL = properties.getProperty("APIACUMULACIONESURL").trim();

            Dcom_sun_net_ssl_checkRevocation =  properties.getProperty("ssl_checkRevocation").trim();

            jdk_internal_httpclient_disableHostnameVerification = properties.getProperty("httpclient_disableHostnameVerification").trim();

            oracle_jdbc_fanEnabled = properties.getProperty("oracle_jdbc_fanEnabled").trim();

            systemProps.put("Dcom.sun.net.ssl.checkRevocation",Dcom_sun_net_ssl_checkRevocation);
            systemProps.put("jdk.internal.httpclient.disableHostnameVerification", jdk_internal_httpclient_disableHostnameVerification);
            systemProps.put("oracle.jdbc.fanEnabled",oracle_jdbc_fanEnabled);
            System.setProperties(systemProps);

        }catch (Exception e){
            logger.info("Hubo un error al cargar las porpiedades" + e);
        }
    }

}
