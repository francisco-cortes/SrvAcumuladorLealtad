package com.baz.lealtad.utils;

import org.apache.log4j.Logger;

import java.sql.*;

public class FabricaDaoUtil {

    private static final Logger logger = Logger.getLogger(FabricaDaoUtil.class);

    public Connection obtenerConexion() throws Exception{

        System.setProperty("oracle.jdbc.fanEnabled","false");
        Class.forName("oracle.jdbc.OracleDriver");
        Connection conexion = DriverManager.getConnection(ConstantesUtil.ORACLE_DATABASE_URL,
                ConstantesUtil.ORACLE_DATABASE_USERNAME,
                ConstantesUtil.ORACLE_DATABASE_PASSWORD);

        if (conexion == null) {
            logger.error("No se pudo crear conexión a - SFBDDEV");
        }

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
