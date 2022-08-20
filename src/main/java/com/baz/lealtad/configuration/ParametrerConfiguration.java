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

    public static String ORACLE_DATABASE_URL = "";

    public static String ORACLE_DATABASE_U = "";

    public static String ORACLE_DATABASE_P = "";

    public static String ORACLE_DATABASE_STOREPROCEDURE = "";

    public static String ORACLE_DATABASE_IN_STOREPROCEDURE = "";

    public static String ENCODING_UTF8 = "";

    public static String AES_KEY = "";

    public static String ALGORITHM_AES = "";

    public static String ALGORITHM_HMAC = "";

    public static final int IV_SIZE = 16;

    public static String SSL_PROTOCOLE = "";

    public static String TOKEN_URL = "";

    public static String CONSUMER_SECRET = "";

    public static String CONSUMER_KEY = "";

    public static String ASIMETRICAS_URL = "";

    public static String SIMETRICAS_URL = "";

    public static String API_ACUMULACIONES_URL = "";

    public static String RSA_PADDING_SCHEME = "";

    public static final int TIME_OUT_MILLISECONDS = 32000;

    public static final int OK_STATUS_CODE_LIMIT = 299;

    public void loadConfiguration(){

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(CONF_FILE_PATH));

            ORACLE_DATABASE_URL = properties.getProperty("BASEURL").trim();

            ORACLE_DATABASE_U = properties.getProperty("BASEUSR").trim();

            ORACLE_DATABASE_P = properties.getProperty("BASEPWORD").trim();

            ORACLE_DATABASE_STOREPROCEDURE = properties.getProperty("BASEPROCEDURE").trim();

            ORACLE_DATABASE_IN_STOREPROCEDURE = properties.getProperty("BASEINPROCEDURE").trim();

            ENCODING_UTF8 = properties.getProperty("ENCODINGUTF8").trim();

            AES_KEY = properties.getProperty("AES").trim();

            ALGORITHM_AES = properties.getProperty("ALGORITHMAES").trim();

            ALGORITHM_HMAC = properties.getProperty("ALGORITHMHMAC").trim();

            TOKEN_URL = properties.getProperty("TOKENURL").trim();

            CONSUMER_SECRET = properties.getProperty("CONSUMER").trim();

            CONSUMER_KEY = properties.getProperty("CONSUMERKEY").trim();

            ASIMETRICAS_URL = properties.getProperty("ASIMETRICASURL").trim();

            SIMETRICAS_URL = properties.getProperty("SIMETRICASURL").trim();

            API_ACUMULACIONES_URL = properties.getProperty("APIACUMULACIONESURL").trim();

        }
        catch (Exception e){

            LOGGER.error("Hubo un error al cargar las porpiedades" + e);

        }
    }

}
