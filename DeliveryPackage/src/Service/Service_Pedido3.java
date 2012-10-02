package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Envio;
import Bean.Pedido;
import Bean.Ruta;
import Bean.Vuelo;
import DAO.DAO_Pedido;
import DAO.DAO_Vuelo;

public class Service_Pedido3 {

	private DAO_Pedido dao_pedido = new DAO_Pedido();
	private DAO_Vuelo dao_vuelo = new DAO_Vuelo();

	
	public static void algoritmoEstadistico(Pedido pedido) throws SQLException{
		
		Service_Pedido3 algoritmo = new Service_Pedido3();
		
		int cantidad = pedido.cantidad;
		pedido.cantidad = 1;
		
		int continuar = 1;
		
		while (cantidad > 0){
			
			continuar = algoritmo.buscarRuta(pedido);
			cantidad--;
			
			if (continuar == 0){
				break;
			}
			
		}
		
	}
	
	public static void imprimirVuelo(Vuelo flight){
		/*Imprime los datos del vuelo*/
		System.out.println("Nœmero de vuelo: " + flight.vuelo_id);
		System.out.println("Ciudad de Inicio: " + flight.ciudad_ini);
		System.out.println("Ciudad final: " + flight.ciudad_fin);
		System.out.println("Hora de partida: "  + (flight.hora_inicio).toString());
		System.out.println("Hora de llegada: " + (flight.hora_fin).toString());
		System.out.println("Capacidad: " + flight.capacidad);
	}
	
	public static void imprimirRuta(ArrayList listaDeVuelos){	
		/*Imprime una lista de vuelos*/
		Vuelo lectorVuelo;
		System.out.println();
		System.out.println("*Posibilidad de ruta a seguir*");
		System.out.println();
		for(int i = 0; i < listaDeVuelos.size();i++){
			lectorVuelo = (Vuelo)listaDeVuelos.get(i);
			System.out.println("->Vuelo #" + i);
			imprimirVuelo(lectorVuelo);
		}
		System.out.println();
		System.out.println("*Fin de Posibilidad de ruta a seguir*");
		System.out.println();
	}
	
	public static Vuelo copiarVuelo(Vuelo vuelo){
		return new Vuelo(vuelo.vuelo_id,vuelo.ciudad_ini,vuelo.ciudad_fin, vuelo.hora_inicio,vuelo.hora_fin, vuelo.capacidad,vuelo.capacidad_actual,vuelo.estado);
	}
	
	public static ArrayList copiarListaVuelos(ArrayList lista){
		
		ArrayList devolver = new ArrayList();
		for(int i = 0; i < lista.size();i++){
			Vuelo flight = (Vuelo)lista.get(i);
			devolver.add(copiarVuelo(flight));
		}
		return devolver;
	}
	
	public static void limpiarVacios(List <Ruta> iterador){
		for(int i = 0; i < iterador.size();i++){
			if(iterador.get(i).capacidad == 0){
				iterador.remove(i);
			}
		}
	}
	
	
	public static void ordenarRutasPropuestas(List <Ruta> rutasPropuestas){
		
		for(int i = 0; i < rutasPropuestas.size() - 1;i++){
			for(int j = i; j < rutasPropuestas.size();j++){
				Ruta ruta1;
				Ruta ruta2;
				if (rutasPropuestas.get(i).capacidad < rutasPropuestas.get(j).capacidad){
					ruta1 = rutasPropuestas.get(i);
					ruta2 = rutasPropuestas.get(j);
					rutasPropuestas.set(i, ruta2);
					rutasPropuestas.set(j, ruta1);	
				}		
			}	
		}
	}
	
