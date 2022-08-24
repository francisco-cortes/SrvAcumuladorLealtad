package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.SpEntradaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ConsultarApiLealtadService;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    private static final ParametrerConfiguration configs = new ParametrerConfiguration();



    private static final ConsultaSalidaService salidaService = new ConsultaSalidaService();
    private static final SpEntradaService spEntrada = new SpEntradaService();
    private static final ObtenerLlavesService obtenerLlaves  = new ObtenerLlavesService();
    private static final CifrarDesifrarAesService cifrarService = new CifrarDesifrarAesService();
    private static final ConsultarApiLealtadService apiService = new ConsultarApiLealtadService();


    private static final Logger LOGGER = Logger.getLogger(MainController.class);


    public static void main(String[] args){
        String MMUSER_HOME = System.getenv("MMUSER_HOME");
        System.setProperty("MMUSER_HOME", MMUSER_HOME);
        configs.loadConfiguration();

        final int token = 0, idacceso = 1, simetrica1 = 2, simetrica2 = 3;
        String[] llavesAes = obtenerLlaves.getLlaves();

        List<CursorSpSalidaModel> responseDb = salidaService.consulta();

        final int mensaje = 0, folio = 1, bandera = 2;
        String[] respuestaApi;

        if (responseDb.size() > 0) {

            for(int i = 0; i < responseDb.size(); i ++){

                String idCliente = cifrarService.cifrar(responseDb.get(i).getFCIDCLIENTE(),
                        llavesAes[simetrica1], llavesAes[simetrica2]);

                String importe = cifrarService.cifrar(String.valueOf(responseDb.get(i).getFNIMPORTE()),
                        llavesAes[simetrica1], llavesAes[simetrica2]);

                //id tipo cliente y id operacion por defecto es 3;
                final int idTipoCliente = 3;
                final int idOperacion = 3;

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("idTipoCliente", idTipoCliente);
                parameters.put("idCliente", responseDb.get(i).getFCIDCLIENTE());
                parameters.put("idClienteCifrado", idCliente);
                parameters.put("importe", responseDb.get(i).getFNIMPORTE());
                parameters.put("importeCifrado", importe);
                parameters.put("sucursal", responseDb.get(i).getFNSUCURSAL());
                parameters.put("idOperacion", idOperacion);
                parameters.put("folioTransaccion", responseDb.get(i).getFCFOLIOTRANSACCION());
                parameters.put("fechaOperacion", responseDb.get(i).getFDFECHAOPERACION());
                parameters.put("negocio", responseDb.get(i).getFCNEGOCIO());
                parameters.put("tipoOperacion", responseDb.get(i).getFCTIPOOPERACION());
                parameters.put("origenTransaccion", responseDb.get(i).getFIORIGENTRANSACCION());
                parameters.put("paisId", responseDb.get(i).getFIPAISID());


                respuestaApi = apiService.consultaApi(llavesAes[idacceso], llavesAes[token],
                        parameters);

                spEntrada.guardarBase(parameters,respuestaApi[folio]
                        ,respuestaApi[mensaje], respuestaApi[bandera]);

            }
        }
        else {
            LOGGER.error("Respuesta vacia del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD \n"+
                    "No se realiza ninguna Accion");
        }
        LOGGER.info(" : SrvAcumuladorLealtad - Ejecutado");
    }
}
