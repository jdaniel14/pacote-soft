package Bean;

import java.util.Date;

public class Vuelo {
	public int vuelo_id;
	public int ciudad_ini;
	public int ciudad_fin;
	public Date hora_inicio;
	public Date hora_fin;
	public int capacidad;
	public int capacidad_actual;
	public String estado;
	
	public Vuelo(int id, int c_ini, int c_fin, Date hr_ini, Date hr_fin, int capac, int capac_act, String est){
		this.vuelo_id = id;
		this.ciudad_ini = c_ini;
		this.ciudad_fin = c_fin;
		this.hora_inicio = hr_ini;
		this.hora_fin = hr_fin;
		this.capacidad = capac;
		this.capacidad_actual = capac_act;
		this.estado = est;
	}
}
