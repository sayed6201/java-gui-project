import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.Window.Type;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import java.awt.Toolkit;

public class ADMIN_PANEL {

	private JFrame frame;
	private JTextField textField_INTEREST_RATE;
	private JTextField textField_LOAN_RATE;
	private JTable table_COMPLAINS;
    Connection conn;
    
    JTextArea textArea;
    private JTable table_EXTERNAL_TRANSFER;
    private JTextField textField_NAME;
    private JTextField textField_ID;
    private JTextField textField_POSITION;
    JLabel LABEL_USER_NAME;
    private JTextField textField_CUSTOMER_NAME;
    private JTextField textField_ACCOUNT_NO_FROM;
    private JTextField textField_BANK_NAME;
    private JTextField textField_AC_NO_2;
    private JTextField textField_AMOUNT;
    private JTextField textField_TOTAL;
    private JTextField textField_BONUS;
    private JTextField textField_BONUS_AMOUNT;
    private JTable table_BONUS;
    private JTable table_INTEREST;
    private JTextField textField_CHARGE_PER_MONTH;
    private JTextField textField_CHARGE_PER_ANUM;
    private JTable table_CHARGE_PER_MONTH;
    private JTable table_CHARGE_PER_ANUM;
    private JTextField textField_TOTAL_ACCOUNT;
    JLabel lblNewLabel_IMAGE;
    private JTextField textField_BANK_FUND;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMIN_PANEL window = new ADMIN_PANEL();
					window.frame.setVisible(true);
					
				    
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */

	public ADMIN_PANEL() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
		setting_fields();
	    current_interest_rate();
	    current_charge_per_month();
	    current_charge_per_anum();
	}

