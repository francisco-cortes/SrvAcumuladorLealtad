package com.baz.lealtad.models;

import java.sql.Date;

public class CursorSpSalidaModel {
  /*
  id tipo cliente
   */
  private int FN_ID_TIPO_CLIENTE;
  /*
  id cliente
   */
  private String FC_ID_CLIENTE;
  /*
  importe
   */
  private Double FN_IMPORTE;
  /*
  sucursal
   */
  private int FN_SUCURSAL;
  /*
  id operacion
   */
  private int FN_ID_OPERACION;
  /*
  folio transaccion
   */
  private String FC_FOLIO_TRANSACCION;
  /*
  fecha operacion
   */
  private Date FD_FECHA_OPERACION;
  /*
  negocio
   */
  private String FC_NEGOCIO;
  /*
  tipo de operacion
   */
  private String FC_TIPO_OPERACION;
  /*
  origen transaccion
   */
  private int FI_ORIGEN_TRANSACCION;
  /*
  id pais
   */
  private int FI_PAIS_ID;

  /*
  gettter
   */
  public int getFnIdTipoCliente(){
    return this.FN_ID_TIPO_CLIENTE;
  }

  public String getFcIdCliente(){
    return this.FC_ID_CLIENTE;
  }

  public Double getFnImporte(){
    return this.FN_IMPORTE;
  }

  public int getFnSucursal(){
    return this.FN_SUCURSAL;
  }

  public int getFnIdOperacion(){
    return this.FN_ID_OPERACION;
  }

  public String getFcFolioTransaccion(){
    return this.FC_FOLIO_TRANSACCION;
  }

  public Date getFdFechaOperacion(){
    return (Date) this.FD_FECHA_OPERACION.clone();
  }

  public String getFcNegocio(){
    return this.FC_NEGOCIO;
  }

  public String getFcTipoOperacion(){
    return this.FC_TIPO_OPERACION;
  }

  public int getFiOrigenTransaccion(){
    return this.FI_ORIGEN_TRANSACCION;
  }

  public int getFiPaisId(){
    return this.FI_PAIS_ID;
  }

  /*
  setters
   */

  public void setFnIdTipoCliente(int idTipoCliente){
    this.FN_ID_TIPO_CLIENTE = idTipoCliente;
  }

  public void setFcIdCliente(String idCliente){
    this.FC_ID_CLIENTE = idCliente;
  }

  public void setFnImporte(Double importe){
    this.FN_IMPORTE = importe;
  }

  public void setFnSucursal(int sucursal){
    this.FN_SUCURSAL = sucursal;
  }

  public void setFnIdOperacion(int idOperacion){
    this.FN_ID_OPERACION = idOperacion;
  }

  public void setFcFolioTransaccion(String folioTransaccion){
    this.FC_FOLIO_TRANSACCION = folioTransaccion;
  }

  public void setFdFechaOperacion(Date fechaOperacion){
    this.FD_FECHA_OPERACION = (Date) fechaOperacion.clone();
  }

  public void setFcNegocio(String negocio){
    this.FC_NEGOCIO = negocio;
  }

  public void setFcTipoOperacion(String tipoOperacion){
    this.FC_TIPO_OPERACION = tipoOperacion;
  }

  public void setFiOrigenTransaccion(int origenTransaccion){
    this.FI_ORIGEN_TRANSACCION = origenTransaccion;
  }

  public void setFiPaisId(int paisId){
    this.FI_PAIS_ID = paisId;
  }

}
