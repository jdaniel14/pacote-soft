package DAO;

import java.sql.SQLException;
import java.sql.Statement;

import Bean.Pedido;

public class DAO_Pedido {
	public void registrarPedido(Pedido pedido){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	"INSERT INTO Pedido(pedido_cantidad, almacen_id_partida, almacen_id_entrega, pedido_fecha_registro, pedido_fecha_entrega, pedido_estado) " +
						"VALUES (" + pedido.cantidad + "," + pedido.almacen_partida + ","+pedido.almacen_entrega+","+ pedido.fecha_registro+","+pedido.fecha_entrega+",'"+pedido.estado+"')";	
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
