package com.baz.lealtad.daos;

import java.util.HashMap;
import java.util.Map;

public class TestParamMaker {
  public static Map parameters(int idTipoCliente, String idCliente, String importe,
                               int sucursal, int idOperacion, String MTCN){
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("idTipoCliente", idTipoCliente);
    parameters.put("idClienteCifrado", idCliente);
    parameters.put("importeCifrado", importe);
    parameters.put("sucursal", sucursal);
    parameters.put("idOperacion", idOperacion);
    parameters.put("folioTransaccion", MTCN);
    return parameters;
  }
}
