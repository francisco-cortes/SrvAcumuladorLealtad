package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpEntradaDao;
import com.baz.lealtad.logger.LogServicio;

import java.sql.Date;
import java.util.Map;

public class SpEntradaService {

    private static final EjecutarSpEntradaDao baseEntradaSp = new EjecutarSpEntradaDao();

    public void guardarBase (Map<String, Object> params, String folioPremia,
                             String comentarios, String bandera, LogServicio log){

        baseEntradaSp.ejecutarSpEntrada(params, folioPremia,comentarios,bandera, log);

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
