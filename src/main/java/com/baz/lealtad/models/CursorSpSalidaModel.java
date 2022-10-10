package com.baz.lealtad.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorSpSalidaModel {
  /*
  id tipo cliente
   */
  private int FNIDTIPOCLIENTE;
  /*
  id cliente
   */
  private String FCIDCLIENTE;
  /*
  importe
   */
  private Double FNIMPORTE;
  /*
  sucursal
   */
  private int FNSUCURSAL;
  /*
  id operacion
   */
  private int FNIDOPERACION;
  /*
  folio transaccion
   */
  private String FCFOLIOTRANSACCION;
  /*
  fecha operacion
   */
  private Date FDFECHAOPERACION;
  /*
  negocio
   */
  private String FCNEGOCIO;
  /*
  tipo de operacion
   */
  private String FCTIPOOPERACION;
  /*
  origen transaccion
   */
  private int FIORIGENTRANSACCION;
  /*
  id pais
   */
  private int FIPAISID;


}
