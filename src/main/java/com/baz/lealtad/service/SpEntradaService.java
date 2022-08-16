package com.baz.lealtad.service;

import com.baz.lealtad.daos.EjecutarSpEntradaDao;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.sql.Date;

public class SpEntradaService {

    private static final Logger logger = Logger.getLogger(ConsultaSalidaService.class);
    private final EjecutarSpEntradaDao baseEntradaSp = new EjecutarSpEntradaDao();

    public void guardarBase (int importe, int sucursal, String fecha,
                             String negocio, String tipoOperacion, int origenTransaccion,
                             int paisId, String folioTransaccion, String idCliente,
                             String folioPremia, String comentarios, String bandera){

        String fechaDDMMYYYY = parsearFecha(fecha);
        int banderaNum = Integer.parseInt(bandera);
        int idOperacion = 3;//Esperar reglas de negocio
        int idTipoCliente = 3;

        baseEntradaSp.ejecutarSpEntrada(idTipoCliente,importe,sucursal,fechaDDMMYYYY,
                negocio, tipoOperacion, origenTransaccion,paisId,
                folioTransaccion,idCliente,idOperacion,folioPremia,comentarios,
                ParametrerConfiguration.NOMBRE_JAR,banderaNum);

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
        } else if (fechaOrigen.contains(" ")){
            String fechaFinal;
            String[] fechaHora = fechaOrigen.split("\\s+");
            String[] fechaYYYYMMDD = fechaHora[0].split("-");
            String[] fechaDDMMYYYY = new String[3];
            fechaDDMMYYYY[0] = fechaYYYYMMDD[2];
            fechaDDMMYYYY[1] = fechaYYYYMMDD[1];
            fechaDDMMYYYY[2] = fechaYYYYMMDD[0];
            fechaFinal = fechaDDMMYYYY[0] + "-" + fechaDDMMYYYY[1]+ "-" + fechaDDMMYYYY [2]+ " " + fechaHora [1];
            return fechaFinal;
        } else {
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
