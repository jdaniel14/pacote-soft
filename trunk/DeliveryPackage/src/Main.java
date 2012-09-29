import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Pedido;
import Service.Service_Pedido;
import Service.Service_Pedido2;
import Service.Service_Pedido3;

public class Main {
	
	public static void main(String [] args) throws SQLException{
 
		Service_Pedido p = new Service_Pedido();
		Service_Pedido3 p3 = new Service_Pedido3();
		
		//int f = 50;
		//while(f != 0){
		//f--;
		//long tiempoI = System.currentTimeMillis();
		///p3.algorithm();
		p.buscarRuta();
		//long tiempoF = System.currentTimeMillis();
		
		
		
		
		//double r = (tiempoF - tiempoI)/1000.0;
		//System.out.println( (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		
		
		//}
		
	}

}
