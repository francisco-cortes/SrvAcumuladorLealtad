package com.baz.lealtad.daos;

import com.baz.lealtad.models.DatabaseResponseModel;
import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;

import java.sql.*;

public class ConexionBaseDao {

    FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public DatabaseResponseModel ejecutarSp(){
        DatabaseResponseModel respuestaSp = new DatabaseResponseModel();
        Connection conexion = null;
        CallableStatement declaracion = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ConstantesUtil.ORACLE_DATABASE_STOREPROCEDURE);
            //declaracion.setBigDecimal(2, tipoDato);
            //declaracion.registerOutParameter(3, OracleTypes.REF_CURSOR);
            declaracion.executeQuery();
            ResultSet resultSet = (ResultSet) declaracion.getObject(3);
        }catch (Exception excepcion){

        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuestaSp;
    }

}
