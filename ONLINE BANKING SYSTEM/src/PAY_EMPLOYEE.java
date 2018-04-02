import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class PAY_EMPLOYEE {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
Connection conn=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PAY_EMPLOYEE window = new PAY_EMPLOYEE();
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
	public PAY_EMPLOYEE() {
		initialize();
		conn=DB_CONNECTOR.Connect_data_base();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void refresh(){
		try{
			String query="select ID,NAME,BALANCE from employee";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
		}
	}
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PAY_EMPLOYEE.class.getResource("/img/bank.png")));
		frame.setBounds(100, 100, 477, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 40, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(119, 37, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("PAY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double amount=Double.parseDouble(textField_1.getText());
				
				try{
				String query="update employee set BALANCE=BALANCE-'"+amount+"' where ID='"+textField.getText()+"'";
				
				PreparedStatement pst=conn.prepareStatement(query);
				pst.execute();
				pst.close();
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, e1);
			}
				
				refresh();
			}});
		btnNewButton.setBounds(36, 112, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("AMOUNT:");
		lblNewLabel_1.setBounds(10, 71, 94, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(119, 68, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(235, 40, 216, 185);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("VIEW");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select ID,NAME,BALANCE from employee";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
					pst.close();
					rs.close();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton_1.setBounds(235, 6, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		PAY_EMPLOYEE window = new PAY_EMPLOYEE();
		window.frame.setVisible(true);
	}
}
