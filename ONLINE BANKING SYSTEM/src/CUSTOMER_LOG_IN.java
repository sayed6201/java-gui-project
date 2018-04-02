import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class CUSTOMER_LOG_IN {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
    Connection conn=null;
    
    
    static String account_num=null;
    static String user_name=null;
    static String customer_name=null;
    static String account_type=null;
    static String account_balance=null;
    static byte[] img_data;
    static double interest_rate;
    static double charge_month;
    static double charge_year;

    static String password ;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CUSTOMER_LOG_IN window = new CUSTOMER_LOG_IN();
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
	public CUSTOMER_LOG_IN() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void method(){
		CUSTOMER_PANEL obj=new CUSTOMER_PANEL();
		obj.setVisible();
		frame.dispose();
	}
	void info_passing(JTextField textField){
		try{
			
		String query="select PASSWORD,ACCOUNT_NO,NAME,USERNAME,BALANCE,IMAGE,INTEREST,CHARGE_PER_MONTH,CHARGE_PER_ANUM from customer where USERNAME='"+textField.getText()+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		
		account_num=rs.getString("ACCOUNT_NO");
		customer_name=rs.getString("NAME");
		user_name=rs.getString("USERNAME");
		account_balance=rs.getString("BALANCE");
		img_data=rs.getBytes("IMAGE");
		interest_rate=rs.getDouble("interest");
		charge_month=rs.getDouble("CHARGE_PER_MONTH");
		charge_year=rs.getDouble("CHARGE_PER_ANUM");
		password=rs.getString("PASSWORD");
		
		pst.close();
		rs.close();
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR");
		}
		
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CUSTOMER_LOG_IN.class.getResource("/img/bank.png")));
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 623, 432);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CUSTOMER LOG IN");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
		lblNewLabel.setBounds(263, 39, 205, 37);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(364, 132, 128, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("USER NAME");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_1.setBackground(SystemColor.inactiveCaption);
		lblNewLabel_1.setBounds(233, 135, 121, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_2.setBounds(233, 187, 121, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(CUSTOMER_LOG_IN.class.getResource("/img/User Filled-100.png")));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(15, 0, 205, 256);
		frame.getContentPane().add(lblNewLabel_4);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(364, 184, 128, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("LOG IN");
		btnNewButton.setIcon(new ImageIcon(CUSTOMER_LOG_IN.class.getResource("/img/login.png")));
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					info_passing(textField);
					String query="select * from customer where USERNAME=? and PASSWORD=? ";
					PreparedStatement pst=conn.prepareStatement(query);
					
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs=pst.executeQuery();
					
					int count=0;
					while(rs.next()){
						count++;
					}
					
					if(count==1) {
						JOptionPane.showMessageDialog(null, "successfully logged in");
						method();
						
					}
					else if(count>1) JOptionPane.showMessageDialog(null, "double");
					else JOptionPane.showMessageDialog(null, "incorrect");
					
					pst.close();
					rs.close();
					
					}
					catch(Exception e1){
						JOptionPane.showMessageDialog(null, "ERROE22");
					}
			}
		});
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(401, 247, 77, 37);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WELCOME OBJ=new WELCOME();
				OBJ.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(CUSTOMER_LOG_IN.class.getResource("/img/back (1).png")));
		btnNewButton_2.setBounds(307, 247, 77, 36);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(CUSTOMER_LOG_IN.class.getResource("/img/customer_log_in.jpg")));
		lblNewLabel_3.setBounds(0, 0, 617, 403);
		frame.getContentPane().add(lblNewLabel_3);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		CUSTOMER_LOG_IN window = new CUSTOMER_LOG_IN();
		window.frame.setVisible(true);
	}
}
