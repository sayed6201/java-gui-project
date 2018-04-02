import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class DISCHARGE_EMPLOYEE {

	private JFrame frame;
	private JTable table;
	private JButton btnNewButton;
    private int discharge=0;
    Connection conn=null;
    private JTextField textField_DURATION;
    JComboBox comboBox_DISCHARGE_STATUS;
    JComboBox comboBox;
    private JLabel lblNewLabel_IMAGE;
    private JLabel lblNewLabel_5;
    private JLabel lblNewLabel_6;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JTable table_1;
    private JScrollPane scrollPane_1;
    JLabel lblNewLabel_DATE;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DISCHARGE_EMPLOYEE window = new DISCHARGE_EMPLOYEE();
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
	public DISCHARGE_EMPLOYEE() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public String time() {
		 
		   DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
		   Date date = new Date();
		   return dateFormat.format(date);
	   }
	
	
	public void refresh(){        //this method has to be called in every button's Actionperformed method.
		try{
			String query="select STATUS,ID,NAME from employee";
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(DISCHARGE_EMPLOYEE.class.getResource("/img/bank.png")));
		frame.setBounds(100, 100, 679, 432);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EMPLOYEE ID:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 33, 143, 14);
		frame.getContentPane().add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(411, 255, 240, 127);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(DISCHARGE_EMPLOYEE.class.getResource("/img/eye.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select STATUS,ID,NAME from employee";
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
		btnNewButton.setBounds(330, 310, 49, 33);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(DISCHARGE_EMPLOYEE.class.getResource("/img/discharged-battery-status-symbol.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String DISCHARGE_type=(String)comboBox_DISCHARGE_STATUS.getSelectedItem();
if(DISCHARGE_type.equals("PERMANENT")){
					try {
						String query="INSERT INTO formar_employee (ID,NAME,IMAGE,ADDRESS,POSITION,DESCRIPTION) SELECT ID,NAME,IMAGE,ADDRESS,POSITION,DESCRIPTION FROM employee where ID='"+textField.getText()+"' "; //will take the id from JTextField....
						PreparedStatement pst= conn.prepareStatement(query);
						pst.execute();
						
						String query1="delete from employee where ID='"+textField.getText()+"'"; //will take the id from JTextField....
						PreparedStatement pst1= conn.prepareStatement(query1);
						pst1.execute();
						
						//DESCRIPTION
						/*
						String query2="UPDATE formar_employee SET DESCRIPTION = append(DESCRIPTION,'\n Discharged on time()')WHERE ID='"+textField.getText()+"'"; //will take the id from JTextField....
						PreparedStatement pst2= conn.prepareStatement(query2);
						pst2.execute();
						pst2.close();
						*/
						JOptionPane.showMessageDialog(null, "EMPLOYEE DISCHARGED");
						pst.close();
						pst1.close();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					refresh();
				}

else if(DISCHARGE_type.equals("TEMPORARY")){
                	int confirmation=JOptionPane.showConfirmDialog(null, "Do you really want to discharge "); //confirmation message before delete....
    				if(confirmation==0){ //YES=0
    					try{
    						
    						String query="INSERT INTO block_list (ID,NAME,IMAGE,DESCRIPTION,PASSWORD,USERNAME) SELECT ID,NAME,IMAGE,DESCRIPTION,PASSWORD,USERNAME FROM employee where ID='"+textField.getText()+"' "; //will take the id from JTextField....
    						PreparedStatement pst=conn.prepareStatement(query);
    						pst.execute();
    						
    						String query1="UPDATE block_list SET BLOCK_DURATION='"+textField_DURATION.getText()+' '+(String)comboBox.getSelectedItem()+"' where ID='"+textField.getText()+"'";
    						PreparedStatement pst1=conn.prepareStatement(query1);
    						pst1.execute();
    						
    						String query2="update employee set STATUS='X' where ID='"+textField.getText()+"'";
    						PreparedStatement pst2=conn.prepareStatement(query2);
    						pst2.execute();
    				//DESCRIPTION		
    						/*
    						String query3="UPDATE block_list SET DESCRIPTION = DESCRIPTION || '\n temporarily Discharged for "+textField_DURATION.getText()+(String)comboBox.getSelectedItem()+" on time()')WHERE ID='"+textField.getText()+"'"; //will take the id from JTextField....
    						PreparedStatement pst3= conn.prepareStatement(query3);
    						pst3.execute();
    						pst3.close();
    						*/
    						JOptionPane.showMessageDialog(null, "EMPLOYEE ACCOUNT HAS BEEN BLOCKED TEMPORARILY");
    						pst.close();
    						pst1.close();
    						pst2.close();
    						
    					}catch(Exception e1){
    						e1.printStackTrace();
    					}
    					refresh();
    					}

				}
			}
		});
		btnNewButton_1.setBounds(177, 224, 49, 33);
		frame.getContentPane().add(btnNewButton_1);
		
		comboBox_DISCHARGE_STATUS = new JComboBox();
		comboBox_DISCHARGE_STATUS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String DISCHARGE_type=(String)comboBox_DISCHARGE_STATUS.getSelectedItem();
				if(DISCHARGE_type.equals("TEMPORARY")){
					textField_DURATION.setEditable(true);
					comboBox.setEnabled(true);
				}else{
					textField_DURATION.setEditable(false);
					comboBox.setEnabled(false);
				}
			}
		});
		comboBox_DISCHARGE_STATUS.setModel(new DefaultComboBoxModel(new String[] {"PERMANENT", "TEMPORARY"}));
		comboBox_DISCHARGE_STATUS.setBounds(194, 142, 131, 20);
		frame.getContentPane().add(comboBox_DISCHARGE_STATUS);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(194, 71, 240, 48);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=table_1.getSelectedRow();
				String ID=(table_1.getModel().getValueAt(row, 0)).toString();
				try {
					String query="select IMAGE,DATE from employee where ID='"+ID+"' ";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					if(rs.getBytes("IMAGE")!=null){
						byte[] img_data=rs.getBytes("IMAGE");
						ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel_IMAGE.setIcon(img);
					}
				    lblNewLabel_DATE.setText(rs.getString("DATE"));
					pst.close();
					rs.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblNewLabel_1 = new JLabel("DISCHARGE STATUS");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 145, 178, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("DURATION OF DISCHARGE:");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(9, 181, 179, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_DURATION = new JTextField();
		textField_DURATION.setEditable(false);
		textField_DURATION.setBounds(194, 178, 55, 20);
		frame.getContentPane().add(textField_DURATION);
		textField_DURATION.setColumns(10);
		
		lblNewLabel_DATE = new JLabel("");
		lblNewLabel_DATE.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_DATE.setForeground(Color.WHITE);
		lblNewLabel_DATE.setBounds(505, 166, 131, 14);
		frame.getContentPane().add(lblNewLabel_DATE);
		
		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"WEEKS", "MONTHS", "YEAR"}));
		comboBox.setBounds(259, 178, 89, 20);
		frame.getContentPane().add(comboBox);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					//String selection=(String)comboBox.getSelectedItem();
					String query="select ID,NAME,STATUS from employee where ID=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1,textField.getText());
					ResultSet rs=pst.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
			}
		});
		textField.setBounds(198, 30, 131, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("JOINED ON:");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(417, 166, 78, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_6 = new JLabel("VIEW DATABASE");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(301, 354, 111, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		lblNewLabel_5 = new JLabel("DISMISS EMPLOYEE");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(138, 268, 130, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(496, 11, 137, 127);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(496, 11, 137, 127);
		frame.getContentPane().add(tabbedPane);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(DISCHARGE_EMPLOYEE.class.getResource("/img/widescreen.jpg")));
		lblNewLabel_4.setBounds(0, 0, 663, 393);
		frame.getContentPane().add(lblNewLabel_4);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		DISCHARGE_EMPLOYEE window = new DISCHARGE_EMPLOYEE();
		window.frame.setVisible(true);
	}
}
