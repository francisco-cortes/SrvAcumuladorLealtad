package com.baz.lealtad.models;

import java.sql.Date;

public class CursorSpSalidaModel {
  /*
  id tipo cliente
   */
  private int fnIdTipoCliente;
  /*
  id cliente
   */
  private String fcIdCliente;
  /*
  importe
   */
  private Double fnImporte;
  /*
  sucursal
   */
  private int fnSucursal;
  /*
  id operacion
   */
  private int fnIdOperacion;
  /*
  folio transaccion
   */
  private String fcFolioTransaccion;
  /*
  fecha operacion
   */
  private Date fdFechaOperacion;
  /*
  negocio
   */
  private String fcNegocio;
  /*
  tipo de operacion
   */
  private String fcTipoOperacion;
  /*
  origen transaccion
   */
  private int fiOrigenTransaccion;
  /*
  id pais
   */
  private int fiPaisId;

  public CursorSpSalidaModel(){
    // No se requiere constructor, gracias sonar
  }

  /*
  obtiene el id tipo cliente
   */
  public int getFnIdTipoCliente(){
    return this.fnIdTipoCliente;
  }
  /*
  obtiene el id cliente
   */
  public String getFcIdCliente(){
    return this.fcIdCliente;
  }
  /*
  obtiene el importe
   */
  public Double getFnImporte(){
    return this.fnImporte;
  }
  /*
  obtiene la surcursal
   */
  public int getFnSucursal(){
    return this.fnSucursal;
  }
  /*
  obtiene el id
   */
  public int getFnIdOperacion(){
    return this.fnIdOperacion;
  }

  /*
  obtiene el folio tencsaccion mtcn
   */
  public String getFcFolioTransaccion(){
    return this.fcFolioTransaccion;
  }

  /*
  fecha de operacion
   */
  public Date getFdFechaOperacion(){
    return (Date) this.fdFechaOperacion.clone();
  }

  /*
  obtiene el negocio
   */
  public String getFcNegocio(){
    return this.fcNegocio;
  }

  /*
  obtiene el tipo de operacion
   */
  public String getFcTipoOperacion(){
    return this.fcTipoOperacion;
  }

  /*
  obtiene el origine de transaccio remesa
   */
  public int getFiOrigenTransaccion(){
    return this.fiOrigenTransaccion;
  }

  /*
  obtiene el id del pasi 01 + mex
   */
  public int getFiPaisId(){
    return this.fiPaisId;
  }

  /*
  setters
   */

  /*
  estable el valor de id tipo cliente
   */
  public void setFnIdTipoCliente(int idTipoCliente){
    this.fnIdTipoCliente = idTipoCliente;
  }

  /*
  establece el valor de id cliente
   */
  public void setFcIdCliente(String idCliente){
    this.fcIdCliente = idCliente;
  }

  /*
  establece el valor del importe
   */
  public void setFnImporte(Double importe){
    this.fnImporte = importe;
  }

  /*
  establece el valor de la sucursal 2314 ventanilla
   */
  public void setFnSucursal(int sucursal){
    this.fnSucursal = sucursal;
  }

  /*
  estyablece el id operacion
   */
  public void setFnIdOperacion(int idOperacion){
    this.fnIdOperacion = idOperacion;
  }

  /*
  establece la varible folio transacciion
   */
  public void setFcFolioTransaccion(String folioTransaccion){
    this.fcFolioTransaccion = folioTransaccion;
  }

  /*
  establese la fecha operacion muy imortante java.sql
   */
  public void setFdFechaOperacion(Date fechaOperacion){
    this.fdFechaOperacion = (Date) fechaOperacion.clone();
  }
  /*
  esablece el negocio
   */
  public void setFcNegocio(String negocio){
    this.fcNegocio = negocio;
  }
  /*
  establece el tipo de operacion
   */
  public void setFcTipoOperacion(String tipoOperacion){
    this.fcTipoOperacion = tipoOperacion;
  }
  /*
  establece el origen de la transaccuib
   */
  public void setFiOrigenTransaccion(int origenTransaccion){
    this.fiOrigenTransaccion = origenTransaccion;
  }

  /*
  establece el valor de pais
   */
  public void setFiPaisId(int paisId){
    this.fiPaisId = paisId;
  }

}
