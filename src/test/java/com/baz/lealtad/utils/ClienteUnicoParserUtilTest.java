package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteUnicoParserUtilTest {
  /*
  test name
   */
  private static final String SERVICE_NAME = "ClienteUnicoParserUtilTEST";
  private static final ClienteUnicoParserUtil clienteUnicoParserUtil = new ClienteUnicoParserUtil();
  private static final LogServicio log = new LogServicio();

  @DisplayName("Prueba unitaria cliente unico conforma xxxx-xxxx-xxxx-xxxx")
  @Test
  public void testClienteUnicoIdeal(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String IDEAL = "0101-0127-4888-123";
    final String ESPERADO = "0101-0127-4888-123";

    String respuesta = clienteUnicoParserUtil.parsear(IDEAL, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + IDEAL + " Esperado: " + ESPERADO
      + "respuesta: " + respuesta);
    assertTrue(respuesta.equals(ESPERADO));
  }

  @DisplayName("Prueba unitaria cliente unico conforma dex numerica de 7 a 10 digitos")
  @Test
  public void testClienteDexNumerico(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String DEX = "159997207";
    final String ESPERADO = "159997207";

    String respuesta = clienteUnicoParserUtil.parsear(DEX, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + DEX + " Esperado: " + ESPERADO
      + "respuesta: " + respuesta);
    assertTrue(respuesta.equals(ESPERADO));
  }

  @DisplayName("Prueba unitaria cliente unico con forma numerica de mas de 10 digitos")
  @Test
  public void testClienteNumerico(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String CU = "010120788844";
    final String ESPERADO = "0101-2078-8844";

    String respuesta = clienteUnicoParserUtil.parsear(CU, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU + " Esperado: " + ESPERADO
      + "respuesta: " + respuesta);
    assertTrue(respuesta.equals(ESPERADO));
  }

  @DisplayName("Prueba unitaria cliente unico con forma x-x-xxxx-xxxxxx")
  @Test
  public void testClienteEspecial(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String CU = "1-2-8757-25037";
    final String ESPERADO = "0102-8757-2503-7";

    String respuesta = clienteUnicoParserUtil.parsear(CU, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU + " Esperado: " + ESPERADO
      + "respuesta: " + respuesta);
    assertTrue(respuesta.equals(ESPERADO));
  }

  @DisplayName("Prueba unitaria cliente unico con forma x-x-xx-xxxxxx")
  @Test
  public void testClienteEspecialDos(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String CU = "11-11-87-2503712345678";
    final String ESPERADO = "1111-0087-2503-7123-4567-8";
    final String CU2 = "11-11-7-250371234567";
    final String ESPERADO2 = "1111-0007-2503-7123-4567";
    final String CU3 = "11-1-187-2503";
    final String ESPERADO3 = "1101-0187-2503-";

    String respuesta = ClienteUnicoParserUtil.parsear(CU, log);
    String respuesta2 = ClienteUnicoParserUtil.parsear(CU2, log);
    String respuesta3 = ClienteUnicoParserUtil.parsear(CU3, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU + " Esperado: " + ESPERADO
      + " respuesta: " + respuesta);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU2 + " Esperado: " + ESPERADO2
      + " respuesta: " + respuesta2);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU3 + " Esperado: " + ESPERADO3
      + " respuesta: " + respuesta3);
    assertTrue(respuesta.equals(ESPERADO)&& respuesta2.equals(ESPERADO2) && respuesta3.equals(ESPERADO3));
  }

  @DisplayName("Prueba unitaria cliente unico con forma x-x-xx-xxxxxx")
  @Test
  public void testClienteInvalido(){
    /*
    objetos
     */
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    constantes
     */
    final String CU = "cadena";
    final String ESPERADO = "cadena";

    String respuesta = ClienteUnicoParserUtil.parsear(CU, log);
    log.mensaje(SERVICE_NAME, "Parseo de Id Cliente, Entrada: " + CU + " Esperado: " + ESPERADO
      + "respuesta: " + respuesta);
    assertTrue(respuesta.equals(ESPERADO));
  }

}
