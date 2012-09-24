package Bean;

import java.util.Date;

public class Envio {
	public int id;
	public int almacen_partida; 
	public int almacen_entrega; 
	public int cantidad;
	public Date fecha_registro; 
	public Date fecha_entrega; 
	public String estado; 
	
	public Envio(int id, int alm_part, int alm_entr, int cant, Date registro, Date entrega, String est){
		this.id = id;
		this.almacen_partida = alm_part;
		this.almacen_entrega = alm_entr;
		this.cantidad = cant;
		this.fecha_registro = registro;
		this.fecha_entrega = entrega;
		this.estado = est;
	}
}
