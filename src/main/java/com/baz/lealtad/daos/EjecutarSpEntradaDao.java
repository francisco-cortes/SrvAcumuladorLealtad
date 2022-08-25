package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.Map;

public class EjecutarSpEntradaDao {

    private static final Logger LOGGER = Logger.getLogger(EjecutarSpEntradaDao.class);
    private static final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public void ejecutarSpEntrada(Map<String, Object> parameters, String folioPremia,
                                  String mensaje, String bandera){

        final int idTipoCliente = 1, importe = 2 , sucursal = 3, fecha = 4,
                negocio = 5, tipoOperacion = 6, origenTransaccion = 7,
                paisId = 8, folioTransaccion = 9, idCliente = 10, idOperacion = 11,
                folio = 12, comentario = 13, usuario = 14, flag = 15,
                respuesta1 = 16, respuesta2 = 17;

        Connection conexion = null;

        CallableStatement declaracion = null;

        try {

            conexion = fabricaDao.obtenerConexion();

            declaracion = conexion.prepareCall(ParametrerConfiguration.oracleDatabaseInStoreprocedure);


            declaracion.setInt(idTipoCliente, (Integer) parameters.get("idTipoCliente"));//tipo cliente
            declaracion.setInt(importe, (Integer) parameters.get("importe") );//importe
            declaracion.setInt(sucursal, (Integer) parameters.get("sucursal"));//sucursal
            declaracion.setDate(fecha, (Date) parameters.get("fechaOperacion"));
            declaracion.setString(negocio, (String) parameters.get("negocio"));//negocio
            declaracion.setString(tipoOperacion, (String) parameters.get("tipoOperacion"));//tipo operacion
            declaracion.setInt(origenTransaccion, (Integer) parameters.get("origenTransaccion"));//origine transaccion
            declaracion.setInt(paisId, (Integer) parameters.get("paisId"));//pais id
            declaracion.setString(folioTransaccion, (String) parameters.get("folioTransaccion") );// folio Transaccion
            declaracion.setString(idCliente, (String) parameters.get("idCliente") );// id cliente
            declaracion.setInt(idOperacion, (Integer) parameters.get("idOperacion"));// id operacion
            declaracion.setString(folio, folioPremia);// folio premia
            declaracion.setString(comentario, mensaje);
            declaracion.setString(usuario, ParametrerConfiguration.NOMBRE_JAR);
            declaracion.setInt(flag, Integer.parseInt(bandera));
            declaracion.registerOutParameter(respuesta1, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(respuesta2, OracleTypes.VARCHAR);

            declaracion.executeQuery();

            if(declaracion.getString(respuesta2) == null ||
                    !declaracion.getString(respuesta2).contains("OPERACION EXITOSA.") ) {
                LOGGER.error("SPTRANSPUNTLEAL no ejecutado o respuesta nula");

            }

        }
        catch (Exception excepcion){
            LOGGER.error("Error en db : " + excepcion);
        }
        finally {
            try {
                assert conexion != null;
                conexion.close();
                assert declaracion != null;
                declaracion.close();
            }
            catch (Exception e) {
                LOGGER.error("no se pudo cerrar conexion: " + e);
            }
        }
    }

}
