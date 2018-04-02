import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.Calendar;

public class CALCULATION {
	
Connection conn=DB_CONNECTOR.Connect_data_base();


public void month_year_checker(){
	 try {
			int current_year=get_year();
			int current_month=get_month();
			
			String query="select MONTH,YEAR from time";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			int month=rs.getInt("MONTH");
			int year=rs.getInt("YEAR");
			
			pst.close();
			rs.close();
			
			if(month!=current_month){
				paying_employee();
				interest_addition();
				charge_per_month();
				deposit_monthly_fund();
				update_month(current_month);
				
			}
			
			if(year!=current_year){
				charge_per_year();
				deposit_yearly_fund();
				update_year(current_year);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
}

public void deposit_yearly_fund(){
	try {
		double total_fund=get_yearly_charge()*customer_number();
		String query="update BANK_FUND set BALANCE=BALANCE+'"+total_fund+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		pst.execute();
		pst.close();
		
	}catch (SQLException e) {
		e.printStackTrace();
	}
}
public void deposit_monthly_fund(){
    try {
		double total_fund=get_monthly_charge()*customer_number();
		String query="update BANK_FUND set BALANCE=BALANCE+'"+total_fund+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		pst.execute();
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public double get_yearly_charge(){
	double amount=0;
	try {
		
		String query="select CHARGE_PER_ANUM from customer";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		
			amount=Double.parseDouble(rs.getString("CHARGE_PER_ANUM"));
		
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return amount;
}

public double get_monthly_charge(){
	double amount=0;
	try {
		
		String query="select CHARGE_PER_MONTH from customer";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		
			amount=Double.parseDouble(rs.getString("CHARGE_PER_MONTH"));
		
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return amount;
}

public int customer_number(){
	int count=0;
	try {
		
		String query="select * from customer";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		while(rs.next()) count++;
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return count;
}


public void charge_per_month(){
   try {
		
		String query="update customer set BALANCE=BALANCE-CHARGE_PER_MONTH";
		PreparedStatement pst=conn.prepareStatement(query);
		pst.execute();
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void charge_per_year(){
	   try {
			
			String query="update customer set BALANCE=BALANCE-CHARGE_PER_ANUM";
			PreparedStatement pst=conn.prepareStatement(query);
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


public void paying_employee(){
   try {
		
		String query="update employee set BALANCE=BALANCE+BONUS+SALARY, BONUS=0";
		PreparedStatement pst=conn.prepareStatement(query);
		pst.execute();
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void interest_addition(){
   try {
		
		String query="update customer set BALANCE=ADDITION+BALANCE";
		PreparedStatement pst=conn.prepareStatement(query);
		pst.execute();
		pst.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public int get_year(){
	int year = Year.now().getValue();
	return year;
}

public int get_month(){
	Calendar cal = Calendar.getInstance();
	int month = cal.get(Calendar.MONTH);
	return month;
}

public void update_month(int month){
	 try {
			String query="update time set month='"+month+"' ";
			PreparedStatement pst=conn.prepareStatement(query);
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

public void update_year(int year){
	 try {
			String query="update time set month='"+year+"' ";
			PreparedStatement pst=conn.prepareStatement(query);
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
}



}
