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
        configs.loadConfiguration();
        logger.info("Inicia: "+ ParametrerConfiguration.NOMBRE_JAR);

        String[] llavesAes = obtenerLlaves.getLlavesAes();
        List<CursorSpSalidaModel> responseDb = salidaService.consulta();
        String[] respuestaApi; //apiService.consultaApi();

        if (responseDb != null){

        for(int i = 0; i < responseDb.size(); i ++){

            String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                    llavesAes[0], llavesAes[1]);
            String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                    llavesAes[0], llavesAes[1]);

            int idOperacion = switch (responseDb.get(i).getFCNEGOCIO()){
                default -> 3;
            };

            respuestaApi = apiService.consultaApi(llavesAes[3], llavesAes[2],
                    responseDb.get(i).getFNIDTIPOCLIENTE(),idCliente,
                    importe,responseDb.get(i).getFNSUCURSAL(),
                    idOperacion, responseDb.get(i).getFCFOLIOTRANSACCION());

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
