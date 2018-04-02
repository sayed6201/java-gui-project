import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class EMPLOYEE_LOG_IN {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
    Connection conn=null;
    static String employee_name;
    static String employee_id;
    static String employee_position;
    static String employee_user_name;
    static byte[] img_data;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EMPLOYEE_LOG_IN window = new EMPLOYEE_LOG_IN();
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
	public EMPLOYEE_LOG_IN() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public boolean block_checker(){
		int count=0;
		try{
			String query="select * from block_list where USERNAME=? and PASSWORD=? ";
			PreparedStatement pst=conn.prepareStatement(query);
			
			pst.setString(1, textField.getText());
			pst.setString(2, passwordField.getText());
			
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				count++;
			}
			pst.close();
			rs.close();
			
			}
			catch(Exception e1){
				JOptionPane.showMessageDialog(null, e1);
			}
		if(count==0) {
			return true;
		}
		else{
			return false;
		}
	}
	
	
	void info_passing(JPasswordField passwordField1){
		try{
		String query="select ID,NAME,POSITION,USERNAME,IMAGE from employee where password='"+passwordField1.getText()+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		
		employee_id=rs.getString("ID");
		employee_name=rs.getString("NAME");
		employee_position=rs.getString("POSITION");
		employee_user_name=rs.getString("USERNAME");
		img_data=rs.getBytes("IMAGE");
		
		pst.close();
		rs.close();
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR");
		}
		
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EMPLOYEE_LOG_IN.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 683, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EMPLOYEE LOG IN");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblNewLabel.setBounds(348, 100, 211, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("USERNAME");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_1.setBounds(348, 188, 126, 21);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(EMPLOYEE_LOG_IN.class.getResource("/img/people.png")));
		lblNewLabel_4.setBounds(509, 14, 129, 157);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WELCOME obj=new WELCOME();
				obj.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1.setIcon(new ImageIcon(EMPLOYEE_LOG_IN.class.getResource("/img/back (1).png")));
		btnNewButton_1.setBounds(399, 296, 71, 33);
		frame.getContentPane().add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(472, 187, 129, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_2.setBounds(348, 233, 114, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(472, 229, 129, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.setIcon(new ImageIcon(EMPLOYEE_LOG_IN.class.getResource("/img/checked (1).png")));
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(block_checker()==true){	
				try{
					String query="select * from employee where username=? and password=? ";
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
						info_passing(passwordField);
						EMPLOYEE_PANEL obj=new EMPLOYEE_PANEL();
						obj.setvisible();
						frame.dispose();
					}
					else if(count>1) JOptionPane.showMessageDialog(null, "double");
					else JOptionPane.showMessageDialog(null, "incorrect");
					
					pst.close();
					rs.close();
					}
					catch(Exception e1){
						JOptionPane.showMessageDialog(null, e1);
					}
			}
			else{
				JOptionPane.showMessageDialog(null, "your account has been blocked contact the admin");
			}
			}
		});
		btnNewButton.setBounds(485, 296, 71, 33);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBackground(SystemColor.menu);
		lblNewLabel_3.setIcon(new ImageIcon(EMPLOYEE_LOG_IN.class.getResource("/img/employee_log_in.png")));
		lblNewLabel_3.setBounds(-323, 0, 1000, 422);
		frame.getContentPane().add(lblNewLabel_3);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		EMPLOYEE_LOG_IN window = new EMPLOYEE_LOG_IN();
		window.frame.setVisible(true);
	}
}
