package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ObtenerLlavesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiLealtadDaoTest {
  @BeforeAll
  static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
    ParametrerConfiguration.setTokenUrl("https://dev-api.bancoazteca.com.mx:8080/oauth2/v1/token");
    ParametrerConfiguration.setAsimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves");
    ParametrerConfiguration.setSimetricasUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/seguridad/v1/aplicaciones/llaves-simetricas/");
    ParametrerConfiguration.setApiAcumulacionesUrl("https://dev-api.bancoazteca.com.mx:8080/data-company/crm/recompensas-lealtad/v1/usuarios/puntos/acumulaciones");
    ParametrerConfiguration.setConsumerSecret("RqV58GRGKromtjdWohnlCqAKy8dt3Cn1");
    ParametrerConfiguration.setConsumerKey("bhm6EI2aBjFVq3FL");
  }

  @DisplayName("Prueba unitaria para api de lealtad")
  @Test
  public void testApiLeatad() {
    CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    ApiLealtadDao apiLealtadDao = new ApiLealtadDao();
    LogServicio log = new LogServicio();
    ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();


    final int TOKEN = 0;
    final int IDACCESO = 1;
    final int SIMETRICA_1 = 2;
    final int SIMETRICA_2 = 3;
    String[] llavesAes = obtenerLlaves.getLlaves(log);

    final int MENSAJE = 0;
    final int FOLIO = 1;
    final int BANDERA = 2;
    String[] respuesta = new String[3];
    boolean respBol = false;

    String idCliente = cifrarService.cifrar("0101-0127-4888-1",
      llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);

    String importe = cifrarService.cifrar(String.valueOf(1802),
      llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2], log);

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("idTipoCliente", 3);
    parameters.put("idClienteCifrado", idCliente);
    parameters.put("importeCifrado", importe);
    parameters.put("sucursal", 127);
    parameters.put("idOperacion", 3);
    parameters.put("folioTransaccion", "E16004447131");;
    try {
      respuesta = apiLealtadDao.getAcumulaciones(llavesAes[IDACCESO], llavesAes[TOKEN],
        parameters, log);
    } catch (Exception e) {
      e.printStackTrace();
    }
    respBol = !respuesta[0].isEmpty();
    assertEquals(true, respBol);
  }

}
