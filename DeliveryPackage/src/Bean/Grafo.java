package Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grafo {
	private int V;
    private int E;
    public List<Vuelo>[] adj;
    public HashMap map;
    private List<Almacen> Almacenes;
    
    public Grafo(List<Almacen> listAlmacen) {
    	map = new HashMap();
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = listAlmacen.size();
        this.E = 0;
        System.out.println(listAlmacen.size());
        Almacenes = listAlmacen;
        adj = (List<Vuelo>[]) new List[V];
        for (int v = 0; v < V; v++) {
        	Almacen almacen = listAlmacen.get(v);
        	System.out.println(almacen.almacen_id);
        	
        	map.put(almacen.almacen_id, v);
            adj[v] = new ArrayList<Vuelo>();
        }
    }
    public int getV(){
    	return V;
    }
    public Grafo(List<Almacen> listAlmacen, List <Vuelo> listVuelo) {
    	this(listAlmacen);
    	System.out.println(listAlmacen.size());
        //if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < listVuelo.size(); i++) {
        	Vuelo vuelo = listVuelo.get(i); 
            int v = vuelo.ciudad_ini;
            //int w = vuelo.ciudad_fin;
            addEdge((Integer)map.get(v), vuelo);
        }
    } 
    
    public void addEdge(int v, Vuelo vuelo) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        //if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        E++;
        adj[v].add(vuelo);
        //adj[w].add(v);
    }
    
    public void rutas(){
    	System.out.println("SALIDAS");
    	for(int i=0; i<V; i++){
    		System.out.println("Desde la ciudad " + Almacenes.get(i).almacen_id+ " salen los sgtes vuelos: ");
    		for(int j=0; j<adj[i].size(); j++){
    			Vuelo vuelo = adj[i].get(j);
    			System.out.println("== " + vuelo.vuelo_id + ") --> " + vuelo.ciudad_fin + " , hr ini : " + vuelo.hora_inicio + " , hr_fin : " + vuelo.hora_fin);
    		}
    		System.out.println();
    	}
    }
}
