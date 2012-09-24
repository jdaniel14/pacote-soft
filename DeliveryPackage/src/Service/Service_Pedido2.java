package Service;
import java.sql.SQLException;
import java.util.List;

import Bean.Almacen;
import Bean.BreadthFirstPaths;
import Bean.Grafo;
import Bean.Pedido;
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
	
	public List <Ruta> buscarRuta(Pedido pedido){
		dao_almacen = new DAO_Almacen();
		dao_vuelo = new DAO_Vuelo();
		//Pedido pedido = new Pedido();
		//pedido.almacen_partida = 1;
		//pedido.almacen_entrega = 3;
		//pedido.cantidad = 3;
		
		List <Ruta> listaRutas = null;
		List <Ruta> listaRutasDev = null;
		try {
			List <Almacen> listAlmacen = dao_almacen.ListarAlmacenes();
			List <Vuelo> listVuelo = dao_vuelo.ListarVuelos();
			System.out.println("-->" + listVuelo.size());
			for(int i=0; i<listVuelo.size(); i++){
				Vuelo vuelo = listVuelo.get(i);
				System.out.println(vuelo.ciudad_ini + "->" + vuelo.ciudad_fin);
				
			}
			
			Grafo grafo = new Grafo(listAlmacen, listVuelo);
			grafo.rutas();
			BreadthFirstPaths busq = new BreadthFirstPaths();
			listaRutas = busq.bfs(grafo, pedido);
			
			
			int capac = pedido.cantidad;
			for(int i=0; i<listaRutas.size(); i++){
				System.out.println(listaRutas.get(i).capacidad + " - " + capac);
				if(capac==0) break;
				
				Ruta ruta_add; 
				int cuant = listaRutas.get(i).capacidad;
				
				if(capac>=cuant){
					capac -= cuant;
					ruta_add = new Ruta(listaRutas.get(i));
				}else{	
					ruta_add = new Ruta(listaRutas.get(i).listaVuelos, capac);
					capac = 0;
				}	
				if(ruta_add.excluyente(listaRutasDev))listaRutasDev.add(ruta_add);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaRutasDev;
	}
	
	
}
