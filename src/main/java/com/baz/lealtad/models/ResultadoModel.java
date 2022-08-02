package com.baz.lealtad.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoModel {
    String idUsuariosTpremia;
    Short idOperacion;
    String folioOperacion;
    String fechaHoraOperacion;
    BalanceLealtadModel[] balancelealtad;
}
