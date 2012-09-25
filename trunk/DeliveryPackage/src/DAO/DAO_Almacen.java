package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Almacen;
import Bean.Fech_Capac;

public class DAO_Almacen {
	public List<Almacen> ListarAlmacenes() throws SQLException{
		List <Almacen> listaAlmacenes = new ArrayList<Almacen>();
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		Statement s = conexion.conn.createStatement();
		s.executeQuery("SELECT * FROM Almacen");
		ResultSet rs = s.getResultSet();
		
		while(rs.next()){
			int id=rs.getInt(1);
			int capacidad=rs.getInt(2);
			//int actual=rs.getInt(3);
			
			Almacen almacen = new Almacen(id, capacidad/*, actual*/);
			listaAlmacenes.add(almacen);
		}
		
		conexion.cerrarConexion();
		return listaAlmacenes;
	}
	
	public int capacidad_almacen(int almacen_id) throws SQLException{
		int capac = 0;
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		Statement s = conexion.conn.createStatement();
		s.executeQuery("SELECT almacen_capacidad FROM Almacen WHERE almacen_id = " + almacen_id);
		ResultSet rs = s.getResultSet();
		
		if(rs.next()){
			capac = rs.getInt(1);
		}
		
		conexion.cerrarConexion();
		return capac;
	}
	
	public List<Fech_Capac>  consultar_movimientos_rango(int almacen_id, Date fech_ini, Date fech_fin)  {
		List<Fech_Capac> list_fech_capac =  new ArrayList<Fech_Capac>();
		Conexion conexion = new Conexion();
		conexion.abrirConexion();
		
		Statement s;
		try {
			s = conexion.conn.createStatement();
		
			String sql = 	" SELECT movimiento_cantidad, movimiento_id, movimiento_hora_entrada, movimiento_hora_salida " +
							" FROM Movimiento " + 
							" WHERE (('2012-09-13 17:00:00.0' BETWEEN movimiento_hora_entrada AND movimiento_hora_salida ) " +
							" AND	('2012-09-13 20:00:00.0' BETWEEN movimiento_hora_entrada AND movimiento_hora_salida )) " +
							" OR 	((movimiento_hora_entrada BETWEEN '2012-09-13 17:00:00.0' AND '2012-09-13 20:00:00.0' ) " + 
							" AND	( movimiento_hora_salida BETWEEN '2012-09-13 17:00:00.0' AND '2012-09-13 20:00:00.0' ))" ;
			s.executeQuery(sql);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				int cant = rs.getInt(1);
				int mov_id = rs.getInt(2);
				Date f_ini = rs.getTimestamp(3);
				Date f_fin = rs.getTimestamp(4);
				
				//int actual=rs.getInt(3);
				
				Fech_Capac fech_capac = new Fech_Capac(cant, mov_id, f_ini, f_fin);
				list_fech_capac.add(fech_capac);
			}
			
			conexion.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cabro");
		}
		return list_fech_capac;
	}
}
