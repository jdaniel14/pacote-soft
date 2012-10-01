package DAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import Bean.Movimiento;

public class DAO_Movimiento {
	public void insertar_Movimiento(Movimiento movimiento){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String f1 = sdf.format(movimiento.hora_entrada);
		String f2 = sdf.format(movimiento.hora_salida);
		
		System.out.println("CANTIDAD DE MOV : " + movimiento.cantidad );
		String sql = 	" INSERT INTO Movimiento(almacen_id, envio_id, movimiento_hora_entrada, movimiento_hora_salida, movimiento_cantidad, movimiento_estado)" +
						" VALUES (" + movimiento.almacen+ "," + movimiento.envio+ ",'" +f1+ "','" +f2+ "'," +movimiento.cantidad+ ",'" +movimiento.estado+ "')";
		
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
			  //System.out.println("1 row affected");
		}
		catch (SQLException s){
			System.out.println("Insertar Movimiento BD : " + s);
		}
		conexion.cerrarConexion();
	}
	
}


