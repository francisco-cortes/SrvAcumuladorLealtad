package com.baz.lealtad.dtos;

import com.baz.lealtad.models.CursorSpSalidaModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpSalidaResponseDto {
    CursorSpSalidaModel cursorSalida;
    String PA_CODIGOSALIDA;
    String PA_MENSAJESALIDA;
}
