package com.baz.lealtad.configuration;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ParametrerConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ParametrerConfiguration.class);

    public static final String NOMBRE_JAR = "SrvAcumuladorLealtad";

    private static final String FILE_SEPARATOR = "file.separator";

    private static final String CONF_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty(FILE_SEPARATOR)
            + NOMBRE_JAR + System.getProperty(FILE_SEPARATOR) + "config" + System.getProperty(FILE_SEPARATOR)
            + "SrvAcumuladorLealtad.properties";

    public static final String CERT_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty(FILE_SEPARATOR)
            + NOMBRE_JAR + System.getProperty(FILE_SEPARATOR) + "certs.cert";

    private static String oracleDatabaseUrl = "";

    private static String oracleDatabaseU = "";

    private static String oracleDatabaseP = "";

    private static String oracleDatabaseStoreprocedure = "";

    private static String oracleDatabaseInStoreprocedure = "";

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String AES_KEY = "AES";

    public static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static final String ALGORITHM_HMAC = "HmacSHA256";

    public static final int IV_SIZE = 16;

    private static String tokenUrl = "";

    private static String consumerSecret = "";

    private static String consumerKey = "";

    private static String asimetricasUrl = "";

    private static String simetricasUrl = "";

    private static String apiAcumulacionesUrl = "";

    public static final String RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding";

    public static final int TIME_OUT_MILLISECONDS = 32000;

    public static final int OK_STATUS_CODE_LIMIT = 299;

    public static final int CANT_LOAD_SOMETHING = 1;

    public static final int ERROR_OR_EXCEPTION = 2;

    public static void loadConfiguration(){

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(CONF_FILE_PATH));

            oracleDatabaseUrl = properties.getProperty("BASEURL").trim();

            oracleDatabaseU = properties.getProperty("BASEUSR").trim();

            oracleDatabaseP = properties.getProperty("BASEPWORD").trim();

            oracleDatabaseStoreprocedure = properties.getProperty("BASEPROCEDURE").trim();

            oracleDatabaseInStoreprocedure = properties.getProperty("BASEINPROCEDURE").trim();

            tokenUrl = properties.getProperty("TOKENURL").trim();

            consumerSecret = properties.getProperty("CONSUMER").trim();

            consumerKey = properties.getProperty("CONSUMERKEY").trim();

            asimetricasUrl = properties.getProperty("ASIMETRICASURL").trim();

            simetricasUrl = properties.getProperty("SIMETRICASURL").trim();

            apiAcumulacionesUrl = properties.getProperty("APIACUMULACIONESURL").trim();

        } catch (IOException e) {
            LOGGER.error("Hubo un error al cargar las porpiedades: " + e);
        }
    }

    public static String getOracleDatabaseUrl (){
        return oracleDatabaseUrl;
    }

    public static String getOracleDatabaseU (){
        return oracleDatabaseU;
    }

    public static String getOracleDatabaseP(){
        return oracleDatabaseP;
    }

    public static String getOracleDatabaseStoreprocedure(){
        return oracleDatabaseStoreprocedure;
    }

    public static String getOracleDatabaseInStoreprocedure(){
        return oracleDatabaseInStoreprocedure;
    }

    public static String getTokenUrl(){
        return tokenUrl;
    }

    public static String getConsumerSecret(){
        return consumerSecret;
    }

    public static String getConsumerKey(){
        return consumerKey;
    }

    public static String getAsimetricasUrl(){
        return asimetricasUrl;
    }

    public static String getSimetricasUrl(){
        return simetricasUrl;
    }

    public static String getApiAcumulacionesUrl(){
        return apiAcumulacionesUrl;
    }

}
