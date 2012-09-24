package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Envio;
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
			
			int vuelo_id = rs.getInt(1);
			int capacidad = rs.getInt(2);
			int capacidad_actual = rs.getInt(3);
			int ciudad_ini = rs.getInt(4);
			int ciudad_fin = rs.getInt(5);
			Date hora_inicio = rs.getTimestamp(6);
			Date hora_fin = rs.getTimestamp(7);
			String estado = rs.getString(8);
			
			//System.out.println("capac: " + capacidad);
			
			Vuelo vuelo = new Vuelo(vuelo_id, ciudad_ini, ciudad_fin, hora_inicio, hora_fin, capacidad, capacidad_actual, estado);
			listaVuelos.add(vuelo);
		}
		
		conexion.cerrarConexion();
		return listaVuelos;
	}
	//INSERT INTO Vuelo(vuelo_capacidad, vuelo_capacidad_actual, almacen_partida, almacen_llegada, vuelo_hora_partida, vuelo_hora_llegada, vuelo_estado) 
	//VALUES(8,0,1,2,'2012/09/13 17:00:00.0','2012/09/13 19:00:00.0','EN ESPERA');
	public void insertarVuelo(Vuelo vuelo){
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	"INSERT INTO Vuelo (vuelo_capacidad, vuelo_capacidad_actual, almacen_partida, almacen_llegada, vuelo_hora_partida, vuelo_hora_llegada, vuelo_estado) " +
						"VALUES (" + vuelo.capacidad+ "," + vuelo.capacidad_actual+ ","+vuelo.ciudad_ini+ ","+vuelo.ciudad_fin+","+vuelo.hora_inicio+","+vuelo.hora_fin+",'"+vuelo.estado+"')";
		
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
			  //System.out.println("1 row affected");
		}
		catch (SQLException s){
			System.out.println("Insertar Vuelo BD");
		}
		conexion.cerrarConexion();
	}
	
	public void actualizar_capacidad(Envio envio, Vuelo vuelo){
		
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		String sql = 	" UPDATE Vuelo " + 
						" SET vuelo_capacidad_actual = vuelo_capacidad - " + envio.cantidad +
						" WHERE vuelo_id="+vuelo.vuelo_id;
		
		try{
			  Statement st = conexion.conn.createStatement();
			  st.executeUpdate(sql);
			  //System.out.println("1 row affected");
		}
		catch (SQLException s){
			System.out.println("Insertar Vuelo BD");
		}
		conexion.cerrarConexion();
	}
}
