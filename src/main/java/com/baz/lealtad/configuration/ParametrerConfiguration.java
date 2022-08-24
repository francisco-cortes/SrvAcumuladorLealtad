package com.baz.lealtad.configuration;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public final class ParametrerConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ParametrerConfiguration.class);

    public static final String NOMBRE_JAR = "SrvAcumuladorLealtad";

    private static final String CONF_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty("file.separator")
            + NOMBRE_JAR + System.getProperty("file.separator") + "config" + System.getProperty("file.separator")
            + "SrvAcumuladorLealtad.properties";

    public static final String CERT_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty("file.separator")
            + NOMBRE_JAR + System.getProperty("file.separator") + "ca-ch.cert";

    public static String oracleDatabaseUrl = "";

    public static String oracleDatabaseU = "";

    public static String oracleDatabaseP = "";

    public static String oracleDatabaseStoreprocedure = "";

    public static String oracleDatabaseInStoreprocedure = "";

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String AES_KEY = "AES";

    public static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static final String ALGORITHM_HMAC = "HmacSHA256";

    public static final int IV_SIZE = 16;

    public static String tokenUrl = "";

    public static String consumerSecret = "";

    public static String consumerKey = "";

    public static String asimetricasUrl = "";

    public static String simetricasUrl = "";

    public static String apiAcumulacionesUrl = "";

    public static final String RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding";

    public static final int TIME_OUT_MILLISECONDS = 32000;

    public static final int OK_STATUS_CODE_LIMIT = 299;

    public static final int CANT_LOAD_SOMETHING = 1;

    public static final int ERROR_OR_EXCEPTION = 2;

    public void loadConfiguration(){

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

        }
        catch (Exception e){

            LOGGER.error("Hubo un error al cargar las porpiedades" + e);
            System.exit(CANT_LOAD_SOMETHING);

        }
    }

}
