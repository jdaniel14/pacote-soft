package DAO;

import java.sql.SQLException;
import java.sql.Statement;

import Bean.Vuelo_Mov;

public class DAO_Vuelo_Mov {
	public void insertar_Vuelo_Mov(Vuelo_Mov vuelo_mov){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		//INSERT INTO Vuelo_Mov(vuelo_id, envio_id)
		//VALUES (1,1);
		
		String sql = 	"INSERT INTO Vuelo_Mov(vuelo_id, envio_id)" +
						"VALUES (" + vuelo_mov.vuelo_id+ "," + vuelo_mov.envio_id+")";
		
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
			  //System.out.println("1 row affected");
		}
		catch (SQLException s){
			System.out.println("Insertar Vuelo_Mov BD");
		}
		conexion.cerrarConexion();		
	}
	
}
