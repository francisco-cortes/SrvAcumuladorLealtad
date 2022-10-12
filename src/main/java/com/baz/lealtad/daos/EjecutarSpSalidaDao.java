package com.baz.lealtad.daos;

import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * EjecutarSpSalidaDao.java
 * Descrpcion: Clase  para ejecutar el primer SP
 * Autor: Francisco Javier Cortes Torres, Desarrollador
 **/

public class EjecutarSpSalidaDao {

  /*
  objeto de conexion a base de daots
   */
  private static final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();


  /**
   * ParameterConfiguracion.java
   * Descrpcion: Clase  para cargar las configuraciones o propiedades par el funcionamiento del jar
   * Autor: Francisco Javier Cortes Torres, Desarrollador
   **/
  public List<CursorSpSalidaModel> ejecutarSpSalida(LogServicio log){
    List<CursorSpSalidaModel> listaCursor = new ArrayList<>();
    /*
    Constantes
     */
    final int CURSOR_RESPUESTA = 1;
    final int MENSAJE_RESPUESTA = 2;
    final int CODIGO_RESPUESTA = 3;
    /*
    inicializacion de conexion
     */
    Connection conexion = null;
    CallableStatement declaracion = null;
    ResultSet resultSet = null;


    try {
      /*
      conexion obtenida atravez de fabrica dao
       */
      conexion = fabricaDao.obtenerConexion();
      /*
      creacion de declaracio estatemen
       */
      declaracion = conexion.prepareCall(ParametrerConfiguration.getOracleDatabaseStoreprocedure());
      declaracion.registerOutParameter(CURSOR_RESPUESTA, OracleTypes.REF_CURSOR);
      declaracion.registerOutParameter(MENSAJE_RESPUESTA, OracleTypes.VARCHAR);
      declaracion.registerOutParameter(CODIGO_RESPUESTA, OracleTypes.VARCHAR);
      /*
      ejecuionde query
       */
      declaracion.executeQuery();
      /*
      obtencion de cursor
       */
      resultSet = (ResultSet) declaracion.getObject(1);
      /*
      si el resulset es null se considera error
       */
      if(resultSet != null){
        /*
        iteracion de el cursor para llenado de data class
         */
        while (resultSet.next()){
          //instacia del modelo data class
          CursorSpSalidaModel cursor = new CursorSpSalidaModel();
          // id tipo cliente
          cursor.setFnIdTipoCliente(resultSet.getInt("FNIDTIPOCLIENTE"));
          // id cliente
          cursor.setFcIdCliente(resultSet.getString("FCIDCLIENTE"));
          // importe
          cursor.setFnImporte(resultSet.getDouble("FNIMPORTE"));
          // succursal
          cursor.setFnSucursal(resultSet.getInt("FNSUCURSAL"));
          // operacion
          cursor.setFnIdOperacion(resultSet.getInt("FNIDOPERACION"));
          // mtcn
          cursor.setFcFolioTransaccion(resultSet.getString("FCFOLIOTRANSACCION"));
          // fecha de operacion
          cursor.setFdFechaOperacion(resultSet.getDate("FDFECHAOPERACION"));
          // oicogen
          cursor.setFcNegocio(resultSet.getString("FCNEGOCIO"));
          // tipo de operacion
          cursor.setFcTipoOperacion(resultSet.getString("FCTIPOOPERACION"));
          // origen
          cursor.setFiOrigenTransaccion(resultSet.getInt("FIORIGENTRANSACCION"));
          // pais
          cursor.setFiPaisId(resultSet.getInt("FIPAISID"));
          listaCursor.add(cursor);
        }


      }
      else {
        /*
        result set vacio
         */
        log.mensaje("EjecutarSpSalidaDao",
          "SPPUNTOSLEALTAD no ejecutado o respuesta nula");

      }
    }
    catch (Exception excepcion){
      /*
      Sql exepcion
       */
      log.exepcion(excepcion, "ERROR en BD");

    }
    finally {

      try {
        /*
        cerrar conexiones
         */
        fabricaDao.cerrarConexion(conexion, declaracion, resultSet);
      }
      catch (Exception e) {
        /*
        sql exepcion
         */
        log.exepcion(e,"ERROR al cerrar Conexion SP 1");

      }

    }

    return listaCursor;

  }

}
