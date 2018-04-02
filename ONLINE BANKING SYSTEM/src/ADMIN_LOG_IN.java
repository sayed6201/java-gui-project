import java.awt.EventQueue;

import javax.swing.JFrame;
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
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class ADMIN_LOG_IN {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
    Connection conn=null;
    static String admin_position=null;
    static String admin_user_name=null;
    static String admin_name=null;
    static String admin_id=null;
    static double total_balance=0;
    static byte[] img_data;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMIN_LOG_IN window = new ADMIN_LOG_IN();
					window.frame.setVisible(true);
					new CALCULATION().month_year_checker();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ADMIN_LOG_IN() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	void info_passing(JPasswordField passwordField1){
		try{
		String query="select ID,NAME,POSITION,USERNAME,IMAGE from admin where password='"+passwordField1.getText()+"'";
		PreparedStatement pst=conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		
		admin_id=rs.getString("ID");
		admin_name=rs.getString("NAME");
		admin_position=rs.getString("POSITION");
		admin_user_name=rs.getString("USERNAME");
		img_data=rs.getBytes("IMAGE");
		
		pst.close();
		rs.close();
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR");
		}
		
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\bank.png"));
		frame.setResizable(false);
		frame.setBounds(100, 100, 715, 440);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMIN LOG IN");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblNewLabel.setBounds(288, 41, 230, 34);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("USER NAME");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_1.setBounds(290, 125, 134, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(434, 122, 129, 23);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setForeground(SystemColor.inactiveCaptionBorder);
		lblNewLabel_4.setBackground(SystemColor.inactiveCaptionBorder);
		lblNewLabel_4.setIcon(new ImageIcon("F:\\Drive\\Completed Projects\\my java project(161-15-7041)\\ONLINE BANKING SYSTEM\\src\\img\\Customs Officer-100.png"));
		lblNewLabel_4.setBounds(77, 55, 179, 254);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_2.setBounds(290, 172, 130, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(434, 169, 129, 23);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\checked.png"));
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					info_passing(passwordField);
					String query="select * from admin where username=? and password=? ";
					PreparedStatement pst=conn.prepareStatement(query);
					
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs=pst.executeQuery();
					
					int count=0;
					while(rs.next()){
						count++;
					}
					
					if(count==1) {
						frame.dispose();
						ADMIN_PANEL obj=new ADMIN_PANEL();
						obj.setvisible();
						
		
						
					}
					else if(count>1) JOptionPane.showMessageDialog(null, "double");
					else JOptionPane.showMessageDialog(null, "incorrect");
					
					pst.close();
					rs.close();
					
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
					}
			}
		});
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WELCOME OBJ=new WELCOME();
				OBJ.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\back.png"));
		btnNewButton_1.setBounds(305, 230, 112, 34);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton.setBounds(449, 230, 134, 34);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_3.setIcon(new ImageIcon(ADMIN_LOG_IN.class.getResource("/img/518079-background-hd.jpg")));
		lblNewLabel_3.setBounds(0, -90, 712, 534);
		frame.getContentPane().add(lblNewLabel_3);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		ADMIN_LOG_IN window = new ADMIN_LOG_IN();
		window.frame.setVisible(true);
	}
}
