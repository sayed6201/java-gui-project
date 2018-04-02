import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JSpinner;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.proteanit.sql.DbUtils;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTable;
import javax.swing.JDesktopPane;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;

public class ADMIN_INFO {

	private JFrame frame;
	private JTable table;
	JLabel lblNewLabel_IMAGE;
    Connection conn=null;
    private JEditorPane editorPane_DISCRIPTION;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    private JLabel lblNewLabel;
    private JScrollPane scrollPane_2;
    private JLabel lblNewLabel_1;
    private JTextField textField_NAME;
    private JLabel lblNewLabel_2;
    private JTextField textField_ID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMIN_INFO window = new ADMIN_INFO();
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
	public ADMIN_INFO() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setBounds(100, 100, 934, 432);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(ADMIN_INFO.class.getResource("/img/eye.png")));
		btnNewButton.setBounds(37, 11, 69, 30);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					
					String query="select * from admin";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));		
					pst.close();
					rs.close();
					}catch(Exception e1){
					e1.printStackTrace();
					}
			}
		});
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(778, 24, 118, 120);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(ADMIN_INFO.class.getResource("/img/clipboard.png")));
		btnNewButton_1.setBounds(367, 11, 69, 30);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MessageFormat header =new MessageFormat("BANKING SYSTEM");
				MessageFormat footer =new MessageFormat("page:1");
				try{
					table.print(JTable.PrintMode.NORMAL,header,footer);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "can not be printed");
				}
			}
		});
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
		});
		scrollPane.setBounds(37, 52, 399, 330);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				 try{
                 	int row=table.getSelectedRow();
     				String ID=(table.getModel().getValueAt(row, 0)).toString();
					String query="select IMAGE,DISCRIPTION,ID,NAME from admin where ID='"+ID+"'";
					PreparedStatement pst=conn.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					if(rs.next()){
						
						if(rs.getBytes("IMAGE")!=null){
						byte[] img_data=rs.getBytes("IMAGE");
						ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel_IMAGE.setIcon(img);
						}
						
						editorPane_DISCRIPTION.setText(rs.getString("DISCRIPTION"));
						textField_NAME.setText(rs.getString("NAME"));
						textField_ID.setText(rs.getString("ID"));
					}else JOptionPane.showMessageDialog(null, "No data found");
					pst.close();
					rs.close();
					}catch(Exception e1){
					e1.printStackTrace();
					}
				
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("NAME");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(557, 157, 69, 14);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(btnNewButton_1);
		
		textField_ID = new JTextField();
		textField_ID.setBounds(626, 180, 162, 20);
		frame.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(557, 182, 69, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_NAME = new JTextField();
		textField_NAME.setBounds(626, 154, 162, 20);
		frame.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(557, 248, 341, 95);
		frame.getContentPane().add(scrollPane_2);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_2.setViewportView(scrollPane_1);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(SystemColor.inactiveCaptionText);
		lblNewLabel.setIcon(new ImageIcon(ADMIN_INFO.class.getResource("/img/BH.jpg")));
		lblNewLabel.setBounds(0, 0, 918, 393);
		frame.getContentPane().add(lblNewLabel);
		
		editorPane_DISCRIPTION = new JEditorPane();
		editorPane_DISCRIPTION.setBounds(557, 248, 339, 93);
		frame.getContentPane().add(editorPane_DISCRIPTION);
		editorPane_DISCRIPTION.setEditable(false);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		ADMIN_INFO window = new ADMIN_INFO();
		window.frame.setVisible(true);
	}
}
