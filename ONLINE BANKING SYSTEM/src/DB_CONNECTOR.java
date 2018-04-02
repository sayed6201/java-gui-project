import java.sql.*;
	import javax.swing.*;
public class DB_CONNECTOR {
		
		public static Connection Connect_data_base(){
			try{
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("Jdbc:sqlite:F:\\Drive\\Completed Projects\\my java project(161-15-7041)\\ONLINE BANKING SYSTEM\\sqlite database\\online banking sysytem.sqlite");
				//JOptionPane.showMessageDialog(null, "connected to database");
				return conn;
				
				}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
				return null;
			}
		}

	}

