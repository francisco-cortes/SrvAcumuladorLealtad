package com.baz.lealtad.daos;

import com.baz.lealtad.dtos.SpSalidaResponseDto;
import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.utils.ConstantesUtil;
import com.baz.lealtad.utils.FabricaDaoUtil;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjecutarSpSalidaDao {

    private static final Logger logger = Logger.getLogger(EjecutarSpSalidaDao.class);
    private FabricaDaoUtil fabricaDao = new FabricaDaoUtil();

    public List<CursorSpSalidaModel> ejecutarSpSalida(){
        List<CursorSpSalidaModel> listaCursor = new ArrayList<>();
        Connection conexion = null;
        CallableStatement declaracion = null;
        ResultSet resultSet = null;

        try {
            conexion = fabricaDao.obtenerConexion();
            declaracion = conexion.prepareCall(ConstantesUtil.ORACLE_DATABASE_STOREPROCEDURE);
            declaracion.registerOutParameter(1, OracleTypes.REF_CURSOR);
            declaracion.registerOutParameter(2, OracleTypes.VARCHAR);
            declaracion.registerOutParameter(3, OracleTypes.VARCHAR);
            declaracion.executeQuery();

            resultSet = (ResultSet) declaracion.getObject(1);
            if(resultSet != null){
                logger.info("Sp ejecutado");
            }else {
                logger.error("Sp no ejecutado o respuesta nula");
            }

            while (resultSet.next()){
                CursorSpSalidaModel cursor = new CursorSpSalidaModel();
                cursor.setFNIDTIPOCLIENTE(resultSet.getInt("FNIDTIPOCLIENTE"));
                cursor.setFCIDCLIENTE(resultSet.getString("FCIDCLIENTE"));
                cursor.setFNIMPORTE(resultSet.getInt("FNIMPORTE"));
                cursor.setFNSUCURSAL(resultSet.getInt("FNSUCURSAL"));
                cursor.setFNIDOPERACION(resultSet.getInt("FNIDOPERACION"));
                cursor.setFCFOLIOTRANSACCION(resultSet.getString("FCFOLIOTRANSACCION"));
                cursor.setFDFECHAOPERACION(resultSet.getString("FDFECHAOPERACION"));
                cursor.setFCNEGOCIO(resultSet.getString("FCNEGOCIO"));
                cursor.setFCTIPOOPERACION(resultSet.getString("FCTIPOOPERACION"));
                cursor.setFIORIGENTRANSACCION(resultSet.getInt("FIORIGENTRANSACCION"));
                cursor.setFIPAISID(resultSet.getInt("FIPAISID"));
                listaCursor.add(cursor);
            }

            //System.out.println(listaCursor);
            //System.out.println(listaCursor.get(1).getFNIMPORTE());
            logger.info(declaracion.getString(3));

        }catch (Exception excepcion){
            logger.error("error en db");
            excepcion.printStackTrace();
        }finally {
            try {
                fabricaDao.cerrarConexion(conexion, declaracion, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaCursor;
    }

}
