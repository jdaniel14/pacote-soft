package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Pedido;
import Bean.Ruta;
import Bean.Vuelo;
import DAO.DAO_Vuelo;

public class Service_Pedido3 {

	
	public static void algorithm() throws SQLException{
		int cityPartida = 1;
		int cityFinal = 5;
		Date fechaInicio = new Date(112,8,13,10,0,0);
		Date fechaFin = new Date(112,8,16,10,0,0);
		int cantidadEnviar = 9;
			
		Vuelo base = new Vuelo(0,cityPartida,cityFinal,fechaInicio,fechaFin,cantidadEnviar,0,"OK");
		
		Pedido pedido = new Pedido(1,cityPartida,cityFinal,fechaInicio,fechaFin,"OK");
		
	
		
		while (cantidadEnviar > 0){
			buscarRuta(pedido,base);
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
		
		
		
	public static void ordenarRutasPropuestasFactor(List <Ruta> rutasPropuestas){
			
		for(int i = 0; i < rutasPropuestas.size() - 1;i++){
				
			for(int j = i; j < rutasPropuestas.size();j++){
				Ruta ruta1;
				Ruta ruta2;
					
				if (rutasPropuestas.get(i).factor > rutasPropuestas.get(j).factor){
					ruta1 = rutasPropuestas.get(i);
					ruta2 = rutasPropuestas.get(j);
						
					rutasPropuestas.set(i, ruta2);
					rutasPropuestas.set(j, ruta1);
						
				}
					
			}			
				
		}
			
			
	}
		
		
		
		
		
	public static void buscarRuta(Pedido pedido, Vuelo base) throws SQLException{
			
		System.out.println("Corriendo Algoritmo...");
		System.out.println();
			
		Date systemTime = new Date();
		//System.out.println(systemTime);
					
		/******Algoritmo*******/
			
		DAO_Vuelo vuelo_DAO = new DAO_Vuelo();
		List <Vuelo> listaVuelos;
		
		listaVuelos = vuelo_DAO.ListarVuelos();
		
		System.out.println("La hora Inca Kola es: " + base.hora_inicio);
		System.out.println();
		
		Integer ICiudad = base.ciudad_ini;
		Integer FCiudad = base.ciudad_fin;
		
		//Vuelo lectorVuelo;
		Vuelo lectorVueloLista;
		Vuelo lectorVuelo;
		
		Vuelo insertVuelo;
			
		//Lo utilizo para leer de mi lista de rutas (hallando los caminos)
		ArrayList lectorListaRutas;
		
		ArrayList rutas = new ArrayList();
		
		Integer lectorCiudadFin;
		
		ArrayList rutasPropuestas = new ArrayList();
		
		
		
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
				if (base.hora_inicio.getTime() < lectorVuelo.hora_inicio.getTime()){
					
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
				
				System.out.println("****Ruta encontrada****");
				lectorListaRutas.add(lectorVueloLista);
				
				opcionR.listaVuelos = copiarListaVuelos(lectorListaRutas);
				
				rutasPropuestas.add(opcionR);
				
				imprimirRuta(lectorListaRutas);
				
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
		
		List <Ruta> iterador = rutasPropuestas;
		
		ArrayList rutaASeguir = new ArrayList();
		
		while (pedido.cantidad > 0){
			
			for(int i = 0; i < iterador.size();i++){
			
				int capReal = 0;
				int factor = 0;
				//Obtengo el valor de capReal
			
				iterador.get(i).capacidad = capReal;
				iterador.get(i).factor = factor;
			}
			
			ordenarRutasPropuestasFactor(iterador);
			
			if (pedido.cantidad <= iterador.get(0).capacidad){
				rutaASeguir.add(iterador.get(0));
				
				System.out.println();
				System.out.println("Se a–adi— una ruta para el pedido");
				System.out.println();
				
				break;
			}
			else{
				pedido.cantidad = pedido.cantidad - iterador.get(0).capacidad;
				
				//Inserto en el cache de base de datos la cantidad iterador.get(0).capacidad
			}
			
		}
		
		
		//Imprimo mi respuesta final
		for (int i = 0; i < rutaASeguir.size();i++){
			System.out.println();
			System.out.println("Se esta enviado: "+ (((Ruta)(rutaASeguir.get(i)))).capacidad);
			System.out.println();
			imprimirRuta((ArrayList)(((Ruta)(rutaASeguir.get(i))).listaVuelos));
		}
			
		/******Fin*******/
		
		System.out.println();
		System.out.println("...Fin Algoritmo");
		
			
			
			
	}
	
}
