package DAO;

import java.sql.SQLException;
import java.sql.Statement;

import Bean.Pedido;

public class DAO_Envio {
	public void registrarEnvio(Pedido pedido, int cant, String estado){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	"INSERT INTO Envio(pedido_id, envio_cantidad, envio_estado) " +
						"VALUES (" + pedido.id + "," + cant + ",'"+estado+"')";	
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
		}
		catch (SQLException s){
			System.out.println("Insertar Vuelo Envio");
		}
		conexion.cerrarConexion();
	}
}
