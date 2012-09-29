package Bean;

import java.util.List;

public class Ruta {
	public List <Vuelo> listaVuelos;
	public int capacidad;
	public int factor;
	public Pedido pedido;
	public Ruta(List <Vuelo> lista, int capac){
		this.listaVuelos = lista;
		this.capacidad = capac;
	}
	public Ruta(Ruta ruta){
		this.listaVuelos = ruta.listaVuelos;
		this.capacidad = ruta.capacidad;
	}
	public boolean excluyente(List<Ruta> listaRutas){
		return true;
	}
}
