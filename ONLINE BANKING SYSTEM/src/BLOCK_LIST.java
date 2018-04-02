import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JDesktopPane;
import javax.swing.JToolBar;

import net.proteanit.sql.DbUtils;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import javax.swing.JProgressBar;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class BLOCK_LIST {

	private JFrame frame;
	private JTable table;
Connection conn=null;
JLabel lblNewLabel_NAME;
JLabel lblNewLabel_IMG;
JLabel lblNewLabel_ID;
JEditorPane editorPane;
private JTextField textField_ID;
private JTextField textField_NAME;
private JLabel lblNewLabel_3;
private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BLOCK_LIST window = new BLOCK_LIST();
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
	public BLOCK_LIST() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void status(JTextField textField_ID){
		try {
			String x="OK";
			String query="update employee set STATUS='"+x+"' where ID='"+textField_ID.getText()+"'";
			PreparedStatement pst2;
			pst2 = conn.prepareStatement(query);
			pst2.execute(); 
			pst2.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}	
	}
	
	void refresh(){
		 try{
				
				String query="select ID,NAME,BLOCK_DURATION from block_list";
				PreparedStatement pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));		
				pst.close();
				rs.close();
				}catch(Exception e1){
				e1.printStackTrace();
				}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(BLOCK_LIST.class.getResource("/img/bank.png")));
		frame.setBounds(100, 100, 785, 408);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(422, 48, 337, 290);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					int row=table.getSelectedRow();
					lblNewLabel_IMG.setIcon(null);
					textField_NAME.setText(null);
					textField_ID.setText(null);
					editorPane.setText(null);
					String ID=(table.getModel().getValueAt(row, 0)).toString();
					
					String query="select ID,NAME,DESCRIPTION,IMAGE from block_list where ID='"+ID+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
					
					ResultSet rs=pst.executeQuery();
					
					    textField_NAME.setText(rs.getString("NAME"));
					    textField_ID.setText(rs.getString("ID"));
						editorPane.setText(rs.getString("DESCRIPTION"));
						byte[] img_data=rs.getBytes("IMAGE");
						ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMG.getWidth(), lblNewLabel_IMG.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel_IMG.setIcon(img);
					pst.close();
					rs.close();
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "no picture found");
					}
			}
		});
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(BLOCK_LIST.class.getResource("/img/eye.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 refresh();
			}
		});
		btnNewButton.setBounds(340, 146, 32, 34);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel_IMG = new JLabel("");
		lblNewLabel_IMG.setBounds(30, 23, 123, 122);
		frame.getContentPane().add(lblNewLabel_IMG);
		
		lblNewLabel_ID = new JLabel("ID:");
		lblNewLabel_ID.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_ID.setForeground(Color.WHITE);
		lblNewLabel_ID.setBounds(32, 185, 58, 14);
		frame.getContentPane().add(lblNewLabel_ID);
		
		lblNewLabel_NAME = new JLabel("NAME:");
		lblNewLabel_NAME.setForeground(Color.WHITE);
		lblNewLabel_NAME.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_NAME.setBounds(32, 210, 58, 14);
		frame.getContentPane().add(lblNewLabel_NAME);
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setBounds(130, 241, 174, 103);
		frame.getContentPane().add(editorPane);
		
		textField_ID = new JTextField();
		textField_ID.setEditable(false);
		textField_ID.setBounds(130, 182, 123, 20);
		frame.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		textField_NAME = new JTextField();
		textField_NAME.setEditable(false);
		textField_NAME.setBounds(130, 207, 123, 20);
		frame.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		
		lblNewLabel_3 = new JLabel("VIEW BLOCK LIST");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(301, 186, 151, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("UNBLOCK");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_2.setBounds(319, 262, 88, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("DESCRIPION:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(28, 235, 103, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(BLOCK_LIST.class.getResource("/img/lock.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				status(textField_ID);
				try {
					
					String query1="delete from block_list where ID='"+textField_ID.getText()+"'"; //will take the id from JTextField....
					PreparedStatement pst1= conn.prepareStatement(query1);
					pst1.execute();
					refresh();
					pst1.close();
					
					lblNewLabel_IMG.setIcon(null);
					textField_NAME.setText(null);
					textField_ID.setText(null);
				    editorPane.setText(null);
				    
				    
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(340, 213, 32, 33);
		frame.getContentPane().add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 23, 123, 122);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(BLOCK_LIST.class.getResource("/img/ADMIN_NATURE.jpg")));
		lblNewLabel_1.setBounds(0, -96, 769, 482);
		frame.getContentPane().add(lblNewLabel_1);
	}

	public void setvisible() {
		// TODO Auto-generated method stub
		BLOCK_LIST window = new BLOCK_LIST();
		window.frame.setVisible(true);
	}
}
