import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CUSTOMER_INFO {

	private JFrame frame;
	private JTextField txtEnterAcNo;
    Connection conn=null;
    private JTable table;
    private JLabel lblNewLabel_IMAGE;
    private JPanel panel;
    private JLabel lblNewLabel;
    private JTextField textField_CUSTOMER_NAME;
    private JTextField textField_PROFESSION;
    private JTextField textField_BALANCE;
    JEditorPane editorPane_ADDRESS;
    private JLabel lblNewLabel_6;
    private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CUSTOMER_INFO window = new CUSTOMER_INFO();
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
	public CUSTOMER_INFO() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}
	
	public void clear(){
		textField_BALANCE.setText(null);
		textField_CUSTOMER_NAME.setText(null);
		textField_PROFESSION.setText(null);
		lblNewLabel_IMAGE.setIcon(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/*
	private void fill_combobox(){
		comboBox.addItem("ID");
		comboBox.addItem("USERNAME");
		comboBox.addItem("NAME"); 

	}
	*/
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CUSTOMER_INFO.class.getResource("/img/bank.png")));
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.setBounds(100, 100, 949, 424);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(CUSTOMER_INFO.class.getResource("/img/group-refresh.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query="select ACCOUNT_NO,NAME,BALANCE from customer";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
					pst.close();
					rs.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		
		btnNewButton.setBounds(807, 50, 37, 33);
		frame.getContentPane().add(btnNewButton);
		//fill_combobox();
		txtEnterAcNo = new JTextField();
		txtEnterAcNo.setText("ENTER AC NO");
		txtEnterAcNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					//String selection=(String)comboBox.getSelectedItem();
					String query="select ACCOUNT_NO,NAME,BALANCE from customer where ACCOUNT_NO=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1,txtEnterAcNo.getText());
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
			}
		});
		txtEnterAcNo.setBounds(433, 63, 173, 20);
		frame.getContentPane().add(txtEnterAcNo);
		txtEnterAcNo.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(433, 119, 478, 226);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clear();
				try{
					int row=table.getSelectedRow();
      				String ID=(table.getModel().getValueAt(row, 0)).toString();
					String query="select ACCOUNT_NO,NAME,BALANCE,PROFESSION,IMAGE,ADDRESS from customer where ACCOUNT_NO='"+ID+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					
					textField_BALANCE.setText(rs.getString("BALANCE"));
					textField_CUSTOMER_NAME.setText(rs.getString("NAME"));
					textField_PROFESSION.setText(rs.getString("PROFESSION"));
				if(rs.getBytes("IMAGE")!=null){	
					byte[] img_data=rs.getBytes("IMAGE");
					ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
					lblNewLabel_IMAGE.setIcon(img);
				}
					editorPane_ADDRESS.setText(rs.getString("ADDRESS"));
					
					pst.close();
					rs.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
			}
		});
		scrollPane.setViewportView(table);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(27, 30, 126, 122);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		panel = new JPanel();
		panel.setBounds(27, 30, 126, 122);
		frame.getContentPane().add(panel);
		
		lblNewLabel = new JLabel("CUSTOMER NAME:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(27, 185, 157, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField_CUSTOMER_NAME = new JTextField();
		textField_CUSTOMER_NAME.setEditable(false);
		textField_CUSTOMER_NAME.setBounds(181, 182, 157, 20);
		frame.getContentPane().add(textField_CUSTOMER_NAME);
		textField_CUSTOMER_NAME.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("CUSTOMER PROFESSION:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setBounds(27, 224, 157, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_PROFESSION = new JTextField();
		textField_PROFESSION.setEnabled(true);
		textField_PROFESSION.setEditable(false);
		textField_PROFESSION.setText("");
		textField_PROFESSION.setBounds(181, 221, 157, 20);
		frame.getContentPane().add(textField_PROFESSION);
		textField_PROFESSION.setColumns(10);
		
		editorPane_ADDRESS = new JEditorPane();
		editorPane_ADDRESS.setEditable(false);
		editorPane_ADDRESS.setBounds(181, 299, 157, 64);
		frame.getContentPane().add(editorPane_ADDRESS);
		
		textField_BALANCE = new JTextField();
		textField_BALANCE.setEditable(false);
		textField_BALANCE.setBounds(181, 255, 157, 20);
		frame.getContentPane().add(textField_BALANCE);
		textField_BALANCE.setColumns(10);
		
		lblNewLabel_6 = new JLabel("LOAD DETAIL");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(239, 107, 87, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_5 = new JLabel("BALANCE:");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(27, 258, 126, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_2 = new JLabel("ADDRESS:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setBounds(27, 297, 126, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("LOAD CUSTOMER DATABASE");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(743, 94, 176, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(CUSTOMER_INFO.class.getResource("/img/admin_pic.jpg")));
		lblNewLabel_4.setBounds(0, 0, 933, 385);
		frame.getContentPane().add(lblNewLabel_4);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		CUSTOMER_INFO window = new CUSTOMER_INFO();
		window.frame.setVisible(true);
	}
}
