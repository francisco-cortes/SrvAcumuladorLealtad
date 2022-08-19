package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpEntradaDao;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.Map;

public class SpEntradaService {

    private static final Logger LOGGER = Logger.getLogger(ConsultaSalidaService.class);

    private static final EjecutarSpEntradaDao baseEntradaSp = new EjecutarSpEntradaDao();

    public void guardarBase (Map<String, Object> params, String folioPremia,
                             String comentarios, String bandera){

        String fechaDDMMYYYY = parsearFecha(String.valueOf(params.get("fechaOperacion")));

        baseEntradaSp.ejecutarSpEntrada(params,fechaDDMMYYYY,
                folioPremia,comentarios,bandera);

    }

    private String parsearFecha(String fechaOrigen){

        if(fechaOrigen.contains("T")){

            String fechaFinal;
            String fecha = fechaOrigen.replace("T", " ");
            String[] fechaHora = fecha.split("\\s+");
            String[] fechaYYYYMMDD = fechaHora[0].split("-");
            String[] fechaDDMMYYYY = new String[3];
            fechaDDMMYYYY[0] = fechaYYYYMMDD[2];
            fechaDDMMYYYY[1] = fechaYYYYMMDD[1];
            fechaDDMMYYYY[2] = fechaYYYYMMDD[0];
            fechaFinal = fechaDDMMYYYY[0] + "-" + fechaDDMMYYYY[1]+ "-" + fechaDDMMYYYY [2]+ " " + fechaHora [1];
            return fechaFinal;

        }
        else if (fechaOrigen.contains(" ")) {

            String fechaFinal;
            String[] fechaHora = fechaOrigen.split("\\s+");
            String[] fechaYYYYMMDD = fechaHora[0].split("-");
            String[] fechaDDMMYYYY = new String[3];
            fechaDDMMYYYY[0] = fechaYYYYMMDD[2];
            fechaDDMMYYYY[1] = fechaYYYYMMDD[1];
            fechaDDMMYYYY[2] = fechaYYYYMMDD[0];
            fechaFinal = fechaDDMMYYYY[0] + "-" + fechaDDMMYYYY[1]+ "-" + fechaDDMMYYYY [2]+ " " + fechaHora [1];
            return fechaFinal;

        }
        else {

            LOGGER.error("Fecha con formato incorrecto");
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
