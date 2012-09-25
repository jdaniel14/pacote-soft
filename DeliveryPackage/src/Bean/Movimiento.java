package Bean;

import java.util.Date;

public class Movimiento {
	public int almacen;
	public int envio;
	public Date hora_entrada; 
	public Date hora_salida;
	public int cantidad;
	public String estado;
	
	
	public Movimiento(int almacen, int envio, Date mov_entr, Date mov_sal, int cant, String est){
		this.almacen = almacen;
		this.envio = envio;
		this.hora_entrada = mov_entr;
		this.hora_salida = mov_sal;
		this.cantidad = cant;
		this.estado = est;
	}
}
