import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class Employee_info {

	private JFrame frame;
	private JTextField textField;
    Connection conn=null;
    private JTable table;
    JEditorPane editorPane_ADDRESS;
    private JLabel lblNewLabel;
    private JTabbedPane tabbedPane_1;
    private JLabel lblNewLabel_1;
    private JTextField textField_POSITION;
    private JLabel lblNewLabel_2;
    private JTextField textField_SALARY;
    private JLabel lblNewLabel_3;
    private JEditorPane editorPane_DESCRIPTION;
    private JButton btnNewButton_1;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_5;
    private JLabel lblNewLabel_6;
    JLabel lblNewLabel_IMAGE;
    private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee_info window = new Employee_info();
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
	public Employee_info() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
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
	
	public void clear(){
		lblNewLabel_IMAGE.setIcon(null);
		editorPane_ADDRESS.setText(null);
		textField_POSITION.setText(null);
		textField_SALARY.setText(null);
		editorPane_DESCRIPTION.setText(null);
	}
	
	public void table(){
		try{
			String query="select ID,NAME,POSITION,STATUS from employee";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Employee_info.class.getResource("/img/bank.png")));
		frame.setBounds(100, 100, 938, 515);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("load data");
		btnNewButton.setIcon(new ImageIcon(Employee_info.class.getResource("/img/loading-cloud.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table();
			}
		});
		btnNewButton.setBounds(358, 28, 42, 33);
		frame.getContentPane().add(btnNewButton);
		//fill_combobox();
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					
					String query="select ID,NAME from employee where ID=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1,textField.getText());
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
			}
		});
		textField.setBounds(10, 51, 173, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 107, 478, 229);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				  try{
					  
					clear();
					
                  	int row=table.getSelectedRow();
      				String ID=(table.getModel().getValueAt(row, 0)).toString();
					String query="select IMAGE,ADDRESS,POSITION,SALARY,DESCRIPTION from employee where ID='"+ID+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					   if(rs.next()){
						byte[] img_data=rs.getBytes("IMAGE");
						ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel_IMAGE.setIcon(img);
						
						editorPane_ADDRESS.setText(rs.getString("ADDRESS"));
						textField_POSITION.setText(rs.getString("POSITION"));
						textField_SALARY.setText(rs.getString("SALARY"));
						editorPane_DESCRIPTION.setText(rs.getString("DESCRIPTION"));
					   }else JOptionPane.showMessageDialog(null, "no data found");
						
					pst.close();
					rs.close();
					}catch(Exception e1){
					e1.printStackTrace();
					}
			}
		});
		
		editorPane_ADDRESS = new JEditorPane();
		editorPane_ADDRESS.setEditable(false);
		editorPane_ADDRESS.setBounds(642, 213, 189, 88);
		frame.getContentPane().add(editorPane_ADDRESS);
		
		lblNewLabel = new JLabel("ADDRESS:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(538, 212, 94, 14);
		frame.getContentPane().add(lblNewLabel);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(504, 0, 14, 476);
		frame.getContentPane().add(tabbedPane_1);
		
		lblNewLabel_1 = new JLabel("POSITION:");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(538, 142, 94, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_POSITION = new JTextField();
		textField_POSITION.setText("");
		textField_POSITION.setBounds(642, 139, 130, 20);
		frame.getContentPane().add(textField_POSITION);
		textField_POSITION.setColumns(10);
		
		lblNewLabel_2 = new JLabel("SALARY:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setBounds(538, 173, 94, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(779, 28, 108, 109);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		lblNewLabel_6 = new JLabel("LOAD EMPLOYEE DATABASE");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(292, 73, 196, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		textField_SALARY = new JTextField();
		textField_SALARY.setBounds(642, 170, 130, 20);
		frame.getContentPane().add(textField_SALARY);
		textField_SALARY.setColumns(10);
		
		lblNewLabel_3 = new JLabel("DESCRIPTION:");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(538, 323, 94, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		editorPane_DESCRIPTION = new JEditorPane();
		editorPane_DESCRIPTION.setBounds(642, 323, 189, 88);
		frame.getContentPane().add(editorPane_DESCRIPTION);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnNewButton_1.setToolTipText("CLEAR FIELDS");
		btnNewButton_1.setIcon(new ImageIcon(Employee_info.class.getResource("/img/cleaning-service.png")));
		btnNewButton_1.setBounds(700, 426, 42, 33);
		frame.getContentPane().add(btnNewButton_1);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Employee_info.class.getResource("/img/august 13 blur feature 5.jpg")));
		lblNewLabel_4.setBounds(0, 0, 504, 476);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setToolTipText("");
		lblNewLabel_5.setIcon(new ImageIcon(Employee_info.class.getResource("/img/sunset.jpg")));
		lblNewLabel_5.setBounds(517, 0, 405, 476);
		frame.getContentPane().add(lblNewLabel_5);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		Employee_info window = new Employee_info();
		window.frame.setVisible(true);
	}
}
