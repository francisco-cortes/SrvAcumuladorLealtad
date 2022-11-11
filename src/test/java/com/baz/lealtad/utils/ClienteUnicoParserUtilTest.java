package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * <b>ClienteUnicoParserUtilTest</b>
 * @descripcion: Test Unitaros sobre el parceo del cliente unico
 * @autor: Francisco Javier Cortes Torres, Desarrollador
 * @ultimaModificacion: 31/10/22
 */
public class ClienteUnicoParserUtilTest {
  /*
  Constantes de respuesta
   */
  private static final String RESPUESTA_LABEL  = " Respuesta: ";
  private static final String ESPERADO_LABEL = " Esperado : ";
  private static final String INICIO_MENSAJE_LOG = "Parseo de Id Cliente, Entrada: ";
  private static final String DEX = "DEX";
  private static final String MM = "MM";
  private static int ID_CLIENTE = 0;
  private static final LogServicio log = new LogServicio();

  /**
   * <b>testClienteUnicoIdeal</b>
   * @descripcion: realiza el parseo con entrada ideal
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @param:
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico conforma xxxx-xxxx-xxxx-xxxx")
  @Test
  public void testClienteUnicoIdeal(){
    /*
    constantes
     */
    final String IDEAL = "0101-0127-4888-123";
    /*
    valor esperado despues de parsear
     */
    final String ESPERADO = "0101-0127-4888-123";

    String[] respuesta = ClienteUnicoParserUtil.parsear(IDEAL, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG
      + "respuesta: " + respuesta[ID_CLIENTE]);
    /*
    asierta si son iguales
     */
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteDexNumerico</b>
   * @descripcion: realiza el parceo para un idcliente tipo dex
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico conforma dex numerica de 7 a 10 digitos")
  @Test
  public void testClienteDexNumerico(){
    /*
    constantes
     */
    final String DEXNUM = "159997207";
    /*
    valor esperado de respuesta
     */
    final String ESPERADO = "159997207";

  String[] respuesta = ClienteUnicoParserUtil.parsear(DEXNUM, log, DEX);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + DEX + ESPERADO_LABEL + ESPERADO
      + RESPUESTA_LABEL + respuesta[ID_CLIENTE]);
    /*
    acierta si son iguales
     */
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteNumerico</b>
   * @descripcion: Realiza el parseo de los id clientes numericos de cliente unico
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma numerica de 12 digitos")
  @Test
  public void testClienteNumerico(){
    /*
    constantes
     */
    final String CU = "01012078484";
    /*
    valor esperado de respuesta
     */
    final String ESPERADO = "0101-2078-0484";

    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU + ESPERADO + ESPERADO
      + RESPUESTA_LABEL + respuesta[ID_CLIENTE]);
    /*
    acierta si es igual
     */
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteEspecial</b>
   * @descripcion: Realiza el parseo con la forma especial sin cuartetos
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma x-x-xxxx-xxxxxx")
  @Test
  public void testClienteEspecial(){
    /*
    constantes
     */
    final String CU = "1-9-2669-185";
    /*
    Valor esperado de respuesta
     */
    final String ESPERADO = "0109-2669-0185";

    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU + ESPERADO_LABEL + ESPERADO
      + RESPUESTA_LABEL + respuesta[ID_CLIENTE]);
    /*
    acierta si son iguales
     */
    System.out.println(respuesta[ID_CLIENTE] + " = " + ESPERADO);
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteEspecialDos</b>
   * @descripcion: Realiza el parseo para sucursales de 2 digistos de la forma especial
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma x-x-xx-xxxxxx")
  @Test
  public void testClienteEspecialDos(){
    /*
    constantes
     */
    final String CU = "11-11-87-2503712345678";
    final String ESPERADO = "1111-0087-2503-7123-4567-8";
    final String CU2 = "11-11-7-250371234567";
    final String ESPERADO2 = "1111-0007-2503-7123-4567";
    final String CU3 = "11-1-187-2503";
    final String ESPERADO3 = "1101-0187-2503";

    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    String[] respuesta2 = ClienteUnicoParserUtil.parsear(CU2, log, MM);
    String[] respuesta3 = ClienteUnicoParserUtil.parsear(CU3, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU + ESPERADO_LABEL + ESPERADO
      + RESPUESTA_LABEL + respuesta);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU2 + ESPERADO_LABEL + ESPERADO2
      + RESPUESTA_LABEL + respuesta2);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU3 + ESPERADO_LABEL + ESPERADO3
      + RESPUESTA_LABEL + respuesta3);
    assertTrue(respuesta[ID_CLIENTE].equals(ESPERADO)&& respuesta2[ID_CLIENTE].equals(ESPERADO2)
      && respuesta3[ID_CLIENTE].equals(ESPERADO3));
  }

  /**
   * <b>testClienteInvalido</b>
   * @descripcion: realiza el parseo para cadenas que no se puedan parsear
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @param:
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma x-x-xx-xxxxxx")
  @Test
  public void testClienteInvalido(){
    /*
    constantes
     */
    final String CU = "cadena";
    /*
    se espera se regrese la misma cadena
     */
    final String ESPERADO = "cadena";

    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU + ESPERADO_LABEL + ESPERADO
      + RESPUESTA_LABEL + respuesta);
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteNumericoOnceDigitos</b>
   * @descripcion: Realiza el parceo para cliente unico con 11 digitos, solo agrega un 0 en el tercer cuarteto
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @param:
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma numerica de 11 digitos")
  @Test
  public void testClienteNumericoOnceDigitos(){
    /*
    constantes
     */
    final String CU = "01279953205";
    final String ESPERADO = "0127-9953-0205";

    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU
      + RESPUESTA_LABEL + respuesta[ID_CLIENTE]);
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

  /**
   * <b>testClienteNumericoDoceMas</b>
   * @descripcion: Realiza el parceo para cliente unico mayor a 12 digitos (maximo 16)
   * @autor: Francisco Javier Cortes Torres, Desarrollador
   * @param:
   * @ultimaModificacion: 31/10/22
   */
  @DisplayName("Prueba unitaria cliente unico con forma numerica de mas de 12 digitos")
  @Test
  public void testClienteNumericoDoceMas(){
    /*
    constantes
     */
    final String CU = "0101207888445726123412";
    final String ESPERADO = "0101-2078-8844-5726-1234-12";

    /*
    el numero maximo de caracteres para clienteUnicoParcer es de 16 caracteres
     */
    String[] respuesta = ClienteUnicoParserUtil.parsear(CU, log, MM);
    log.mensaje(ParametrerConfiguration.SYSTEM_NAME_TEST, INICIO_MENSAJE_LOG + CU + ESPERADO + ESPERADO
      + RESPUESTA_LABEL + respuesta[ID_CLIENTE]);
    /*
    acierta si son iguales
     */
    assertEquals(respuesta[ID_CLIENTE],ESPERADO);
  }

}
