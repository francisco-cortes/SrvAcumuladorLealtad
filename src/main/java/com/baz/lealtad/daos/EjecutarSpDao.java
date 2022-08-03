package com.baz.lealtad.daos;

import com.baz.lealtad.dtos.DatabaseResponseDto;
import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;

import java.sql.*;

public class EjecutarSpDao {

    private FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public DatabaseResponseDto ejecutarSp(){
        DatabaseResponseDto respuestaSp = new DatabaseResponseDto();
        Connection conexion = null;
        CallableStatement declaracion = null;
        ResultSet resultSet = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ConstantesUtil.ORACLE_DATABASE_STOREPROCEDURE);
            //declaracion.setBigDecimal(2, tipoDato);
            //declaracion.registerOutParameter(3, OracleTypes.REF_CURSOR);
            declaracion.executeQuery();
            resultSet = (ResultSet) declaracion.getObject(3);
        }catch (Exception excepcion){
            respuestaSp.setRespuestaXD("sin conexion base");
            excepcion.printStackTrace();
        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuestaSp;
    }

}
