package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Bean.Almacen;

public class DAO_Almacen {
	public List<Almacen> ListarAlmacenes() throws SQLException{
		List <Almacen> listaAlmacenes = new ArrayList<Almacen>();
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		Statement s = conexion.conn.createStatement();
		s.executeQuery("SELECT * FROM Almacén");
		ResultSet rs = s.getResultSet();
		
		while(rs.next()){
			int id=rs.getInt(1);
			int capacidad=rs.getInt(2);
			int actual=rs.getInt(3);
			
			Almacen almacen = new Almacen(id, capacidad, actual);
			listaAlmacenes.add(almacen);
		}
		
		conexion.cerrarConexion();
		return listaAlmacenes;
	}
}
