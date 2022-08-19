package com.baz.lealtad.configuration;

public final class ParametrerConfiguration {


    public static final String NOMBRE_JAR = "SrvAcumuladorLealtad";

    /*private static final String CONF_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty("file.separator")
            + NOMBRE_JAR + System.getProperty("file.separator") + "config" + System.getProperty("file.separator")
            + "SrvGeoBazDig.properties";*/

    public static String ORACLE_DATABASE_URL = "jdbc:oracle:thin:@10.81.11.77:1521:SFBDDEV";

    public static String ORACLE_DATABASE_U = "C3Multimarcas";

    public static String ORACLE_DATABASE_P = "BuSTxN4LMm";

    public static String ORACLE_DATABASE_STOREPROCEDURE = "call C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD(?, ?, ?)";

    public static String ORACLE_DATABASE_IN_STOREPROCEDURE = "call C3MULTIMARCAS.PAPLANLEALTAD01.SPTRANSPUNTLEAL(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static String ENCODING_UTF8 = "UTF-8";

    public static String AES_KEY = "AES";

    public static String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static String ALGORITHM_HMAC = "HmacSHA256";

    public static int IV_SIZE = 16;

    public static String SSL_PROTOCOLE = "ssl";

    public static String TOKEN_URL = "https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token";

    public static String CONSUMER_SECRET = "RqV58GRGKromtjdWohnlCqAKy8dt3Cn1";

    public static String CONSUMER_KEY = "bhm6EI2aBjFVq3FL";

    public static String ASIMETRICAS_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves";

    public static String SIMETRICAS_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves-simetricas/";

    public static String API_ACUMULACIONES_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/crm/recompensas-lealtad/v1/usuarios/puntos/acumulaciones";

    public static String RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding";

    public static int Time_OUT_MILLISECONDS = 32000;

    public static int OK_STATUS_CODE_LIMIT = 299;

    /*public void loadConfiguration(){
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
    }*/

}
