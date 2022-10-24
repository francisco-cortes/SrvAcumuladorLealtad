package com.baz.lealtad.utils;

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
  private static final Pattern FALTA_UN_DIGITO = Pattern.compile("(\\d{11})");
  private static final Pattern SEVEN_TEN_IS_DEX = Pattern.compile("(\\d{7,10})");
  private static final Pattern SPECIAL_CASE = Pattern.compile("(\\d)-(\\d)-(\\d{4})-(\\d{4,})");
  private static final Pattern IDEAL_SPECIAL_CASE = Pattern.compile("(\\d{1,2})-(\\d{1,2})-(\\d{1,4})-(\\d{4,})");

  /**
   * parserar
   * Descrpcion: Metodo principal valida la entrada con las regex definidas para identificar su tipo
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: idcliente(String), log(LogServicio)
   * returns: String
   **/

  public static String parsear (String idCliente, LogServicio log) {
    String newIdCliente = "";
    //entrada ideal
    if(IDEAL_CU.matcher(idCliente).matches()){
      newIdCliente = idCliente;
    }
    else{
      // solo digitos mayor a 7 digitos
      if(SEVEN_MORE_DIGITS_DEX.matcher(idCliente).matches()){
        // digitos de 7 a 10
        if(SEVEN_TEN_IS_DEX.matcher(idCliente).matches()){
          newIdCliente = idCliente;
        }
        else {
          // si son mas de 1 0solo se anade guines
          newIdCliente = onlyAddDash(idCliente);
        }

      }
      // caso especial x-x-xxxx-xxxxxx
      else if (SPECIAL_CASE.matcher(idCliente).matches()){

        newIdCliente = specialCase(idCliente);

      }
      // caosi espeical x-x-x-xxxx-xxxxx
      else if (IDEAL_SPECIAL_CASE.matcher(idCliente).matches()){
        newIdCliente = specialCase(idCliente);
      }
      else {
        //la entrada no puede ser paseada por que no hay concide con ninguna regex
        log.mensaje(SERVICE_NAME,
          "ERROR no se pudo hallar la forma de la entrada ID Cliente");
        newIdCliente = idCliente;

      }
    }
    return newIdCliente;
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
    // construye la cadena
    return auxBuild.toString();

  }

  /**
   * specialCasepais
   * Descrpcion: valida el valor pais y retorna valores concatenados
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

    if(FALTA_UN_DIGITO.matcher(input).matches()){
      StringBuilder str = new StringBuilder(input);
      str.insert(NINE,"0");
    }

    Pattern p = Pattern.compile("(.{" + FOUR + "})", Pattern.DOTALL);
    Matcher m = p.matcher(input);
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

}
