package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import Bean.Fech_Capac;
import Bean.Pedido;

public class DAO_Pedido {
	public int registrarPedido(Pedido pedido){
		int est = 0;
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		
		String sql;
			
		try{
				sql = 	" INSERT INTO Pedido(pedido_cantidad, almacen_id_partida, almacen_id_entrega, pedido_fecha_registro, pedido_fecha_entrega, pedido_estado) " +
						" VALUES (" + pedido.cantidad + "," + pedido.almacen_partida + ","+pedido.almacen_entrega+","+ pedido.fecha_registro+","+pedido.fecha_entrega+",'"+pedido.estado+"')";
				Statement st = conexion.conn.createStatement();
				st.executeUpdate(sql);
				
				sql = 	" SELECT last_insert_id()";
				Statement st1 = conexion.conn.createStatement();
				ResultSet rs = st1.getResultSet();
				if(rs.next()){
					est = rs.getInt(1);
					return est;					
				}
		}
		catch (SQLException s){
			System.out.println("Insertar Vuelo Envio");
		}
		conexion.cerrarConexion();
		return est;
	}
}
