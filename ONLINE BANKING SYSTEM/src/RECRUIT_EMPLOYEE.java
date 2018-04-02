import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
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
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class RECRUIT_EMPLOYEE {

	private JFrame frame;
	private JTextField textField_NAME;
	private JTextField textField_ID;
	private JTextField textField_USER_NAME;
	private JTextField textField_PASSWORD;
	private JTextField textField_SALARY;
	private JTable table;
    Connection conn=null;
    private JTextField txtUploadImage;
    private JTextField textField_POSITION;
    private JTextField textField_CONTACT;
    JLabel lblNewLabel;
    JLabel lblNewLabel_IMAGE;
    JEditorPane editorPane;
    private JLabel lblNewLabel_7;
    private JEditorPane editorPane_1;
    private JTabbedPane tabbedPane;
    private JTabbedPane tabbedPane_1;
    private JLabel lblNewLabel_8;
    private JLabel lblNewLabel_9;
    private JLabel lblNewLabel_10;
    private JLabel lblNewLabel_11;
    private JLabel lblNewLabel_12;
    private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RECRUIT_EMPLOYEE window = new RECRUIT_EMPLOYEE();
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
	
	public RECRUIT_EMPLOYEE() {
		conn=DB_CONNECTOR.Connect_data_base();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
    public void setting_null(){
    	txtUploadImage.setText(null);
        textField_POSITION.setText(null);
        textField_CONTACT.setText(null);
        textField_CONTACT.setText(null);
        lblNewLabel_IMAGE.setIcon(null);;
        editorPane.setText(null);
        textField_NAME.setText(null);
    	textField_ID.setText(null);
    	textField_USER_NAME.setText(null);
    	textField_PASSWORD.setText(null);
    	textField_SALARY.setText(null);
    	editorPane_1.setText(null);
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
	
	public ImageIcon resizeimage(String image_path){
		
		ImageIcon selected_image=new ImageIcon(image_path);
		Image Img=selected_image.getImage();
		Image newimg=Img.getScaledInstance(lblNewLabel_IMAGE.getWidth(), lblNewLabel_IMAGE.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image=new ImageIcon(newimg);
		return image;
		 
	}
	
	
	public void refresh(){
		try{
			String query="select ID,NAME,POSITION,SALARY from employee";
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(RECRUIT_EMPLOYEE.class.getResource("/img/bank.png")));
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 255, 204));
		frame.setBounds(100, 100, 831, 564);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("EMPLOYEE NAME:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblName.setBounds(10, 41, 114, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblNewLabel_1 = new JLabel("EMPLOYEE ID");
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 70, 114, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USER NAME:");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 99, 114, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("PASSWORD:");
		lblNewLabel_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 134, 114, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("SALARY RANGE:");
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(10, 159, 114, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		textField_NAME = new JTextField();
		textField_NAME.setBounds(125, 38, 114, 20);
		frame.getContentPane().add(textField_NAME);
		textField_NAME.setColumns(10);
		
		textField_ID = new JTextField();
		textField_ID.setBounds(125, 66, 114, 20);
		frame.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		textField_USER_NAME = new JTextField();
		textField_USER_NAME.setBounds(125, 95, 114, 22);
		frame.getContentPane().add(textField_USER_NAME);
		textField_USER_NAME.setColumns(10);
		
		textField_PASSWORD = new JTextField();
		textField_PASSWORD.setBounds(125, 128, 114, 20);
		frame.getContentPane().add(textField_PASSWORD);
		textField_PASSWORD.setColumns(10);
		
		textField_SALARY = new JTextField();
		textField_SALARY.setBounds(125, 156, 114, 20);
		frame.getContentPane().add(textField_SALARY);
		textField_SALARY.setColumns(10);
		
		lblNewLabel_IMAGE = new JLabel("");
		lblNewLabel_IMAGE.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_IMAGE.setBounds(310, 70, 124, 103);
		frame.getContentPane().add(lblNewLabel_IMAGE);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/detective-magnifying-glass.png")));
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
		btnNewButton.setBounds(282, 187, 26, 23);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(507, 99, 286, 404);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("load data");
		btnNewButton_1.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/refresh-round-symbol.png")));
		btnNewButton_1.setBackground(new Color(224, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select ID,NAME,POSITION,SALARY from employee";
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
		btnNewButton_1.setBounds(508, 44, 43, 40);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("save");
		btnNewButton_2.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/open-book-top-view.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtUploadImage.getText().equals("UPLOAD IMAGE")==false){
				try{
					String query="insert into employee (ID,NAME,USERNAME,PASSWORD,SALARY,POSITION,IMAGE,ADDRESS,DESCRIPTION) values(?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst=conn.prepareStatement(query);
					byte[] imageFileArr=getByteArrayFromFile(txtUploadImage.getText());
					
					pst.setString(1, textField_ID.getText());
					pst.setString(2, textField_NAME.getText());
					pst.setString(3, textField_USER_NAME.getText());
					pst.setString(4, textField_PASSWORD.getText());
					pst.setString(5, textField_SALARY.getText());
					pst.setString(6, textField_POSITION.getText());
					pst.setBytes(7, imageFileArr);
					pst.setString(8, editorPane.getText());
					pst.setString(9, editorPane_1.getText());
					
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "data saved");
					
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				refresh();
				}else{
					JOptionPane.showMessageDialog(null, "you must upload image");
				}
			}
		});
		btnNewButton_2.setBounds(310, 429, 49, 40);
		frame.getContentPane().add(btnNewButton_2);
		
		txtUploadImage = new JTextField();
		txtUploadImage.setEditable(false);
		txtUploadImage.setText("UPLOAD IMAGE");
		txtUploadImage.setBounds(310, 187, 135, 20);
		frame.getContentPane().add(txtUploadImage);
		txtUploadImage.setColumns(10);
		
		lblNewLabel = new JLabel("POSITION:");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 190, 114, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField_POSITION = new JTextField();
		textField_POSITION.setBounds(125, 187, 114, 20);
		frame.getContentPane().add(textField_POSITION);
		textField_POSITION.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("ADDRESS");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 243, 92, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(112, 243, 159, 103);
		frame.getContentPane().add(editorPane);
		
		JLabel lblNewLabel_6 = new JLabel("CONTACT NO:");
		lblNewLabel_6.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(10, 215, 114, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		textField_CONTACT = new JTextField();
		textField_CONTACT.setBounds(125, 212, 114, 20);
		frame.getContentPane().add(textField_CONTACT);
		textField_CONTACT.setColumns(10);
		
		JButton btnNewButton_CLEAR = new JButton("");
		btnNewButton_CLEAR.setToolTipText("clear");
		btnNewButton_CLEAR.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/cleaning-service.png")));
		btnNewButton_CLEAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setting_null();
			}
		});
		btnNewButton_CLEAR.setBounds(310, 364, 49, 40);
		frame.getContentPane().add(btnNewButton_CLEAR);
		
		lblNewLabel_9 = new JLabel("SAVE");
		lblNewLabel_9.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(369, 446, 65, 14);
		frame.getContentPane().add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("CLEAR");
		lblNewLabel_10.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(369, 380, 65, 14);
		frame.getContentPane().add(lblNewLabel_10);
		
		lblNewLabel_12 = new JLabel("LOAD DATA");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_12.setBounds(561, 55, 124, 14);
		frame.getContentPane().add(lblNewLabel_12);
		
		lblNewLabel_7 = new JLabel("DESCRIPTION:");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		lblNewLabel_7.setBounds(10, 364, 105, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(112, 364, 159, 103);
		frame.getContentPane().add(editorPane_1);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(310, 68, 124, 105);
		frame.getContentPane().add(tabbedPane);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(469, 0, 16, 525);
		frame.getContentPane().add(tabbedPane_1);
		
		lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/august 13 blur feature 5.jpg")));
		lblNewLabel_8.setBounds(0, 0, 471, 525);
		frame.getContentPane().add(lblNewLabel_8);
		
		lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(RECRUIT_EMPLOYEE.class.getResource("/img/BH.jpg")));
		lblNewLabel_11.setBounds(481, 0, 344, 525);
		frame.getContentPane().add(lblNewLabel_11);
	}

	public void setVisible() {
		// TODO Auto-generated method stub
		RECRUIT_EMPLOYEE window = new RECRUIT_EMPLOYEE();
		window.frame.setVisible(true);
	}
}
