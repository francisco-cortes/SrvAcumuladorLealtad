package com.baz.lealtad.service;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.Objects;

public class ObtenerLlavesService {

    private static final Logger logger = Logger.getLogger(ObtenerLlavesService.class);
    private static final TokenDao token = new TokenDao();
    private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();

    public HttpResponse<String> tokenApiResponse;
    public HttpResponse<String> asimetricasApiResponse;
    public HttpResponse<String> simetricasApiResponse;
    public String t = "";
    public String[] a = new String[3];
    public String[] s = new String[2];

    private void getLlaves (){
        try {
            tokenApiResponse = token.getToken();

            if(tokenApiResponse.statusCode() >= 200 && tokenApiResponse.statusCode() < 300){
                logger.info("Token Obtenido: " + tokenApiResponse.statusCode());
                JSONObject tokenResponse = new JSONObject(tokenApiResponse.body());
                t = tokenResponse.getString("access_token");

                try {
                    asimetricasApiResponse = llavesAsimetricas.getLlavesAsimetricas(t);
                    if(asimetricasApiResponse.statusCode() >= 200 && asimetricasApiResponse.statusCode() < 300){
                        logger.info("Llaves asimetricas obtenidas");

                        JSONObject asimetricasResponse = new JSONObject(asimetricasApiResponse.body());

                        a[0] = asimetricasResponse.getJSONObject("resultado").getString("idAcceso");
                        a[1] = asimetricasResponse.getJSONObject("resultado").getString("accesoPublico");
                        a[2] = asimetricasResponse.getJSONObject("resultado").getString("accesoPrivado");

                        try {
                            asimetricasApiResponse = llavesSimetricas.getLlavesSimetricas(t,a[0]);

                            if(simetricasApiResponse.statusCode() >= 200 && simetricasApiResponse.statusCode() < 300){
                                logger.info("Llaves simetricas obtenidas");

                                JSONObject simetricasResponse = new JSONObject(simetricasApiResponse.body());

                                s[0] = simetricasResponse.getJSONObject("resultado").getString("accesoSimetrico");
                                s[1] = simetricasResponse.getJSONObject("resultado").getString("codigoAutentificacionHash");

                            }else {
                                logger.error("Error al obtener llaves Simetricas, Validar");
                                s[0] = "";
                                s[1] = "";
                            }

                        }catch (Exception e){
                            logger.error("No se pudo obtener todas las llaves: \n" + e);
                        }

                    }else {
                        logger.error("Error al obtener llaves asimetricas, Validar");
                        a[0] = "";
                        a[1] = "";
                        a[2] = "";
                    }

                }catch (Exception e){
                    logger.error("No se pudo obtener todas las llaves: \n" + e);
                }

            }else {
                logger.error("No se pudo obtener el token, Validar accesos" + tokenApiResponse.statusCode());
                t = "";
            }
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
