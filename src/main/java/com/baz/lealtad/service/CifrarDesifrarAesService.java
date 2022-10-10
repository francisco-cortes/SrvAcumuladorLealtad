package com.baz.lealtad.service;

import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.CifradorAesUtil;
/**
 * CifrarDesifrarAesService.jaca
 * Descrpcion: contienen los metodos de invocacios para cifrar y decifrar con AES
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class CifrarDesifrarAesService {
  /*
  objetos
   */
  private static final CifradorAesUtil cifradorAes = new CifradorAesUtil();
  /**
   * cifrar
   * Descrpcion: cifra el texto en aes
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: texto(String), aes(String), hmc(String), log(LogService)
   * returns: String
   **/
  public String cifrar (String texto, String aes, String hmac, LogServicio log){

    return cifradorAes.encryptAes(aes,hmac,texto,log);

  }

}
