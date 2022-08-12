package com.baz.lealtad.daos;

import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;

public class EjecutarSpEntradaDao {

    private static final Logger logger = Logger.getLogger(EjecutarSpEntradaDao.class);
    private final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public void ejecutarSpEntrada(int PA_FNIDTIPOCLIENTE, int PA_FNIMPORTE, int PA_FNSUCURSAL,
                                  String PA_FDFECHAOPERACION, String PA_FCNEGOCIO, String PA_FCTIPOOPERACION,
                                  int PA_FIORIGENTRANSACCION, int PA_FIPAISID, String PA_FCFOLIOTRANSACCION,
                                  String PA_FCIDCLIENTE, int PA_FNIDOPERACION ,String PA_FCFOLIO,
                                  String PA_FCMENSAJE, String PA_FCUSUARIO, int PA_BANDERA){
        Connection conexion = null;
        CallableStatement declaracion = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ConstantesUtil.ORACLE_DATABASE_IN_STOREPROCEDURE);
            declaracion.setInt(1, PA_FNIDTIPOCLIENTE);
            declaracion.setInt(2, PA_FNIMPORTE);
            declaracion.setInt(3, PA_FNSUCURSAL);
            declaracion.setString(4, PA_FDFECHAOPERACION);//fecha
            declaracion.setString(5, PA_FCNEGOCIO);
            declaracion.setString(6, PA_FCTIPOOPERACION);
            declaracion.setInt(7, PA_FIORIGENTRANSACCION);
            declaracion.setInt(8, PA_FIPAISID);
            declaracion.setString(9, PA_FCFOLIOTRANSACCION);
            declaracion.setString(10, PA_FCIDCLIENTE);
            declaracion.setInt(11, PA_FNIDOPERACION);
            declaracion.setString(12, PA_FCFOLIO);
            declaracion.setString(13,PA_FCMENSAJE);
            declaracion.setString(14, PA_FCUSUARIO);
            declaracion.setInt(15, PA_BANDERA);
            declaracion.registerOutParameter(16, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(17, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            if(declaracion.getString(17) == null) {
                logger.error("SPTRANSPUNTLEAL no ejecutado o respuesta nula");
            }

        }catch (Exception excepcion){
            logger.error("Error en db : " + excepcion);
        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, null);
            } catch (Exception e) {
                logger.error("no se pudo cerrar conexion: " + e);
            }
        }
    }

}
