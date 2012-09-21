package Service;
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
	
	

	
	
	
	
	
	
	
	
	
	
	public static void buscarRuta(){
		
		System.out.println("Corriendo Algoritmo...");
		System.out.println();
		
		/******Algoritmo*******/
		
		DAO_Vuelo vuelo_DAO = new DAO_Vuelo();
		
		Vuelo base = new Vuelo(0,1,9,new Date(),new Date(),0);
		
		Integer ICiudad = base.ciudad_ini;
		Integer FCiudad = base.ciudad_fin;
		
		Integer condicion = 0;
		
		Vuelo lectorVuelo = new Vuelo(0,0,0,new Date(),new Date(),0);
		
		ArrayList rutas = new ArrayList();
		
		while (condicion < 100){
			/*Mientras que no se haya leido toda la tabla vuelos*/
			
			
			/*Verifico si el vuelo tiene como inicio "leerCiudad"*/
			
			/*si la tiene, agrego a la lista, sino sigo denuevo con el bucle*/
			
			
			
			if (ICiudad == lectorVuelo.ciudad_ini){
				
				ArrayList opcionR = new ArrayList();
				
				opcionR.add(lectorVuelo);
				rutas.add(opcionR);
			}
			
			break;
		}
		
		/******Fin*******/
		
		System.out.println();
		System.out.println("...Fin Algoritmo");
		
		
		
	}
	
	
}
