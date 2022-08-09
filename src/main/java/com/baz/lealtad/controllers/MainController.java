package com.baz.lealtad.controllers;

import com.baz.lealtad.daos.LlavesAsimetricasDao;
import com.baz.lealtad.daos.LlavesSimetricasDao;
import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.utils.PropUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    private static PropUtil prop= new PropUtil();
    private static ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        prop.setProperties();
        logger.info("|------------------------------------------|");
        logger.info("|-----------------Inicia-------------------|");
        logger.info("|------------------------------------------|");

        List<CursorSpSalidaModel> responseDb = new ArrayList<>();
        System.out.println("hello world");

        //responseDb = salidaService.consulta();
        //System.out.println(responseDb);
        obtenerLlaves.getLlaves();
        obtenerLlaves.decifrarSimetricas();

        logger.info("------------------------------------------");
        logger.info("-------------------FIN--------------------");
        logger.info("------------------------------------------");
    }

}
