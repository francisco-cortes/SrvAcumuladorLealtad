package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.utils.PropUtil;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private static final PropUtil prop= new PropUtil();
    private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        prop.setProperties();
        logger.info("|------------------------------------------|");
        logger.info("|-----------------Inicia-------------------|");
        logger.info("|------------------------------------------|");

        String[] llavesAes = new String[2];
        List<CursorSpSalidaModel> responseDb = new ArrayList<>();

        //responseDb = salidaService.consulta();
        //System.out.println(responseDb);
        llavesAes = obtenerLlaves.getLlavesAes();
        String ej = cifrarService.cifrar("i", llavesAes[0], llavesAes[1]);
        String ej2 = cifrarService.decifrar(ej,llavesAes[0],llavesAes[1]);
        logger.info(ej2);



        logger.info("------------------------------------------");
        logger.info("-------------------FIN--------------------");
        logger.info("------------------------------------------");
    }

}
