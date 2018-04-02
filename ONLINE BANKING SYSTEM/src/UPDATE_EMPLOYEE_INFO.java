import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;

public class UPDATE_EMPLOYEE_INFO {

	private JFrame frmEmployeeDataUpdate;
	private JTable table;
	private JTextField textField_ID;
	private JTextField textField_NAME;
	private JTextField textField_USER_NAME;
	private JTextField textField_SALARY;
	private JTextField textField_PASSWORD;
	private JTextField textField_POSITION;
    Connection conn=null;
    private JTextField txtUploadImage;
    JEditorPane editorPane_ADDRESS;
    JEditorPane editorPane_DESCRIPTION;
    JLabel lblNewLabel_IMAGE;
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UPDATE_EMPLOYEE_INFO window = new UPDATE_EMPLOYEE_INFO();
					window.frmEmployeeDataUpdate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public void img(JTextField txtUploadImage, JTextField textField_ID){
		try {
		byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
		String query="update employee SET IMAGE=? where ID='"+textField_ID.getText()+"' ";
	    PreparedStatement pst;
		pst = conn.prepareStatement(query);
		pst.setBytes(1, imageFileArr);
		pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void clear_field(){
		txtUploadImage.setText(null);
		textField_ID.setText(null);;
		textField_NAME.setText(null);
		textField_USER_NAME.setText(null);
	    textField_SALARY.setText(null);
		textField_PASSWORD.setText(null);
		textField_POSITION.setText(null);
		editorPane_ADDRESS.setText(null);
		lblNewLabel_IMAGE.setIcon(null);
		editorPane_DESCRIPTION.setText(null);
		txtUploadImage.setText(null);
	}
	
	public void refresh(){        //this method has to be called in every button's Actionperformed method.
		try{
			String query="select ID,NAME,SALARY,POSITION,USERNAME,PASSWORD,SALARY from employee";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	public UPDATE_EMPLOYEE_INFO() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
public ImageIcon resizeimage(String image_path){
		
		ImageIcon selected_image=new ImageIcon(image_path);
		Image Img=selected_image.getImage();
		Image newimg=Img.getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image=new ImageIcon(newimg);
		return image;
		 
	}
	
	private byte[] getByteArrayFromFile(String filePath){  
	    byte[] result=null;  
	    FileInputStream fileInStr=null;  
	    try{  
	        File imgFile=new File(filePath);  
	        fileInStr=new FileInputStream(imgFile);  
	        long imageSize=imgFile.length();  
	          
	        if(imageSize>Integer.MAX_VALUE){  
	            return null;    //image is too large  
	        }  
	          
	        if(imageSize>0){  
	            result=new byte[(int)imageSize];  
	            fileInStr.read(result);  
	        }  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            fileInStr.close();  
	        } catch (Exception e) {  
	        }  
	    }  
	    return result;  
	}
	
	private void initialize() {
		frmEmployeeDataUpdate = new JFrame();
		frmEmployeeDataUpdate.setResizable(false);
		frmEmployeeDataUpdate.setIconImage(Toolkit.getDefaultToolkit().getImage(UPDATE_EMPLOYEE_INFO.class.getResource("/img/bank.png")));
		frmEmployeeDataUpdate.setTitle("Employee Data Update");
		frmEmployeeDataUpdate.setBackground(new Color(153, 50, 204));
		frmEmployeeDataUpdate.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmEmployeeDataUpdate.getContentPane().setForeground(new Color(119, 136, 153));
		frmEmployeeDataUpdate.setBounds(100, 100, 1020, 478);
		frmEmployeeDataUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEmployeeDataUpdate.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(609, 93, 353, 321);
		frmEmployeeDataUpdate.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {      //MOUSE CLICKED ACTION
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clear_field();
				try{
				int row=table.getSelectedRow();
				String ID=(table.getModel().getValueAt(row, 0)).toString();
				
				String query="select * from employee where ID='"+ID+"' ";
				PreparedStatement pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				
				
					textField_ID.setText(rs.getString("ID"));;
					textField_NAME.setText(rs.getString("NAME"));
					textField_USER_NAME.setText(rs.getString("USERNAME"));
				    textField_SALARY.setText(rs.getString("SALARY"));
					textField_PASSWORD.setText(rs.getString("PASSWORD"));
					textField_POSITION.setText(rs.getString("POSITION"));
					editorPane_DESCRIPTION.setText(rs.getString("DESCRIPTION"));
					
					if(rs.getBytes("IMAGE")!=null){
						byte[] img_data=rs.getBytes("IMAGE");
						ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel_IMAGE.setIcon(img);
					}
						editorPane_DESCRIPTION.setText(rs.getString("DESCRIPTION"));
						editorPane_ADDRESS.setText(rs.getString("ADDRESS"));
					
				
				pst.close();
				rs.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				refresh();
			}
		});
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 58, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NAME");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 83, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USERNAME");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 108, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("SALARY");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 133, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setToolTipText("image field");
		lblNewLabel_IMAGE.setBounds(381, 58, 133, 128);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_IMAGE);
		
		textField_ID = new JTextField();
		textField_ID.setEditable(false);
		textField_ID.setBounds(92, 55, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		textField_NAME = new JTextField();
		textField_NAME.setBounds(92, 80, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		
		textField_USER_NAME = new JTextField();
		textField_USER_NAME.setBounds(92, 105, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_USER_NAME);
		textField_USER_NAME.setColumns(10);
		
		textField_SALARY = new JTextField();
		textField_SALARY.setBounds(92, 130, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_SALARY);
		textField_SALARY.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("update data");
		btnNewButton.setIcon(new ImageIcon(UPDATE_EMPLOYEE_INFO.class.getResource("/img/cloud-upload.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="Update employee SET ADDRESS='"+editorPane_ADDRESS.getText()+"', DESCRIPTION='"+editorPane_DESCRIPTION.getText()+"', POSITION='"+textField_POSITION.getText()+"' ,PASSWORD='"+textField_PASSWORD.getText()+"' ,SALARY='"+textField_SALARY.getText()+"' ,NAME='"+textField_NAME.getText()+"' ,USERNAME='"+textField_USER_NAME.getText()+"' where ID='"+textField_ID.getText()+"' ";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "updated");
					pst.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
				refresh();
			}
			
		});
		btnNewButton.setBounds(194, 346, 44, 33);
		frmEmployeeDataUpdate.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("load employee database");
		btnNewButton_1.setIcon(new ImageIcon(UPDATE_EMPLOYEE_INFO.class.getResource("/img/loading-cloud.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select ID,NAME,SALARY,POSITION,USERNAME,PASSWORD,SALARY from employee";
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
		btnNewButton_1.setBounds(609, 39, 44, 33);
		frmEmployeeDataUpdate.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("PASSWORD");
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(10, 158, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_4);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(92, 155, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("POSITION");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(10, 183, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_5);
		
		textField_POSITION = new JTextField();
		textField_POSITION.setBounds(92, 180, 114, 20);
		frmEmployeeDataUpdate.getContentPane().add(textField_POSITION);
		textField_POSITION.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(538, 0, 16, 439);
		frmEmployeeDataUpdate.getContentPane().add(tabbedPane);
		
		editorPane_ADDRESS = new JEditorPane();
		editorPane_ADDRESS.setBounds(92, 211, 147, 98);
		frmEmployeeDataUpdate.getContentPane().add(editorPane_ADDRESS);
		
		txtUploadImage = new JTextField();
		txtUploadImage.setBounds(381, 191, 133, 20);
		frmEmployeeDataUpdate.getContentPane().add(txtUploadImage);
		txtUploadImage.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("upload image");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file=new JFileChooser();
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.image","jpg","gif","png");
				file.addChoosableFileFilter(filter);
				int result=file.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION){
					File selectedFile = file.getSelectedFile();
					String path=selectedFile.getAbsolutePath();
					lblNewLabel_IMAGE.setIcon(resizeimage(path));
					txtUploadImage.setText(path);
				}
				byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
				img(txtUploadImage,textField_ID);
			}
		});
		btnNewButton_2.setBounds(354, 188, 22, 23);
		frmEmployeeDataUpdate.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_6 = new JLabel("UPDATE");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(194, 390, 58, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_9 = new JLabel("ADDRESS");
		lblNewLabel_9.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(10, 208, 68, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("CLEAR");
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(291, 390, 46, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_8 = new JLabel("DESCRIPTION");
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(268, 245, 89, 14);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_8);
		
		editorPane_DESCRIPTION = new JEditorPane();
		editorPane_DESCRIPTION.setBounds(367, 241, 147, 71);
		frmEmployeeDataUpdate.getContentPane().add(editorPane_DESCRIPTION);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(381, 58, 133, 128);
		frmEmployeeDataUpdate.getContentPane().add(tabbedPane_1);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(UPDATE_EMPLOYEE_INFO.class.getResource("/img/cleaning-service.png")));
		btnNewButton_3.setToolTipText("clear fields");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_field();
			}
		});
		btnNewButton_3.setBounds(291, 346, 44, 33);
		frmEmployeeDataUpdate.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(UPDATE_EMPLOYEE_INFO.class.getResource("/img/employee_log.png")));
		lblNewLabel_7.setBounds(0, 0, 541, 439);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(UPDATE_EMPLOYEE_INFO.class.getResource("/img/customer_log_in.jpg")));
		lblNewLabel_11.setBounds(554, 0, 450, 439);
		frmEmployeeDataUpdate.getContentPane().add(lblNewLabel_11);

	}

	public void setVisible() {
		// TODO Auto-generated method stub
		UPDATE_EMPLOYEE_INFO window = new UPDATE_EMPLOYEE_INFO();
		window.frmEmployeeDataUpdate.setVisible(true);
	}
}
