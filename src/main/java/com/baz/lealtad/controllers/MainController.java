package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.*;
import com.baz.lealtad.utils.PropUtil;
import org.apache.log4j.Logger;

import java.util.List;

public class MainController {

    private static final PropUtil prop= new PropUtil();
    private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final SpEntradaService spEntrada = new SpEntradaService();
    private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    private static final ConsultarApiLealtadService apiService = new ConsultarApiLealtadService();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        prop.setProperties();
        logger.info("|------------------------------------------|");
        logger.info("|-----------------Inicia-------------------|");
        logger.info("|------------------------------------------|");

        String[] llavesAes = obtenerLlaves.getLlavesAes();
        List<CursorSpSalidaModel> responseDb = salidaService.consulta();
        String[] respuestaApi; //apiService.consultaApi();

        if (responseDb != null){
            logger.info("Se obtivieron: " + responseDb.size() + " registros "
                    + " del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD");

        for(int i = 0; i < responseDb.size(); i ++){

            logger.info("\n Registro " + i + ": " +  responseDb.get(i).getFCIDCLIENTE());

            String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                    llavesAes[0], llavesAes[1]);
            String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                    llavesAes[0], llavesAes[1]);

            respuestaApi = apiService.consultaApi(llavesAes[3], llavesAes[2],
                    responseDb.get(i).getFNIDTIPOCLIENTE(),idCliente,
                    importe,responseDb.get(i).getFNSUCURSAL(),
                    responseDb.get(i).getFNIDOPERACION(), responseDb.get(i).getFCFOLIOTRANSACCION());

            spEntrada.guardarBase(responseDb.get(i).getFNIDTIPOCLIENTE(), responseDb.get(i).getFNIMPORTE(),
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

        //String ej = cifrarService.cifrar("hola", llavesAes[0], llavesAes[1]);
        //String ej2 = cifrarService.decifrar(ej,llavesAes[0],llavesAes[1]);
        //logger.info(ej);
        //logger.info(ej2);


        /*System.out.println("simetrico: " + llavesAes[0]);
        System.out.println("hash: " + llavesAes [1] );
        System.out.println("Token: " + llavesAes[2]);
        System.out.println("Acceso: " + llavesAes[3]);
        System.out.println(cifrarService.cifrar("0101-0482-4558", llavesAes[0], llavesAes[1]));
        System.out.println(cifrarService.cifrar("100", llavesAes[0], llavesAes[1]));
         */

        //for(int i = 0; i < responseDb.size(); i++){
            //System.out.println(responseDb.get(i).getFCIDCLIENTE() + " " + responseDb.get(i).getFCFOLIOTRANSACCION());
        //}

        /*try {
            leal.getAcumulaciones(llavesAes[3], llavesAes[2], 3,
                    cifrarService.cifrar("0101-0482-4558", llavesAes[0], llavesAes[1]),
                    cifrarService.cifrar("100.00", llavesAes[0], llavesAes[1]),
                    482, 1,
                    "3w54e65r66545617t87yuIINNJio6RTT7YU2022060108");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //spEntrada.guardarBase();


        logger.info("!------------------------------------------!");
        logger.info("!-------------------FIN--------------------!");
        logger.info("!------------------------------------------!");
    }

}
