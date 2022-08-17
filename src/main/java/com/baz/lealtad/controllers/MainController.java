package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.*;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.util.List;

public class MainController {

    private static final ParametrerConfiguration configs = new ParametrerConfiguration();
    private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final SpEntradaService spEntrada = new SpEntradaService();
    private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    private static final ConsultarApiLealtadService apiService = new ConsultarApiLealtadService();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        String MMUSER_HOME = System.getenv("MMUSER_HOME");
        System.setProperty("MMUSER_HOME", MMUSER_HOME);
        configs.loadConfiguration();
        logger.info("Inicia: "+ ParametrerConfiguration.NOMBRE_JAR);

        String[] llavesAes = obtenerLlaves.getLlaves();// token = 0, idacceso = 1, simetricas = 2 y 3
        List<CursorSpSalidaModel> responseDb = salidaService.consulta();
        String[] respuestaApi; //apiService.consultaApi();

        if (responseDb != null){

        for(int i = 0; i < responseDb.size(); i ++){

            String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                    llavesAes[2], llavesAes[3]);
            String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                    llavesAes[2], llavesAes[3]);

            //idTipoCliente y idOperacion default 3;

            respuestaApi = apiService.consultaApi(llavesAes[1], llavesAes[0],
                    3,idCliente, importe,
                    responseDb.get(i).getFNSUCURSAL(), 3,
                    responseDb.get(i).getFCFOLIOTRANSACCION());

            spEntrada.guardarBase(responseDb.get(i).getFNIMPORTE(),
                    responseDb.get(i).getFNSUCURSAL(),responseDb.get(i).getFDFECHAOPERACION(),
                    responseDb.get(i).getFCNEGOCIO(),responseDb.get(i).getFCTIPOOPERACION(),
                    responseDb.get(i).getFIORIGENTRANSACCION(), responseDb.get(i).getFIPAISID(),
                    responseDb.get(i).getFCFOLIOTRANSACCION(),responseDb.get(i).getFCIDCLIENTE(),
                    respuestaApi[1],respuestaApi[0],
                    respuestaApi[2]);
        }
        } else {
            logger.error("Respuesta nula del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD \n"+
                    "No se realiza ninguna Accion");
        }
        logger.info(" :FIN!");
    }

}
