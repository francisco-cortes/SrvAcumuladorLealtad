package com.baz.lealtad.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceLealtadModel {
    String tipo;
    Short puntosRedimir;
    Short totalPuntosAnterior;
    Short totalPuntosFinal;
    Short puntosRecompenza;
}
