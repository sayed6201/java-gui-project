import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.TextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class CUSTOMER_PANEL {

	private JFrame frame;
	private JTextField textField_CUSTOMER_BALANCE;
	private JTextField textField_INTEREST_RATE;
	private JTextField textField_RECEIVER_AC;
	private JTextField textField_TRANSFER_AMOUNT;
	private JTextField textField_CUSTOMER_NAME;
	private JTextField textField_ACCOUNT_NO;
    Connection conn=null;
    private JTextField textField_LOG_IN;
    private JComboBox comboBox_TRANSFER_TYPE;
    private JTextField textField_BANK_NAME;
    private JTextArea textArea;
    private JTable table_NOTIFICATION;
    private JLabel lblNewLabel_IMAGE;
    private JTextField textField_CHARGE_MONTH;
    private JTextField textField_CHARGE_ANUM;
    private JTextField textField_USERNAME;
    private JTextField textField_PASSWORD;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CUSTOMER_PANEL window = new CUSTOMER_PANEL();
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
	public CUSTOMER_PANEL() {
		conn=DB_CONNECTOR.Connect_data_base();
		
		initialize();
		setting_fields();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void notification(){
		try{	
			String query="select NOTIFICATION from customer_notification where AC_NO='"+textField_ACCOUNT_NO.getText()+"'";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table_NOTIFICATION.setModel(DbUtils.resultSetToTableModel(rs));		
			pst.close();
			rs.close();
			}catch(Exception e1){
			e1.printStackTrace();
			}
	}
	
	void setting_fields(){
		
		textField_CUSTOMER_BALANCE.setText(CUSTOMER_LOG_IN.account_balance);
		textField_INTEREST_RATE.setText(Double.toString(CUSTOMER_LOG_IN.interest_rate));
		textField_LOG_IN.setText(CUSTOMER_LOG_IN.user_name);
		textField_CUSTOMER_NAME.setText(CUSTOMER_LOG_IN.customer_name);
		textField_ACCOUNT_NO.setText(CUSTOMER_LOG_IN.account_num);
		if(CUSTOMER_LOG_IN.img_data!=null){
		ImageIcon img=new ImageIcon(new ImageIcon(CUSTOMER_LOG_IN.img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
		lblNewLabel_IMAGE.setIcon(img);}
		textField_CHARGE_MONTH.setText(Double.toString(CUSTOMER_LOG_IN.charge_month));
		textField_CHARGE_ANUM.setText(Double.toString(CUSTOMER_LOG_IN.charge_year));
		
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CUSTOMER_PANEL.class.getResource("/img/bank.png")));
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 899, 431);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CURRENT BALANCE:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 262, 144, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField_CUSTOMER_BALANCE = new JTextField();
		textField_CUSTOMER_BALANCE.setEditable(false);
		textField_CUSTOMER_BALANCE.setBounds(164, 259, 120, 20);
		frame.getContentPane().add(textField_CUSTOMER_BALANCE);
		textField_CUSTOMER_BALANCE.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("INTEREST RATE:");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 293, 144, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_INTEREST_RATE = new JTextField();
		textField_INTEREST_RATE.setEditable(false);
		textField_INTEREST_RATE.setBounds(164, 290, 120, 20);
		frame.getContentPane().add(textField_INTEREST_RATE);
		textField_INTEREST_RATE.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(389, 0, 494, 388);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 255, 153));
		tabbedPane.addTab("MONEY TRANSFER", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("RECEIVER'S AC NO:");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 28, 148, 14);
		panel.add(lblNewLabel_2);
		
		textField_RECEIVER_AC = new JTextField();
		textField_RECEIVER_AC.setBounds(168, 25, 119, 20);
		panel.add(textField_RECEIVER_AC);
		textField_RECEIVER_AC.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("AMOUNT:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 59, 148, 14);
		panel.add(lblNewLabel_3);
		
		textField_TRANSFER_AMOUNT = new JTextField();
		textField_TRANSFER_AMOUNT.setBounds(168, 56, 119, 20);
		panel.add(textField_TRANSFER_AMOUNT);
		textField_TRANSFER_AMOUNT.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/send.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					String transfer_type=(String)comboBox_TRANSFER_TYPE.getSelectedItem();
					if(transfer_type.equals("INTERNAL")==true){
						String query="insert into internal_customer_transfer_message (AC_NO,NAME,AMOUNT,TO_AC) values(?,?,?,?)";
					    PreparedStatement pst=conn.prepareStatement(query);
					    pst.setString(1, textField_ACCOUNT_NO.getText());// '"+textField_ACCOUNT_NO.getText()+"'
					    pst.setString(2, textField_CUSTOMER_NAME.getText());//'"+textField_CUSTOMER_NAME.getText()+"'
					    pst.setString(3, textField_TRANSFER_AMOUNT.getText());//'"+textField_TRANSFER_AMOUNT.getText()+"'
					    pst.setString(4, textField_RECEIVER_AC.getText());//'"+textField_RECEIVER_AC.getText()+"'
					    pst.execute();
					    
					    JOptionPane.showMessageDialog(null, "Transfer Request sent to Bank you eill receive confirmation Message");
					    textField_TRANSFER_AMOUNT.setText(null);
					    textField_RECEIVER_AC.setText(null);;
					    
					    pst.close();
					}else{
						String query="insert into external_transfer_message (AC_NO,NAME,AMOUNT,TO_AC,BANK_NAME) values(?,?,?,?,?)";
					    PreparedStatement pst=conn.prepareStatement(query);
					    pst.setString(1, textField_ACCOUNT_NO.getText());// '"+textField_ACCOUNT_NO.getText()+"'
					    pst.setString(2, textField_CUSTOMER_NAME.getText());//'"+textField_CUSTOMER_NAME.getText()+"'
					    pst.setString(3, textField_TRANSFER_AMOUNT.getText());//'"+textField_TRANSFER_AMOUNT.getText()+"'
					    pst.setString(4, textField_RECEIVER_AC.getText());//'"+textField_RECEIVER_AC.getText()+"'
					    pst.setString(5, textField_BANK_NAME.getText());
					    pst.execute();
					    
					    JOptionPane.showMessageDialog(null, "Transfer Request sent to Bank you Will receive confirmation Message");
					    textField_TRANSFER_AMOUNT.setText(null);
					    textField_RECEIVER_AC.setText(null);;
					    textField_BANK_NAME.setText(null);
					    
					    pst.close();
					}
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnNewButton.setBounds(175, 167, 44, 33);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_13 = new JLabel("TRANSFER");
		lblNewLabel_13.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_13.setForeground(Color.WHITE);
		lblNewLabel_13.setBounds(164, 211, 105, 14);
		panel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_8 = new JLabel("TRANSFER TYPE:");
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(10, 90, 148, 14);
		panel.add(lblNewLabel_8);
		
		comboBox_TRANSFER_TYPE = new JComboBox();
		comboBox_TRANSFER_TYPE.setModel(new DefaultComboBoxModel(new String[] {"INTERNAL", "EXTERNAL"}));
		comboBox_TRANSFER_TYPE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String transfer_type=(String)comboBox_TRANSFER_TYPE.getSelectedItem();
				if(transfer_type.equals("INTERNAL")==true) textField_BANK_NAME.setEditable(false);
				else textField_BANK_NAME.setEditable(true);
			}
		});
		comboBox_TRANSFER_TYPE.setBounds(168, 87, 119, 20);
		panel.add(comboBox_TRANSFER_TYPE);
		
		JLabel lblNewLabel_9 = new JLabel("BANK NAME:");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_9.setBounds(10, 125, 148, 14);
		panel.add(lblNewLabel_9);
		
		textField_BANK_NAME = new JTextField();
		textField_BANK_NAME.setEditable(false);
		textField_BANK_NAME.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		textField_BANK_NAME.setBounds(168, 118, 119, 20);
		panel.add(textField_BANK_NAME);
		textField_BANK_NAME.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("");
		lblNewLabel_12.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/house.jpg")));
		lblNewLabel_12.setBounds(0, 0, 489, 374);
		panel.add(lblNewLabel_12);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 102));
		tabbedPane.addTab("COMPLAIN", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Write your complain to us");
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 13));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(24, 23, 238, 22);
		panel_1.add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/send.png")));
		btnNewButton_1.setBounds(215, 284, 48, 33);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query="insert into complains (ACCOUNT_NO,NAME,COMPLAINS) values('"+textField_ACCOUNT_NO.getText()+"','"+textField_CUSTOMER_NAME.getText()+"','"+textArea.getText()+"')";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Complains sent to the admins");
					textArea.setText(null);
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
			
		});
		
		panel_1.add(btnNewButton_1);
		
		textArea = new JTextArea();
		textArea.setBounds(24, 56, 438, 204);
		panel_1.add(textArea);
		
		JLabel lblNewLabel_14 = new JLabel("New label");
		lblNewLabel_14.setToolTipText("SEND");
		lblNewLabel_14.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/august 13 blur feature 5.jpg")));
		lblNewLabel_14.setBounds(0, 0, 489, 360);
		panel_1.add(lblNewLabel_14);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("NOTIFICATION", null, panel_2, null);
		panel_2.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/rubbish-bin.png")));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to Delete all Notifications ");
		if(confirmation==0){
				try{
					String query="Delete from customer_notification where AC_NO='"+textField_ACCOUNT_NO.getText()+"' "; //will take the id from JTextField....
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "NOTIFICATION DELETED");
					notification();
					pst.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
		}else {
			return;
		}
			}
		});
		btnNewButton_3.setBounds(363, 22, 43, 33);
		panel_2.add(btnNewButton_3);
		
		JLabel lblNewLabel_17 = new JLabel("DELETE");
		lblNewLabel_17.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setBounds(362, 66, 70, 14);
		panel_2.add(lblNewLabel_17);
		
		JLabel lblNewLabel_16 = new JLabel("SEE NOTIFICATION");
		lblNewLabel_16.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setBounds(36, 66, 114, 14);
		panel_2.add(lblNewLabel_16);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 105, 351, 164);
		panel_2.add(scrollPane);
		
		table_NOTIFICATION = new JTable();
		scrollPane.setViewportView(table_NOTIFICATION);
		table_NOTIFICATION.setBackground(new Color(250, 250, 210));
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/turn-notifications-on-button.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notification();
			}
		});
		btnNewButton_4.setBounds(70, 22, 43, 33);
		panel_2.add(btnNewButton_4);
		
		JLabel lblNewLabel_15 = new JLabel("");
		lblNewLabel_15.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/abstract-19313_640.jpg")));
		lblNewLabel_15.setBounds(0, 0, 489, 360);
		panel_2.add(lblNewLabel_15);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("ACCOUNT SETTING", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_21 = new JLabel("USERNAME");
		lblNewLabel_21.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_21.setForeground(Color.WHITE);
		lblNewLabel_21.setBounds(71, 74, 86, 14);
		panel_4.add(lblNewLabel_21);
		
		textField_USERNAME = new JTextField();
		textField_USERNAME.setBounds(167, 71, 122, 20);
		panel_4.add(textField_USERNAME);
		textField_USERNAME.setColumns(10);
		
		JLabel lblNewLabel_22 = new JLabel("PASSWORD");
		lblNewLabel_22.setForeground(Color.WHITE);
		lblNewLabel_22.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_22.setBounds(71, 130, 86, 14);
		panel_4.add(lblNewLabel_22);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(167, 127, 122, 20);
		panel_4.add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		JButton btnNewButton_6 = new JButton("SAVE");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to SAVE changes");
				if(confirmation==0){
				try{
					String query="UPDATE CUSTOMER SET USERNAME='"+textField_USERNAME.getText()+"', PASSWORD='"+textField_PASSWORD.getText()+"' WHERE ACCOUNT_NO='"+CUSTOMER_LOG_IN.account_num+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
		            pst.execute();
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
			}
		});
		btnNewButton_6.setBounds(164, 197, 89, 23);
		panel_4.add(btnNewButton_6);
		
		JLabel lblNewLabel_20 = new JLabel("");
		lblNewLabel_20.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/employee_log.png")));
		lblNewLabel_20.setBounds(0, 0, 489, 360);
		panel_4.add(lblNewLabel_20);
		
		JLabel lblNewLabel_5 = new JLabel("NAME:");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setBackground(SystemColor.menu);
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(10, 200, 144, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		textField_CUSTOMER_NAME = new JTextField();
		textField_CUSTOMER_NAME.setEditable(false);
		textField_CUSTOMER_NAME.setBounds(164, 197, 120, 20);
		frame.getContentPane().add(textField_CUSTOMER_NAME);
		textField_CUSTOMER_NAME.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("CHARGE PER MONTH:");
		lblNewLabel_18.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_18.setForeground(Color.WHITE);
		lblNewLabel_18.setBounds(10, 318, 151, 14);
		frame.getContentPane().add(lblNewLabel_18);
		
		textField_CHARGE_ANUM = new JTextField();
		textField_CHARGE_ANUM.setEditable(false);
		textField_CHARGE_ANUM.setBounds(164, 349, 120, 20);
		frame.getContentPane().add(textField_CHARGE_ANUM);
		textField_CHARGE_ANUM.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("CHARGE PER ANUM:");
		lblNewLabel_19.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_19.setForeground(Color.WHITE);
		lblNewLabel_19.setBounds(10, 352, 144, 14);
		frame.getContentPane().add(lblNewLabel_19);
		
		JLabel lblNewLabel_10 = new JLabel("LOG OUT");
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(293, 55, 71, 14);
		frame.getContentPane().add(lblNewLabel_10);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setToolTipText("view admin information");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ADMIN_INFO obj=new ADMIN_INFO();
				obj.setVisible();
			}
		});
		btnNewButton_5.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/money.png")));
		btnNewButton_5.setBounds(293, 86, 57, 33);
		frame.getContentPane().add(btnNewButton_5);
		
		textField_CHARGE_MONTH = new JTextField();
		textField_CHARGE_MONTH.setEditable(false);
		textField_CHARGE_MONTH.setBounds(164, 321, 120, 20);
		frame.getContentPane().add(textField_CHARGE_MONTH);
		textField_CHARGE_MONTH.setColumns(10);
		
		JLabel lblNewLabel_23 = new JLabel("ABOUT BANK");
		lblNewLabel_23.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_23.setForeground(Color.WHITE);
		lblNewLabel_23.setBounds(278, 130, 101, 14);
		frame.getContentPane().add(lblNewLabel_23);
		
		JLabel lblNewLabel_6 = new JLabel("ACCOUNT NO:");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(10, 231, 120, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		textField_ACCOUNT_NO = new JTextField();
		textField_ACCOUNT_NO.setEditable(false);
		textField_ACCOUNT_NO.setBounds(164, 228, 120, 20);
		frame.getContentPane().add(textField_ACCOUNT_NO);
		textField_ACCOUNT_NO.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/logout.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CUSTOMER_LOG_IN obj=new CUSTOMER_LOG_IN();
				obj.setVisible();
				frame.dispose();
			}
		});
		
		JLabel lblNewLabel_11 = new JLabel("LOGGED IN AS:");
		lblNewLabel_11.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setBounds(10, 6, 104, 14);
		frame.getContentPane().add(lblNewLabel_11);
		btnNewButton_2.setToolTipText("LOG OUT");
		btnNewButton_2.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2.setBounds(293, 11, 57, 33);
		frame.getContentPane().add(btnNewButton_2);
		
		textField_LOG_IN = new JTextField();
		textField_LOG_IN.setEditable(false);
		textField_LOG_IN.setAutoscrolls(false);
		textField_LOG_IN.setBounds(118, 3, 111, 20);
		frame.getContentPane().add(textField_LOG_IN);
		textField_LOG_IN.setColumns(10);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(41, 34, 131, 132);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(CUSTOMER_PANEL.class.getResource("/img/nature_1.jpg")));
		lblNewLabel_7.setBounds(0, 0, 463, 388);
		frame.getContentPane().add(lblNewLabel_7);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(41, 34, 131, 132);
		frame.getContentPane().add(panel_3);
	}

	public void setVisible() {
		CUSTOMER_PANEL window = new CUSTOMER_PANEL();
		window.frame.setVisible(true);
	}
}
