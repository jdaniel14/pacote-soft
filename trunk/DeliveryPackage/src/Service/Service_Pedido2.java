package Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
	private DAO_Almacen dao_almacen;
	private DAO_Vuelo dao_vuelo;
	
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
	}
	public void ReservarAlmacen(){}
	public void ReservarAvion(){}
	
	public List <Ruta> buscarRuta(Pedido pedido){
		dao_almacen = new DAO_Almacen();
		dao_vuelo = new DAO_Vuelo();
		//Pedido pedido = new Pedido();Love Unit
		//pedido.almacen_partida = 1;
		//pedido.almacen_entrega = 3;
		//pedido.cantidad = 3;
		
		List <Ruta> listaRutas = null;
		List <Ruta> listaRutasDev = new ArrayList <Ruta>();
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
	
	public int hallar_capacidad_real(Ruta ruta, Pedido pedido){
		int capac, capac_final = Integer.MAX_VALUE; Date fech_ini, fech_fin; Vuelo vuelo1, vuelo2;
		
		List<Vuelo> listaVuelo = ruta.listaVuelos;
		vuelo1 = listaVuelo.get(0);
		
		int tam = listaVuelo.size();
		fech_ini = pedido.fecha_registro;
		fech_fin = vuelo1.hora_inicio;
		capac = this.dao_almacen.capacidad_almacen(fech_ini, fech_fin);
		capac_final = Math.min(capac_final, Math.min(capac, vuelo1.capacidad_actual));
		
		for(int i=1; i<tam; i++){
			vuelo1 = listaVuelo.get(i-1);
			vuelo2 = listaVuelo.get(i);
			fech_ini = vuelo1.hora_fin;
			fech_fin = vuelo2.hora_inicio;
			this.dao_almacen.capacidad_almacen(fech_ini, fech_fin);
			capac_final = Math.min(capac_final, Math.min(capac, vuelo2.capacidad_actual));
		}
		vuelo2 = listaVuelo.get(tam-1);
		fech_ini = vuelo2.hora_fin;
		fech_fin = pedido.fecha_entrega;
		this.dao_almacen.capacidad_almacen(fech_ini, fech_fin);
		capac_final = Math.min(capac_final, Math.min(capac, vuelo2.capacidad_actual));
		
		return capac_final;
	}
}
