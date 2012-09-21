package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Almacen;
import Bean.Vuelo;

public class DAO_Vuelo {
	public List<Vuelo> ListarVuelos() throws SQLException{
		List <Vuelo> listaVuelos = new ArrayList<Vuelo>();
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		Statement s = conexion.conn.createStatement();
		s.executeQuery("SELECT * FROM Vuelo");
		ResultSet rs = s.getResultSet();
		
		while(rs.next()){
			
			int vuelo_id=rs.getInt(1);
			int ciudad_ini=rs.getInt(2);
			int ciudad_fin=rs.getInt(3);
			Date hora_inicio=rs.getTime(4);
			Date hora_fin=rs.getTime(5);
			int capacidad=rs.getInt(6);
			
			Vuelo vuelo = new Vuelo(vuelo_id, ciudad_ini, ciudad_fin, hora_inicio, hora_fin, capacidad);
			listaVuelos.add(vuelo);
		}
		
		conexion.cerrarConexion();
		return listaVuelos;
	}
}