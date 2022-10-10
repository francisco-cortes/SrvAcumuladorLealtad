package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

/**
 * EjecutarSpEntradaDao.java
 * Descrpcion: Se encarga de realizar la conexion y el statement para el segundo sp
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class EjecutarSpEntradaDao {

  /*
  Crear conexion a base de dato con FabricaDao
   */
  private static final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();
  /*
  Constantes
   */
  private static final String SERVICE_NAME = "EjecutarSpEntradaDao.ejecutarSpEntrada";

  /**
   * Metodo ejecutarSPEntrada
   * Descrpcion: realiza la invocacion del sp
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   * Params: parameters(Map), folioPremia(String), mensaje(String), log(LogServisio
   **/

  public String ejecutarSpEntrada(Map<String, Object> parameters, String folioPremia,
                                String mensaje, String bandera, LogServicio log){
    String resp = "OPERACION EXITOSA";
    log.setBegTimeMethod(SERVICE_NAME, ParametrerConfiguration.SYSTEM_NAME);
    /*
    Constantes para el indice de los objetos del SP
     */
    final int ID_TIPO_CLIENTE = 1;
    final int IMPORTE = 2;
    final int SUCURSAL = 3;
    final int FECHA = 4;
    final int NEGOCIO = 5;
    final int TIPO_OPERACION = 6;
    final int ORIGEN_TRANSACCION = 7;
    final int PAIS_ID = 8;
    final int FOLIO_TRANSACCION = 9;
    final int ID_CLIENTE = 10;
    final int ID_OPERACION = 11;
    final int FOLIO = 12;
    final int COMENTARIO = 13;
    final int USUARIO = 14;
    final int FLAG = 15;
    final int RESPUESTA_1 = 16;
    final int RESPUESTA_2 = 17;

    /*
    inizializacion de conexion
     */
    Connection conexion = null;
    CallableStatement declaracion = null;

    try {
      /*
      crea conexion con fabrica dao
       */
      conexion = fabricaDao.obtenerConexion();
      /*
      crea declaracion statemen
       */
      declaracion = conexion.prepareCall(ParametrerConfiguration.getOracleDatabaseInStoreprocedure());

      //tipo cliente
      declaracion.setInt(ID_TIPO_CLIENTE, (Integer) parameters.get("idTipoCliente"));
      //importe
      declaracion.setDouble (IMPORTE, (Double) parameters.get("importe") );
      //sucursal
      declaracion.setInt(SUCURSAL, (Integer) parameters.get("sucursal"));
      //fecha de operacion
      declaracion.setDate(FECHA, (Date) parameters.get("fechaOperacion"));
      //negocio
      declaracion.setString(NEGOCIO, (String) parameters.get("negocio"));
      //tipo operacion
      declaracion.setString(TIPO_OPERACION, (String) parameters.get("tipoOperacion"));
      //origine transaccion
      declaracion.setInt(ORIGEN_TRANSACCION, (Integer) parameters.get("origenTransaccion"));
      //pais id
      declaracion.setInt(PAIS_ID, (Integer) parameters.get("paisId"));
      // folio Transaccion
      declaracion.setString(FOLIO_TRANSACCION, (String) parameters.get("folioTransaccion") );
      // id cliente
      declaracion.setString(ID_CLIENTE, (String) parameters.get("idCliente") );
      // id operacion
      declaracion.setInt(ID_OPERACION, (Integer) parameters.get("idOperacion"));
      // folio premia
      declaracion.setString(FOLIO, folioPremia);
      // mensaje de lealtad
      declaracion.setString(COMENTARIO, mensaje);
      // usuario de ultima modificacion
      declaracion.setString(USUARIO, ParametrerConfiguration.NOMBRE_JAR);
      // bien o mal
      declaracion.setInt(FLAG, Integer.parseInt(bandera));
      //salida uno
      declaracion.registerOutParameter(RESPUESTA_1, OracleTypes.VARCHAR);
      //salida dos
      declaracion.registerOutParameter(RESPUESTA_2, OracleTypes.VARCHAR);
      /*
      ejecuta el sp con la configuracion previa
       */
      declaracion.executeQuery();
      /*
      si la respuesta es null o no dice operacion EXITOSA se considera un fallo
       */
      if(declaracion.getString(RESPUESTA_2) == null ||
        !"OPERACION EXITOSA.".contains(declaracion.getString(RESPUESTA_2)) ) {
        log.mensaje(SERVICE_NAME,
          "ERROR SPTRANSPUNTLEAL no ejecutado o respuesta nula");
        resp = "OPERRACION FALLIDA";
      }

    }
    catch (ClassNotFoundException | SQLException e) {
      /*
      sql exepcion
       */
      log.exepcion(e,"Error en conexion a SP2");
    }
    finally {
      try {
        /*
        evitar conexiones nulas
         */
        assert conexion != null;
        assert declaracion != null;
        /*
        cerrar conexiones
         */
        fabricaDao.cerrarConexionSinResult(conexion, declaracion);
      }
      catch (SQLException e) {
        log.exepcion(e,"Error en conexion a SP2");
      }
    }
    return resp;
  }

}
