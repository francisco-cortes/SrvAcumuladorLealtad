package com.baz.lealtad.controllers;

import com.baz.lealtad.models.CursorSpSalidaModel;
import com.baz.lealtad.service.ConsultaSalidaService;
import com.baz.lealtad.service.SpEntradaService;
import com.baz.lealtad.service.ObtenerLlavesService;
import com.baz.lealtad.service.CifrarDesifrarAesService;
import com.baz.lealtad.service.ConsultarApiLealtadService;
import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.utils.ClienteUnicoParserUtil;
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


        final String MMUSER_HOME = System.getenv("MMUSER_HOME");
        System.setProperty("MMUSER_HOME", MMUSER_HOME);
        configs.loadConfiguration();

        final int TOKEN = 0;
        final int IDACCESO = 1;
        final int SIMETRICA_1 = 2;
        final int SIMETRICA_2 = 3;
        String[] llavesAes = obtenerLlaves.getLlaves();

        List<CursorSpSalidaModel> responseDb = salidaService.consulta();

        final int MENSAJE = 0;
        final int FOLIO = 1;
        final int BANDERA = 2;
        String[] respuestaApi;

        //------------
        /*int idTipoCliente = 3;
        String idC = "176643425";
        String fol = "MC-3425";
        int idclienteTam = idC.length();

        String idClienteParseado = ClienteUnicoParserUtil.parsear(idC);

        int importeRedondeado =  (int) Math.round(900.02);

        String idCliente = cifrarService.cifrar(idClienteParseado,
          llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2]);

        String importe = cifrarService.cifrar(String.valueOf(importeRedondeado),
          llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2]);

        //id tipo cliente y id operacion por defecto es 3;
        if(idclienteTam > 10){
                    idTipoCliente = 3;
                }
                else{
                    idTipoCliente = 5;
                }
        final int ID_OPERACION = 3;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idTipoCliente", idTipoCliente);
        parameters.put("idCliente", idC);
        parameters.put("idClienteCifrado", idCliente);
        parameters.put("idClienteParseado", idClienteParseado);
        parameters.put("importe", 100.01);
        parameters.put("importeCifrado", importe);
        parameters.put("importeRedondo", importeRedondeado);
        parameters.put("sucursal", "200");
        parameters.put("idOperacion", ID_OPERACION);
        parameters.put("folioTransaccion", fol);
        parameters.put("fechaOperacion", "");
        parameters.put("negocio", "DEX");
        parameters.put("tipoOperacion", "ENVIO");
        parameters.put("origenTransaccion", "3");
        parameters.put("paisId", "1");

        respuestaApi = apiService.consultaApi(llavesAes[IDACCESO], llavesAes[TOKEN],
          parameters);*/
        //---------------------------
        if (responseDb.size() > 0) {

            int fallidosLealtad = 0;
            int idTipoCliente = 3;

            for(int i = 0; i < responseDb.size(); i ++){

                int idclienteTam = responseDb.get(i).getFCIDCLIENTE().length();

                String idClienteParseado = ClienteUnicoParserUtil.parsear(responseDb.get(i).getFCIDCLIENTE());

                int importeRedondeado =  (int) Math.round(responseDb.get(i).getFNIMPORTE());

                String idCliente = cifrarService.cifrar(idClienteParseado,
                        llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2]);

                String importe = cifrarService.cifrar(String.valueOf(importeRedondeado),
                        llavesAes[SIMETRICA_1], llavesAes[SIMETRICA_2]);

                //id tipo cliente y id operacion por defecto es 3;
                if(idclienteTam > 10){
                    idTipoCliente = 3;
                }
                else{
                    idTipoCliente = 5;
                }

                final int ID_OPERACION = 3;

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("idTipoCliente", idTipoCliente);
                parameters.put("idCliente", responseDb.get(i).getFCIDCLIENTE());
                parameters.put("idClienteCifrado", idCliente);
                parameters.put("idClienteParseado", idClienteParseado);
                parameters.put("importe", responseDb.get(i).getFNIMPORTE());
                parameters.put("importeCifrado", importe);
                parameters.put("importeRedondo", importeRedondeado);
                parameters.put("sucursal", responseDb.get(i).getFNSUCURSAL());
                parameters.put("idOperacion", ID_OPERACION);
                parameters.put("folioTransaccion", responseDb.get(i).getFCFOLIOTRANSACCION());
                parameters.put("fechaOperacion", responseDb.get(i).getFDFECHAOPERACION());
                parameters.put("negocio", responseDb.get(i).getFCNEGOCIO());
                parameters.put("tipoOperacion", responseDb.get(i).getFCTIPOOPERACION());
                parameters.put("origenTransaccion", responseDb.get(i).getFIORIGENTRANSACCION());
                parameters.put("paisId", responseDb.get(i).getFIPAISID());

                respuestaApi = apiService.consultaApi(llavesAes[IDACCESO], llavesAes[TOKEN],
                        parameters);

                spEntrada.guardarBase(parameters,respuestaApi[FOLIO]
                        ,respuestaApi[MENSAJE], respuestaApi[BANDERA]);

                if(respuestaApi[BANDERA].equals("1")){
                    fallidosLealtad ++;
                }

            }

            if (fallidosLealtad > 0){
                LOGGER.error("Hubo " + fallidosLealtad + " respuestas negativas de API Lealtad");
                System.exit(ParametrerConfiguration.ERROR_OR_EXCEPTION);
            }
            else {
                System.exit(0);
            }
        }
        else {

            LOGGER.error("Respuesta vacia del SP C3MULTIMARCAS.PAPLANLEALTAD01.SPPUNTOSLEALTAD \n"+
                    "No se realiza ninguna Accion");
            System.exit(ParametrerConfiguration.CANT_LOAD_SOMETHING);

        }
    }
}
