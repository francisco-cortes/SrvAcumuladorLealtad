package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.sql.*;

public class FabricaDaoUtil {

    private static final Logger logger = Logger.getLogger(FabricaDaoUtil.class);

    public Connection obtenerConexion() throws Exception{

        System.setProperty("oracle.jdbc.fanEnabled","false");
        Class.forName("oracle.jdbc.OracleDriver");
        Connection conexion = DriverManager.getConnection(ParametrerConfiguration.ORACLE_DATABASE_URL,
                ParametrerConfiguration.ORACLE_DATABASE_U,
                ParametrerConfiguration.ORACLE_DATABASE_P);

        return conexion;
    }

    public void cerrarConexion(Connection conexion, CallableStatement declaracionInvocable, ResultSet resultado) throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            logger.info("Conexion cerrada");
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
