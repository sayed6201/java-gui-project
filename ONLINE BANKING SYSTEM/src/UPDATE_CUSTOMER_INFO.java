import java.awt.EventQueue;
import java.awt.Image;

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
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class UPDATE_CUSTOMER_INFO {

	private JFrame frmCustomerDataUpdate;
	private JTable table;
	private JTextField textField_AC_NO;
	private JTextField textField_NAME;
	private JTextField textField_USER_NAME;
	private JTextField textField_BALANCE;
	private JTextField textField_PASSWORD;
	private JTextField textField_PROFESSION;
    Connection conn=null;
    private JTextField textField_GENDER;
    private JTextField txtUploadImage;
    JLabel lblNewLabel_IMAGE ;
    JEditorPane editorPane_ADDRESS;
    JEditorPane editorPane_DESCRIPTION;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UPDATE_CUSTOMER_INFO window = new UPDATE_CUSTOMER_INFO();
					window.frmCustomerDataUpdate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public void clear_field(){
		txtUploadImage.setText(null);
		textField_AC_NO.setText(null);;
		textField_NAME.setText(null);
		textField_USER_NAME.setText(null);
	    textField_BALANCE.setText(null);
		textField_PASSWORD.setText(null);
		textField_PROFESSION.setText(null);
		editorPane_ADDRESS.setText(null);
		textField_GENDER.setText(null);
		lblNewLabel_IMAGE.setIcon(null);
		editorPane_DESCRIPTION.setText(null);
	}
	

	
public void img(JTextField txtUploadImage, JTextField textField_AC_NO){
	try {
	byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
	String query="update customer set IMAGE=? where ACCOUNT_NO='"+textField_AC_NO.getText()+"' ";
    PreparedStatement pst;
	pst = conn.prepareStatement(query);
	pst.setBytes(1, imageFileArr);
	pst.execute();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
	

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

	
	public void refresh(){        //this method has to be called in every button's Actionperformed method.
		try{
			String query="select ACCOUNT_NO,NAME,BALANCE,PROFESSION,USERNAME,PASSWORD,ADDRESS,GENDER from customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils.rar has to be downloaded
			pst.close();
			rs.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	public UPDATE_CUSTOMER_INFO() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCustomerDataUpdate = new JFrame();
		frmCustomerDataUpdate.setIconImage(Toolkit.getDefaultToolkit().getImage(UPDATE_CUSTOMER_INFO.class.getResource("/img/bank.png")));
		frmCustomerDataUpdate.setResizable(false);
		frmCustomerDataUpdate.setTitle("Customer Data Update");
		frmCustomerDataUpdate.setBackground(Color.LIGHT_GRAY);
		frmCustomerDataUpdate.getContentPane().setBackground(new Color(0, 255, 127));
		frmCustomerDataUpdate.getContentPane().setForeground(new Color(119, 136, 153));
		frmCustomerDataUpdate.setBounds(100, 100, 1063, 472);
		frmCustomerDataUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCustomerDataUpdate.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(648, 83, 365, 321);
		frmCustomerDataUpdate.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {      //MOUSE CLICKED ACTION
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clear_field();
				try{
				int row=table.getSelectedRow();
				String AC_NO=(table.getModel().getValueAt(row, 0)).toString();
				
				String query="select * from customer where ACCOUNT_NO='"+AC_NO+"' ";
				PreparedStatement pst=conn.prepareStatement(query);
				
				ResultSet rs=pst.executeQuery();
				    
				    
					textField_AC_NO.setText(rs.getString("ACCOUNT_NO"));;
					textField_NAME.setText(rs.getString("NAME"));
					textField_USER_NAME.setText(rs.getString("USERNAME"));
				    textField_BALANCE.setText(rs.getString("BALANCE"));
					textField_PASSWORD.setText(rs.getString("PASSWORD"));
					textField_PROFESSION.setText(rs.getString("PROFESSION"));
					editorPane_ADDRESS.setText(rs.getString("ADDRESS"));
					textField_GENDER.setText(rs.getString("GENDER"));
					
				if(rs.getBytes("IMAGE")!=null){
					byte[] img_data=rs.getBytes("IMAGE");
					ImageIcon img=new ImageIcon(new ImageIcon(img_data).getImage().getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_DEFAULT));
					lblNewLabel_IMAGE.setIcon(img);
				}
					editorPane_DESCRIPTION.setText(rs.getString("DESCRIPTION"));
				
				pst.close();
				rs.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				refresh();
			}
		});
		
		JLabel lblNewLabel = new JLabel("AC NO:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 58, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NAME");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 83, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USERNAME");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 108, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("BALANCE");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 133, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_3);
		
		textField_AC_NO = new JTextField();
		textField_AC_NO.setEditable(false);
		textField_AC_NO.setBounds(92, 55, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_AC_NO);
		textField_AC_NO.setColumns(10);
		
		textField_NAME = new JTextField();
		textField_NAME.setBounds(92, 80, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		
		textField_USER_NAME = new JTextField();
		textField_USER_NAME.setBounds(92, 105, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_USER_NAME);
		textField_USER_NAME.setColumns(10);
		
		textField_BALANCE = new JTextField();
		textField_BALANCE.setEditable(false);
		textField_BALANCE.setBounds(92, 130, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_BALANCE);
		textField_BALANCE.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(UPDATE_CUSTOMER_INFO.class.getResource("/img/cloud-upload.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
                    String query="update customer set NAME='"+textField_NAME.getText()+"',USERNAME='"+textField_USER_NAME.getText()+"',PASSWORD='"+textField_PASSWORD.getText()+"',PROFESSION='"+textField_PROFESSION.getText()+"',BALANCE='"+textField_BALANCE.getText()+"',ADDRESS='"+editorPane_ADDRESS.getText()+"',GENDER='"+textField_GENDER.getText()+"',DESCRIPTION='"+editorPane_DESCRIPTION.getText()+"' where ACCOUNT_NO='"+textField_AC_NO.getText()+"' ";
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
		
		btnNewButton.setBounds(234, 364, 46, 33);
		frmCustomerDataUpdate.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("load customer database");
		btnNewButton_1.setIcon(new ImageIcon(UPDATE_CUSTOMER_INFO.class.getResource("/img/refresh-round-symbol.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select ACCOUNT_NO,NAME,BALANCE,PROFESSION,USERNAME,PASSWORD,ADDRESS,GENDER from customer";
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
		btnNewButton_1.setBounds(648, 32, 46, 40);
		frmCustomerDataUpdate.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("PASSWORD");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 158, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_4);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(92, 155, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("PROFESSION");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(10, 183, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_5);
		
		textField_PROFESSION = new JTextField();
		textField_PROFESSION.setBounds(92, 180, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_PROFESSION);
		textField_PROFESSION.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("GENDER:");
		lblNewLabel_7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(10, 214, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_7);
		
		textField_GENDER = new JTextField();
		textField_GENDER.setBounds(92, 211, 109, 20);
		frmCustomerDataUpdate.getContentPane().add(textField_GENDER);
		textField_GENDER.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBounds(606, 0, 19, 443);
		frmCustomerDataUpdate.getContentPane().add(tabbedPane);
		
		JLabel lblNewLabel_8 = new JLabel("ADDRESS:");
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(10, 239, 82, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_8);
		
		editorPane_ADDRESS = new JEditorPane();
		editorPane_ADDRESS.setBounds(92, 242, 141, 93);
		frmCustomerDataUpdate.getContentPane().add(editorPane_ADDRESS);
		
		JLabel lblNewLabel_9 = new JLabel("DESCRIPTION:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(261, 58, 89, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_9);
		
		editorPane_DESCRIPTION = new JEditorPane();
		editorPane_DESCRIPTION.setBounds(356, 55, 157, 83);
		frmCustomerDataUpdate.getContentPane().add(editorPane_DESCRIPTION);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(356, 183, 124, 112);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_IMAGE);
		
		txtUploadImage = new JTextField();
		txtUploadImage.setText("UPLOAD IMAGE");
		txtUploadImage.setEditable(false);
		txtUploadImage.setBounds(356, 298, 124, 20);
		frmCustomerDataUpdate.getContentPane().add(txtUploadImage);
		txtUploadImage.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("");
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
				//byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
				img(txtUploadImage,textField_AC_NO);
			}
		});
		
		JLabel lblNewLabel_12 = new JLabel("CLEAR");
		lblNewLabel_12.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setBounds(359, 408, 46, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_12);
		
		JLabel lblNewLabel_10 = new JLabel("UPDATE DATA");
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(213, 408, 108, 14);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_10);
		btnNewButton_2.setToolTipText("upload image");
		btnNewButton_2.setBounds(326, 298, 25, 23);
		frmCustomerDataUpdate.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_field();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(UPDATE_CUSTOMER_INFO.class.getResource("/img/cleaning-service.png")));
		btnNewButton_3.setToolTipText("clear fields");
		btnNewButton_3.setBounds(359, 364, 46, 33);
		frmCustomerDataUpdate.getContentPane().add(btnNewButton_3);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(356, 183, 124, 104);
		frmCustomerDataUpdate.getContentPane().add(tabbedPane_1);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(UPDATE_CUSTOMER_INFO.class.getResource("/img/house.jpg")));
		lblNewLabel_6.setBounds(0, 0, 605, 460);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(UPDATE_CUSTOMER_INFO.class.getResource("/img/admin_pic.jpg")));
		lblNewLabel_11.setBounds(625, 0, 442, 443);
		frmCustomerDataUpdate.getContentPane().add(lblNewLabel_11);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		UPDATE_CUSTOMER_INFO window = new UPDATE_CUSTOMER_INFO();
		window.frmCustomerDataUpdate.setVisible(true);
	}
}
