package com.baz.lealtad.service;

import com.baz.lealtad.controllers.MainController;
import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;

public class ObtenerLlavesService {

    private static final Logger logger = Logger.getLogger(ObtenerLlavesService.class);
    private static TokenDao token = new TokenDao();
    private static LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();

    public String t = "";
    public String[] a = new String[3];
    public String[] s = new String[2];

    public void getLlaves (){
        try {
            t = token.getToken();
            a = llavesAsimetricas.getLlavesAsimetricas(t);
            s = llavesSimetricas.getLlavesSimetricas(t,a[0]);
            logger.info("llaves obtenidas");
        }catch (Exception e){
            logger.error("no se pudo obtener todas las llaves");
            System.out.println(e);
        }
    }

    public String cifrarRsa(String texto, String llavePublica, String llavePrivada){
        String cifradoRsa = "";
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);
        try {
            cifradoRsa = cifradorRsa.encrypt(texto);
            logger.info(texto + " : Cifrado en RSA");
        } catch (Exception e) {
            logger.error("No se pudo cifrar en RSA");
            cifradoRsa = texto;
            e.printStackTrace();
        }
        return cifradoRsa;
    }

    public String decifrarRsa(String texto, String llavePublica, String llavePrivada){
        String decifradoRsa = "";
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);
        try {
            decifradoRsa = cifradorRsa.decrypt(texto);
            logger.info(texto + " : Decifrado en RSA");
        }catch (Exception e){
            logger.error("No se pudo decifrar en RSA");
            decifradoRsa = texto;
            e.printStackTrace();
        }
        return decifradoRsa;
    }

    public String[] decifrarSimetricas(){
        String[] simetricasDesifradas = new String[2];
        String accesoSimetrico = decifrarRsa(s[0],a[1],a[2]);
        String codigoAutentificacionHash = decifrarRsa(s[1],a[1],a[2]);
        logger.info(accesoSimetrico);
        logger.info(codigoAutentificacionHash);
        simetricasDesifradas[0] = accesoSimetrico;
        simetricasDesifradas[1] = codigoAutentificacionHash;
        return simetricasDesifradas;
    }

}