void pending_request(){
	try{
		String query="select AC_NO,BANK_NAME,AMOUNT,TO_AC,STATUS from external_transfer_message";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		table_EXTERNAL_TRANSFER.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
		pst.close();
		rs.close();
	}catch(Exception e){
		JOptionPane.showMessageDialog(null, e);
	}
}
/*
	public void tester(){
		start_month;
		Calendar cal = Calendar.getInstance();
		int month =1+ cal.get(Calendar.MONTH);
		if(start_month!=month){

		}

	}
*/

	/**
	 * Initialize the contents of the frame.
	 */
	
	 public void current_charge_per_month(){
		   try{
		    String query="select CHARGE_PER_MONTH FROM customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table_CHARGE_PER_MONTH.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
	   }catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
		}
	   }	
	
	 public void current_charge_per_anum(){
		   try{
		    String query="select CHARGE_PER_ANUM FROM customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table_CHARGE_PER_ANUM.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
	   }catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
		}
	   }
	 
	
   public void current_interest_rate(){
	   try{
	    String query="select INTEREST FROM customer";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		table_INTEREST.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
		pst.close();
		rs.close();
   }catch(Exception e1){
		JOptionPane.showMessageDialog(null, e1);
	}
   }

	public void table_refresh(){
		try{
			String query="select ID,NAME,SALARY,BONUS,BALANCE from employee where ID='"+textField_BONUS.getText()+"'";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table_BONUS.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
		}
	}

	public String time() {

		   DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
		   Date date = new Date();
		   return dateFormat.format(date);
	   }

   void setting_fields(){

		textField_NAME.setText(ADMIN_LOG_IN.admin_name);
		textField_ID.setText(ADMIN_LOG_IN.admin_id);
		textField_POSITION.setText(ADMIN_LOG_IN.admin_position);
		LABEL_USER_NAME.setText(ADMIN_LOG_IN.admin_user_name);
		if(ADMIN_LOG_IN.img_data!=null){
			ImageIcon img=new ImageIcon(new ImageIcon(ADMIN_LOG_IN.img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
			lblNewLabel_IMAGE.setIcon(img);}
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
   
   public void authorization_status(JTextField textField_ACCOUNT_NO_FROM){
		try {
			String query="update external_transfer_message set STATUS='AUTHORIZED' where AC_NO='"+textField_ACCOUNT_NO_FROM.getText()+"'";
			PreparedStatement pst= conn.prepareStatement(query);
			pst.execute();
	        pst.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   
   void clear_pending_request(){
	   int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to CLEAR ");
		if(confirmation==0){
		try{
			String query="Delete from external_transfer_message";
			PreparedStatement pst=conn.prepareStatement(query);
	        pst.execute();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
   }

	void referesh(){
		try{

			String query="select * from complains";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table_COMPLAINS.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
			}
	}
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ADMIN_PANEL.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.setType(Type.POPUP);
		frame.setBounds(100, 100, 1048, 513);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(396, 0, 646, 466);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("TRANSECTION", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 22, 416, 84);
		panel.add(scrollPane_2);

		table_EXTERNAL_TRANSFER = new JTable();
		scrollPane_2.setViewportView(table_EXTERNAL_TRANSFER);
		table_EXTERNAL_TRANSFER.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					int row=table_EXTERNAL_TRANSFER.getSelectedRow();
					String T=(table_EXTERNAL_TRANSFER.getModel().getValueAt(row, 0)).toString();
					String query="select * from external_transfer_message where AC_NO='"+T+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();

					textField_CUSTOMER_NAME.setText(rs.getString("NAME"));
				    textField_ACCOUNT_NO_FROM.setText(rs.getString("AC_NO"));
				    textField_BANK_NAME.setText(rs.getString("BANK_NAME"));
				    textField_AC_NO_2.setText(rs.getString("TO_AC"));
				    textField_AMOUNT.setText(rs.getString("AMOUNT"));

				    pst.close();
					rs.close();
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
					}


			}
		});
		table_EXTERNAL_TRANSFER.setBackground(SystemColor.menu);

		JButton btnNewButton_4 = new JButton("SEE PENDING TRANSECTION");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pending_request();
			}
		});
		btnNewButton_4.setBackground(SystemColor.activeCaptionBorder);
		btnNewButton_4.setBounds(10, 0, 224, 23);
		panel.add(btnNewButton_4);

		textField_CUSTOMER_NAME = new JTextField();
		textField_CUSTOMER_NAME.setEditable(false);
		textField_CUSTOMER_NAME.setBounds(222, 141, 126, 20);
		panel.add(textField_CUSTOMER_NAME);
		textField_CUSTOMER_NAME.setColumns(10);

		textField_ACCOUNT_NO_FROM = new JTextField();
		textField_ACCOUNT_NO_FROM.setEditable(false);
		textField_ACCOUNT_NO_FROM.setBounds(222, 172, 126, 20);
		panel.add(textField_ACCOUNT_NO_FROM);
		textField_ACCOUNT_NO_FROM.setColumns(10);

		textField_BANK_NAME = new JTextField();
		textField_BANK_NAME.setEditable(false);
		textField_BANK_NAME.setBounds(222, 222, 126, 20);
		panel.add(textField_BANK_NAME);
		textField_BANK_NAME.setColumns(10);

		JButton btnNewButton_6 = new JButton("AUTHORIZE TRANSECTION");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query="select BALANCE from customer where ACCOUNT_NO='"+textField_ACCOUNT_NO_FROM.getText()+"'";
			    double balance=0;
			    double transfer=Double.parseDouble(textField_AMOUNT.getText());
				try {
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					balance=Double.parseDouble(rs.getString("BALANCE"));
					if(balance>transfer){
						balance=balance-transfer;
						query="update customer set BALANCE='"+balance+"' where ACCOUNT_NO='"+textField_ACCOUNT_NO_FROM.getText()+"'";
						PreparedStatement pst1=conn.prepareStatement(query);
						pst1.execute();
						JOptionPane.showMessageDialog(null, "successfully transfered");

				//sending notification......
						String ntf="\n"+Double.toString(transfer)+" tk transfered to "+textField_BANK_NAME.getText()+" at AC: "+textField_AC_NO_2.getText()+" on "+time();
						notification(textField_ACCOUNT_NO_FROM.getText(),ntf);
				//saving to record book

					    try{
							String statement="insert into record_book (AC_NO,NAME,TRANSECTION_TYPE,AMOUNT,TO_AC_NO,BANK_NAME,DATE) values(?,?,?,?,?,?,?)";
							PreparedStatement pst2=conn.prepareStatement(statement);

							pst2.setString(1,textField_ACCOUNT_NO_FROM.getText());
							pst2.setString(2,textField_CUSTOMER_NAME.getText());
							pst2.setString(3,"EXTERNAL TRANSFER");
							pst2.setString(4,textField_AMOUNT.getText());
							pst2.setString(5,textField_AC_NO_2.getText());
							pst2.setString(6,textField_BANK_NAME.getText());
							pst2.setString(7,time());
							pst2.execute();
							pst2.close();
							}catch(Exception e1){
								JOptionPane.showMessageDialog(null, "COULDN'T RECORD");
							}
					    
				//Marking status...........
					    authorization_status(textField_ACCOUNT_NO_FROM);
						pst1.close();
					}
					else{
						JOptionPane.showMessageDialog(null, "insufficient cash");
					}

					pst.close();
					rs.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pending_request();
			}
		});
		btnNewButton_6.setForeground(Color.DARK_GRAY);
		btnNewButton_6.setBackground(new Color(153, 102, 204));
		btnNewButton_6.setBounds(143, 338, 235, 23);
		panel.add(btnNewButton_6);

		JLabel lblNewLabel_16 = new JLabel("BANK NAME:");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_16.setBounds(32, 224, 148, 14);
		panel.add(lblNewLabel_16);

		textField_AC_NO_2 = new JTextField();
		textField_AC_NO_2.setEditable(false);
		textField_AC_NO_2.setBounds(222, 253, 126, 20);
		panel.add(textField_AC_NO_2);
		textField_AC_NO_2.setColumns(10);
		
		JLabel lblNewLabel_29 = new JLabel("CLEAR");
		lblNewLabel_29.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_29.setForeground(Color.WHITE);
		lblNewLabel_29.setBounds(478, 85, 63, 21);
		panel.add(lblNewLabel_29);

		textField_AMOUNT = new JTextField();
		textField_AMOUNT.setEditable(false);
		textField_AMOUNT.setBounds(221, 284, 127, 20);
		panel.add(textField_AMOUNT);
		textField_AMOUNT.setColumns(10);
		
		JButton btnNewButton_11 = new JButton("");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear_pending_request();
			}
		});
		btnNewButton_11.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/cleaning-service.png")));
		btnNewButton_11.setBounds(479, 41, 43, 42);
		panel.add(btnNewButton_11);

		JLabel lblNewLabel_18 = new JLabel("AMOUNT:");
		lblNewLabel_18.setForeground(Color.WHITE);
		lblNewLabel_18.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_18.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_18.setBounds(32, 286, 126, 14);
		panel.add(lblNewLabel_18);

		JLabel lblNewLabel_17 = new JLabel("RECEIVER'S AC NO:");
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_17.setBounds(32, 255, 172, 14);
		panel.add(lblNewLabel_17);

		JLabel lblNewLabel_15 = new JLabel("TO:");
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_15.setBounds(10, 199, 74, 14);
		panel.add(lblNewLabel_15);

		JLabel lblNewLabel_14 = new JLabel("ACCOUNT NO:");
		lblNewLabel_14.setForeground(Color.WHITE);
		lblNewLabel_14.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_14.setBounds(32, 174, 134, 14);
		panel.add(lblNewLabel_14);

		JLabel lblNewLabel_11 = new JLabel("FROM:");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_11.setBounds(10, 117, 63, 14);
		panel.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("CUSTOMER NAME:");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_12.setBounds(32, 142, 148, 14);
		panel.add(lblNewLabel_12);

		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/house.jpg")));
		lblNewLabel_5.setBounds(0, -27, 641, 465);
		panel.add(lblNewLabel_5);

		JLabel lblNewLabel_13 = new JLabel("ACCOUNT NO:");
		lblNewLabel_13.setBackground(SystemColor.windowText);
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_13.setBounds(32, 167, 117, 14);
		panel.add(lblNewLabel_13);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("RATE SETTING", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("SET INTEREST RATE:");
		lblNewLabel_2.setBounds(10, 123, 145, 14);
		panel_1.add(lblNewLabel_2);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));

		textField_INTEREST_RATE = new JTextField();
		textField_INTEREST_RATE.setBounds(218, 120, 86, 20);
		panel_1.add(textField_INTEREST_RATE);
		textField_INTEREST_RATE.setColumns(10);

		JButton btnNewButton = new JButton("SET");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		         
			    try {
			    	double rate=Double.parseDouble(textField_INTEREST_RATE.getText());
			        double f=(rate/100)/12;
			    	String query="update customer set INTEREST='"+rate+"',INTEREST_FRACTION='"+f+"',ADDITION='"+f+"'*BALANCE ";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();

					JOptionPane.showMessageDialog(null, "set");
					pst.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    current_interest_rate();

			}
		});
		btnNewButton.setBounds(314, 118, 61, 23);
		panel_1.add(btnNewButton);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		
		table_INTEREST = new JTable();
		table_INTEREST.setBounds(238, 299, 115, 14);
		panel_1.add(table_INTEREST);

		JLabel lblNewLabel_3 = new JLabel("SET LOAN RATE: ");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 154, 145, 14);
		panel_1.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		
		JLabel lblNewLabel_23 = new JLabel("SERVICE CHARGE PER MONTH:");
		lblNewLabel_23.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_23.setForeground(Color.WHITE);
		lblNewLabel_23.setBounds(10, 59, 211, 14);
		panel_1.add(lblNewLabel_23);
		
		JLabel lblNewLabel_24 = new JLabel("SERVICE CHARGE PER ANUM:");
		lblNewLabel_24.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_24.setForeground(Color.WHITE);
		lblNewLabel_24.setBounds(10, 84, 211, 14);
		panel_1.add(lblNewLabel_24);
		
		textField_CHARGE_PER_MONTH = new JTextField();
		textField_CHARGE_PER_MONTH.setBounds(218, 56, 86, 20);
		panel_1.add(textField_CHARGE_PER_MONTH);
		textField_CHARGE_PER_MONTH.setColumns(10);
		
		JButton btnNewButton_9 = new JButton("SET");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double charge=Double.parseDouble(textField_CHARGE_PER_MONTH.getText());
				try{
					String query="update customer set CHARGE_PER_MONTH='"+charge+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					pst.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				current_charge_per_month();
				
			}
		});
		btnNewButton_9.setBounds(314, 55, 61, 23);
		panel_1.add(btnNewButton_9);
		
		JLabel lblNewLabel_25 = new JLabel("CURRENT CHARGE PER MONTH:");
		lblNewLabel_25.setForeground(Color.WHITE);
		lblNewLabel_25.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_25.setBounds(10, 243, 211, 14);
		panel_1.add(lblNewLabel_25);
		
		textField_CHARGE_PER_ANUM = new JTextField();
		textField_CHARGE_PER_ANUM.setBounds(218, 87, 86, 20);
		panel_1.add(textField_CHARGE_PER_ANUM);
		textField_CHARGE_PER_ANUM.setColumns(10);
		
		JButton btnNewButton_10 = new JButton("SET");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 double charge=Double.parseDouble(textField_CHARGE_PER_ANUM.getText());
				try{
					String query="update customer set CHARGE_PER_ANUM='"+charge+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					pst.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				current_charge_per_anum();
			}
		});
		btnNewButton_10.setBounds(314, 84, 61, 23);
		panel_1.add(btnNewButton_10);
		
		table_CHARGE_PER_ANUM = new JTable();
		table_CHARGE_PER_ANUM.setBounds(238, 274, 115, 14);
		panel_1.add(table_CHARGE_PER_ANUM);
		
		table_CHARGE_PER_MONTH = new JTable();
		table_CHARGE_PER_MONTH.setBounds(238, 243, 115, 14);
		panel_1.add(table_CHARGE_PER_MONTH);

		textField_LOAN_RATE = new JTextField();
		textField_LOAN_RATE.setBounds(218, 151, 86, 20);
		panel_1.add(textField_LOAN_RATE);
		textField_LOAN_RATE.setColumns(10);
		
		JLabel lblNewLabel_26 = new JLabel("CURRENT CHARGE PER ANUM:");
		lblNewLabel_26.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_26.setForeground(Color.WHITE);
		lblNewLabel_26.setBounds(10, 274, 211, 14);
		panel_1.add(lblNewLabel_26);
		
		JLabel lblNewLabel_22 = new JLabel("CURRENT INTEREST RATE:");
		lblNewLabel_22.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_22.setForeground(Color.WHITE);
		lblNewLabel_22.setBounds(10, 299, 218, 14);
		panel_1.add(lblNewLabel_22);

		JButton btnNewButton_1 = new JButton("SET");
		btnNewButton_1.setBounds(316, 149, 59, 23);
		panel_1.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		lblNewLabel_1.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/admin_pic.jpg")));
		lblNewLabel_1.setBounds(0, 0, 641, 438);
		panel_1.add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 0, 255));
		tabbedPane.addTab("COMPLAINS", null, panel_2, null);
		panel_2.setLayout(null);

		table_COMPLAINS = new JTable();
		table_COMPLAINS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
				int row=table_COMPLAINS.getSelectedRow();
				String T=(table_COMPLAINS.getModel().getValueAt(row, 0)).toString();
				String query="select * from complains where ACCOUNT_NO='"+T+"'";
				PreparedStatement pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();

				textArea.setText(rs.getString("COMPLAINS"));

				pst.close();
				rs.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		table_COMPLAINS.setBounds(10, 22, 383, 62);
		panel_2.add(table_COMPLAINS);

		JButton btnNewButton_2 = new JButton("LOAD COMPLAINS");
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query="select * from complains";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table_COMPLAINS.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
					pst.close();
					rs.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnNewButton_2.setBounds(10, 0, 172, 23);
		panel_2.add(btnNewButton_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 383, 62);
		panel_2.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 106, 225, 129);
		panel_2.add(textArea);

		JButton btnNewButton_3 = new JButton("CLEAR");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
			}
		});
		btnNewButton_3.setBounds(245, 107, 89, 23);
		panel_2.add(btnNewButton_3);

		JButton btnNewButton_DELETE = new JButton("DELETE");
		btnNewButton_DELETE.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try{
					int row=table_COMPLAINS.getSelectedRow();
					String T=(table_COMPLAINS.getModel().getValueAt(row, 0)).toString();
					String query="delete from complains where ACCOUNT_NO='"+T+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					//ResultSet rs=pst.executeQuery();
					pst.execute();
					//textArea.setText(rs.getString("COMPLAINS"));

					pst.close();
					//rs.close();
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, e1);
					}
				referesh();
			}
		});
		btnNewButton_DELETE.setBounds(245, 141, 89, 23);
		panel_2.add(btnNewButton_DELETE);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/BH.jpg")));
		lblNewLabel.setBounds(0, 0, 641, 438);
		panel_2.add(lblNewLabel);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("BONUS PAYMENT", null, panel_4, null);
		panel_4.setLayout(null);

		textField_BONUS = new JTextField();
		textField_BONUS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table_refresh();
			}
		});
		textField_BONUS.setBounds(141, 38, 104, 20);
		panel_4.add(textField_BONUS);
		textField_BONUS.setColumns(10);

		JLabel lblNewLabel_19 = new JLabel();
		lblNewLabel_19.setForeground(Color.WHITE);
		lblNewLabel_19.setText("EMPLOYEE ID:");
		lblNewLabel_19.setBounds(10, 38, 124, 21);
		panel_4.add(lblNewLabel_19);

		JLabel lblNewLabel_21 = new JLabel("AMOUNT");
		lblNewLabel_21.setForeground(Color.WHITE);
		lblNewLabel_21.setBounds(10, 70, 124, 14);
		panel_4.add(lblNewLabel_21);

		textField_BONUS_AMOUNT = new JTextField();
		textField_BONUS_AMOUNT.setBounds(141, 67, 104, 20);
		panel_4.add(textField_BONUS_AMOUNT);
		textField_BONUS_AMOUNT.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(96, 187, 328, 78);
		panel_4.add(scrollPane_1);

		table_BONUS = new JTable();
		scrollPane_1.setViewportView(table_BONUS);

		JButton btnNewButton_8 = new JButton("SET");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="update employee set BONUS='"+textField_BONUS_AMOUNT.getText()+"' where ID='"+textField_BONUS.getText()+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				table_refresh();
			}
		});
		btnNewButton_8.setBounds(112, 124, 89, 23);
		panel_4.add(btnNewButton_8);
		
		JLabel lblNewLabel_30 = new JLabel("");
		lblNewLabel_30.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/ADMIN_NATURE.jpg")));
		lblNewLabel_30.setBounds(0, 0, 641, 438);
		panel_4.add(lblNewLabel_30);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("TOTAL BALANCE", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel lblNewLabel_10 = new JLabel("TOTAL BALANCE:");
		lblNewLabel_10.setBackground(Color.BLACK);
		lblNewLabel_10.setBounds(10, 102, 154, 14);
		panel_3.add(lblNewLabel_10);
		lblNewLabel_10.setForeground(SystemColor.text);
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));

		JButton btnNewButton_7 = new JButton("");
		btnNewButton_7.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/loading-cloud.png")));
		btnNewButton_7.setToolTipText("REFRESH");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String total_balance=null;
				try{
					String query="select sum(BALANCE) from customer";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();

					if(rs.next()){
						total_balance=rs.getString("sum(BALANCE)");
						textField_TOTAL.setText(total_balance);
					}

					rs.close();
					pst.close();


					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, e1);
					}
				textField_TOTAL_ACCOUNT.setText(Integer.toString(new CALCULATION().customer_number()));
				
			}
		});
		btnNewButton_7.setBounds(15, 36, 73, 33);
		panel_3.add(btnNewButton_7);

		textField_TOTAL = new JTextField();
		textField_TOTAL.setEditable(false);
		textField_TOTAL.setBounds(168, 100, 293, 20);
		panel_3.add(textField_TOTAL);
		textField_TOTAL.setColumns(10);
		
		JLabel lblNewLabel_27 = new JLabel("TOTAL ACCOUNTS:");
		lblNewLabel_27.setForeground(SystemColor.text);
		lblNewLabel_27.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_27.setBounds(10, 133, 154, 14);
		panel_3.add(lblNewLabel_27);
		
		textField_TOTAL_ACCOUNT = new JTextField();
		textField_TOTAL_ACCOUNT.setEditable(false);
		textField_TOTAL_ACCOUNT.setBounds(168, 131, 293, 20);
		panel_3.add(textField_TOTAL_ACCOUNT);
		textField_TOTAL_ACCOUNT.setColumns(10);
		
		JLabel lblNewLabel_31 = new JLabel("");
		lblNewLabel_31.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/LOGIN_2.jpg")));
		lblNewLabel_31.setBounds(0, 0, 641, 438);
		panel_3.add(lblNewLabel_31);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("BANK FUND", null, panel_5, null);
		panel_5.setLayout(null);
		
		JButton btnNewButton_12 = new JButton("SEE BANK FUND");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String query="SELECT BALANCE FROM BANK_FUND";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					
					textField_BANK_FUND.setText(rs.getString("BALANCE"));
					
					pst.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton_12.setBounds(31, 159, 168, 23);
		panel_5.add(btnNewButton_12);
		
		textField_BANK_FUND = new JTextField();
		textField_BANK_FUND.setEditable(false);
		textField_BANK_FUND.setBounds(230, 156, 249, 29);
		panel_5.add(textField_BANK_FUND);
		textField_BANK_FUND.setColumns(10);
		
		JLabel lblNewLabel_32 = new JLabel("DAFFODIL BANKING SYSTEM");
		lblNewLabel_32.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_32.setBounds(188, 59, 424, 14);
		panel_5.add(lblNewLabel_32);

		textField_NAME = new JTextField();
		textField_NAME.setEditable(false);
		textField_NAME.setBounds(189, 260, 128, 20);
		frame.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);

		textField_ID = new JTextField();
		textField_ID.setEditable(false);
		textField_ID.setBounds(189, 291, 128, 20);
		frame.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);

		textField_POSITION = new JTextField();
		textField_POSITION.setEditable(false);
		textField_POSITION.setBounds(189, 322, 128, 20);
		frame.getContentPane().add(textField_POSITION);
		textField_POSITION.setColumns(10);

		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setToolTipText("Log Out");
		btnNewButton_5.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/power.png")));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ADMIN_LOG_IN obj=new ADMIN_LOG_IN();
				obj.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_5.setBackground(Color.LIGHT_GRAY);
		btnNewButton_5.setBounds(162, 367, 73, 31);
		frame.getContentPane().add(btnNewButton_5);

		LABEL_USER_NAME = new JLabel("New label");
		LABEL_USER_NAME.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		LABEL_USER_NAME.setForeground(Color.WHITE);
		LABEL_USER_NAME.setBounds(139, 57, 106, 14);
		frame.getContentPane().add(LABEL_USER_NAME);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(35, 106, 121, 117);
		frame.getContentPane().add(lblNewLabel_IMAGE);

		JLabel lblNewLabel_9 = new JLabel("ADMIN PANEL");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_9.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		lblNewLabel_9.setBounds(25, 11, 154, 31);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_28 = new JLabel("LOGGED IN AS:");
		lblNewLabel_28.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_28.setForeground(Color.WHITE);
		lblNewLabel_28.setBounds(10, 57, 154, 14);
		frame.getContentPane().add(lblNewLabel_28);

		JLabel lblNewLabel_8 = new JLabel("POSITION:");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_8.setBounds(35, 324, 106, 14);
		frame.getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_7 = new JLabel("ADMIN ID:");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_7.setBounds(35, 293, 106, 14);
		frame.getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_6 = new JLabel("ADMIN NAME:");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_6.setBounds(35, 262, 144, 14);
		frame.getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(ADMIN_PANEL.class.getResource("/img/admin_pic.jpg")));
		lblNewLabel_4.setBounds(-21, -24, 1063, 490);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_20 = new JLabel("New label");
		lblNewLabel_20.setBounds(0, 0, 46, 14);
		frame.getContentPane().add(lblNewLabel_20);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.LIGHT_GRAY);
		menuBar.setBackground(Color.LIGHT_GRAY);
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Admin");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("View Admin Panel");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ADMIN_INFO obj=new ADMIN_INFO();
				obj.setVisible();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Employee");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("View Employee Information");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Employee_info obj=new Employee_info();
				obj.setVisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Recruit Employee");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RECRUIT_EMPLOYEE obj=new RECRUIT_EMPLOYEE();
				obj.setVisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Update Employee Information");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UPDATE_EMPLOYEE_INFO obj=new UPDATE_EMPLOYEE_INFO();
				obj.setVisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Block Employee ID");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DISCHARGE_EMPLOYEE obj=new DISCHARGE_EMPLOYEE();
				obj.setVisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("See Block List");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLOCK_LIST OBJ=new BLOCK_LIST();
				OBJ.setvisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_10);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("pay employee");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PAY_EMPLOYEE OBJ=new PAY_EMPLOYEE();
				OBJ.setVisible();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_8);

		JMenu mnNewMenu_2 = new JMenu("Customer");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("View Customer Information");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CUSTOMER_INFO obj=new CUSTOMER_INFO();
				obj.setVisible();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Update Customer Information");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UPDATE_CUSTOMER_INFO obj=new UPDATE_CUSTOMER_INFO();
				obj.setVisible();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Enroll Customer");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CUSTOMER_ENROLLING obj=new CUSTOMER_ENROLLING();
				obj.setVisible();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_7);
	}

	public void setvisible() {
		// TODO Auto-generated method stub
		ADMIN_PANEL window = new ADMIN_PANEL();
		window.frame.setVisible(true);
	}
}