	public void imprimirRuta(List<Ruta> listaRutas, Pedido pedido){
		imprimirPedido(pedido);
		int tamList = listaRutas.size();
		System.out.println("Lista de Envios:");
		System.out.println();
		
		System.out.println("**********************************************************************");
		for(int i=0; i<tamList; i++){
			Ruta ruta = listaRutas.get(i);
			//System.out.println("--------------------------------------------------------------");
			System.out.println("EnvioID : " + ruta.envio.id + ", Cant : " + ruta.envio.cantidad);
			System.out.println();
			List<Vuelo> listVuelos = ruta.listaVuelos;
			int tamVuel = listVuelos.size();
			for(int j=0;j<tamVuel; j++){
				Vuelo vuelo = listVuelos.get(j); 
				System.out.println("VueloID : " + vuelo.vuelo_id);
				System.out.println("--> Almacen Ini : " + vuelo.ciudad_ini + ", Hora Ini : " + vuelo.hora_inicio);
				System.out.println("--> Almacen Fin : " + vuelo.ciudad_fin + ", Hora Fin : " + vuelo.hora_fin);
			}
			System.out.println("--------------------------------------------------------------");
		}
	}
	
	public void imprimirPedido(Pedido pedido){
		System.out.println("Datos del Pedido");
		System.out.println("Pedido(ID) : " + pedido.id + ", Cantidad : " + pedido.cantidad);
		System.out.println("Almacen de inicio : " + pedido.almacen_partida + ", Hora de inicio : " + pedido.fecha_registro);
		System.out.println("Almacen dinal : " + pedido.almacen_entrega + ", Hora final : " + pedido.fecha_entrega);
		
	}
	
	
	public int buscarRuta(Pedido pedido) throws SQLException{	
		List<Ruta> RutasDeViaje = new ArrayList<Ruta>();
		int estado = 0;
		
		Date systemTime = new Date();
		List <Vuelo> listaVuelos;
		listaVuelos = dao_vuelo.ListarVuelos();
		//System.out.println(" CANTIDAD : " + listaVuelos.size());
		
		
		Integer ICiudad = pedido.almacen_partida;
		Integer FCiudad = pedido.almacen_entrega;
		
		/*System.out.println("Pedido:");
		System.out.println("Fecha inicio: " + pedido.fecha_registro);
		System.out.println("Fecha fin: " + pedido.fecha_entrega);
		System.out.println();*/
		Vuelo lectorVueloLista;
		Vuelo lectorVuelo;
		Vuelo insertVuelo;
		//Lo utilizo para leer de mi lista de rutas (hallando los caminos)
		ArrayList lectorListaRutas;
		ArrayList rutas = new ArrayList();
		
		Integer lectorCiudadFin;
		
		ArrayList rutasPropuestas = new ArrayList();
		
		Service_Pedido2 metodos = new Service_Pedido2();
		
		//****************************************
		
		
		/*Inicializo la lista con los primeros vuelos que deberian iniciar
		 * Puedo utilizar este primer bucle para crear el grafo
		 * el cual haria mas eficiente el algoritmo
		 */
		for(int i = 0; i < listaVuelos.size(); i++){
			/*Mientras que no se haya leido toda la tabla vuelos*/
			
			lectorVuelo = listaVuelos.get(i);
		
			/*Verifico si el vuelo tiene como inicio "leerCiudad"*/
			
			/*si la tiene, agrego a la lista, sino sigo denuevo con el bucle*/
			if (ICiudad == lectorVuelo.ciudad_ini){
				if (pedido.fecha_registro.getTime() < lectorVuelo.hora_inicio.getTime()){
					ArrayList opcionR = new ArrayList();
					insertVuelo = lectorVuelo;
					opcionR.add(insertVuelo);
					rutas.add(opcionR);
				}
			}
		}

		while (!rutas.isEmpty()){	
			//Cojo la lista de vuelos que toca la primera ruta como opcion
			lectorListaRutas = (ArrayList)rutas.get(0);
			rutas.remove(0);
			
			//Cojo el ultimo vuelo en el que me quede de la ruta que cree
			int pos = lectorListaRutas.size() - 1;			
			lectorVueloLista = (Vuelo)lectorListaRutas.get(pos);
			lectorListaRutas.remove(pos);
			lectorCiudadFin = lectorVueloLista.ciudad_fin;

			if (lectorCiudadFin == FCiudad){				
				Ruta opcionR = new Ruta(null,0);;
				//System.out.println("****Ruta encontrada****");
				lectorListaRutas.add(lectorVueloLista);
				opcionR.listaVuelos = copiarListaVuelos(lectorListaRutas);
				rutasPropuestas.add(opcionR);
				//imprimirRuta(lectorListaRutas);
				
			}
			
			for(int i = 0; i < listaVuelos.size(); i++){
				/*Mientras que no se haya leido toda la tabla vuelos*/
				lectorVuelo = listaVuelos.get(i);
				/*Verifico si el vuelo tiene como inicio "leerCiudad"*/
				/*si la tiene, agrego a la lista, sino sigo denuevo con el bucle*/
				if (lectorCiudadFin == lectorVuelo.ciudad_ini){
					if (lectorVueloLista.hora_fin.getTime() < lectorVuelo.hora_inicio.getTime()){
						ArrayList opcionR;
						opcionR = copiarListaVuelos(lectorListaRutas);
						opcionR.add(copiarVuelo(lectorVueloLista));
						opcionR.add(copiarVuelo(lectorVuelo));
					
						rutas.add(opcionR);
					}
				}	
			}
		}
		
		//Segunda Parte del algoritmo
		List <Ruta> iterador = rutasPropuestas;
		
		if (iterador.size() == 0){
			estado = 0;
			System.out.println("No se encontro posibles rutas");
			return 0;
		}
		
		//Registro temporal del pedido
		int ult_pedido = dao_pedido.registrarPedido(pedido);
		pedido.id = ult_pedido;

		while (pedido.cantidad > 0){
			
			if (iterador.size() == 0){
				System.out.println("No se encontr— una ruta");
				estado = 0;
				return 0;
			}
			
			for(int i = 0; i < iterador.size();i++){
				int factor = 0;
				iterador.get(i).capacidad = metodos.devolver_capacidad_real(iterador.get(i),pedido);
				iterador.get(i).factor = factor;
			}
			
			limpiarVacios(iterador);
			ordenarRutasPropuestas(iterador);

			if (iterador.size() == 0){
				System.out.println("No se encontr— una ruta");
				estado = 0;
				return 0;
			}

			Ruta ruta_ins; 
			Envio envio_insertar;

			if (pedido.cantidad <= iterador.get(0).capacidad){
				
				iterador.get(0).cantidadEnviada = pedido.cantidad;
				iterador.get(0).capacidad -= iterador.get(0).cantidadEnviada;
				
				envio_insertar = metodos.actualizacion_cache(pedido, iterador.get(0).cantidadEnviada, iterador.get(0));
				
				ruta_ins = iterador.get(0);
				ruta_ins.envio = envio_insertar;
				RutasDeViaje.add(ruta_ins);
				
				iterador.remove(0);
				
				estado = 1;
				
				break;
			}
			else{
				
				iterador.get(0).cantidadEnviada = iterador.get(0).capacidad;
				iterador.get(0).capacidad -= iterador.get(0).cantidadEnviada;
				
				pedido.cantidad = pedido.cantidad - iterador.get(0).cantidadEnviada;
				
				envio_insertar = metodos.actualizacion_cache(pedido, iterador.get(0).cantidadEnviada, iterador.get(0));
				
				ruta_ins = iterador.get(0);
				ruta_ins.envio = envio_insertar;
				RutasDeViaje.add(ruta_ins);
				
				iterador.remove(0);
			}

		}
		
		if(estado==1){
			System.out.println("Ruta Encontrada");
			imprimirRuta(RutasDeViaje, pedido);
		}
		else{
			System.out.println("No se encontr— ruta");
		}
		
		return estado;
	
	}
	
}
