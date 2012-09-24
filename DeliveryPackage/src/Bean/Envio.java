package Bean;

import java.util.Date;

public class Envio {
	public int id;
	public int pedido_id;	 
	public int cantidad; 
	public String estado; 
	
	public Envio(){}
	
	public Envio(int id, int ped_id, int cant,String est){
		this.id = id;
		this.pedido_id = ped_id;
		this.cantidad = cant;		
		this.estado = est;
	}
	
}
