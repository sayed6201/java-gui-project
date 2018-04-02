import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class CUSTOMER_ENROLLING{

	private JFrame frame;
	private JTextField textField_NAME;
	private JTextField textField_AC_NO;
	private JTextField textField_USER_NAME;
	private JTextField textField_PASSWORD;
	private JTextField textField_BALANCE;
	private JTable table;
    Connection conn=null;
    private JTextField textField_PROFESSION;
    private JTextField txtUploadImage;
    JComboBox comboBox_GENDER;
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
					CUSTOMER_ENROLLING window = new CUSTOMER_ENROLLING();
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
	public CUSTOMER_ENROLLING() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
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
	
	public double current_interest_rate(){
		
		   try{
		    String query="select INTEREST FROM customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			double rate=rs.getDouble("INTEREST");
			pst.close();
			rs.close();
			return rate;
	   }catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
			return 0;
		}
		   
	   }
	
	public double charge_per_month(){
		
		   try{
		    String query="select CHARGE_PER_MONTH FROM customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			double rate=rs.getDouble("CHARGE_PER_MONTH");
			pst.close();
			rs.close();
			return rate;
	   }catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
			return 0;
		}
		   
	   }
	
	public double charge_per_anum(){
		
		   try{
		    String query="select CHARGE_PER_ANUM FROM customer";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			double rate=rs.getDouble("CHARGE_PER_ANUM");
			pst.close();
			rs.close();
			return rate;
	   }catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
			return 0;
		}
		   
	   }
	
	
