package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import Bean.Pedido;

public class DAO_Pedido {
	public DAO_Pedido(){}
	public int registrarPedido(Pedido pedido){
		int est = 0;
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		
		String sql;
			
		try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				String f1 = sdf.format(pedido.fecha_registro);
				String f2 = sdf.format(pedido.fecha_entrega);
				sql = 	" INSERT INTO Pedido(pedido_cantidad, almacen_id_partida, almacen_id_entrega, pedido_fecha_registro, pedido_fecha_entrega, pedido_estado) " +
						" VALUES (" + pedido.cantidad + "," + pedido.almacen_partida + ","+pedido.almacen_entrega+",'"+ f1+"','"+f2+"','"+pedido.estado+"')";
				Statement st = conexion.conn.createStatement();
				st.executeUpdate(sql);
				
				sql = 	" SELECT last_insert_id()";
				Statement st1 = conexion.conn.createStatement();
				st1.executeQuery(sql);
				ResultSet rs = st1.getResultSet();
				System.out.println("AQUI");
				if(rs.next()){
					System.out.println("AQUI xD");
					est = rs.getInt(1);
					System.out.println(est);
					return est;					
				}
		}
		catch (SQLException s){
			System.out.println("Error en Pedido : " + s);
		}
		conexion.cerrarConexion();
		return est;
	}
}
