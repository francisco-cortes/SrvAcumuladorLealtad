package com.baz.lealtad.utils;

import com.baz.lealtad.configuration.ParametrerConfiguration;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * FabricaDaoUtil
 * Descrpcion: Construye y cierra conexiones a base de datos
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/
public class   FabricaDaoUtil {
  /**
   * obtenerConexion
   * Descrpcion: creoa le objeto SQL.Connection
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: paisCero(String)
   * returns: String
   **/
  public Connection obtenerConexion() throws ClassNotFoundException, SQLException {
    /*
    propiedades necesarias
     */
    System.setProperty("oracle.jdbc.fanEnabled","false");
    Class.forName("oracle.jdbc.OracleDriver");
    /*
    cadena de coenxion otrogada por servicios de base datos
     */
    String cadenaConexion = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON) " +
      "(ADDRESS=(PROTOCOL=TCP)(HOST=" + ParametrerConfiguration.getOracleDatabaseIp() + ")(PORT="
      + ParametrerConfiguration.getOracleDatabasePort() + "))" +
      ")(CONNECT_DATA=(SERVICE_NAME=" + ParametrerConfiguration.getOracleDatabaseName() +
      ")(SERVER=DEDICATED)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETIRES=180)(DELAY=5))))";
    /*
    construye y retorna el objeto connection a treves del drive manager
     */
    return DriverManager.getConnection(cadenaConexion,
      ParametrerConfiguration.getOracleDatabaseU(),
      ParametrerConfiguration.getOracleDatabaseP());
  }

  /**
   * cerrarConexion
   * Descrpcion: Cierra y termina los procesos resultantes de la consulta a base de datos
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: Connection, Statement, ResultSet
   * returns: Void
   **/
  public void cerrarConexion(Connection conexion, Statement declaracionInvocable, ResultSet resultado)
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
  /**
   * cerrarConexionSinResult
   * Descrpcion: Cierra y termina los procesos resultantes de la consulta a base de datos sin result set ya que hay
   * consulta que no generan y asi se evita un fallo por excepcion
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * params: Connection, Statement
   * returns: Void
   **/
  public void cerrarConexionSinResult(Connection conexion, Statement declaracionInvocable)
    throws SQLException {

    if (!conexion.isClosed()) {
      conexion.close();
    }

    if (!declaracionInvocable.isClosed()){
      declaracionInvocable.close();
    }
  }
}
