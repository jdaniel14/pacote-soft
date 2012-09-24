import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Pedido;
import Service.Service_Pedido2;

public class Main {
	
	public static void main(String [] args){
		
		
		
		try {
			int cant = 3;
			int almacen_partida = 1;
			int almacen_llegada = 3;
			String str_date1 ="2012/09/13 15:00:00";
			String str_date2 ="2012/09/14 15:00:00";
			String estado = "NORMAL";
			DateFormat formatter ; 
			Date date1, date2 ; 
			formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			date1 = (Date)formatter.parse(str_date1);
			date2 = (Date)formatter.parse(str_date2);
			Pedido pedido = new Pedido(cant, almacen_partida, almacen_llegada, date1, date2, estado);
			
			Service_Pedido2 algorithm = new Service_Pedido2();
			algorithm.buscarRuta(pedido);
			
		} catch (ParseException e){
			System.out.println("Exception :"+e);   
		}
	}

}
