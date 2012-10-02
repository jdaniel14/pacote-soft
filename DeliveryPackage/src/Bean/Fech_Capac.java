package Bean;

import java.util.Date;

public class Fech_Capac {
	public int almacen_id;
	public int cant;
	public int mov_id;
	public Date fech_ini;
	public Date fech_fin;
	public Fech_Capac(int alm, int cant, int mov_id, Date fech_ini, Date fech_fin){
		this.almacen_id = alm;
		this.cant = cant;
		this.mov_id = mov_id;
		this.fech_ini = fech_ini;
		this.fech_fin = fech_fin;
	}
}
