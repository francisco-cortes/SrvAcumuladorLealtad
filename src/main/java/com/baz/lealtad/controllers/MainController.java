package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.SpEntradaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ConsultarApiLealtadService;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.util.List;

public class MainController {

    private static final ParametrerConfiguration configs = new ParametrerConfiguration();



    private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final SpEntradaService spEntrada = new SpEntradaService();
    private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    private static final ConsultarApiLealtadService apiService = new ConsultarApiLealtadService();


    private static final Logger log = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        //String MMUSER_HOME = System.getenv("MMUSER_HOME");
        //System.setProperty("MMUSER_HOME", MMUSER_HOME);
        //configs.loadConfiguration();

        log.info("Inicia: "+ ParametrerConfiguration.NOMBRE_JAR);

        int token = 0, idacceso = 1, simetrica1 = 2, simetrica2 = 3;
        String[] llavesAes = obtenerLlaves.getLlaves();

        List<CursorSpSalidaModel> responseDb = salidaService.consulta();

        String[] respuestaApi;

        if (responseDb.size() > 0) {

            log.info("Se obtuvieron: " + responseDb.size() + " del primer sp");

            for(int i = 0; i < responseDb.size(); i ++){

                /**
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("importe", responseDb.get(i).getFNIMPORTE());
                parameters.put("sucursal", responseDb.get(i).getFNSUCURSAL());
                parameters.put("fechaOperacion", responseDb.get(i).getFDFECHAOPERACION());
                parameters.put("negocio", responseDb.get(i).getFCNEGOCIO());
                parameters.put("tipoOperacion", responseDb.get(i).getFCTIPOOPERACION());
                parameters.put("origenTransaccion", responseDb.get(i).getFIORIGENTRANSACCION());
                parameters.put("paisId", responseDb.get(i).getFIPAISID());
                parameters.put("folioTransaccion", responseDb.get(i).getFCFOLIOTRANSACCION());
                parameters.put("idCliente", responseDb.get(i).getFCIDCLIENTE());
                 **/

                String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                        llavesAes[simetrica1], llavesAes[simetrica2]);

                String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                        llavesAes[simetrica1], llavesAes[simetrica2]);


                log.info("Cifrado idCleinte: " + idCliente + "\n Cifrado improte:" + importe);

                //id tipo cliente y id operacion por defecto es 3;
                int idTipoCliente = 3;
                int idOperacion = 3;

                respuestaApi = apiService.consultaApi(llavesAes[token], llavesAes[idacceso],
                        idTipoCliente,idCliente, importe,
                        responseDb.get(i).getFNSUCURSAL(), idOperacion,
                        responseDb.get(i).getFCFOLIOTRANSACCION());

                spEntrada.guardarBase(responseDb.get(i).getFNIMPORTE(),
                        responseDb.get(i).getFNSUCURSAL(),responseDb.get(i).getFDFECHAOPERACION(),
                        responseDb.get(i).getFCNEGOCIO(),responseDb.get(i).getFCTIPOOPERACION(),
                        responseDb.get(i).getFIORIGENTRANSACCION(), responseDb.get(i).getFIPAISID(),
                        responseDb.get(i).getFCFOLIOTRANSACCION(),responseDb.get(i).getFCIDCLIENTE(),
                        respuestaApi[1],respuestaApi[0],
                        respuestaApi[2]);
            }
        }
        else {
            log.error("Respuesta nula o vacia del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD \n"+
                    "No se realiza ninguna Accion");
        }
        log.info(" :FIN!");
    }
}
