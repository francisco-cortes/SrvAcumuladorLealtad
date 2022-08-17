package com.baz.lealtad.service;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;

public class ObtenerLlavesService {

    private static final Logger logger = Logger.getLogger(ObtenerLlavesService.class);
    private static final TokenDao Llavetoken = new TokenDao();
    private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();

    public String[] getLlaves (){
        String token = "";
        String[] asimeticas = new String[3];
        String[] simetricas = new String[2];
        String[] simetricasDecifradas;
        try {
            token = Llavetoken.getToken();
        }catch (Exception e){
            logger.error("No se pudo obtener Token: " + e);
        }finally {
            try {
                asimeticas = llavesAsimetricas.getLlavesAsimetricas(token);
            }catch (Exception e){
                logger.error("No se pudo obtener Asimetricas: " + e);
            }finally {
                try {
                    simetricas = llavesSimetricas.getLlavesSimetricas(token, asimeticas[0]);
                }catch (Exception e){
                    logger.error("No se pudo obtener Simetricas: " + e);
                }
            }
        }
        simetricasDecifradas = decifrarSimetricas(simetricas[0], simetricas[1], asimeticas[1], asimeticas[2]);
        String[] llavero = new String[4];
        llavero[0] = token;
        llavero[1] = asimeticas[0]; //id acceso
        llavero[2] = simetricasDecifradas[0]; // acceso simetrico
        llavero[3] = simetricasDecifradas[1]; // codigo hash

        return llavero;
    }

    private String decifrarRsa(String texto, String llavePublica, String llavePrivada){
        String decifradoRsa;
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);
        try {
            decifradoRsa = cifradorRsa.decrypt(texto);
        }catch (Exception e){
            logger.error("No se pudo decifrar en RSA Error:" + e);
            decifradoRsa = texto;
        }
        return decifradoRsa;
    }

    private String[] decifrarSimetricas(String acceso, String codigo, String llavePublica, String llavePrivada){
        String[] simetricasDesifradas = new String[2];
        String accesoSimetrico = decifrarRsa(acceso,llavePublica,llavePrivada);
        String codigoAutentificacionHash = decifrarRsa(codigo,llavePublica,llavePrivada);
        simetricasDesifradas[0] = accesoSimetrico;
        simetricasDesifradas[1] = codigoAutentificacionHash;
        return simetricasDesifradas;
    }

}
