package com.baz.lealtad.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpSalidaResponseDto {
    private BigDecimal FNREGISTROS;
    private BigDecimal FNTOTAL;
    private String FNUSUARIO;

    public BigDecimal getFnRegistros(){
        if (this.FNREGISTROS == null){
            return this.FNREGISTROS == null ? BigDecimal.valueOf(0) : this.FNREGISTROS;
        }
        else {
            return this.FNREGISTROS;
        }
    }

    public BigDecimal getFnTotal(){
        if (this.FNTOTAL == null){
            return this.FNTOTAL == null ? BigDecimal.valueOf(0) : this.FNTOTAL;
        }
        else {
            return this.FNTOTAL;
        }
    }

    public String getFnUsuario(){
        if (this.FNUSUARIO == null){
            return this.FNUSUARIO == null ? "" : this.FNUSUARIO;
        }
        else {
            return this.FNUSUARIO;
        }
    }
}
