package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpEntradaDao;
import org.apache.log4j.Logger;

import java.sql.Date;

public class SpEntradaService {

    private static final Logger logger = Logger.getLogger(ConsultaSalidaService.class);
    private EjecutarSpEntradaDao baseEntradaSp = new EjecutarSpEntradaDao();

    public void guardarBase (){
        logger.info("Consulta del segundo SP");
        parsearFecha("2016-04-05T17:00:00");
        baseEntradaSp.ejecutarSpEntrada(1,2,3,parsearFecha("2016-04-05T17:00:00"),
                "xxx", "xxx", 1,1,
                "xxx","xxx","xxx","xxx",
                parsearFecha("2016-04-05T17:00:00"),"",1,2,
                2,2,3,"xxx",
                "xxx",1);

    }

    private String parsearFecha(String fechaOrigen){
        if(fechaOrigen.contains("T")){
            String fechaFinal = "";
            String fecha = fechaOrigen.replace("T", " ");
            String[] fechaHora = fecha.split("\\s+");
            String[] fechaYYYYMMDD = fechaHora[0].split("-");
            String[] fechaDDMMYYYY = new String[3];
            fechaDDMMYYYY[0] = fechaYYYYMMDD[2];
            fechaDDMMYYYY[1] = fechaYYYYMMDD[1];
            fechaDDMMYYYY[2] = fechaYYYYMMDD[0];
            fechaFinal = fechaDDMMYYYY[0] + "-" + fechaDDMMYYYY[1]+ "-" + fechaDDMMYYYY [2]+ " " + fechaHora [1];
            return fechaFinal;
        }else {
            logger.error("Fecha con formato incorrecto");
            long mili = System.currentTimeMillis();
            Date date = new Date(mili);
            String actual = date.toString();
            String[] fechaYYYYMMDD = actual.split("-");
            String[] fechaDDMMYYYY = new String[3];
            fechaDDMMYYYY[0] = fechaYYYYMMDD[2];
            fechaDDMMYYYY[1] = fechaYYYYMMDD[1];
            fechaDDMMYYYY[2] = fechaYYYYMMDD[0];
            actual = fechaDDMMYYYY[0] + "-" + fechaDDMMYYYY[1]+ "-" + fechaDDMMYYYY [2];
            return actual;
        }

    }

}
