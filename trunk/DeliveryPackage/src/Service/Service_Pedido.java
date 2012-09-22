package Service;
import java.sql.SQLException;
import java.util.*;
import Bean.*;
import DAO.*;

public class Service_Pedido {

	
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
	}
	public void ReservarAlmacen(){}
	public void ReservarAvion(){}
	
	

	
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
		return new Vuelo(vuelo.vuelo_id,vuelo.ciudad_ini,vuelo.ciudad_fin, new Date(), new Date(), vuelo.capacidad);
	}
	
	public static ArrayList copiarListaVuelos(ArrayList lista){
		
		ArrayList devolver = new ArrayList();
		
		for(int i = 0; i < lista.size();i++){
			Vuelo flight = (Vuelo)lista.get(i);
			devolver.add(copiarVuelo(flight));
		}
		
		return devolver;
	}
	
	public static void buscarRuta() throws SQLException{
		
		System.out.println("Corriendo Algoritmo...");
		System.out.println();
		
		/******Algoritmo*******/
		
		DAO_Vuelo vuelo_DAO = new DAO_Vuelo();
		List <Vuelo> listaVuelos;
		
		listaVuelos = vuelo_DAO.ListarVuelos();
		
		ArrayList imprimeme = (ArrayList)listaVuelos;
		
		//imprimirRuta(imprimeme);
		
		Vuelo base = new Vuelo(0,1,5,new Date(),new Date(),0);
		
		Integer ICiudad = base.ciudad_ini;
		Integer FCiudad = base.ciudad_fin;
		
		Integer condicion = 0;
		
		//Vuelo lectorVuelo;
		Vuelo lectorVueloLista;
		Vuelo lectorVuelo;
		
		Vuelo insertVuelo;
		
		//Lo utilizo para leer de mi lista de rutas (hallando los caminos)
		ArrayList lectorListaRutas;
		
		ArrayList rutas = new ArrayList();
		
		Integer lectorCiudadFin;
		
		//****************************************
		
		//+++++OJO; Verificar si el usuario escribe viajar de 1 a 1 (mismo lugar)
		
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
				
				ArrayList opcionR = new ArrayList();
				
				insertVuelo = lectorVuelo;
				
				opcionR.add(insertVuelo);
				rutas.add(opcionR);
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
				System.out.println("****Ruta encontrada****");
				lectorListaRutas.add(lectorVueloLista);
				imprimirRuta(lectorListaRutas);
				
			}
			
			for(int i = 0; i < listaVuelos.size(); i++){
				/*Mientras que no se haya leido toda la tabla vuelos*/
				
				lectorVuelo = listaVuelos.get(i);
				
				
				/*Verifico si el vuelo tiene como inicio "leerCiudad"*/
				
				/*si la tiene, agrego a la lista, sino sigo denuevo con el bucle*/
				if (lectorCiudadFin == lectorVuelo.ciudad_ini){
					
					ArrayList opcionR;
					opcionR = copiarListaVuelos(lectorListaRutas);
					
					opcionR.add(copiarVuelo(lectorVueloLista));
					opcionR.add(copiarVuelo(lectorVuelo));
					
					rutas.add(opcionR);
				}
				
			}
			
			
			
		}
		
		
		/******Fin*******/
		
		System.out.println();
		System.out.println("...Fin Algoritmo");
		
		
		
	}
	
	
}
