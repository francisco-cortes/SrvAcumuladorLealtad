package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Map;

public class EjecutarSpEntradaDao {

    private static final Logger log = Logger.getLogger(EjecutarSpEntradaDao.class);
    private final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public void ejecutarSpEntrada(Map<String, Object> parameters, String fecha,
                                  String folioPremia, String mensaje, String bandera){

        Connection conexion = null;
        CallableStatement declaracion = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ParametrerConfiguration.ORACLE_DATABASE_IN_STOREPROCEDURE);
            declaracion.setInt(1, (Integer) parameters.get("idTipoCliente"));//tipo cliente
            declaracion.setInt(2, (Integer) parameters.get("importe") );//importe
            declaracion.setInt(3, (Integer) parameters.get("sucursal"));//sucursal
            declaracion.setString(4, fecha);//fecha
            declaracion.setString(5, (String) parameters.get("negocio"));//negocio
            declaracion.setString(6, (String) parameters.get("tipoOperacion"));//tipo operacion
            declaracion.setInt(7, (Integer) parameters.get("origenTransaccion"));//origine transaccion
            declaracion.setInt(8, (Integer) parameters.get("paisId"));//pais id
            declaracion.setString(9, (String) parameters.get("folioTransaccion") );// folio Transaccion
            declaracion.setString(10, (String) parameters.get("idCliente") );// id cliente
            declaracion.setInt(11, (Integer) parameters.get("idOperacion"));// id operacion
            declaracion.setString(12, folioPremia);// folio premia
            declaracion.setString(13,mensaje);
            declaracion.setString(14, ParametrerConfiguration.NOMBRE_JAR);
            declaracion.setInt(15, Integer.parseInt(bandera));
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
