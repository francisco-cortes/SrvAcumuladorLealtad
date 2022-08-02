package com.baz.lealtad.utils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FabricaDaoUtil {

    private Connection conexion;

    public Connection obtenerConexion() throws Exception{

        Class.forName("oracle.jdbc.OracleDriver");
        conexion = DriverManager.getConnection(ConstantesUtil.ORACLE_DATABASE_URL,
                ConstantesUtil.ORACLE_DATABASE_USERNAME,
                ConstantesUtil.ORACLE_DATABASE_URL);

        if (conexion != null) {System.out.println("Connected with ");}

        return conexion;
    }

    public void cerrarConexion(Connection conexion, CallableStatement declaracionInvocable, ResultSet resultado) throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }

        if(resultado != null){
            resultado.close();
        }

        if (declaracionInvocable != null){
            declaracionInvocable.close();
        }
    }
}
