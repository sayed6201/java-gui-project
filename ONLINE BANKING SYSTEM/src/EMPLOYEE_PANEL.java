import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Canvas;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import javax.swing.Box;
import java.awt.Button;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Label;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class EMPLOYEE_PANEL {

	private JFrame frame;
	private JTextField textField_DEPOSIT_AC;
	private JTextField textField_DEPOSIT_AMOUNT;
	private JTable table;
    Connection conn=null;
    private JTextField textField_WITHDRAW_AC;
    private JTextField textField_WITHDRAW_AMOUNT;
    private JTable table_WITHDRAW;
    private JTextField textField_TRANSFER_AC;
    private JTextField textField_TRANSFER_AMOUNT;
    private JTextField textField_TRANSFER_AC2;
    private JTable table_TRANSFER_FROM;
    private int transfer=0;
    private JTextField textField_BALANCE_CHECK;
    private JTable table_BALANCE_CHECK;
    private JTable table_TRANSFER_TO;
    private JTextField textField_BANK_NAME;
    JComboBox comboBox_TRANSFER_TYPE ;
    private JTable table_STATEMENT;
    private JTextField textField_STATEMENT_SEARCH;
    private JTextField textField__EMPLOYEE_NAME;
    private JTextField textField_EMPLOYEE_POSITION;
    private JTextField textField_EMPLOYEE_ID;
    JLabel lblNewLabel_USER_NAME;
    JLabel lblNewLabel_IMAGE ;
    private JTable table_1;
    private JTextField textField_AC_FROM;
    private JTextField textField_AC_TO;
    private JTextField textField_BALANCE_AC_FROM;
    private JTextField textField_AMOUNT1;
    private JTextField textField_BALANCE_AC_TO;
    private JTextField textField_USERNAME;
    private JTextField textField_PASSWORD;
	/**
	 * Launch the application.
	 */
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EMPLOYEE_PANEL window = new EMPLOYEE_PANEL();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EMPLOYEE_PANEL() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
		setting_fields();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	 private void updating_balance(double balance, String ac){
		 try{
			String query="Update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+ac+"'";
			PreparedStatement pst1=conn.prepareStatement(query);
			pst1.execute();
		 } catch (SQLException e) {
				e.printStackTrace();
			}
	 }
	 
	 public void internal_transfer_refresh(){
		 try{
				String query="select AC_NO,NAME,AMOUNT,TO_AC,STATUS from internal_customer_transfer_message";
				PreparedStatement pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table_1.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
				pst.close();
				rs.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
			}
	 }
	
	
	  void setting_fields(){
			
		    textField__EMPLOYEE_NAME.setText(EMPLOYEE_LOG_IN.employee_name);
			textField_EMPLOYEE_ID.setText(EMPLOYEE_LOG_IN.employee_id);
			textField_EMPLOYEE_POSITION.setText(EMPLOYEE_LOG_IN.employee_position);
			lblNewLabel_USER_NAME.setText(EMPLOYEE_LOG_IN.employee_user_name);
			if(EMPLOYEE_LOG_IN.img_data!=null){
				ImageIcon img=new ImageIcon(new ImageIcon(EMPLOYEE_LOG_IN.img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
				lblNewLabel_IMAGE.setIcon(img);}
		}
	
	public String time() {
		 
		   DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
		   Date date = new Date();
		   return dateFormat.format(date);
	   }
	
	public void notification(String ac,String notification){
		try {
			String query="insert into customer_notification (AC_NO,NOTIFICATION) values(?,?)";
			PreparedStatement pst= conn.prepareStatement(query);
			pst.setString(1, ac);
			pst.setString(2, notification);
			
			pst.execute();
	        pst.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getname(String ac){
		try{
		String query="select NAME from customer where ACCOUNT_NO";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		String name=rs.getString("NAME");
		pst.close();
		rs.close();
		return name;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return "error";
		}
	}
	
	public void authorization_internal_transfer(String ac1){
		try {
			String query="update internal_customer_transfer_message set STATUS='AUTHORIZED' where AC_NO='"+ac1+"'";
			PreparedStatement pst= conn.prepareStatement(query);
			pst.execute();
	        pst.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public double getbalance(String ac){
		try{
		String query="select BALANCE from customer where ACCOUNT_NO='"+ac+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		double balance=rs.getDouble("BALANCE");
		pst.close();
		rs.close();
		return balance;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}
	
	private void Table_loading(JTextField textField,JTable table){//deposit
		try{
			//String selection=(String)comboBox.getSelectedItem();
			String query="select ACCOUNT_NO,NAME,BALANCE from customer where ACCOUNT_NO='"+textField.getText()+"'";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(rs));

			pst.close();
			rs.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
	}
	
	private void table_clearing(JTable table){
		//dataModel.setRowCount(0);
		table.setModel(null);
	}
	
	
    private void deposit(JTextField textField_AC,JTextField textField_AMOUNT){	
	try{
		String ac=textField_AC.getText();
		double amount=Double.parseDouble(textField_AMOUNT.getText());
		String query="select BALANCE from customer where ACCOUNT_NO='"+ac+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		double balance=Double.parseDouble(rs.getString("BALANCE"));
		
		balance+=amount;
		
		query="Update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+ac+"'";
		PreparedStatement pst1=conn.prepareStatement(query);
		pst1.execute();
		JOptionPane.showMessageDialog(null, "Cash deposited");
		//table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
		
		try{
			String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
			PreparedStatement pst2=conn.prepareStatement(statement);
			pst2.setString(1,textField_AC.getText());
			String name=getname(textField_AC);
			pst2.setString(2,name);
			pst2.setString(3,"DEPOSITED");
			pst2.setString(4,textField_AMOUNT.getText());
			pst2.setString(5,"-");
			pst2.setString(6,"-");
			pst2.setString(7,time());
			pst2.execute();
			pst2.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
			}
		
		
		pst.close();
		rs.close();
	}catch(Exception e1){
		JOptionPane.showMessageDialog(null, "Account doesn't exist");
	}
	
}
  
    
    public String getname(JTextField textField_AC){
    	try{
    	String query="select NAME from customer where ACCOUNT_NO='"+textField_AC.getText()+"' ";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		String x=rs.getString("NAME");
		pst.close();
		rs.close();
		return x;
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, e);
    		return "error";
    	}
    }
    
    
    private void withdraw(JTextField textField_AC,JTextField textField_AMOUNT){
    	try{
			String ac=textField_AC.getText();
			double amount=Double.parseDouble(textField_AMOUNT.getText());
			String query="select BALANCE from customer where ACCOUNT_NO='"+ac+"'";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			double balance=Double.parseDouble(rs.getString("BALANCE"));
			
			if(balance>amount){
			balance-=amount;
			query="Update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+ac+"'";
			PreparedStatement pst1=conn.prepareStatement(query);
			pst1.execute();
			JOptionPane.showMessageDialog(null, "Cash withdrawn");
			//table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			try{
			String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
			PreparedStatement pst2=conn.prepareStatement(statement);
			pst2.setString(1,textField_AC.getText());
			String name=getname(textField_AC);
			pst2.setString(2,name);
			pst2.setString(3,"WITHDRAWN");
			pst2.setString(4,textField_AMOUNT.getText());
			pst2.setString(5,"-");
			pst2.setString(6,"-");
			pst2.setString(7,time());
			pst2.execute();
			pst2.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Account doesn't exist3333");
			}
			pst1.close();
			}
			else JOptionPane.showMessageDialog(null, "Account does not have enough money");
			
			pst.close();
			rs.close();
			
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "Account doesn't exist");
		}
		
    }
    
    
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EMPLOYEE_PANEL.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 1216, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(390, 0, 820, 423);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 127));
		tabbedPane.addTab("Deposit", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("AC NO:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(182, 85, 84, 20);
		panel.add(lblNewLabel);
		
		textField_DEPOSIT_AC = new JTextField();
		textField_DEPOSIT_AC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Table_loading(textField_DEPOSIT_AC,table);
			}
		});
		
		JLabel lblNewLabel_23 = new JLabel("AMOUNT:");
		lblNewLabel_23.setForeground(Color.WHITE);
		lblNewLabel_23.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_23.setBackground(Color.WHITE);
		lblNewLabel_23.setBounds(182, 119, 102, 14);
		panel.add(lblNewLabel_23);
		textField_DEPOSIT_AC.setBounds(311, 86, 136, 20);
		panel.add(textField_DEPOSIT_AC);
		textField_DEPOSIT_AC.setColumns(10);
		
		textField_DEPOSIT_AMOUNT = new JTextField();
		textField_DEPOSIT_AMOUNT.setBounds(311, 117, 136, 20);
		panel.add(textField_DEPOSIT_AMOUNT);
		textField_DEPOSIT_AMOUNT.setColumns(10);
		
		JButton btnNewButton_7 = new JButton("");
		btnNewButton_7.setToolTipText("done");
		btnNewButton_7.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/checked.png")));
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deposit(textField_DEPOSIT_AC,textField_DEPOSIT_AMOUNT);//simplified
				//notification sending.......		
							String ntf="\n"+textField_DEPOSIT_AMOUNT.getText()+" tk deposited to your account "+" on "+time();
							notification(textField_DEPOSIT_AC.getText(),ntf);
							Table_loading(textField_DEPOSIT_AC,table);
			}
		});
		btnNewButton_7.setBounds(397, 183, 50, 33);
		panel.add(btnNewButton_7);
		
		JLabel lblNewLabel_24 = new JLabel("DONE");
		lblNewLabel_24.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_24.setForeground(Color.WHITE);
		lblNewLabel_24.setBounds(397, 227, 46, 14);
		panel.add(lblNewLabel_24);
		
		JButton btnNewButton_8 = new JButton("");
		btnNewButton_8.setToolTipText("clear");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_DEPOSIT_AC.setText(null);
				textField_DEPOSIT_AMOUNT.setText(null);
				table_clearing(table);
				
			}
		});
		btnNewButton_8.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/cleaning-service.png")));
		btnNewButton_8.setBounds(311, 183, 50, 33);
		panel.add(btnNewButton_8);
		
		JLabel lblNewLabel_25 = new JLabel("CLEAR");
		lblNewLabel_25.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
		lblNewLabel_25.setEnabled(true);
		lblNewLabel_25.setForeground(Color.WHITE);
		lblNewLabel_25.setBounds(315, 227, 46, 14);
		panel.add(lblNewLabel_25);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(547, 86, 203, 130);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_22 = new JLabel("");
		lblNewLabel_22.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_22.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/house.jpg")));
		lblNewLabel_22.setBounds(0, 0, 815, 395);
		panel.add(lblNewLabel_22);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 228, 225));
		tabbedPane.addTab("Withdraw", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("AC NO:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_1.setBounds(132, 106, 86, 17);
		panel_1.add(lblNewLabel_1);
		
		textField_WITHDRAW_AC = new JTextField();
		textField_WITHDRAW_AC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				Table_loading(textField_WITHDRAW_AC,table_WITHDRAW);
			}
		});
		
		JLabel lblNewLabel_21 = new JLabel("CLEAR");
		lblNewLabel_21.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_21.setForeground(Color.WHITE);
		lblNewLabel_21.setBounds(297, 256, 57, 14);
		panel_1.add(lblNewLabel_21);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_WITHDRAW_AC.setText(null);
				textField_WITHDRAW_AMOUNT.setText(null);
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/cleaning-service.png")));
		btnNewButton_3.setBounds(297, 214, 57, 33);
		panel_1.add(btnNewButton_3);
		
		JLabel lblNewLabel_20 = new JLabel("DONE");
		lblNewLabel_20.setForeground(Color.WHITE);
		lblNewLabel_20.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_20.setBounds(195, 255, 57, 17);
		panel_1.add(lblNewLabel_20);
		textField_WITHDRAW_AC.setBounds(268, 105, 111, 20);
		panel_1.add(textField_WITHDRAW_AC);
		textField_WITHDRAW_AC.setColumns(10);
		
		textField_WITHDRAW_AMOUNT = new JTextField();
		textField_WITHDRAW_AMOUNT.setBounds(268, 146, 111, 20);
		panel_1.add(textField_WITHDRAW_AMOUNT);
		textField_WITHDRAW_AMOUNT.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/checked (1).png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				withdraw(textField_WITHDRAW_AC,textField_WITHDRAW_AMOUNT);
		//notification sending.......		
				String ntf="\n"+textField_WITHDRAW_AMOUNT.getText()+" tk withdrawn from your account"+" on "+time();
				notification(textField_WITHDRAW_AC.getText(),ntf);
				Table_loading(textField_WITHDRAW_AC,table_WITHDRAW);
			}
		});
		btnNewButton.setBounds(195, 214, 57, 33);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel_19 = new JLabel("AMOUNT:");
		lblNewLabel_19.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_19.setForeground(Color.WHITE);
		lblNewLabel_19.setBounds(132, 149, 126, 14);
		panel_1.add(lblNewLabel_19);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(465, 107, 212, 122);
		panel_1.add(scrollPane_1);
		
		table_WITHDRAW = new JTable();
		scrollPane_1.setViewportView(table_WITHDRAW);
		
		JLabel lblNewLabel_18 = new JLabel("");
		lblNewLabel_18.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/employee_log1.jpg")));
		lblNewLabel_18.setBounds(0, 0, 815, 395);
		panel_1.add(lblNewLabel_18);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("CUSTOMER REQUEST", null, panel_5, null);
		panel_5.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 39, 795, 109);
		panel_5.add(scrollPane_4);
		
		table_1 = new JTable();
		scrollPane_4.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try{
					int row=table_1.getSelectedRow();
					String T=(table_1.getModel().getValueAt(row, 0)).toString();
					String query="select * from internal_customer_transfer_message where AC_NO='"+T+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();

					textField_AC_FROM.setText(rs.getString("AC_NO"));
				    textField_AC_TO.setText(rs.getString("TO_AC"));
				    textField_AMOUNT1.setText(rs.getString("AMOUNT"));
				    
				    textField_BALANCE_AC_FROM.setText(Double.toString(getbalance(textField_AC_FROM.getText())));
				    textField_BALANCE_AC_TO.setText(Double.toString(getbalance(textField_AC_TO.getText())));

				    pst.close();
					rs.close();
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, e1);
					}
			
			}
		});
		
		JButton btnNewButton_11 = new JButton("REFRESH");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internal_transfer_refresh();
			}
		});
		btnNewButton_11.setBounds(10, 5, 128, 23);
		panel_5.add(btnNewButton_11);
		
		JLabel lblNewLabel_31 = new JLabel("FROM ACCOUNT:");
		lblNewLabel_31.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_31.setBounds(56, 193, 158, 14);
		panel_5.add(lblNewLabel_31);
		
		textField_AC_FROM = new JTextField();
		textField_AC_FROM.setEditable(false);
		textField_AC_FROM.setBounds(206, 190, 135, 20);
		panel_5.add(textField_AC_FROM);
		textField_AC_FROM.setColumns(10);
		
		JLabel lblNewLabel_32 = new JLabel("TO ACCOUNT:");
		lblNewLabel_32.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_32.setBounds(56, 289, 145, 14);
		panel_5.add(lblNewLabel_32);
		
		textField_AC_TO = new JTextField();
		textField_AC_TO.setEditable(false);
		textField_AC_TO.setBounds(206, 286, 135, 20);
		panel_5.add(textField_AC_TO);
		textField_AC_TO.setColumns(10);
		
		JLabel lblNewLabel_33 = new JLabel("TOTAL BALANCE:");
		lblNewLabel_33.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_33.setBounds(381, 193, 135, 14);
		panel_5.add(lblNewLabel_33);
		
		textField_BALANCE_AC_FROM = new JTextField();
		textField_BALANCE_AC_FROM.setEditable(false);
		textField_BALANCE_AC_FROM.setBounds(526, 190, 135, 20);
		panel_5.add(textField_BALANCE_AC_FROM);
		textField_BALANCE_AC_FROM.setColumns(10);
		
		JLabel lblNewLabel_34 = new JLabel("AMOUNT TO BE TRANSFERRED:");
		lblNewLabel_34.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_34.setBounds(56, 246, 211, 14);
		panel_5.add(lblNewLabel_34);
		
		textField_AMOUNT1 = new JTextField();
		textField_AMOUNT1.setEditable(false);
		textField_AMOUNT1.setBounds(277, 243, 184, 20);
		panel_5.add(textField_AMOUNT1);
		textField_AMOUNT1.setColumns(10);
		
		JLabel lblNewLabel_35 = new JLabel("TOTAL BALANCE:");
		lblNewLabel_35.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_35.setBounds(381, 289, 135, 14);
		panel_5.add(lblNewLabel_35);
		
		textField_BALANCE_AC_TO = new JTextField();
		textField_BALANCE_AC_TO.setEditable(false);
		textField_BALANCE_AC_TO.setBounds(526, 286, 135, 20);
		panel_5.add(textField_BALANCE_AC_TO);
		textField_BALANCE_AC_TO.setColumns(10);
		
		JButton btnNewButton_12 = new JButton("TRANSFER");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				double amount=Double.parseDouble(textField_AMOUNT1.getText());
				double balance1=getbalance(textField_AC_FROM.getText());
				double balance2=getbalance(textField_AC_TO.getText());
				
				if(amount<balance1){
					balance1-=amount;
			//update ac from............
					updating_balance(balance1,textField_AC_FROM.getText());
					
					balance2+=amount;
			// updating ac to
					updating_balance(balance2,textField_AC_TO.getText());
					
			//saving in record boook1,,,,,,,,,,,
					try{
						String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
						PreparedStatement pst2=conn.prepareStatement(statement);
						pst2.setString(1,textField_AC_FROM.getText());
						String name=getname(textField_AC_FROM);
						pst2.setString(2,name);
						pst2.setString(3,"INTERNAL TRANSFER");
						pst2.setString(4,textField_AMOUNT1.getText());
						pst2.setString(5,textField_AC_TO.getText());
						pst2.setString(6,"DBS");
						pst2.setString(7,time());
						pst2.execute();
						pst2.close();
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
						}
					
			//SAVING IN RECORD BOOK 2......................
					
					try{
						String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
						PreparedStatement pst21=conn.prepareStatement(statement);
						pst21.setString(1,textField_AC_TO.getText());
						String name=getname(textField_AC_TO);
						pst21.setString(2,name);
						pst21.setString(3,"DEPOSITED");
						pst21.setString(4,textField_AMOUNT1.getText());
						pst21.setString(5,"-");
						pst21.setString(6,"DBS");
						pst21.setString(7,time());
						pst21.execute();
						pst21.close();
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
						}
					JOptionPane.showMessageDialog(null, "Money transferred");
					
					textField_BALANCE_AC_FROM.setText(Double.toString(getbalance(textField_AC_FROM.getText())));
				    textField_BALANCE_AC_TO.setText(Double.toString(getbalance(textField_AC_TO.getText())));
				    authorization_internal_transfer(textField_AC_FROM.getText());
				    internal_transfer_refresh();
					
					
				}else{
					JOptionPane.showMessageDialog(null, "Account Doesn't have enough cash");
				}
				
				
				
				
			}
		});
		btnNewButton_12.setBounds(277, 328, 137, 23);
		panel_5.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("CLEAR");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_AC_FROM.setText(null);
				textField_AC_TO.setText(null);
				textField_BALANCE_AC_FROM.setText(null);
				textField_BALANCE_AC_TO.setText(null);
				textField_AMOUNT1.setText(null);
			}
		});
		btnNewButton_13.setBounds(429, 328, 111, 23);
		panel_5.add(btnNewButton_13);
		
		JButton btnNewButton_14 = new JButton("CLEAR CHART");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to CLEAR the chart ");
				if(confirmation==0){
					try{
						String query="Delete from internal_customer_transfer_message";
						PreparedStatement pst=conn.prepareStatement(query);
				        pst.execute();
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
					}
					
					try{
						String query="select AC_NO,NAME,AMOUNT,TO_AC,STATUS from internal_customer_transfer_message";
						PreparedStatement pst=conn.prepareStatement(query);
						ResultSet rs=pst.executeQuery();
						table_1.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
						pst.close();
						rs.close();
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
					}
				}
				
			}
		});
		btnNewButton_14.setBounds(640, 5, 165, 23);
		panel_5.add(btnNewButton_14);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 255, 102));
		tabbedPane.addTab("Transfer", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("AC NO");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 58, 78, 14);
		panel_2.add(lblNewLabel_2);
		
		textField_TRANSFER_AC = new JTextField();
		textField_TRANSFER_AC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Table_loading(textField_TRANSFER_AC,table_TRANSFER_FROM);
			}
		});
		textField_TRANSFER_AC.setBounds(146, 55, 122, 20);
		panel_2.add(textField_TRANSFER_AC);
		textField_TRANSFER_AC.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("AMOUNT");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 89, 98, 14);
		panel_2.add(lblNewLabel_3);
		
		textField_TRANSFER_AMOUNT = new JTextField();
		textField_TRANSFER_AMOUNT.setBounds(146, 86, 122, 20);
		panel_2.add(textField_TRANSFER_AMOUNT);
		textField_TRANSFER_AMOUNT.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("RECEIVER'S AC NO");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_4.setBounds(10, 118, 137, 25);
		panel_2.add(lblNewLabel_4);
		
		textField_TRANSFER_AC2 = new JTextField();
		textField_TRANSFER_AC2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Table_loading(textField_TRANSFER_AC2,table_TRANSFER_TO);
			}
		});
		textField_TRANSFER_AC2.setBounds(146, 120, 122, 20);
		panel_2.add(textField_TRANSFER_AC2);
		textField_TRANSFER_AC2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/send.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String transfer_type=(String)comboBox_TRANSFER_TYPE.getSelectedItem();
				
					
					try{
						
						double amount=Double.parseDouble(textField_TRANSFER_AMOUNT.getText());
						String ac=textField_TRANSFER_AC.getText();
						//double amount=Double.parseDouble(textField_TRANSFER_AMOUNT.getText());
						String query="select BALANCE from customer where ACCOUNT_NO='"+ac+"'";
						PreparedStatement pst=conn.prepareStatement(query);
						ResultSet rs=pst.executeQuery();
						double balance=Double.parseDouble(rs.getString("BALANCE"));
						
						
				if(transfer_type.equals("INTERNAL")==true){
						
						if(balance>amount){

						balance-=amount;
						query="Update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+ac+"'";
						PreparedStatement pst1=conn.prepareStatement(query);
						pst1.execute();
						String name1=getname(textField_TRANSFER_AC);
						
				//notification sending.......		
						String ntf="\n"+Double.toString(amount)+" tk transfered to "+textField_TRANSFER_AC2.getText()+" on "+time();
						notification(ac,ntf);
						
				//record book................		
						try{
							String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
							PreparedStatement pst2=conn.prepareStatement(statement);
							pst2.setString(1,textField_TRANSFER_AC.getText());
							String name=getname(textField_TRANSFER_AC);
							pst2.setString(2,name);
							pst2.setString(3,"INTERNAL TRANSFER");
							pst2.setString(4,textField_TRANSFER_AMOUNT.getText());
							pst2.setString(5,textField_TRANSFER_AC2.getText());
							pst2.setString(6,"DBS");
							pst2.setString(7,time());
							pst2.execute();
							pst2.close();
							}catch(Exception e){
								JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
							}
						
						    
							String ac2=textField_TRANSFER_AC2.getText();
							query="select BALANCE from customer where ACCOUNT_NO='"+ac2+"'";
							PreparedStatement pst2=conn.prepareStatement(query);
							ResultSet rs2=pst2.executeQuery();
							balance=Double.parseDouble(rs2.getString("BALANCE"));
							balance+=amount;
							
							query="Update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+ac2+"'";
							PreparedStatement pst3=conn.prepareStatement(query);
							pst3.execute();
							JOptionPane.showMessageDialog(null, "Money transfered");
							
			//RECORD BOOK
							try{
								String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
								PreparedStatement pst21=conn.prepareStatement(statement);
								pst21.setString(1,textField_TRANSFER_AC2.getText());
								String name=getname(textField_TRANSFER_AC2);
								pst21.setString(2,name);
								pst21.setString(3,"DEPOSITED");
								pst21.setString(4,textField_TRANSFER_AMOUNT.getText());
								pst21.setString(5,"-");
								pst21.setString(6,"DBS");
								pst21.setString(7,time());
								pst21.execute();
								pst21.close();
								}catch(Exception e){
									JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
								}
							
							pst2.close();
							rs2.close();
							pst3.close();
						}
						else JOptionPane.showMessageDialog(null, "Account does not have enough money to transfer");
						}
				else if(transfer_type.equals("EXTERNAL")==true){
					
					if(balance>amount){
						
							String query1="insert into external_transfer_message (AC_NO,NAME,AMOUNT,TO_AC,BANK_NAME) values(?,?,?,?,?)";
						    PreparedStatement pst1=conn.prepareStatement(query1);
						    pst1.setString(1, textField_TRANSFER_AC.getText());// '"+textField_ACCOUNT_NO.getText()+"'
						    pst1.setString(2, getname(textField_TRANSFER_AC.getText()));//'"+textField_CUSTOMER_NAME.getText()+"'
						    pst1.setString(3, textField_TRANSFER_AMOUNT.getText());//'"+textField_TRANSFER_AMOUNT.getText()+"'
						    pst1.setString(4, textField_TRANSFER_AC2.getText());//'"+textField_RECEIVER_AC.getText()+"'
						    pst1.setString(5, textField_BANK_NAME.getText());
						    pst1.execute();
						    
						    JOptionPane.showMessageDialog(null, "Transfer Request sent to the admin panel for review");
						    pst1.close();
					}else {
						JOptionPane.showMessageDialog(null,"ACCOUNT DOESN'T HAVE ENOUGH CASH");
					}
			}else JOptionPane.showMessageDialog(null, "select transfer type");
						
				pst.close();
				rs.close();
				
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "Account doesn't exist");
					}
					
					Table_loading(textField_TRANSFER_AC,table_TRANSFER_FROM);
					Table_loading(textField_TRANSFER_AC2,table_TRANSFER_TO);	
		
			}
		});
		
		btnNewButton_1.setBounds(146, 313, 46, 33);
		panel_2.add(btnNewButton_1);
		
		table_TRANSFER_FROM = new JTable();
		table_TRANSFER_FROM.setBounds(146, 233, 299, 25);
		panel_2.add(table_TRANSFER_FROM);
		
		JLabel lblNewLabel_6 = new JLabel("TRANSFER FROM:");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_6.setBounds(10, 233, 146, 14);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("TRANSFER TO:");
		lblNewLabel_7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(10, 280, 137, 14);
		panel_2.add(lblNewLabel_7);
		
		table_TRANSFER_TO = new JTable();
		table_TRANSFER_TO.setBounds(146, 280, 299, 25);
		panel_2.add(table_TRANSFER_TO);
		
		comboBox_TRANSFER_TYPE = new JComboBox();
		comboBox_TRANSFER_TYPE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String transfer_type=(String)comboBox_TRANSFER_TYPE.getSelectedItem();
				if(transfer_type.equals("INTERNAL")==true) textField_BANK_NAME.setEditable(false);
				else textField_BANK_NAME.setEditable(true);
			}
		});
		comboBox_TRANSFER_TYPE.setModel(new DefaultComboBoxModel(new String[] {"INTERNAL", "EXTERNAL"}));
		comboBox_TRANSFER_TYPE.setBounds(146, 151, 122, 20);
		panel_2.add(comboBox_TRANSFER_TYPE);
		
		JLabel lblNewLabel_9 = new JLabel("TRANSFER TYPE:");
		lblNewLabel_9.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(10, 154, 137, 14);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("BANK NAME:");
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(10, 185, 122, 14);
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_27 = new JLabel("DONE");
		lblNewLabel_27.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
		lblNewLabel_27.setEnabled(true);
		lblNewLabel_27.setForeground(Color.WHITE);
		lblNewLabel_27.setBounds(146, 373, 46, 14);
		panel_2.add(lblNewLabel_27);
		
		JLabel lblNewLabel_28 = new JLabel("CLEAR");
		lblNewLabel_28.setForeground(Color.WHITE);
		lblNewLabel_28.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
		lblNewLabel_28.setEnabled(true);
		lblNewLabel_28.setBounds(224, 373, 63, 14);
		panel_2.add(lblNewLabel_28);
		
		textField_BANK_NAME = new JTextField();
		textField_BANK_NAME.setEditable(false);
		textField_BANK_NAME.setBounds(146, 182, 122, 20);
		panel_2.add(textField_BANK_NAME);
		textField_BANK_NAME.setColumns(10);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/cleaning-service.png")));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    textField_TRANSFER_AC.setText(null);
			    textField_TRANSFER_AMOUNT.setText(null);
			    textField_TRANSFER_AC2.setText(null);
			    textField_BANK_NAME.setText(null);
			     
			    
			}
		});
		btnNewButton_5.setBounds(222, 313, 46, 33);
		panel_2.add(btnNewButton_5);
		
		JLabel lblNewLabel_26 = new JLabel("");
		lblNewLabel_26.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/admin_pic.jpg")));
		lblNewLabel_26.setBounds(0, 0, 815, 395);
		panel_2.add(lblNewLabel_26);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(204, 204, 255));
		tabbedPane.addTab("BALANCE CHECK", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("AC NO:");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(46, 72, 64, 14);
		panel_3.add(lblNewLabel_5);
		
		textField_BALANCE_CHECK = new JTextField();
		textField_BALANCE_CHECK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Table_loading(textField_BALANCE_CHECK,table_BALANCE_CHECK);
			}
		});
		
		JButton btnNewButton_9 = new JButton("");
		btnNewButton_9.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/detective-magnifying-glass.png")));
		btnNewButton_9.setBounds(263, 56, 49, 33);
		panel_3.add(btnNewButton_9);
		
		textField_BALANCE_CHECK.setBounds(120, 69, 132, 20);
		panel_3.add(textField_BALANCE_CHECK);
		textField_BALANCE_CHECK.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(45, 110, 407, 75);
		panel_3.add(scrollPane_2);
		
		table_BALANCE_CHECK = new JTable();
		scrollPane_2.setViewportView(table_BALANCE_CHECK);
		
		JLabel lblNewLabel_29 = new JLabel("");
		lblNewLabel_29.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/final.jpg")));
		lblNewLabel_29.setBounds(0, 0, 815, 395);
		panel_3.add(lblNewLabel_29);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 255, 102));
		tabbedPane.addTab("BANK STATEMENT", null, panel_4, null);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 148, 795, 225);
		panel_4.add(scrollPane_3);
		

		table_STATEMENT = new JTable();
		scrollPane_3.setViewportView(table_STATEMENT);
		
		textField_STATEMENT_SEARCH = new JTextField();
		textField_STATEMENT_SEARCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					
					String query="select * from record_book where AC_NO=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1,textField_STATEMENT_SEARCH.getText());
					ResultSet rs=pst.executeQuery();
					table_STATEMENT.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
					}catch(Exception e1){
						e1.printStackTrace();
					}
			}
		});
		
		JButton btnNewButton_10 = new JButton("");
		btnNewButton_10.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/eye.png")));
		btnNewButton_10.setBounds(10, 35, 46, 33);
		panel_4.add(btnNewButton_10);
		textField_STATEMENT_SEARCH.setBounds(61, 48, 123, 20);
		panel_4.add(textField_STATEMENT_SEARCH);
		textField_STATEMENT_SEARCH.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/printer.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					MessageFormat header =new MessageFormat("DAFFODIL BANKING SYSTEM");
					MessageFormat footer =new MessageFormat("All rights Reserved 2018");
					try{
						table_STATEMENT.print(JTable.PrintMode.NORMAL,header,footer);
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "can not be printed");
					}
				}
			
		});
		
		JLabel lblNewLabel_30 = new JLabel("PRINT");
		lblNewLabel_30.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_30.setForeground(Color.WHITE);
		lblNewLabel_30.setBounds(10, 90, 46, 14);
		panel_4.add(lblNewLabel_30);
		btnNewButton_4.setBounds(10, 106, 46, 33);
		panel_4.add(btnNewButton_4);
		
		JLabel lblNewLabel_17 = new JLabel("");
		lblNewLabel_17.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/cool-background.jpg")));
		lblNewLabel_17.setBounds(0, 0, 815, 395);
		panel_4.add(lblNewLabel_17);
		
		JPanel panel_6 = new JPanel();
		panel_6.setForeground(Color.WHITE);
		tabbedPane.addTab("ACCOUNT SETTINGS", null, panel_6, null);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_36 = new JLabel("USERNAME:");
		lblNewLabel_36.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_36.setForeground(Color.WHITE);
		lblNewLabel_36.setBounds(151, 129, 118, 14);
		panel_6.add(lblNewLabel_36);
		
		JLabel lblNewLabel_39 = new JLabel("SAVE");
		lblNewLabel_39.setForeground(Color.WHITE);
		lblNewLabel_39.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_39.setBounds(301, 296, 95, 14);
		panel_6.add(lblNewLabel_39);
		
		textField_USERNAME = new JTextField();
		textField_USERNAME.setBounds(279, 126, 155, 20);
		panel_6.add(textField_USERNAME);
		textField_USERNAME.setColumns(10);
		
		JLabel lblNewLabel_37 = new JLabel("PASSWORD");
		lblNewLabel_37.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_37.setForeground(Color.WHITE);
		lblNewLabel_37.setBounds(151, 183, 118, 14);
		panel_6.add(lblNewLabel_37);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(279, 180, 155, 20);
		panel_6.add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		JButton btnNewButton_15 = new JButton("");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to SAVE changes");
				if(confirmation==0){
				try{
					String query="UPDATE employee SET USERNAME='"+textField_USERNAME.getText()+"', PASSWORD='"+textField_PASSWORD.getText()+"' WHERE ID='"+EMPLOYEE_LOG_IN.employee_id+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
		            pst.execute();
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				}
			}
		});
		btnNewButton_15.setToolTipText("save changes");
		btnNewButton_15.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/clipboard.png")));
		btnNewButton_15.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		btnNewButton_15.setForeground(Color.WHITE);
		btnNewButton_15.setBounds(288, 251, 67, 33);
		panel_6.add(btnNewButton_15);
		
		JLabel lblNewLabel_38 = new JLabel("");
		lblNewLabel_38.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/rr.jpg")));
		lblNewLabel_38.setBounds(0, 0, 815, 395);
		panel_6.add(lblNewLabel_38);
		
		JLabel lblNewLabel_8 = new JLabel("LOGGED IN AS:");
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(10, 11, 132, 21);
		frame.getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_15 = new JLabel("LOG OUT");
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_15.setBounds(26, 355, 87, 21);
		frame.getContentPane().add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("CUSTOMER FORM");
		lblNewLabel_16.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setBounds(122, 355, 125, 21);
		frame.getContentPane().add(lblNewLabel_16);
		
		lblNewLabel_USER_NAME = new JLabel("");
		lblNewLabel_USER_NAME.setForeground(Color.WHITE);
		lblNewLabel_USER_NAME.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_USER_NAME.setBounds(168, 8, 158, 24);
		frame.getContentPane().add(lblNewLabel_USER_NAME);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("form");
		btnNewButton_2.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/form.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CUSTOMER_ENROLLING obj=new CUSTOMER_ENROLLING();
				obj.setVisible();
			}
		});
		btnNewButton_2.setBounds(148, 311, 57, 33);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_11 = new JLabel("EMPLOYEE NAME:");
		lblNewLabel_11.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setBounds(10, 207, 132, 14);
		frame.getContentPane().add(lblNewLabel_11);
		
		textField__EMPLOYEE_NAME = new JTextField();
		textField__EMPLOYEE_NAME.setBackground(Color.LIGHT_GRAY);
		textField__EMPLOYEE_NAME.setEditable(false);
		textField__EMPLOYEE_NAME.setBounds(169, 204, 132, 20);
		frame.getContentPane().add(textField__EMPLOYEE_NAME);
		textField__EMPLOYEE_NAME.setColumns(10);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(10, 43, 125, 112);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		JLabel lblNewLabel_12 = new JLabel("EMPLOYEE POSITION:");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_12.setBounds(10, 238, 149, 14);
		frame.getContentPane().add(lblNewLabel_12);
		
		textField_EMPLOYEE_POSITION = new JTextField();
		textField_EMPLOYEE_POSITION.setBackground(Color.LIGHT_GRAY);
		textField_EMPLOYEE_POSITION.setEditable(false);
		textField_EMPLOYEE_POSITION.setBounds(169, 235, 132, 20);
		frame.getContentPane().add(textField_EMPLOYEE_POSITION);
		textField_EMPLOYEE_POSITION.setColumns(10);
		
		JButton btnNewButton_6 = new JButton("");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EMPLOYEE_LOG_IN obj=new EMPLOYEE_LOG_IN();
				obj.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_6.setToolTipText("log out");
		btnNewButton_6.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/logout.png")));
		btnNewButton_6.setBounds(26, 311, 57, 33);
		frame.getContentPane().add(btnNewButton_6);
		
		JLabel lblNewLabel_14 = new JLabel("EMPLOYEE ID:");
		lblNewLabel_14.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_14.setForeground(Color.WHITE);
		lblNewLabel_14.setBounds(10, 176, 132, 14);
		frame.getContentPane().add(lblNewLabel_14);
		
		textField_EMPLOYEE_ID = new JTextField();
		textField_EMPLOYEE_ID.setEditable(false);
		textField_EMPLOYEE_ID.setBackground(Color.LIGHT_GRAY);
		textField_EMPLOYEE_ID.setBounds(169, 173, 132, 20);
		frame.getContentPane().add(textField_EMPLOYEE_ID);
		textField_EMPLOYEE_ID.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("");
		lblNewLabel_13.setIcon(new ImageIcon(EMPLOYEE_PANEL.class.getResource("/img/6.jpg")));
		lblNewLabel_13.setBounds(0, 0, 390, 424);
		frame.getContentPane().add(lblNewLabel_13);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public void setvisible() {
		// TODO Auto-generated method stub
		EMPLOYEE_PANEL window = new EMPLOYEE_PANEL();
		window.frame.setVisible(true);
	}
}
