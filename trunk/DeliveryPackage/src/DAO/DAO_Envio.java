package DAO;

import java.sql.SQLException;
import java.sql.Statement;

import Bean.Envio;

public class DAO_Envio {
	public void registrarEnvio(Envio envio){
		//INSERT INTO Envio(almacen_id_partida, almacen_id_entrega, envio_cantidad,envio_fecha_registro, envio_fecha_entrega, envio_estado) 
		//VALUES(1,3,3,'2012/09/13 15:00:00.0','2012/09/14 15:00:00.0', 'ACTIVO');
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	"INSERT INTO Envio(almacen_id_partida, almacen_id_entrega, envio_cantidad,envio_fecha_registro, envio_fecha_entrega, envio_estado) " +
						"VALUES (" + envio.id+ "," + envio.almacen_partida+ ","+envio.almacen_entrega+ ","+envio.cantidad+","+envio.fecha_registro+","+envio.fecha_entrega+",'"+envio.estado+"')";	
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
