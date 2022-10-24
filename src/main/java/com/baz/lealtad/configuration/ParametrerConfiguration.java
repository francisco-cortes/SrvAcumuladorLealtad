package com.baz.lealtad.configuration;

/**
 * ParameterConfiguracion.java
 * Descrpcion: Clase  para cargar las configuraciones o propiedades par el funcionamiento del jar
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

import com.baz.lealtad.logger.LogServicio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ParametrerConfiguration {

  /*
  Usuario en BD y nombre de aplicativo
   */
  public static final String NOMBRE_JAR = "SrvAcumuladorLealtad";
  /*
  Separador de archivos = /
   */
  private static final String FILE_SEPARATOR = "file.separator";
  /*
  Nombre de ambiente
   */
  public static final String MMUSER_HOME = "MMUSER_HOME";

  /*
  Ruta donde se aloja el jar
   */
  private static final String CONF_FILE_PATH = System.getenv(MMUSER_HOME) + System.getProperty(FILE_SEPARATOR)
    + NOMBRE_JAR + System.getProperty(FILE_SEPARATOR) + "config" + System.getProperty(FILE_SEPARATOR)
    + "SrvAcumuladorLealtad.properties";

  /*
    Ruta del certificado para conexion HTTPS a apis internas Baz
    */
  public static final String CERT_FILE_PATH = System.getenv("MMUSER_HOME") + System.getProperty(FILE_SEPARATOR)
    + NOMBRE_JAR + System.getProperty(FILE_SEPARATOR) + "certs.cert";

  /*
  ip para base de datos oracle
   */
  private static String oracleDatabaseIp = "";
  /*
  puerto para base de datos oracle
   */
  private static String oracleDatabasePort = "";
  /*
  nombre para base de datos oracle
   */
  private static String oracleDatabaseName = "";
  /*
  usuario para base de datos oracle
   */
  private static String oracleDatabaseU = "";
  /*
  contrase;a para base de datos oracle
   */
  private static String oracleDatabaseP = "";
  /*
  procedimiento almacenado
   */
  private static String oracleDatabaseStoreprocedure = "";
  /*
  segundo procedimiento almacenado
   */
  private static String oracleDatabaseInStoreprocedure = "";
  /*
  stopper de errores jar
   */
  private static String contencionLog = "100";
  /*
  UTF 8
   */
  public static final String ENCODING_UTF8 = "UTF-8";
  /*
  llave AES
   */
  public static final String AES_KEY = "AES";
  /*
  algoritmo AES
   */
  public static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
  /*
  algoritmo HMAC
   */
  public static final String ALGORITHM_HMAC = "HmacSHA256";
  /*
  tamano de vector IV
   */
  public static final int IV_SIZE = 16;

  /*
  Url para obtener el token baz
   */
  private static String tokenUrl = "";
  /*
  Usuario Consumer para auth2
   */
  private static String consumerSecret = "";
  /*
  contrasena Consumer para auth2
   */
  private static String consumerKey = "";
  /*
  url para obtener llaves Asimetricas
   */
  private static String asimetricasUrl = "";
  /*
  url para obtener llaves simetricas
   */
  private static String simetricasUrl = "";
  /*
  url para obtener acumulaciones lealtad
   */
  private static String apiAcumulacionesUrl = "";

  public static final String RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding";
  /*
  time out para respuestas en milisegundos
   */
  public static final int TIME_OUT_MILLISECONDS = 32000;
  /*
  codigos menores a 299 son repuestas positivas
   */
  public static final int OK_STATUS_CODE_LIMIT = 299;
  /*
  constante para salida sistema no pudo cargar algo
   */
  public static final int CANT_LOAD_SOMETHING = 1;
  /*
  constante para salida de sistema en exepciones
   */
  public static final int ERROR_OR_EXCEPTION = 2;
  /*
  Nombre del sistema para log en pruebas unitarias
   */
  public static final String SYSTEM_NAME_TEST = "SrvAcumuladorLealtadUnitTEST";
  /*
  id tipo negocion Dinero Express
   */
  public static final String DEX = "dex";

  private ParametrerConfiguration(){
    //Empty constructor because sonnarqube is retard
  }

  public static Boolean loadConfigs(){
    LogServicio log = new LogServicio();
    try {
      /*
      carga de archivo properties con un fileInputStream
      .trim para evitar errores por espacios en propiedades
       */
      Properties properties = new Properties();
      properties.load(new FileInputStream(CONF_FILE_PATH));

      oracleDatabaseIp = properties.getProperty("BASEIP").trim();

      oracleDatabasePort = properties.getProperty("BASEPORT").trim();

      oracleDatabaseName = properties.getProperty("BASENAME").trim();

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

      contencionLog = properties.getProperty("CONTENCION").trim();

    }
    catch (IOException e) {
      /*
      expecion impresa en consola al punto de llamar esta todavia no se llama al loger
       */
      log.exepcion(e,"Hubo un error al cargar las porpiedades: " + e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Metodo: loadConfiguration
   * Descrpcion: metodo para obtener propiedades del archivo SrvAcumuladorLealtad.properties
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/

  /**
   * Seccion Getter
   * Descrpcion: metodos get para obtener las variables static
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/
  /*
  get para la ip de base
   */
  public static String getOracleDatabaseIp (){
    return oracleDatabaseIp;
  }
  /*
  get para el puerto de la base
 */
  public static String getOracleDatabasePort () {
    return oracleDatabasePort;
  }
  /*
  get para la base de datos
   */
  public static String getOracleDatabaseName () {
    return oracleDatabaseName;
  }
  /*
  get para el usuario de base de datos
   */
  public static String getOracleDatabaseU (){
    return oracleDatabaseU;
  }
  /*
  get para la contrase;a de base
   */
  public static String getOracleDatabaseP(){
    return oracleDatabaseP;
  }
  /*
  get primer sp
   */
  public static String getOracleDatabaseStoreprocedure(){
    return oracleDatabaseStoreprocedure;
  }
  /*
  get segundo sp
   */
  public static String getOracleDatabaseInStoreprocedure(){
    return oracleDatabaseInStoreprocedure;
  }
  /*
  get para url de TOKEN
   */
  public static String getTokenUrl(){
    return tokenUrl;
  }
  /*
  get para credenical de api
   */
  public static String getConsumerSecret(){
    return consumerSecret;
  }
  /*
  gewt para credencial de api
   */
  public static String getConsumerKey(){
    return consumerKey;
  }
  /*
  get para url de llaves asimetricas
   */
  public static String getAsimetricasUrl(){
    return asimetricasUrl;
  }
  /*
  getp para url de simetricas
   */
  public static String getSimetricasUrl(){
    return simetricasUrl;
  }
  /*
  get para url de acumulaciones
   */
  public static String getApiAcumulacionesUrl(){
    return apiAcumulacionesUrl;
  }

  /*
  get para la coantidad de contencion para el log
   */
  public static int getContencionLog(){
    return Integer.parseInt(contencionLog);
  }

  /**
   * Fin de seccion Getters
   **/

  /**
   * Seccion Setter
   * Descrpcion: metodos getter usados por las clases de test
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/
  public static void setOracleDatabaseIp (String ip){
    oracleDatabaseIp = ip;
  }

  public static void setOracleDatabasePort (String port) {
    oracleDatabasePort = port;
  }

  public static void setOracleDatabaseName (String name) {
    oracleDatabaseName = name;
  }

  public static void setOracleDatabaseU (String user){
    oracleDatabaseU = user;
  }

  public static void setOracleDatabaseP(String p){
    oracleDatabaseP = p;
  }

  public static void setOracleDatabaseStoreprocedure(String outSp ){
    oracleDatabaseStoreprocedure = outSp;
  }

  public static void setOracleDatabaseInStoreprocedure(String inSp){
    oracleDatabaseInStoreprocedure = inSp;
  }

  public static void setTokenUrl(String tokeUrl){
    tokenUrl = tokeUrl;
  }

  public static void setConsumerSecret(String userConsumer){
    consumerSecret = userConsumer;
  }

  public static void setConsumerKey(String pConsumer){
    consumerKey = pConsumer;
  }

  public static void setAsimetricasUrl(String asimetrica){
    asimetricasUrl = asimetrica;
  }

  public static void setSimetricasUrl(String simetrica){
    simetricasUrl = simetrica;
  }

  public static void setApiAcumulacionesUrl(String piUrl){
    apiAcumulacionesUrl = piUrl;
  }

  /**
   * fin setters
   **/

}
