package Service;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import Bean.*;
import DAO.*;


public class Service_Pedido {
	private DAO_Pedido dao_pedido = new DAO_Pedido();
	private DAO_Vuelo dao_vuelo = new DAO_Vuelo();
	public void ConfirmarEnvio(){
		ReservarAlmacen();
		ReservarAvion();
		//dao_pedido.registrarPedido(new Pedido());
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
	
	
	
	
	
	public void buscarRuta() throws SQLException{
		
		Date systemTime = new Date();
		
		List <Vuelo> listaVuelos;
		
		listaVuelos = dao_vuelo.ListarVuelos();
		System.out.println(" CANTIDAD : " + listaVuelos.size());
		int cityPartida = 1;
		int cityFinal = 5;
		Date fechaInicio = new Date(112,8,13,10,0,0);
		Date fechaFin = new Date(112,8,16,10,0,0);
		int cantidadEnviar = 7;
		
		//Vuelo base = new Vuelo(0,cityPartida,cityFinal,fechaInicio,fechaFin,cantidadEnviar,0,"OK");
		
		Pedido pedido = new Pedido(cantidadEnviar,cityPartida,cityFinal,fechaInicio,fechaFin,"OK");
		
		Integer ICiudad = pedido.almacen_partida;
		Integer FCiudad = pedido.almacen_entrega;
		
		System.out.println("Pedido:");
		System.out.println("Fecha inicio: " + pedido.fecha_registro);
		System.out.println("Fecha fin: " + pedido.fecha_entrega);
		System.out.println();
		
		//Vuelo lectorVuelo;
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
				
				System.out.println("****Ruta encontrada****");
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
		
		List <Ruta> iterador = rutasPropuestas;
		
		if (iterador.size() == 0){
			System.out.println("No se encontr— una ruta");
			System.exit(1);
		}
		//System.out.println("NUMERO DE RUTAS : " + iterador.size());
		
		/*for(int i = 0; i < iterador.size();i++){
			imprimirRuta((ArrayList)iterador.get(i).listaVuelos);
			System.out.println("Capacidad Real de la ruta: " + iterador.get(i).capacidad);
			System.out.println("Factor Actual de la ruta: " + iterador.get(i).factor);
		}
		
		for (int i = 0; i < iterador.size();i++){
			iterador.get(i).capacidad = metodos.devolver_capacidad_real(iterador.get(i),pedido);
		}
		
		for(int i = 0; i < iterador.size();i++){
			imprimirRuta((ArrayList)iterador.get(i).listaVuelos);
			System.out.println("Capacidad Real de la ruta: " + iterador.get(i).capacidad);
			System.out.println("Factor Actual de la ruta: " + iterador.get(i).factor);
		}
		
		*/
		
		//System.out.println("Registra Pedido");
		int ult_pedido = dao_pedido.registrarPedido(pedido);
		pedido.id = ult_pedido;
		//System.out.println("Pedido : " + ult_pedido);
		
		
		ArrayList capacidades = new ArrayList();
		
		ArrayList rutaASeguir = new ArrayList();
		
		
		int i_ruta =1;
		while (pedido.cantidad > 0){
			//System.out.println("ESTAMOS EN LA RUTA : " + (i_ruta++));
			if (iterador.size() == 0){
				System.out.println("No se encontr— una ruta");
				System.exit(1);
			}
			
			for(int i = 0; i < iterador.size();i++){
			
				int factor = 0;
				
				
				iterador.get(i).capacidad = metodos.devolver_capacidad_real(iterador.get(i),pedido);
				//System.out.println("CAPAC REAL : " + iterador.get(i).capacidad);
				iterador.get(i).factor = factor;
			}
			
			limpiarVacios(iterador);
			
			//System.out.println("HOla");
			
			ordenarRutasPropuestas(iterador);
			
			//System.out.println("tam " + iterador.size());
			int enviado=0;
			if (pedido.cantidad <= iterador.get(0).capacidad){
				
				//int temp ;
				//temp = iterador.get(0).capacidad;
				iterador.get(0).cantidadEnviada = pedido.cantidad;
				iterador.get(0).capacidad -= iterador.get(0).cantidadEnviada;
				
				rutaASeguir.add(iterador.get(0));
				//System.out.println("ANTES DE INSERT ---------------------");
				metodos.actualizacion_cache(pedido, iterador.get(0).cantidadEnviada, iterador.get(0));
				enviado = iterador.get(0).cantidadEnviada;
				iterador.remove(0);
				
				System.out.println();
				System.out.println("Se complet— el env’o");
				System.out.println();
				
				
				break;
			}
			else{
				
				iterador.get(0).cantidadEnviada = iterador.get(0).capacidad;
				iterador.get(0).capacidad -= iterador.get(0).cantidadEnviada;
				
				pedido.cantidad = pedido.cantidad - iterador.get(0).cantidadEnviada;
				
				rutaASeguir.add(iterador.get(0));
				
				//System.out.println(iterador.get(0).cantidadEnviada);
				//System.out.println("ANTES DE INSERT ---------------------");
				metodos.actualizacion_cache(pedido, iterador.get(0).cantidadEnviada, iterador.get(0));
				//System.out.println("SALIMOS DE ACTUALIZACION DE CACHE");
				enviado = iterador.get(0).cantidadEnviada;
				iterador.remove(0);
			}
			System.out.println("CUANTO INSERTO : " + enviado);
			//System.out.println("CUANTO FALTA : " + pedido.cantidad);
		}
		
		
		//Imprimo mi respuesta final
		/*for (int i = 0; i < rutaASeguir.size();i++){
			//System.out.println();
			//System.out.println("Se esta enviando: " + ((Ruta)rutaASeguir.get(i)).cantidadEnviada);
			//System.out.println();
			imprimirRuta((ArrayList)(((Ruta)(rutaASeguir.get(i))).listaVuelos));
		}*/
	}
	
	
}
