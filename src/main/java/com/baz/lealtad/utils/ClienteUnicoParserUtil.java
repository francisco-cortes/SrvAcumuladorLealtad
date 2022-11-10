package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Cliente UnicoParserUtil
 * Descrpcion: Clase cambiar el formato de ID Cliente y poder ser procesado por la api lealtad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class ClienteUnicoParserUtil {
  /*
  Constantes globales
   */
  private static final String SERVICE_NAME = "ClienteUnicoParserUtil";
  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int THREE = 3;
  private static final int FOUR = 4;
  private static final int EIGHT = 8;
  private static final int NINE = 9;
  private static final int TEN = 10;
  private static final int ELEVEN = 11;
  private static final int TWELVE = 12;
  private static final int FOURTEN = 14;
  private static final int SIXTEN = 16;
  private static final int HUNDRED = 100;
  private static final int THOUSAND = 1000;
  /*
  Regex
   */
  private static final Pattern IDEAL_CU = Pattern.compile("((\\d{4})-(\\d{4})-(\\d{4}))-(\\d{1,4})");
  private static final Pattern SEVEN_MORE_DIGITS_DEX = Pattern.compile("(\\d{7,})");
  private static final Pattern IDEAL_SPECIAL_CASE = Pattern.compile("(\\d{1,2})-(\\d{1,2})-(\\d{1,4})-(\\d{3,})");
  /**
   * parserar
   * Descrpcion: Metodo principal valida la entrada con las regex definidas para identificar su tipo
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: idcliente(String), log(LogServicio)
   * returns: String
   **/

  public static String[] parsear (String idCliente, LogServicio log, String negocio) {
    String[] respuesta = new String[TWO];
    String nuevoIdCliente = "";
    String idTipoCliente = "";
    if(ParametrerConfiguration.DEX.equalsIgnoreCase(negocio)){
      nuevoIdCliente = removeDash(idCliente);
      idTipoCliente = "5";
    }
    else{
      idTipoCliente = "3";
      if(IDEAL_CU.matcher(idCliente).matches()){
        nuevoIdCliente = idCliente;
      }
      else if(SEVEN_MORE_DIGITS_DEX.matcher(idCliente).matches()){
        nuevoIdCliente = onlyAddDash(idCliente);
      }
      else if (IDEAL_SPECIAL_CASE.matcher(idCliente).matches()){
        nuevoIdCliente = specialCase(idCliente);
      }
      else {
        //la entrada no puede ser paseada porque no hay coincide con ninguna regex
        log.mensaje(SERVICE_NAME + ParametrerConfiguration.VERSION,
          "ERROR no se pudo hallar la forma de la entrada ID Cliente");
        nuevoIdCliente = idCliente;
      }
    }
    respuesta[ZERO] = nuevoIdCliente;
    respuesta[ONE] = idTipoCliente;
    return respuesta;
  }

  /**
   * specialCase
   * Descrpcion: elimina en guion separnadolo y reconstrullendo la cadena
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/

  private static String specialCase(String input){
    StringBuilder auxBuild = new StringBuilder();

    String[] separado = input.split("-");
    // pais
    auxBuild.append(specialCasePais(separado[ZERO]));
    // canal
    auxBuild.append(specialCaseCanal(separado[ONE]));
    // sucursal
    auxBuild.append(specialCaseSucc(separado[TWO]));
    // folio
    auxBuild.append(specialCaseLast(separado[THREE]));
    int len = auxBuild.length() - 1;
    // construye la cadena
    if ("-".equals(auxBuild.substring(len))){
      auxBuild.deleteCharAt(len);
    }
    return auxBuild.toString();
  }

  /**
   * specialCasepais
   * Descrpcion: válida el valor pais y retorna valores concatenados
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: paisCero(String)
   * returns: String
   **/
  private static String specialCasePais(String paisCero){
    int pais = Integer.parseInt(paisCero);
    /*
    concatena un 0 a la cadena si es menor a 10
     */
    if(pais<TEN){
      return "0" + paisCero;
    }
    else {
      return paisCero;
    }
  }

  /**
   * specialCaseCanal
   * Descrpcion: valida el valor canal y retorna valores concatenados
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: canalOne(String)
   * returns: String
   **/
  private static String specialCaseCanal(String canalOne){
    int canal = Integer.parseInt(canalOne);
    /*
    concatena un 0 si el valor es menor a 10
     */
    if(canal<TEN){
      return  "0" + canalOne + "-";
    }
    else {
      return canalOne + "-";
    }
  }

  /**
   * specialCaseSucc
   * Descrpcion: valida el valor succursal y retorna valores concatenados
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: sucursalTwo(String)
   * returns: String
   **/
  private static String specialCaseSucc(String sucursalTwo){
    String succ = "";
    /*
    concatena 3 ceros si es menor a 10
     */
    int sucursal = Integer.parseInt(sucursalTwo);
    if (sucursal < TEN){
      succ = "0" + "0" + "0" + sucursalTwo + "-";
    }
    /*
    concatena 2 ceros si es meno a 100
     */
    else if (sucursal < HUNDRED){
      succ = "0" + "0" + sucursalTwo + "-";
    }
    /*
    concatena un 0 si es menor a 1000
     */
    else if (sucursal < THOUSAND){
      succ = "0" + sucursalTwo + "-";
    }
    else {
      succ = sucursalTwo + "-";
    }
    return succ;
  }

  /**
   * specialCaseLast
   * Descrpcion: valida el valor de la ultima parte de la cadena y retorna valores concatenados
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: paisCero(String)
   * returns: String
   **/
  private static String specialCaseLast(String lastThree){
    /*
    obtiene el tamano de la uultima parte del arrgglo
     */
    int lastLenght = lastThree.length();
    String aux = "";

    if(lastLenght == THREE){
      StringBuilder str = new StringBuilder(lastThree);
      str.insert(ZERO,"0");
      lastThree = str.toString();
    }
    /*
    si el tamano es meno o igual a 8 inserta un guion
     */
    if (lastLenght <= EIGHT){
      StringBuilder str = new StringBuilder(lastThree);
      str.insert(FOUR, "-");
      aux = str.toString();
    }
    /*
    si el tamano es menor o igual a 12 inserta 2 guiones
     */
    else if (lastLenght <= TWELVE){
      StringBuilder str = new StringBuilder(lastThree);
      str.insert(FOUR,"-");
      str.insert(NINE,"-");
      aux = str.toString();
    }
    /*
    si el tamano es menor o igula a 16 inserta 3 guiones
     */
    else if (lastLenght <= SIXTEN){
      StringBuilder str = new StringBuilder(lastThree);
      str.insert(FOUR,"-");
      str.insert(NINE,"-");
      str.insert(FOURTEN,"-");
      aux = str.toString();
    }
    /*
    si el tamano no se encuentra en los if regresa la cadena igual
     */
    else{
      aux = lastThree;
    }
    return aux;
  }

  /**
   * onlyAddDash
   * Descrpcion: Si el tamano de la cadena es mayor a 10 solo agrega un guion cada 4 caracteres de la cadena original
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: paisCero(String)
   * returns: String
   **/
  private static String onlyAddDash(String input){

    String onlyDigits;
    /*
    Al llegar a este metodo se considera un id cliente CU porque se tiene que formar 3 cuartetos separados con guion
    existen caso en el que falta 1 numero para acompletar los 3 cuartetos
    con esta validacion se trata de acompletar agregando un 0 en la 9 posicion
     */
    if(input.length() == NINE){
      StringBuilder str = new StringBuilder(input);
      str.insert(EIGHT,"0");
      str.insert(NINE,"0");
      str.insert(TEN,"0");
      onlyDigits = str.toString();
    }
    else if(input.length() == TEN){
      StringBuilder str = new StringBuilder(input);
      str.insert(EIGHT,"0");
      str.insert(NINE,"0");
      onlyDigits = str.toString();
    }
    else if(input.length() == ELEVEN){
      StringBuilder str = new StringBuilder(input);
      str.insert(EIGHT,"0");
      onlyDigits = str.toString();
    }
    else {
      onlyDigits = input;
    }

    Pattern p = Pattern.compile("(.{" + FOUR + "})", Pattern.DOTALL);
    Matcher m = p.matcher(onlyDigits);
    String aux = m.replaceAll("$1" + "-");
    int newLenght = aux.length();
    String[] checker = aux.split("");
    if (checker[checker.length - ONE].contains("-")) {
      newLenght = newLenght - ONE;
    }
    StringBuilder auxBuild = new StringBuilder();
    for (int i = 0; i < newLenght; i++) {
      auxBuild.append(checker[i]);
    }
    return auxBuild.toString();
  }

  private static String removeDash(String idCliente){
    if (idCliente.contains("-")){
      return idCliente.replace("-","");
    }
    else {
      return idCliente;
    }
  }

}
