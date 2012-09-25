package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Bean.Pedido;

public class DAO_Envio {
	public int registrarEnvio(Pedido pedido, int cant, String estado){
		int est=0;
		
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String 	sql;
		try{
				sql = 	"INSERT INTO Envio(pedido_id, envio_cantidad, envio_estado) " +
					"VALUES (" + pedido.id + "," + cant + ",'"+estado+"')";
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
