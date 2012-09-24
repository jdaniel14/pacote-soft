package DAO;

import java.sql.SQLException;
import java.sql.Statement;

import Bean.Movimiento;

public class DAO_Movimiento {
	public void insertar_Movimiento(Movimiento movimiento){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	" INSERT INTO Movimiento(almacen_id, envio_id, movimiento_hora_entrada, movimiento_hora_salida, movimiento_cantidad, movimiento_estado)" +
						" VALUES (" + movimiento.almacen+ "," + movimiento.envio+ "," +movimiento.hora_entrada+ "," +movimiento.hora_salida+ "," +movimiento.cantidad+ ",'" +movimiento.estado+ "')";
		
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
			  //System.out.println("1 row affected");
		}
		catch (SQLException s){
			System.out.println("Insertar Movimiento BD");
		}
		conexion.cerrarConexion();
	}
	
}


