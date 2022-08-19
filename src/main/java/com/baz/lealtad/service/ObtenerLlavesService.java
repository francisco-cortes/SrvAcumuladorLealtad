package com.baz.lealtad.service;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;

public class ObtenerLlavesService {

    private static final Logger LOGGER = Logger.getLogger(ObtenerLlavesService.class);
    private static final TokenDao Llavetoken = new TokenDao();
    private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();

    public String[] getLlaves (){

        final int tokenIndex = 0, idAcesso = 0, accesoPublic = 1,
                accesoPrivado = 2, accesoSimetrico = 0, codigoHash = 1,
                idAccesoIndex = 1, accesoSimetricoIndex = 2, codigoHashIndex = 3;
        String token = "";
        String[] asimeticas = new String[3];
        String[] simetricas = new String[2];
        String[] simetricasDecifradas;

        try {

            token = Llavetoken.getToken();
            LOGGER.info("toke obtenido: " + token + "\n");

        }
        catch (Exception e){

            LOGGER.error("No se pudo obtener Token: " + e);

        }
        finally {

            try {

                asimeticas = llavesAsimetricas.getLlavesAsimetricas(token);
                LOGGER.info("llaves asimetricas obtenidas: "
                        + asimeticas[idAcesso] + "\n"
                        + asimeticas[accesoPublic] + "\n"
                        + asimeticas[accesoPrivado] + "\n");

            }
            catch (Exception e){

                LOGGER.error("No se pudo obtener Asimetricas: " + e);

            }
            finally {

                try {

                    simetricas = llavesSimetricas.getLlavesSimetricas(token, asimeticas[idAcesso]);
                    LOGGER.info("llaves simetrcias obtenidas: "
                            + simetricas[accesoSimetrico] + "\n"
                            + simetricas[codigoHash] + "\n");

                }
                catch (Exception e){

                    LOGGER.error("No se pudo obtener Simetricas: " + e);

                }
            }
        }

        simetricasDecifradas = decifrarSimetricas(simetricas[accesoSimetrico], simetricas[codigoHash],
                asimeticas[accesoPublic], asimeticas[accesoPrivado]);

        LOGGER.info("llaves simetricas decifradas: "
                + simetricasDecifradas[accesoSimetrico] + "\n"
                + simetricasDecifradas[codigoHash] + "\n");

        String[] llavero = new String[4];

        llavero[tokenIndex] = token;
        llavero[idAccesoIndex] = asimeticas[idAcesso]; //id acceso
        llavero[accesoSimetricoIndex] = simetricasDecifradas[accesoSimetrico]; // acceso simetrico
        llavero[codigoHashIndex] = simetricasDecifradas[codigoHash]; // codigo hash

        return llavero;
    }

    private String decifrarRsa(String texto, String llavePublica, String llavePrivada){

        String decifradoRsa;
        CifradorRsaUtil cifradorRsa = new CifradorRsaUtil(llavePublica, llavePrivada);

        try {

            decifradoRsa = cifradorRsa.decrypt(texto);

        }catch (Exception e){

            LOGGER.error("No se pudo decifrar en RSA Error:" + e);
            decifradoRsa = texto;

        }

        return decifradoRsa;

    }

    private String[] decifrarSimetricas(String acceso, String codigo, String llavePublica, String llavePrivada){
        final int accIndex = 0, codigoHash = 1;
        String[] simetricasDesifradas = new String[2];
        String accesoSimetrico = decifrarRsa(acceso,llavePublica,llavePrivada);
        String codigoAutentificacionHash = decifrarRsa(codigo,llavePublica,llavePrivada);
        simetricasDesifradas[accIndex] = accesoSimetrico;
        simetricasDesifradas[codigoHash] = codigoAutentificacionHash;
        return simetricasDesifradas;

    }

}
