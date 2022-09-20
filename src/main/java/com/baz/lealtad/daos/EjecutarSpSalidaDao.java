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

public class EjecutarSpSalidaDao {

    private static final FabricaDaoUtil fabricaDao = new FabricaDaoUtil();


    public List<CursorSpSalidaModel> ejecutarSpSalida(LogServicio log){
        log.setBegTimeMethod("EjecutarSpSalidaDao.ejecutarSpSalida", ParametrerConfiguration.SYSTEM_NAME);
        List<CursorSpSalidaModel> listaCursor = new ArrayList<>();

        final int cursorRespuesta = 1, mensajeRespuesta = 2, codigoRespuesta = 3;

        Connection conexion = null;
        CallableStatement declaracion = null;
        ResultSet resultSet = null;

        try {

            conexion = fabricaDao.obtenerConexion();

            declaracion = conexion.prepareCall(ParametrerConfiguration.getOracleDatabaseStoreprocedure());
            declaracion.registerOutParameter(cursorRespuesta, OracleTypes.REF_CURSOR);
            declaracion.registerOutParameter(mensajeRespuesta, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(codigoRespuesta, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            resultSet = (ResultSet) declaracion.getObject(1);

            if(resultSet != null){

                while (resultSet.next()){

                    CursorSpSalidaModel cursor = new CursorSpSalidaModel();

                    cursor.setFNIDTIPOCLIENTE(resultSet.getInt("FNIDTIPOCLIENTE"));
                    cursor.setFCIDCLIENTE(resultSet.getString("FCIDCLIENTE"));
                    cursor.setFNIMPORTE(resultSet.getDouble("FNIMPORTE"));
                    cursor.setFNSUCURSAL(resultSet.getInt("FNSUCURSAL"));
                    cursor.setFNIDOPERACION(resultSet.getInt("FNIDOPERACION"));
                    cursor.setFCFOLIOTRANSACCION(resultSet.getString("FCFOLIOTRANSACCION"));
                    cursor.setFDFECHAOPERACION(resultSet.getDate("FDFECHAOPERACION"));
                    cursor.setFCNEGOCIO(resultSet.getString("FCNEGOCIO"));
                    cursor.setFCTIPOOPERACION(resultSet.getString("FCTIPOOPERACION"));
                    cursor.setFIORIGENTRANSACCION(resultSet.getInt("FIORIGENTRANSACCION"));
                    cursor.setFIPAISID(resultSet.getInt("FIPAISID"));
                    listaCursor.add(cursor);
                }


            }
            else {

                log.mensaje("EjecutarSpSalidaDao.ejecutarSpSalida",
                  "SPPUNTOSLEALTAD no ejecutado o respuesta nula");

            }
        }
        catch (Exception excepcion){

            log.exepcion(excepcion, "ERROR en BD");

        }
        finally {

            try {
                fabricaDao.cerrarConexion(conexion, declaracion, resultSet);
            }
            catch (Exception e) {

                log.exepcion(e,"ERROR al cerrar Conexion SP 1");

            }

        }

        return listaCursor;

    }

}
