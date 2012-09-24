package Bean;

import java.util.Date;

public class Pedido {
	public int id;
	public int cantidad;
	public int almacen_partida;
	public int almacen_entrega;
	public Date fecha_registro;
	public Date fecha_entrega;
	public String estado;
	public Pedido(){}
	public Pedido(int id, int cant, int part, int entr, Date f_reg, Date f_entr, String est){
		this.id = id;
		this.cantidad = cant;
		this.almacen_partida = part;
		this.almacen_entrega = entr;
		this.fecha_registro = f_reg;
		this.fecha_entrega = f_entr;
		this.estado = est;
	}
}
