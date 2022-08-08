package com.baz.lealtad.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorSpSalidaModel {

    private int FNIDTIPOCLIENTE;
    private String FCIDCLIENTE;
    private int FNIMPORTE;
    private int FNSUCURSAL;
    private int FNIDOPERACION;
    private String FCFOLIOTRANSACCION;
    private String FDFECHAOPERACION;
    private String FCNEGOCIO;
    private String FCTIPOOPERACION;
    private int FIORIGENTRANSACCION;
    private int FIPAISID;


}
