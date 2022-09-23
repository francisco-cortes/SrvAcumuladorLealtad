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

        String cadenaConexion = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON) " +
          "(ADDRESS=(PROTOCOL=TCP)(HOST=" + ParametrerConfiguration.getOracleDatabaseIp() + ")(PORT=" + ParametrerConfiguration.getOracleDatabasePort() + "))" +
          ")(CONNECT_DATA=(SERVICE_NAME=" + ParametrerConfiguration.getOracleDatabaseName() +
          ")(SERVER=DEDICATED)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETIRES=180)(DELAY=5))))";

        return DriverManager.getConnection(cadenaConexion,
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
