package com.baz.lealtad.controllers;

import com.baz.lealtad.daos.TokenDao;
import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.utils.PropUtil;
import org.apache.log4j.Logger;

public class MainController {

    private static PropUtil prop= new PropUtil();
    private static ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static TokenDao token = new TokenDao();
    private static final Logger logger = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        prop.setProperties();
        logger.info("|------------------------------------------|");
        logger.info("|-----------------Inicia-------------------|");
        logger.info("|------------------------------------------|");

        SpSalidaResponseDto responseDb = new SpSalidaResponseDto();
        System.out.println("hello world");

        responseDb = salidaService.consulta("JOSE");
        try {
            token.getToken();
        }catch (Exception e){
            System.out.println(e);
        }


        System.out.println(responseDb.getFnRegistros());

        logger.info("------------------------------------------");
        logger.info("-------------------FIN--------------------");
        logger.info("------------------------------------------");
    }

}
