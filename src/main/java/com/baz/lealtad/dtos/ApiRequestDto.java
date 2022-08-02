package com.baz.lealtad.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequestDto {
    Short idTipoCliente;
    String idCliente;
    String importe;
    Short sucursal;
    Short idOperacion;
    String folioTransaccion;
}
