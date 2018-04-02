import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Window.Type;

public class WELCOME {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WELCOME window = new WELCOME();
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
	 **/
	
	public WELCOME() {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WELCOME.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.setType(Type.POPUP);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 779, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("ADMIN");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\admin-with-cogwheels.png"));
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton.setToolTipText("Log in as Admin");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   ADMIN_LOG_IN obj=new ADMIN_LOG_IN();
			   obj.setVisible();
			   frame.dispose();
			}
		});
		btnNewButton.setBounds(218, 335, 102, 39);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CUSTOMER");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\user-avatar-with-check-mark.png"));
		btnNewButton_1.setToolTipText("Log in as Customer");
		btnNewButton_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CUSTOMER_LOG_IN obj=new CUSTOMER_LOG_IN();
				obj.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(505, 335, 139, 38);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("EMPLOYEE");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\sayed ahmed\\workspace\\ONLINE BANKING SYSTEM\\src\\img\\man-with-tie.png"));
		btnNewButton_2.setToolTipText("Log in as Employee");
		btnNewButton_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EMPLOYEE_LOG_IN obj=new EMPLOYEE_LOG_IN();
				obj.setVisible();
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(346, 336, 133, 38);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("ENTER AS:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		lblNewLabel_1.setBounds(27, 328, 108, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("DAFFODIL BANKING SYSTEM");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblNewLabel_3.setBounds(270, 23, 249, 32);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(WELCOME.class.getResource("/img/LOGO.png")));
		lblNewLabel_2.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 40));
		lblNewLabel_2.setBounds(255, 53, 270, 271);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setIcon(new ImageIcon(WELCOME.class.getResource("/img/ADMIN_NATURE.jpg")));
		lblNewLabel.setBounds(0, 0, 773, 446);
		frame.getContentPane().add(lblNewLabel);
		
		 
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		WELCOME window = new WELCOME();
		window.frame.setVisible(true);
	}
}
