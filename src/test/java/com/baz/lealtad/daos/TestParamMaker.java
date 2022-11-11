package com.baz.lealtad.daos;

import java.util.HashMap;
import java.util.Map;

/**
 * TESTParamMakers
 * Description: Hace un mapa de objetos para construir después una peticion al api de lealtad
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public final class TestParamMaker {
  private TestParamMaker(){
    // crear un constructor privado para esconder el publico default
  }
  public static Map<String, Object> parameters(int idTipoCliente, String idCliente, String importe,
                               int sucursal, int idOperacion, String mtcn){
    //hash map string object
    Map<String, Object> parameters = new HashMap<>();
    //int
    parameters.put("idTipoCliente", idTipoCliente);
    //String
    parameters.put("idClienteCifrado", idCliente);
    //String
    parameters.put("importeCifrado", importe);
    //String
    parameters.put("sucursal", sucursal);
    //String
    parameters.put("idOperacion", idOperacion);
    //String
    parameters.put("folioTransaccion", mtcn);
    /*
    retorna mapa de hash
     */
    return parameters;
  }
}
