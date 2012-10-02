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
import DAO.DAO_Vuelo_Mov;

public class Service_Pedido2 {
	private DAO_Almacen dao_almacen = new DAO_Almacen();
	private DAO_Vuelo dao_vuelo =  new DAO_Vuelo();
	private DAO_Pedido dao_pedido = new DAO_Pedido(); 
	private DAO_Envio dao_envio = new DAO_Envio();
	private DAO_Movimiento dao_movimiento = new DAO_Movimiento();
	private DAO_Vuelo_Mov dao_vuelo_mov = new DAO_Vuelo_Mov();
	
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
	}
	public void ReservarAlmacen(){}
	public void ReservarAvion(){}
	
	public List <Ruta> buscarRuta(Pedido pedido){
		
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

	
	public int devolver_capacidad_real(Ruta ruta, Pedido pedido) throws SQLException{
		int capac_real=Integer.MAX_VALUE, vuelo_capac, capac_almacen, almacen_id; Date fech_ini, fech_fin; Vuelo vuelo1, vuelo2;
		
		List<Vuelo> listVuelos = ruta.listaVuelos;
		int tamListVuelos = listVuelos.size();
		
		vuelo1=listVuelos.get(0);
		capac_real = vuelo1.capacidad - vuelo1.capacidad_actual;
		
		for(int i=1; i<tamListVuelos; i++){
			vuelo1 = listVuelos.get(i-1);
			vuelo2 = listVuelos.get(i);
			fech_ini = vuelo1.hora_fin;
			fech_fin = vuelo2.hora_inicio;
			almacen_id = vuelo1.ciudad_fin;
			vuelo_capac = vuelo1.capacidad - vuelo1.capacidad_actual;
			capac_almacen = capacidad_almacen(almacen_id, fech_ini, fech_fin);
			capac_real = Math.min(capac_real, Math.min(capac_almacen, vuelo_capac));
		}
		vuelo2 = listVuelos.get(tamListVuelos-1);
		capac_almacen = capacidad_almacen(vuelo2.ciudad_fin, vuelo2.hora_fin, pedido.fecha_entrega);
		capac_real = Math.min(capac_real, capac_almacen);
		return capac_real;
	}
	
	
	public int capacidad_almacen(int almacen_id, Date fech_ini, Date fech_fin) throws SQLException{
		int capac=0; int arr_cant[]; Fech_Capac f_c;
		List<Fech_Capac> list_fech_capac = (this.dao_almacen.consultar_movimientos_rango(almacen_id, fech_ini, fech_fin));
		int tam_list = list_fech_capac.size();
		//System.out.println("INI: " + fech_ini + ", FIN: "+ fech_fin );
		
		if(tam_list>0){
			long min_fech=Long.MAX_VALUE, max_fech=Long.MIN_VALUE;
			
			for(int i=0; i<tam_list; i++){
				f_c = list_fech_capac.get(i);
				min_fech = Math.min(min_fech, f_c.fech_ini.getTime());
				max_fech = Math.max(max_fech, f_c.fech_fin.getTime());
			}
			Long tam_arr = (max_fech - min_fech)/3600000;
			arr_cant = new int[(int)(long)tam_arr];
			
			Long ii, ff;
			for(int i=0; i<tam_list; i++){
				f_c = list_fech_capac.get(i);
				ii = f_c.fech_ini.getTime();
				ff = f_c.fech_fin.getTime();
				//System.out.println("ini : " + f_c.fech_ini + " ,  fin : " + f_c.fech_fin+", almacen : " + f_c.almacen_id);
				int x = (int)(long)(ii - min_fech)/3600000;
				int y = (int)(long)((tam_arr - (max_fech - ff)/3600000));
				for(int var = x; var<y; var++){
					arr_cant[var] += f_c.cant;
				}
			}
			/*for(int i=0; i<tam_arr; i++){
				System.out.print(arr_cant[i] + " ");
			}*/
			//System.out.println();
			//System.out.println("tanm_list : " + tam_list + ", tam_arr : " + tam_arr);
			capac = Integer.MIN_VALUE;
			for(int i=0; i<tam_arr; i++){
				capac = Math.max(capac, arr_cant[i]);
			}
		}
		//System.out.println("capacidad:" + capac + ", almacen :" + dao_almacen.capacidad_almacen(almacen_id) +", alm_id :" + almacen_id);
		int dev = dao_almacen.capacidad_almacen(almacen_id) - capac;
		return dev;
	}

	public Envio actualizacion_cache(Pedido pedido, int cant, Ruta ruta){
		Vuelo vuelo1, vuelo2;
		
		int ult_envio = dao_envio.registrarEnvio(pedido, cant, "OK");
		Envio envio = new Envio();
		envio.id = ult_envio;
		envio.cantidad = cant;
		
		List <Vuelo> listVuelos = ruta.listaVuelos;
		int tamList = listVuelos.size();
		for(int i=0; i<tamList-1; i++){
			vuelo1 = listVuelos.get(i);
			vuelo2 = listVuelos.get(i+1);
			Movimiento mov = new Movimiento(vuelo1.ciudad_fin, ult_envio, vuelo1.hora_fin, vuelo2.hora_inicio, cant, "OK");
			dao_movimiento.insertar_Movimiento(mov);
			dao_vuelo.actualizar_capacidad(envio, vuelo1);
		}
		vuelo1 = listVuelos.get(tamList - 1);
		Movimiento mov = new Movimiento(vuelo1.ciudad_fin, ult_envio, vuelo1.hora_fin, pedido.fecha_entrega, cant, "OK");
		dao_movimiento.insertar_Movimiento(mov);
		dao_vuelo.actualizar_capacidad(envio, vuelo1);
		
		return envio;
	}
}
