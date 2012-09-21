package Bean;

import java.util.List;

public class Ruta {
	public List <Vuelo> listaVuelos;
	public int capacidad;
	public Ruta(List <Vuelo> lista, int capac){
		this.listaVuelos = lista;
		this.capacidad = capac;
	}
}
