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
		p.buscarRuta();
	}

}
