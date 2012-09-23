package Bean;

public class Almacen {
	public int almacen_id;
	public int almacen_capacidad;
	public int almacen_capacidad_actual;
	
	public Almacen(int id, int capacidad/*, int actual*/){
		this.almacen_id = id;
		this.almacen_capacidad_actual = capacidad;
		//this.almacen_capacidad_actual = actual;
	}
}
