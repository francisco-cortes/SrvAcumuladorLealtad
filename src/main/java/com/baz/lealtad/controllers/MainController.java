package com.baz.lealtad.controllers;

import com.baz.lealtad.dtos.DatabaseResponseDto;
import com.baz.lealtad.service.ConsultaBaseService;
import com.baz.lealtad.utils.CifradorRsaUtil;
import org.apache.log4j.Logger;

import java.util.Map;

public class MainController {

    private static ConsultaBaseService dbService = new ConsultaBaseService();
    private static CifradorRsaUtil cifrardor = new CifradorRsaUtil("AAAAB3NzaC1yc2EAAAABJQAAAQB/nAmOjTmezNUDKYvEeIRf2YnwM9/uUG1d0BYs" +
            "c8/tRtx+RGi7N2lUbp728MXGwdnL9od4cItzky/zVdLZE2cycOa18xBK9cOWmcKS" +
            "0A8FYBxEQWJ/q9YVUgZbFKfYGaGQxsER+A0w/fX8ALuk78ktP31K69LcQgxIsl7r" +
            "NzxsoOQKJ/CIxOGMMxczYTiEoLvQhapFQMs3FL96didKr/QbrfB1WT6s3838SEaX" +
            "fgZvLef1YB2xmfhbT9OXFE3FXvh2UPBfN+ffE7iiayQf/2XR+8j4N4bW30DiPtOQ" +
            "LGUrH1y5X/rpNZNlWW2+jGIxqZtgWg7lTy3mXy5x836Sj/","MIIBCgKCAQEA+xGZ/wcz9ugFpP07Nspo6U17l0YhFiFpxxU4pTk3Lifz9R3zsIsu" +
            "ERwta7+fWIfxOo208ett/jhskiVodSEt3QBGh4XBipyWopKwZ93HHaDVZAALi/2A" +
            "+xTBtWdEo7XGUujKDvC2/aZKukfjpOiUI8AhLAfjmlcD/UZ1QPh0mHsglRNCmpCw" +
            "mwSXA9VNmhz+PiB+Dml4WWnKW/VHo2ujTXxq7+efMU4H2fny3Se3KYOsFPFGZ1TN" +
            "QSYlFuShWrHPtiLmUdPoP6CV2mML1tk+l7DIIqXrQhLUKDACeM5roMx0kLhUWB8P" +
            "+0uj1CNlNN4JRZlC7xFfqiMbFRU9Z4N6YwIDAQAB");

    public static Map<String, String> params;
    private static final Logger logger = Logger.getLogger(MainController.class);
    public static final String LOG_PATH = "lealtatjavaarchive.log.dir";


    public static void main(String[] args){
        logger.info("------------------------------------------");
        logger.info("-----------------Inicia-------------------");
        logger.info("------------------------------------------");
        String hola = "hola";
        DatabaseResponseDto responseDb = new DatabaseResponseDto();
        System.out.println("hello world");
        try {
            /*hola = cifrardor.encrypt("Hello");
            System.out.println(hola);
            hola = cifrardor.decrypt(hola);
            System.out.println(hola);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        responseDb = dbService.consulta("bye");
        System.out.println(responseDb.getRespuestaXD());


    }

}
