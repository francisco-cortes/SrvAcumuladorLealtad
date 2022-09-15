package com.baz.lealtad.utils;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteUnicoParserUtil {

  private static final Logger LOGGER = Logger.getLogger(ClienteUnicoParserUtil.class);

  public static String parsear (String idCliente) {

    if(idCliente.matches("((\\d{4})-(\\d{4})-(\\d{4}))-(\\d{1,4})")){
      return idCliente;
    }
    else{

      if(idCliente.matches("(\\d{7,})")){

        if(idCliente.matches("(\\d{7,10})")){
          return idCliente;
          //return onlyAddTen(idCliente);
        }
        else {
          return onlyAddDash(idCliente);
        }

      }
      else if (idCliente.matches("(\\d)-(\\d)-(\\d{4})-(\\d{4,})")){

        return specialCase(idCliente);

      }
      else if (idCliente.matches("(\\d)-(\\d{1,4})-(\\d{2,4})-(\\d{4,})")){
        return specialCase(idCliente);
      }
      else {

        LOGGER.error("no se pudo hallar la forma de la entrada ID Cliente");
        return idCliente;

      }
    }
  }

  private static String parsaerNumeros(String input){
    String aux = "";
    StringBuilder str = new StringBuilder(input);
    int inputLenght = input.length();

    if (inputLenght == 16){
      str.insert(4, '-');
      str.insert(9, '-');
      str.insert(14, '-');
      aux = str.toString();
    }
    else if (inputLenght == 9){
      str.insert(0,"0");
      str.insert(2,"0");
      str.insert(4,"-");
      str.insert(9,"-");
      str.insert(10,"0");
      aux = str.toString();
    }
    else if (inputLenght > 9){
      str.insert(0,"0");
      str.insert(2,"0");
      str.insert(4,"-");
      str.insert(9,"-");
      str.insert(14,"-");
      aux = str.toString();
    }
    else {
      LOGGER.error("la cadena no tiene la longitud requerida");
    }
    return aux;
  }

  private static String parsearFormato(String input){
    return input.replace("-", "");
  }

  private static String specialCase(String input){

    String aux = "";
    String[] separado = new String[6];

    if (input.contains("-")){

      separado = input.split("-");

    }
    else if (input.contains(" ")){
      separado = input.split(" ");
    }
    else {
      //separado[0] = input.substring(1);
      //separado[1] = input.substring(2);
      //separado[2] = input.substring(3);
      //separado[3] = input.substring(4);
    }

    int pais = Integer.parseInt(separado[0]);
    if(pais<10){
      separado[0] = "0" + separado[0];
    }
    else {
      separado[0] = separado[0];
    }

    int canal = Integer.parseInt(separado[1]);
    if(canal<10){
      separado[1] = "0" + separado[1] + "-";
    }
    else {
      separado[0] = separado[0] + "-";
    }

    int sucursal = Integer.parseInt(separado[2]);
    if (sucursal < 10){
      separado[2] = "0" + "0" + "0" + separado[2] + "-";
    }
    else if (sucursal < 100){
      separado[2] = "0" + "0" + separado[2] + "-";
    }
    else if (sucursal < 1000){
      separado[2] = "0" + separado[2] + "-";
    }
    else if (sucursal < 10000){
      separado[2] = separado[2] + "-";
    }

    int lastLenght = separado[3].length();
    if(lastLenght <= 4){
      separado[3] = separado[3];
    }
    else if (lastLenght <= 8){
      StringBuilder str = new StringBuilder(separado[3]);
      str.insert(4, "-");
      separado[3] = str.toString();
    }
    else if (lastLenght <= 12){
      StringBuilder str = new StringBuilder(separado[3]);
      str.insert(4,"-");
      str.insert(9,"-");
      separado[3] = str.toString();
    }
    for(int i = 0; i < separado.length; i++){
      aux = aux + separado[i];
    }
    return aux;

  }

  private static String onlyAddTen(String input){
    String tenOfDeath = "10";
    return tenOfDeath + input;
  }

  private static String onlyAddDash(String input){
    Pattern p = Pattern.compile("(.{" + 4 + "})", Pattern.DOTALL);
    Matcher m = p.matcher(input);
    String aux = m.replaceAll("$1" + "-");
    int newLenght = aux.length();
    String aux2 = "";
    String[] checker = aux.split("");
    if (checker[checker.length - 1].contains("-")) {
      newLenght = newLenght - 1;
    }
    for (int i = 0; i < newLenght; i++) {
      aux2 = aux2 + checker[i];
    }
    return aux2;
  }

}
