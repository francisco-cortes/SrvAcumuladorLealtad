package com.baz.lealtad.daos;

import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;

public class EjecutarSpSalidaDao {

    private static final Logger logger = Logger.getLogger(EjecutarSpSalidaDao.class);
    private FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public SpSalidaResponseDto ejecutarSpSalida(String ejemplo){
        SpSalidaResponseDto cursor = new SpSalidaResponseDto();
        Connection conexion = null;
        CallableStatement declaracion = null;
        ResultSet resultSet = null;
        BigDecimal tipoDato = BigDecimal.valueOf(1);

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ConstantesUtil.ORACLE_DATABASE_STOREPROCEDURE);
            declaracion.setString(1, ejemplo);
            declaracion.setBigDecimal(2, tipoDato);
            declaracion.registerOutParameter(3, OracleTypes.REF_CURSOR);
            declaracion.registerOutParameter(4, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(5, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            resultSet = (ResultSet) declaracion.getObject(3);
            if(resultSet != null){
                logger.info("Sp ejecutado");
            }else {
                logger.error("Sp no ejecutado o respuesta nula");
            }

            while (resultSet.next()){
                cursor.setFNREGISTROS(resultSet.getBigDecimal("FNREGISTROS"));
                cursor.setFNTOTAL(resultSet.getBigDecimal("FNTOTAL"));
                cursor.setFNUSUARIO(resultSet.getString("FCUSUARIO"));
            }


        }catch (Exception excepcion){
            logger.error("error en db");
            excepcion.printStackTrace();
        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cursor;
    }

}
