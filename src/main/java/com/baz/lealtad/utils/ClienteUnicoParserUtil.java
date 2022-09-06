package com.baz.lealtad.utils;

import org.apache.log4j.Logger;

public class ClienteUnicoParserUtil {

  private static final Logger LOGGER = Logger.getLogger(ClienteUnicoParserUtil.class);

  public static String parsear (String idCliente) {

    if(idCliente.matches("((\\d{4,4})-(\\d{4,4})-(\\d{4,4}))-(\\d{1,4})")){
      return idCliente;
    }
    else{

      if(idCliente.matches("(\\d{9,})")){

        LOGGER.error("Se recibio un idCliente numerico");
        return parsaerNumeros(idCliente);

      }
      else if (idCliente.matches("(\\d)-(\\d)-(\\d{4})-(\\d{4,})")){

        LOGGER.error("Se recibio un idCliente con formato x-x-xxxx-xxxxx");
        return parsaerNumeros(parsearFormato(idCliente));

      }
      else {

        LOGGER.error("no se pudo hallar la forma de la entrada");
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

}
