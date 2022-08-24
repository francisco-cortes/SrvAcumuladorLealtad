package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FabricaDaoUtil {

    private static final Logger LOGGER = Logger.getLogger(FabricaDaoUtil.class);

    public Connection obtenerConexion() throws Exception{

        System.setProperty("oracle.jdbc.fanEnabled","false");
        Class.forName("oracle.jdbc.OracleDriver");

        return DriverManager.getConnection(ParametrerConfiguration.oracleDatabaseUrl,
                ParametrerConfiguration.oracleDatabaseU,
                ParametrerConfiguration.oracleDatabaseP);

    }

    public void cerrarConexion(Connection conexion, CallableStatement declaracionInvocable, ResultSet resultado)
            throws SQLException {

        if (!conexion.isClosed()) {

            conexion.close();

        }

        if(!resultado.isClosed()){

            resultado.close();

        }

        if (!declaracionInvocable.isClosed()){

            declaracionInvocable.close();

        }
    }
}
