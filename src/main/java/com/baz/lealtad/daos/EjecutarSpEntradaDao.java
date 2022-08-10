package com.baz.lealtad.daos;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

public class EjecutarSpEntradaDao {

    private static final Logger logger = Logger.getLogger(EjecutarSpEntradaDao.class);
    private FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public void ejecutarSpEntrada(int PA_FNIDTIPOCLIENTE, int PA_FNIMPORTE, int PA_FNSUCURSAL,
                                  String PA_FDFECHAOPERACION, String PA_FCNEGOCIO, String PA_FCTIPOOPERACION,
                                  int PA_FIORIGENTRANSACCION, int PA_FIPAISID, String PA_FCFOLIOTRANSACCION,
                                  String PA_FCIDCLIENTE, String PA_FCFOLIOPREMIA, String PA_FCIDUSUARIOTPREMIA,
                                  String PA_FDFECHAHORAOPERACION, String PA_FCTIPO, int PA_FNIDOPERACION,
                                  int PA_FNPUNTOSREDIMIR, int PA_FNTOTALPUNTOSANTERIOR, int PA_FNTOTALPUNTOSFINAL,
                                  int PA_FNPUNTOSRECOMPENSA, String PA_FCCOMENTARIOS, String PA_FCUSUARIO,
                                  int PA_BANDERA){
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
            declaracion.setString(11, PA_FCFOLIOPREMIA);
            declaracion.setString(12, PA_FCIDUSUARIOTPREMIA);
            declaracion.setString(13, PA_FDFECHAHORAOPERACION);//fechas
            declaracion.setString(14, PA_FCTIPO);
            declaracion.setInt(15, PA_FNIDOPERACION);
            declaracion.setInt(16, PA_FNPUNTOSREDIMIR);
            declaracion.setInt(17, PA_FNTOTALPUNTOSANTERIOR);
            declaracion.setInt(18, PA_FNTOTALPUNTOSFINAL);
            declaracion.setInt(19, PA_FNPUNTOSRECOMPENSA);
            declaracion.setString(20, PA_FCCOMENTARIOS);
            declaracion.setString(21, PA_FCUSUARIO);
            declaracion.setInt(22, PA_BANDERA);
            declaracion.registerOutParameter(23, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(24, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            if(declaracion.getString(24) != null){
                logger.info("Sp ejecutado");
                logger.info(declaracion.getString(24));
            }else {
                logger.error("Sp no ejecutado o respuesta nula");
            }

        }catch (Exception excepcion){
            logger.error("error en db");
            excepcion.printStackTrace();
        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
