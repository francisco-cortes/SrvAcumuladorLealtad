package com.baz.lealtad.utils;

import jakarta.ws.rs.InternalServerErrorException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetodosGenericosUtil {

    public MetodosGenericosUtil() {
    }

    public static String obtenerJSONPorObjeto(Object objeto) {
        String jsonString = null;

        try {
            if (objeto != null) {
                jsonString = (new JSONObject(objeto)).toString();
            }

            return jsonString;
        } catch (RuntimeException var3) {
            throw new InternalServerErrorException("500 - C20001 - Ocurrió un incoveniente al procesar la solicitud. - Utils-obtenerJSONPorObjeto", var3);
        }
    }

    public static String limpiarCadenaConCaracteres(String texto) throws Exception {
        try {
            texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
            texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            return texto;
        } catch (Exception var2) {
            throw new InternalServerErrorException("500 - C20001 - Ocurrió un incoveniente al procesar la solicitud. - Utils-limpiarCadenaConCaracteres", var2);
        }
    }

    public static String asignarFormatoMonto(String monto, int decimales) {
        try {
            BigDecimal montoFormatear = new BigDecimal(monto);
            return String.valueOf(montoFormatear.setScale(decimales, RoundingMode.FLOOR));
        } catch (Exception var3) {
            throw new InternalServerErrorException("500 - C20001 - Ocurrió un incoveniente al procesar la solicitud. - Utils-asignarFormatoMonto", var3);
        }
    }

    public static String convertirInputStreamString(InputStream entrada) {
        try {
            BufferedInputStream bufferEntrada = new BufferedInputStream(entrada);
            ByteArrayOutputStream ArraySalida = new ByteArrayOutputStream();

            for(int resultado = bufferEntrada.read(); resultado != -1; resultado = bufferEntrada.read()) {
                byte resultadoByte = (byte)resultado;
                ArraySalida.write(resultadoByte);
            }

            return ArraySalida.toString("UTF-8");
        } catch (IOException var5) {
            throw new InternalServerErrorException("500 C20001 Ocurrió un incoveniente al procesar la solicitud - Utils-convertirInputStreamString", var5);
        }
    }

    public static String generarFechaTipoyyyyMMdd(String fecha) {
        fecha = fecha.replace(" ", "");
        String dia = "";
        String mes = "";
        String anio = "";
        String resultado = "";
        if (!fecha.isEmpty()) {
            String[] nuevaFecha = fecha.split("-");
            if (nuevaFecha != null && nuevaFecha.length == 3) {
                dia = nuevaFecha[0];
                mes = nuevaFecha[1];
                anio = nuevaFecha[2];
                resultado = anio + "-" + mes + "-" + dia;
            }
        }

        return resultado;
    }

    public static String obtenerFechaActualFormatoPersonalizado(String formato) {
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        String fechaPersonalizada = sdf.format(fechaActual);
        return fechaPersonalizada;
    }
}
