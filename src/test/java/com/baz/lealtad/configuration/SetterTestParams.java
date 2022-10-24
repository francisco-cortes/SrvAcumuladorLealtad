package com.baz.lealtad.configuration;

public class SetterTestParams {
  public static void setAllRequiredParams(){
    /*
    Ip base oracle desarrollo
     */
    ParametrerConfiguration.setOracleDatabaseIp("10.97.167.38");
    /*
    port base oracle desarrollo
     */
    ParametrerConfiguration.setOracleDatabasePort("1522");
    /*
    nombre base oracle desarrollo
     */
    ParametrerConfiguration.setOracleDatabaseName("orcl");
    /*
    usuario oracle desarrollo
     */
    ParametrerConfiguration.setOracleDatabaseP("BuSTxN4LMm");
    /*
    contraseña oracle desarrllo
     */
    ParametrerConfiguration.setOracleDatabaseU("C3Multimarcas");
    /*
    set token url api seguridad
     */
    ParametrerConfiguration.setTokenUrl("https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token");
    /*
    set asimetricas url api seguridad
     */
    ParametrerConfiguration.setAsimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/" +
      "seguridad/v1/aplicaciones/llaves");
    /*
    set simetricas url api seguridad
     */
    ParametrerConfiguration.setSimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/" +
      "seguridad/v1/aplicaciones/llaves-simetricas/");
    /*
    set acumulaciones url api lealtad
     */
    ParametrerConfiguration.setApiAcumulacionesUrl("https://dev-api.bancoazteca.com.mx:8080/" +
      "data-company/crm/recompensas-lealtad/v1/usuarios/puntos/acumulaciones");
    /*
    set consumer secret
     */
    ParametrerConfiguration.setConsumerSecret("RqV58GRGKromtjdWohnlCqAKy8dt3Cn1");
    /*
    set consumer key
     */
    ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3FL");
    /*
    Segundo SP
     */
    ParametrerConfiguration.setOracleDatabaseInStoreprocedure("call C3MULTIMARCAS.PAPLANLEALTAD01.SPTRANSPUNTLEAL" +
      "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    /*
    primer SP
     */
    ParametrerConfiguration.setOracleDatabaseStoreprocedure("call C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD" +
      "(?, ?, ?)");
  }
}
