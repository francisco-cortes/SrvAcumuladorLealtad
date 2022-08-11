package com.baz.lealtad.service;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;

import java.util.Objects;

public class ObtenerLlavesService {

    private static final Logger logger = Logger.getLogger(ObtenerLlavesService.class);
    private static final TokenDao token = new TokenDao();
    private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();

    public String t = "";
    public String[] a = new String[3];
    public String[] s = new String[2];

    private void getLlaves (){
        getToken();

        if(!Objects.equals(t, "")){
            getAsimetricas();
        }else {

        }

        if(!Objects.equals(a[0], "")){
            getSimetricas();
        }else {

        }


    }

    private void getToken(){
        try {
            t = token.getToken();
        }catch (Exception e){
            logger.error("No se pudo obtener todas las llaves: \n" + e);
        }
    }

    private void getAsimetricas(){
        try {
            a = llavesAsimetricas.getLlavesAsimetricas(t);
        }catch (Exception e){
            logger.error("No se pudo obtener todas las llaves: \n" + e);
        }
    }

    private void getSimetricas(){
        try {
            s = llavesSimetricas.getLlavesSimetricas(t,a[0]);
        }catch (Exception e){
            logger.error("No se pudo obtener todas las llaves: \n" + e);
        }
    }

    public String[] getLlavesAes(){
        getLlaves();
        return decifrarSimetricas();
    }

    public String cifrarRsa(String texto, String llavePublica, String llavePrivada){
        String cifradoRsa;
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);
        try {
            cifradoRsa = cifradorRsa.encrypt(texto);
        } catch (Exception e) {
            logger.error("No se pudo cifrar en RSA");
            cifradoRsa = texto;
            e.printStackTrace();
        }
        return cifradoRsa;
    }

    private String decifrarRsa(String texto, String llavePublica, String llavePrivada){
        String decifradoRsa;
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);
        try {
            decifradoRsa = cifradorRsa.decrypt(texto);
        }catch (Exception e){
            logger.error("No se pudo decifrar en RSA");
            decifradoRsa = texto;
            e.printStackTrace();
        }
        return decifradoRsa;
    }

    private String[] decifrarSimetricas(){
        String[] simetricasDesifradas = new String[4];
        String accesoSimetrico = decifrarRsa(s[0],a[1],a[2]);
        String codigoAutentificacionHash = decifrarRsa(s[1],a[1],a[2]);
        simetricasDesifradas[0] = accesoSimetrico;
        simetricasDesifradas[1] = codigoAutentificacionHash;

        simetricasDesifradas[2] = t;
        simetricasDesifradas[3] = a[0];

        logger.info("Llaves simetricas desifradas");
        return simetricasDesifradas;
    }

}
