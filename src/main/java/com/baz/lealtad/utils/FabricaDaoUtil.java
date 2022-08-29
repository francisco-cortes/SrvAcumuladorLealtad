package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FabricaDaoUtil {

    public Connection obtenerConexion() throws Exception{

        System.setProperty("oracle.jdbc.fanEnabled","false");
        Class.forName("oracle.jdbc.OracleDriver");

        return DriverManager.getConnection(ParametrerConfiguration.getOracleDatabaseUrl(),
                ParametrerConfiguration.getOracleDatabaseU(),
                ParametrerConfiguration.getOracleDatabaseP());

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
