import java.sql.*;


public class Main {
	
	public static void main(String [] args){
		int cantidad=10, ciudad_ini_id=1, ciudad_fin_id=3;
		Connection conn = null;
		try{
			String username="root";
			String password="123";
			String database="mydb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql:///"+database,username,password);
			
			/*
			if(!conn.isClosed()){
					Statement s = conn.createStatement();
				s.executeQuery("SELECT * FROM Peru");
				ResultSet rs = s.getResultSet();
				while(rs.next()){
					int pkey = rs.getInt(1);
					System.out.println("id : " + pkey);
					
				}
				rs.close();
				s.close();
			}*/
			
		}catch(Exception e){
			System.err.println("Error");
		}
		
	
	}

}
