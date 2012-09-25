package Bean;

import java.util.Date;

public class Fech_Capac {
	public int cant;
	public int mov_id;
	public Date fech_ini;
	public Date fech_fin;
	public Fech_Capac(int cant, int mov_id, Date fech_ini, Date fech_fin){
		this.cant = cant;
		this.mov_id = mov_id;
		this.fech_ini = fech_ini;
		this.fech_fin = fech_fin;
	}
}
