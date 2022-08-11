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
        String[] respuestaApi = new String[3]; //apiService.consultaApi();


        /*for(int i = 0; i < responseDb.size(); i ++){
            //System.out.println(responseDb.get(i).getFNIDTIPOCLIENTE());
            String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                    llavesAes[0], llavesAes[1]);
            String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                    llavesAes[0], llavesAes[1]);
            respuestaApi = apiService.consultaApi(llavesAes[3], llavesAes[2],
                    responseDb.get(i).getFNIDTIPOCLIENTE(),idCliente,
                    importe,responseDb.get(i).getFNSUCURSAL(),
                    responseDb.get(i).getFNIDOPERACION(), responseDb.get(i).getFCFOLIOTRANSACCION());
        }*/

        String ej = cifrarService.cifrar("hola", llavesAes[0], llavesAes[1]);
        String ej2 = cifrarService.decifrar(ej,llavesAes[0],llavesAes[1]);
        logger.info(ej2);

        //System.out.println(llavesAes[2]);
        //System.out.println(llavesAes[3]);
        //System.out.println(cifrarService.cifrar("0101-0482-4558", llavesAes[0], llavesAes[1]));
        //System.out.println(cifrarService.cifrar("100", llavesAes[0], llavesAes[1]));

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


        logger.info("------------------------------------------");
        logger.info("-------------------FIN--------------------");
        logger.info("------------------------------------------");
    }

}