public ImageIcon resizeimage(String image_path){
		
		ImageIcon selected_image=new ImageIcon(image_path);
		Image Img=selected_image.getImage();
		Image newimg=Img.getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image=new ImageIcon(newimg);
		return image;
		 
	}
	
	public void refresh(){
		try{
			String query="select ACCOUNT_NO,NAME,BALANCE,PROFESSION,ADDRESS from customer";
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CUSTOMER_ENROLLING.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 255, 204));
		frame.setBounds(100, 100, 932, 706);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("CUSTOMER NAME");
		lblName.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(39, 39, 115, 18);
		frame.getContentPane().add(lblName);
		
		JLabel lblNewLabel_1 = new JLabel("AC NO");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(39, 70, 115, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USER NAME:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setBounds(39, 95, 115, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("PASSWORD:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setBounds(39, 120, 115, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("INITIAL DEPOSIT:");
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(39, 145, 115, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		textField_NAME = new JTextField();
		textField_NAME.setBounds(178, 38, 135, 20);
		frame.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		
		textField_AC_NO = new JTextField();
		textField_AC_NO.setBounds(178, 67, 135, 20);
		frame.getContentPane().add(textField_AC_NO);
		textField_AC_NO.setColumns(10);
		
		textField_USER_NAME = new JTextField();
		textField_USER_NAME.setBounds(178, 92, 135, 20);
		frame.getContentPane().add(textField_USER_NAME);
		textField_USER_NAME.setColumns(10);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(178, 117, 135, 20);
		frame.getContentPane().add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		textField_BALANCE = new JTextField();
		textField_BALANCE.setBounds(178, 142, 135, 20);
		frame.getContentPane().add(textField_BALANCE);
		textField_BALANCE.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(524, 156, 337, 325);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(CUSTOMER_ENROLLING.class.getResource("/img/loading-cloud.png")));
		btnNewButton_1.setBounds(738, 548, 44, 33);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select ACCOUNT_NO,NAME,BALANCE,PROFESSION,ADDRESS from customer";
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
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(CUSTOMER_ENROLLING.class.getResource("/img/diskette.png")));
		btnNewButton_2.setBounds(581, 548, 44, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtUploadImage.getText().equals("UPLOAD IMAGE")==false){
				try{
					byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
					String query="insert into customer (ACCOUNT_NO,NAME,USERNAME,PASSWORD,BALANCE,PROFESSION,ADDRESS,GENDER,DESCRIPTION,IMAGE,INTEREST,INTEREST_FRACTION,ADDITION,CHARGE_PER_MONTH,CHARGE_PER_ANUM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst=conn.prepareStatement(query);
					
					pst.setString(1, textField_AC_NO.getText());
					pst.setString(2, textField_NAME.getText());
					pst.setString(3, textField_USER_NAME.getText());
					pst.setString(4, textField_PASSWORD.getText());
					pst.setString(5, textField_BALANCE.getText());
					pst.setString(6, textField_PROFESSION.getText());
					pst.setString(7, editorPane_ADDRESS.getText());
					pst.setString(8, (String)comboBox_GENDER.getSelectedItem());
					pst.setString(9, editorPane_DESCRIPTION.getText());
					pst.setBytes(10, imageFileArr);
					double rate=current_interest_rate();
					double fraction=(rate/100)/12;
					pst.setString(11,Double.toString(rate));
					pst.setString(12,Double.toString(fraction));
					pst.setString(13,Double.toString(fraction*Double.parseDouble(textField_BALANCE.getText())));
					pst.setString(14,Double.toString(charge_per_month()));
					pst.setString(15,Double.toString(charge_per_anum()));
					
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "data saved");
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				refresh();
				}else{
					JOptionPane.showMessageDialog(null, "you must upload customer image");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("PROFESSION");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setBounds(39, 170, 86, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField_PROFESSION = new JTextField();
		textField_PROFESSION.setBounds(178, 167, 135, 20);
		frame.getContentPane().add(textField_PROFESSION);
		textField_PROFESSION.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("ADDRESS:");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setBounds(39, 226, 86, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_5 = new JLabel("GENDER");
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(39, 201, 115, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		comboBox_GENDER = new JComboBox();
		comboBox_GENDER.setBounds(178, 198, 78, 20);
		comboBox_GENDER.setModel(new DefaultComboBoxModel(new String[] {"MALE", "FEMALE"}));
		frame.getContentPane().add(comboBox_GENDER);
		
		editorPane_ADDRESS = new JEditorPane();
		editorPane_ADDRESS.setBounds(178, 229, 189, 98);
		frame.getContentPane().add(editorPane_ADDRESS);
		
		JLabel lblNewLabel_7 = new JLabel("PICTURE:");
		lblNewLabel_7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(39, 342, 86, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(178, 342, 129, 119);
		frame.getContentPane().add(tabbedPane);
		
		txtUploadImage = new JTextField();
		txtUploadImage.setBounds(159, 472, 160, 20);
		txtUploadImage.setEditable(false);
		txtUploadImage.setText("UPLOAD IMAGE");
		frame.getContentPane().add(txtUploadImage);
		txtUploadImage.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(323, 471, 44, 23);
		btnNewButton.addActionListener(new ActionListener() {
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
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(422, 0, 22, 733);
		tabbedPane_1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane_1.setForeground(Color.DARK_GRAY);
		tabbedPane_1.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(tabbedPane_1);
		
		JLabel lblNewLabel_8 = new JLabel("DESCRIPTION:");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_8.setBounds(39, 517, 86, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		editorPane_DESCRIPTION = new JEditorPane();
		editorPane_DESCRIPTION.setBounds(178, 517, 197, 111);
		frame.getContentPane().add(editorPane_DESCRIPTION);
		
		JLabel lblNewLabel_11 = new JLabel("SAVE");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
		lblNewLabel_11.setBounds(585, 592, 46, 14);
		frame.getContentPane().add(lblNewLabel_11);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBounds(178, 350, 124, 111);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(CUSTOMER_ENROLLING.class.getResource("/img/cleaning-service.png")));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField_AC_NO.setText(null);
				textField_NAME.setText(null);
				textField_USER_NAME.setText(null);
				textField_PASSWORD.setText(null);
			    textField_BALANCE.setText(null);
				textField_PROFESSION.setText(null);
				editorPane_ADDRESS.setText(null);
				editorPane_DESCRIPTION.setText(null);
				lblNewLabel_IMAGE.setIcon(null);
				
			}
		});
		
		JLabel lblNewLabel_13 = new JLabel("LOAD DATA");
		lblNewLabel_13.setForeground(Color.WHITE);
		lblNewLabel_13.setFont(new Font("Eras Medium ITC", Font.BOLD, 11));
		lblNewLabel_13.setBounds(725, 592, 78, 14);
		frame.getContentPane().add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("CUSTOMER DATABASE");
		lblNewLabel_14.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblNewLabel_14.setForeground(Color.WHITE);
		lblNewLabel_14.setBounds(601, 119, 230, 14);
		frame.getContentPane().add(lblNewLabel_14);
		
		JLabel lblNewLabel_12 = new JLabel("CLEAR");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
		lblNewLabel_12.setEnabled(true);
		lblNewLabel_12.setBounds(660, 592, 46, 14);
		frame.getContentPane().add(lblNewLabel_12);
		btnNewButton_3.setBounds(660, 548, 44, 33);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon(CUSTOMER_ENROLLING.class.getResource("/img/cool-background.jpg")));
		lblNewLabel_9.setBounds(0, 0, 424, 677);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setIcon(new ImageIcon(CUSTOMER_ENROLLING.class.getResource("/img/august 13 blur feature 5.jpg")));
		lblNewLabel_10.setBounds(442, 0, 493, 677);
		frame.getContentPane().add(lblNewLabel_10);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		CUSTOMER_ENROLLING window = new CUSTOMER_ENROLLING();
		window.frame.setVisible(true);
	}
}