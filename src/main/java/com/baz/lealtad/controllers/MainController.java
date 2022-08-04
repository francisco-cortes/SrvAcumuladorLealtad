package com.baz.lealtad.controllers;

import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.service.ConsultaSalidaService;
import org.apache.log4j.Logger;

public class MainController {

    private static ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        logger.info("|------------------------------------------|");
        logger.info("|-----------------Inicia-------------------|");
        logger.info("|------------------------------------------|");

        SpSalidaResponseDto responseDb = new SpSalidaResponseDto();
        System.out.println("hello world");

        responseDb = salidaService.consulta("JOSE");

        System.out.println(responseDb.getFnRegistros());

        logger.info("------------------------------------------");
        logger.info("-------------------FIN--------------------");
        logger.info("------------------------------------------");
    }

}
