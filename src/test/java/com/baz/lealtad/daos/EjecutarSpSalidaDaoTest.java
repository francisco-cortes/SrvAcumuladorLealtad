package com.baz.lealtad.daos;

import com.baz.lealtad.configuration.ParametrerConfiguration;
import com.baz.lealtad.logger.LogServicio;
import com.baz.lealtad.models.CursorSpSalidaModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class EjecutarSpSalidaDaoTest {

  @BeforeAll
  static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
    ParametrerConfiguration.setOracleDatabaseIp("10.81.11.77");
    ParametrerConfiguration.setOracleDatabasePort("1521");
    ParametrerConfiguration.setOracleDatabaseName("SFBDDEV");
    ParametrerConfiguration.setOracleDatabaseP("BuSTxN4LMm");
    ParametrerConfiguration.setOracleDatabaseU("C3Multimarcas");

  }

  @DisplayName("Prueba unitaria para obtener la respuesta del primer sp")
  @Test
  public void testEjecutarSpSalidaDao() {
    LogServicio log = new LogServicio();
    EjecutarSpSalidaDao ejecutarSpSalidaDao = new EjecutarSpSalidaDao();
    List<CursorSpSalidaModel> cursorSpSalidaModelList = ejecutarSpSalidaDao.ejecutarSpSalida(log);
    for(int i = 0; i < cursorSpSalidaModelList.size()-1; i++){
      System.out.println(cursorSpSalidaModelList.get(i).getFCIDCLIENTE());
    }
    assertNotNull(cursorSpSalidaModelList);
  }
}
