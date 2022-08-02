package com.baz.lealtad.dtos;

import com.baz.lealtad.models.ResultadoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto {
    String mensaje;
    String folio;
    ResultadoModel resultadoModel;
}
