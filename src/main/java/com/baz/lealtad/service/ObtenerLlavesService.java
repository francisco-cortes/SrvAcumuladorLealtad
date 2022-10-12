package com.baz.lealtad.service;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.CifradorRsaUtil;

/**
 *
 * ObtenerLlavesService
 * Descrpcion: Clase service que maneja los daos para obtener las llaves y accesos de api Seguridad baz
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class ObtenerLlavesService {
  /*
  objetos
   */
  private static final TokenDao Llavetoken = new TokenDao();
  private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
  private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();
  private static final CifradorRsaUtil cifradorRsa = new CifradorRsaUtil();
  /*
  constantes globales
   */
  private static final int TAMANO_LLAVERO = 4 ;
  private static final int TAMANO_SIMETRICAS_DE = 2;

  /**
   * Metodo get llaves
   * Descrpcion: invoca daos de api seguridad
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: log(LogServicio)
   * returns: String array
   **/

  public String[] getLlaves (LogServicio log){
    /*
    cosntantes
     */
    final int TOKEN_INDEX = 0;
    final int ID_ACCESO = 0;
    final int ACCESO_PRIVADO = 2;
    final int ACCESO_SIMETRICO = 0;
    final int CODIGO_HASH = 1;
    final int ID_ACCESO_INDEX = 1;
    final int ACCESO_SIMETRICO_INDEZ = 2;
    final int CODIGO_HASH_INDEX = 3;
    final int TAMANO_ASIMETRICAS = 3;
    final int TAMANO_SIMETRICAS = 2;
    String token = "";
    String[] asimeticas = new String[TAMANO_ASIMETRICAS];
    String[] simetricas = new String[TAMANO_SIMETRICAS];
    String[] simetricasDecifradas;

    try {
      /*
      obtiene el token
       */
      token = Llavetoken.getToken(log);

    }
    catch (Exception e){
      /*
      error de token
       */
      log.exepcion(e, "ERROR No se pudo obtener Token");

    }
    finally {
      /*
      despues de intentar el token
       */
      try {
        /*
        obtiene llaves asimetricas
         */
        asimeticas = llavesAsimetricas.getLlavesAsimetricas(token, log);

      }
      catch (Exception e){
        /*
        error de ASimetricas
         */
        log.exepcion(e, "ERROR No se pudo obtener Asimetricas");

      }
      finally {
        /*
        depues de intentar obtner llaves Asimeticas
         */
        try {
          /*
            obtiene simetricas
           */
          simetricas = llavesSimetricas.getLlavesSimetricas(token, asimeticas[ID_ACCESO], log);

        }
        catch (Exception e){
          /*
          error de simetricas
           */
          log.exepcion(e, "ERROR No se pudo obtener Simetricas");

        }
      }
    }
    /*
    desifra las llaves simetricas
    requiere las llaves Asimetricas como vector de decifracion
     */
    simetricasDecifradas = decifrarSimetricas(simetricas[ACCESO_SIMETRICO], simetricas[CODIGO_HASH],
      asimeticas[ACCESO_PRIVADO], log);
    /*
    construye respuesta
     */
    String[] llavero = new String[TAMANO_LLAVERO];
    /*
    asigna respuesta final
     */
    llavero[TOKEN_INDEX] = token;
    llavero[ID_ACCESO_INDEX] = asimeticas[ID_ACCESO];
    llavero[ACCESO_SIMETRICO_INDEZ] = simetricasDecifradas[ACCESO_SIMETRICO];
    llavero[CODIGO_HASH_INDEX] = simetricasDecifradas[CODIGO_HASH];

    return llavero;
  }
  /**
   * Metodo decifrarRsa
   * Descrpcion: desifra en RSA
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: texto(String), llavesPrivadas(String), log(LogServicio)
   **/
  private String decifrarRsa(String texto, String llavePrivada, LogServicio log){

    String decifradoRsa;

    try {
      /*
      invoca metodo para decifrar util
       */
      decifradoRsa = cifradorRsa.decrypt(texto, llavePrivada);

    }
    catch (Exception e){
      /*
      error de decifracion
       */
      log.exepcion(e,"ERROR No se pudo decifrar en RSA");
      decifradoRsa = texto;

    }
    /*
    returns decifrado
     */
    return decifradoRsa;

  }

  private String[] decifrarSimetricas(String acceso, String codigo,
                                      String llavePrivada, LogServicio log){
    final int ACC_INDEX = 0;
    final int CODIGO_HASH = 1;
    String[] simetricasDesifradas = new String[TAMANO_SIMETRICAS_DE];
    String accesoSimetrico = decifrarRsa(acceso,llavePrivada,log);
    String codigoAutentificacionHash = decifrarRsa(codigo,llavePrivada,log);
    simetricasDesifradas[ACC_INDEX] = accesoSimetrico;
    simetricasDesifradas[CODIGO_HASH] = codigoAutentificacionHash;
    return simetricasDesifradas;

  }

}
