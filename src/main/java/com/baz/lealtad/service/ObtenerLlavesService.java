package com.baz.lealtad.service;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.CifradorRsaUtil;

public class ObtenerLlavesService {

    private static final TokenDao Llavetoken = new TokenDao();
    private static final LlavesAsimetricasDao llavesAsimetricas = new LlavesAsimetricasDao();
    private static final LlavesSimetricasDao llavesSimetricas = new LlavesSimetricasDao();
    private static final CifradorRsaUtil cifradorRsa = new CifradorRsaUtil();

    public String[] getLlaves (LogServicio log){
        log.setBegTimeMethod("getLLaves", ParametrerConfiguration.SYSTEM_NAME);
        final int tokenIndex = 0, idAcesso = 0, accesoPublic = 1,
                accesoPrivado = 2, accesoSimetrico = 0, codigoHash = 1,
                idAccesoIndex = 1, accesoSimetricoIndex = 2, codigoHashIndex = 3;
        String token = "";
        String[] asimeticas = new String[3];
        String[] simetricas = new String[2];
        String[] simetricasDecifradas;

        try {

            token = Llavetoken.getToken(log);

        }
        catch (Exception e){

            log.exepcion(e, "ERROR No se pudo obtener Token");

        }
        finally {

            try {

                asimeticas = llavesAsimetricas.getLlavesAsimetricas(token, log);

            }
            catch (Exception e){

                log.exepcion(e, "ERROR No se pudo obtener Asimetricas");

            }
            finally {

                try {

                    simetricas = llavesSimetricas.getLlavesSimetricas(token, asimeticas[idAcesso], log);

                }
                catch (Exception e){

                    log.exepcion(e, "ERROR No se pudo obtener Simetricas");

                }
            }
        }

        simetricasDecifradas = decifrarSimetricas(simetricas[accesoSimetrico], simetricas[codigoHash],
                asimeticas[accesoPublic], asimeticas[accesoPrivado], log);

        String[] llavero = new String[4];

        llavero[tokenIndex] = token;
        llavero[idAccesoIndex] = asimeticas[idAcesso]; //id acceso
        llavero[accesoSimetricoIndex] = simetricasDecifradas[accesoSimetrico]; // acceso simetrico
        llavero[codigoHashIndex] = simetricasDecifradas[codigoHash]; // codigo hash

        return llavero;
    }

    private String decifrarRsa(String texto, String llavePublica, String llavePrivada, LogServicio log){

        String decifradoRsa;

        try {

            decifradoRsa = cifradorRsa.decrypt(texto, llavePrivada);

        }catch (Exception e){

            log.exepcion(e,"ERROR No se pudo decifrar en RSA");
            decifradoRsa = texto;

        }

        return decifradoRsa;

    }

    private String[] decifrarSimetricas(String acceso, String codigo, String llavePublica,
                                        String llavePrivada, LogServicio log){
        final int accIndex = 0, codigoHash = 1;
        String[] simetricasDesifradas = new String[2];
        String accesoSimetrico = decifrarRsa(acceso,llavePublica,llavePrivada,log);
        String codigoAutentificacionHash = decifrarRsa(codigo,llavePublica,llavePrivada,log);
        simetricasDesifradas[accIndex] = accesoSimetrico;
        simetricasDesifradas[codigoHash] = codigoAutentificacionHash;
        return simetricasDesifradas;

    }

}
