package Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    // single source
    public BreadthFirstPaths(Grafo G, int ini, int fin) {
        marked = new boolean[G.getV()];
        distTo = new int[G.getV()];
        edgeTo = new int[G.getV()];
        bfs(G, ini, fin);

    }

    // BFS from single source
    public void bfs(Grafo G, int ini, int fin) {
        Queue< List<Vuelo> > q = new LinkedList< List<Vuelo> >();
        for (int v = 0; v < G.getV(); v++) distTo[v] = INFINITY;
        distTo[ini] = 0;
        marked[ini] = true;
 
        int inicial = (Integer) G.map.get(ini);
        //int finn  = (Integer) G.map.get(fin);
        
        for(int i=0; i<G.adj[inicial].size(); i++){
        	List<Vuelo> listIni = new ArrayList <Vuelo>();
        	Vuelo vuelo = G.adj[inicial].get(i);
        	listIni.add(vuelo);
        	q.add(listIni);
        }
        
        while (!q.isEmpty()) {
        	List<Vuelo> path = q.peek(); q.poll();
        	int tam = path.size();
        	int dest_aux = path.get(tam-1).ciudad_fin;
        	//System.out.println("destino auxiliar : " + dest_aux + " final : " + fin);
        	
        	if(dest_aux == fin){
        		System.out.println("Se alcanz— destino " + tam);
        		imprimir_path(path);
        		System.out.println("---------------------------");
        	}
        	//else{
        	//	imprimir_path(path);
        	//}
        	Vuelo vf = path.get(tam-1);
        	int dd = (Integer)G.map.get(dest_aux);
            for (int i=0; i<G.adj[dd].size(); i++) {
                Vuelo vv = G.adj[(Integer)G.map.get(dest_aux)].get(i);
            	if(no_esta_incluido(vv, path, ini) && cumple_tiempo(vv,vf)){
            		List<Vuelo>new_path = new ArrayList<Vuelo>(path);//path.begin(),path.end()
            		//System.out.println("- " + vv.ciudad_fin + " -- " + i);
                    new_path.add(vv);
                    q.add(new_path);
                }
            }
        }
        
    }
    
    private boolean cumple_tiempo(Vuelo vv, Vuelo vf){
    	
    	Date dv = vv.hora_inicio;
    	Date df = vf.hora_fin;
    	System.out.println("vv "+dv);
    	System.out.println("vf "+df);
    	return (dv.getTime() > df.getTime());
    }
    
    private boolean no_esta_incluido(Vuelo vuelo, List<Vuelo> path, int ini){
    	int ciudad = vuelo.ciudad_fin;
    	if(ciudad == ini) return false;
    	for(int i=0; i<path.size(); i++){
    		Vuelo vv = path.get(i);
    		if(vv.ciudad_fin == ciudad) return false;
    	}
    	return true;
    }

    // is there a path between s (or sources) and v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // length of shortest path between s (or sources) and v
    public int distTo(int v) {
        return distTo[v];
    }

    public void imprimir_path(List <Vuelo> path){
    	
    	int tot=0;
    	for(int k=0; k<path.size(); k++){
			System.out.print(" " + path.get(k).vuelo_id + " -> " + path.get(k).capacidad + " -- ");
			tot += path.get(k).capacidad;
			
		}
    	System.out.print(" -> " + tot);
		System.out.println();
    	
    }
}