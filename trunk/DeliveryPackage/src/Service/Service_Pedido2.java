package Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Almacen;
import Bean.BreadthFirstPaths;
import Bean.Envio;
import Bean.Fech_Capac;
import Bean.Grafo;
import Bean.Movimiento;
import Bean.Pedido;
import Bean.Ruta;
import Bean.Vuelo;
import DAO.DAO_Almacen;
import DAO.DAO_Envio;
import DAO.DAO_Movimiento;
import DAO.DAO_Pedido;
import DAO.DAO_Vuelo;

public class Service_Pedido2 {
	private DAO_Almacen dao_almacen = new DAO_Almacen();
	private DAO_Vuelo dao_vuelo =  new DAO_Vuelo();
	private DAO_Pedido dao_pedido = new DAO_Pedido(); 
	private DAO_Envio dao_envio = new DAO_Envio();
	private DAO_Movimiento dao_movimiento = new DAO_Movimiento();
	
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
	}
	public void ReservarAlmacen(){}
	public void ReservarAvion(){}
	
	public List <Ruta> buscarRuta(Pedido pedido){		
		//Pedido pedido = new Pedido();Love Unit
		//pedido.almacen_partida = 1;
		//pedido.almacen_entrega = 3;
		//pedido.cantidad = 3;
		
		List <Ruta> listaRutas = null;
		List <Ruta> listaRutasDev = new ArrayList <Ruta>();
		try {
			List <Almacen> listAlmacen = dao_almacen.ListarAlmacenes();
			List <Vuelo> listVuelo = dao_vuelo.ListarVuelos();
			//System.out.println("-->" + listVuelo.size());
			for(int i=0; i<listVuelo.size(); i++){
				Vuelo vuelo = listVuelo.get(i);
				//System.out.println(vuelo.ciudad_ini + "->" + vuelo.ciudad_fin);
				
			}
			
			Grafo grafo = new Grafo(listAlmacen, listVuelo);
			grafo.rutas();
			BreadthFirstPaths busq = new BreadthFirstPaths();
			listaRutas = busq.bfs(grafo, pedido);
			
			
			int capac = pedido.cantidad;
			for(int i=0; i<listaRutas.size(); i++){
				//System.out.println(listaRutas.get(i).capacidad + " - " + capac);
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
	
	/*public int hallar_capacidad_real(Ruta ruta, Pedido pedido){
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
	}*/
	
	public int devolver_capacidad_real(Ruta ruta, Pedido pedido) throws SQLException{
		int capac_real=Integer.MAX_VALUE, vuelo_capac, capac_almacen, almacen_id; Date fech_ini, fech_fin; Vuelo vuelo1, vuelo2;
		
		List<Vuelo> listVuelos = ruta.listaVuelos;
		int tamListVuelos = listVuelos.size();
		
		//System.out.println("tam xD: " + tamListVuelos);
		
		//capac_real = capacidad_almacen(almacen_id, fech_ini, fech_fin);
		
		for(int i=1; i<tamListVuelos; i++){
			vuelo1 = listVuelos.get(i-1);
			vuelo2 = listVuelos.get(i);
			fech_ini = vuelo1.hora_fin;
			fech_fin = vuelo2.hora_inicio;
			almacen_id = vuelo1.ciudad_fin;
			vuelo_capac = vuelo1.capacidad - vuelo1.capacidad_actual;
			
			capac_almacen = capacidad_almacen(almacen_id, fech_ini, fech_fin);
			//System.out.println("--> " + vuelo1.vuelo_id + ": " + capac_almacen);
			
			//System.out.println("--> capac_real : " + capac_real +",  capac_almacen : "+capac_almacen + ", vuelo_capac :" + vuelo_capac);
			
			capac_real = Math.min(capac_real, Math.min(capac_almacen, vuelo_capac));
		}
		vuelo2 = listVuelos.get(tamListVuelos-1);
		capac_almacen = capacidad_almacen(vuelo2.ciudad_fin, vuelo2.hora_fin, pedido.fecha_entrega);
		capac_real = Math.min(capac_real, capac_almacen);
		//System.out.println("--> REAL : " + capac_real);
		return capac_real;
	}
	public int capacidad_almacen(int almacen_id, Date fech_ini, Date fech_fin) throws SQLException{
		int capac=0; int arr_cant[]; Fech_Capac f_c;
		List<Fech_Capac> list_fech_capac = (this.dao_almacen.consultar_movimientos_rango(almacen_id, fech_ini, fech_fin));
		int tam_list = list_fech_capac.size();
		//System.out.println("tam_arr : " + tam_list);
		
		long min_fech=Long.MAX_VALUE, max_fech=Long.MIN_VALUE;
		
		for(int i=0; i<tam_list; i++){
			f_c = list_fech_capac.get(i);
			//System.out.println("mov_id : " + f_c.mov_id + ", cant: "+f_c.cant + ", f_ini" + f_c.fech_ini + ", f_fin:" + f_c.fech_fin);
			min_fech = Math.min(min_fech, f_c.fech_ini.getTime());
			max_fech = Math.max(max_fech, f_c.fech_fin.getTime());
		}
		Long tam_arr = (max_fech - min_fech)/3600000;
		arr_cant = new int[(int)(long)tam_arr];
		
		//System.out.println("tam_arr : " + tam_arr);
		
		Long ii, ff;
		for(int i=0; i<tam_list; i++){
			f_c = list_fech_capac.get(i);
			ii = f_c.fech_ini.getTime();
			ff = f_c.fech_fin.getTime();
			int x = (int)(long)(ii - min_fech)/3600000;
			int y = (int)(long)((tam_arr - (max_fech - ff)/3600000));
			//System.out.println("x:" + x + " y: " + y + " " + f_c.fech_ini);
			for(int var = x; var<y; var++){
				arr_cant[var] += f_c.cant;
			}
		}
		
		capac = Integer.MIN_VALUE;
		for(int i=0; i<tam_list; i++){
			//System.out.println( "cant " + arr_cant[i]);
			capac = Math.max(capac, arr_cant[i]);
		}
		//System.out.println("%%%%%%" + (dao_almacen.capacidad_almacen(almacen_id) - capac));
		return dao_almacen.capacidad_almacen(almacen_id) - capac;
		//PANCHO MARICON
	}

	public int actualizacion_cache(Pedido pedido, int cant, Ruta ruta){
		int res= 0;Vuelo vuelo1, vuelo2;
		int ult_pedido = dao_pedido.registrarPedido(pedido);
		pedido.id = ult_pedido;
		int ult_envio = dao_envio.registrarEnvio(pedido, cant, "OK");
		Envio envio = new Envio();
		envio.id = ult_pedido;
		envio.cantidad = cant;
		
		
		List <Vuelo> listVuelos = ruta.listaVuelos;
		vuelo1 = listVuelos.get(0);
		dao_vuelo.actualizar_capacidad(envio, vuelo1);
		
		int tamList = listVuelos.size();
		for(int i=1; i<tamList; i++){
			vuelo1 = listVuelos.get(i-1);
			vuelo2 = listVuelos.get(i);
			Movimiento mov = new Movimiento(vuelo1.ciudad_fin, ult_envio, vuelo1.hora_fin, vuelo2.hora_inicio, cant, "OK");
			dao_movimiento.insertar_Movimiento(mov);

			dao_vuelo.actualizar_capacidad(envio, vuelo2);
		}
		
		return res;
	}

}
