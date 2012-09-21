package Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.Almacen;
import Bean.Ruta;
import Bean.Vuelo;
import DAO.DAO_Almacen;
import DAO.DAO_Vuelo;

public class Service_Pedido2 {
	DAO_Almacen dao_almacen;
	DAO_Vuelo dao_vuelo;
	
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
	}
	public void ReservarAlmacen(){}
	public void ReservarAvion(){}
	
	public List<Ruta> buscarRuta(){
		dao_almacen = new DAO_Almacen();
		dao_vuelo = new DAO_Vuelo();
		
		List <Ruta> listaRutas = null;
		try {
			List <Almacen> listAlmacen = dao_almacen.ListarAlmacenes();
			List <Vuelo> listVuelo = dao_vuelo.ListarVuelos();
			//Grafo grafo = new Grafo(listAlmacen, listVuelo);
			//listaRutas = grafo.rutas();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaRutas;
	}
	
	
}
