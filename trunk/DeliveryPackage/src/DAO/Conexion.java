package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion{
	Connection conn;
	
	public Conexion(){
		this.conn = null;
	}
	
	public Connection abrirConexion(){
		//conn = null;
		try{
			String username="root";
			String password="jose";
			String database="dp1";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql:///"+database, username, password);
			
		}catch(Exception e){
			System.out.println("Error en Conexion");
		}
		return conn;
	}
	
	public void cerrarConexion(){
		try{
			conn.close();
		}catch(Exception e){
			System.out.println("Error");
		}
	}
}
