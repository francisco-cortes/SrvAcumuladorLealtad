package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;

public class EjecutarSpEntradaDao {

    private static final Logger log = Logger.getLogger(EjecutarSpEntradaDao.class);
    private final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public void ejecutarSpEntrada(int idTipoCliente ,int importe, int sucursal, String fecha,
                                  String negocio, String tipoOperacion, int origenTransaccion,
                                  int paisId, String folioTransaccion, String idCliente,
                                  int idOperacion, String folioPremia,
                                  String PA_FCMENSAJE, String PA_FCUSUARIO, int PA_BANDERA){

        Connection conexion = null;
        CallableStatement declaracion = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ParametrerConfiguration.ORACLE_DATABASE_IN_STOREPROCEDURE);
            declaracion.setInt(1, idTipoCliente);
            declaracion.setInt(2, importe);
            declaracion.setInt(3, sucursal);
            declaracion.setString(4, fecha);
            declaracion.setString(5, negocio);
            declaracion.setString(6, tipoOperacion);
            declaracion.setInt(7, origenTransaccion);
            declaracion.setInt(8, paisId);
            declaracion.setString(9, folioTransaccion);
            declaracion.setString(10, idCliente);
            declaracion.setInt(11, idOperacion);
            declaracion.setString(12, folioPremia);
            declaracion.setString(13,PA_FCMENSAJE);
            declaracion.setString(14, PA_FCUSUARIO);
            declaracion.setInt(15, PA_BANDERA);
            declaracion.registerOutParameter(16, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(17, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            log.info(declaracion.getString(17));

            if(declaracion.getString(17) == null) {
                log.error("SPTRANSPUNTLEAL no ejecutado o respuesta nula");
            }

        }
        catch (Exception excepcion){
            log.error("Error en db : " + excepcion);
        }
        finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, null);
            }
            catch (Exception e) {
                log.error("no se pudo cerrar conexion: " + e);
            }
        }
    }

}
