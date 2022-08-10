package com.baz.lealtad.utils;

public final class ConstantesUtil {

    public final static String NOMBRE_JAR = "Acumulador Lealtad";

    public final static String ORACLE_DATABASE_URL = "jdbc:oracle:thin:@10.81.11.77:1521:SFBDDEV";

    public final static String ORACLE_DATABASE_USERNAME = "C3Multimarcas";

    public final static String ORACLE_DATABASE_PASSWORD = "BuSTxN4LMm";

    public final static String ORACLE_DATABASE_STOREPROCEDURE = "call C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD(?, ?, ?)";

    public final static String ORACLE_DATABASE_IN_STOREPROCEDURE = "call C3MULTIMARCAS.PAPLANLEALTAD01.SPTRANSPUNTLEAL(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public final static String KEY_CIPHER = "B2A0A0R2N1Q8C0U7O2I0A1T6ZETCETCU";

    public final static String IV_PARAM = "02 A6 81 F1 19 8B 87 60 E6 56 81 C1 65 88 5D 34";

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String AES_KEY = "AES";

    public static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static final String ALGORITHM_HMAC = "HmacSHA256";

    public static final int IV_SIZE = 16;

    public final static String TOKEN_URL = "https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token";

    public final static String CONSUMER_SECRET = "RqV58GRGKromtjdWohnlCqAKy8dt3Cn1";

    public final static String CONSUMER_KEY = "bhm6EI2aBjFVq3FL";

    public final static String ASIMETRICAS_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves";

    public final static String SIMETRICAS_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves-simetricas/";

    public final static String API_ACUMULACIONES_URL = "https://dev-api.bancoazteca.com.mx:8080/data-company/crm/recompensas-lealtad/v1/usuarios/puntos/acumulaciones";

}
