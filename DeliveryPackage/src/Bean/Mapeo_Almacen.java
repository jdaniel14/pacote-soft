package Bean;//NO SIRVE

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mapeo_Almacen{
	public HashMap mapAlmacenes;
	public HashMap mapVuelos;
	public List <Almacen> listAlmacenes;
	public List <Vuelo> listVuelos;
	
	public Mapeo_Almacen(){
		this.mapAlmacenes = new HashMap();
		this.mapVuelos = new HashMap();
		this.listAlmacenes = new ArrayList <Almacen>(); 
		this.listVuelos = new ArrayList <Vuelo>();
	}
	
}
