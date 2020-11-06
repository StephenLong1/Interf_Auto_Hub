package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import dao.TesterDao;
import model.Tester;
import model.UserType;
import source.SSHService;
import util.StringUtil;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class LoginFrame_1 extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordTextField;
	private JComboBox comboBox_userType;
	private JTextField textField_userName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LoginFrame_1 frame = new LoginFrame_1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame_1() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int whidth=getWidth();
				int height=getHeight();
			}
		});
		setTitle("自动化登陆界面");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame_1.class.getResource("/测试自动化.png")));
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 636);
		setLocationRelativeTo(null);
		setResizable(false);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("核心项目组  自动化测试工具");
		lblNewLabel.setBounds(222, 43, 591, 145);
		lblNewLabel.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/自动化 (1).png")));
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		
		JLabel label = new JLabel("用户名：");
		label.setBounds(306, 218, 129, 42);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/用户名.png")));
		
		JLabel label_1 = new JLabel("密  码：");
		label_1.setBounds(305, 278, 125, 42);
		label_1.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/密 码.png")));
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		passwordTextField = new JPasswordField();
		passwordTextField.getKeyListeners();
		passwordTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		passwordTextField.setBounds(439, 288, 210, 29);
		passwordTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		passwordTextField.setColumns(10);
		passwordTextField.setText(SSHService.TesterPassword());
		
		JLabel label_1_1 = new JLabel("用户类型：");
		label_1_1.setBounds(305, 350, 129, 42);
		label_1_1.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/数据中心-用户类型管理.png")));
		label_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
	    comboBox_userType = new JComboBox();
	    comboBox_userType.setBounds(439, 358, 211, 28);
	    comboBox_userType.setToolTipText("");

		comboBox_userType.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		comboBox_userType.setModel(new DefaultComboBoxModel(new UserType[] {UserType.TESTER, UserType.DEVELOPER, UserType.ADMIN}));
		
		JButton button_login = new JButton("登录");
		button_login.setBounds(317, 467, 101, 39);
		button_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction(e);
				
			}
		});
		button_login.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		button_login.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/登录.png")));
		
		JButton button_reset = new JButton("重置");
		button_reset.setBounds(533, 467, 101, 38);
		button_reset.setIcon(new ImageIcon(LoginFrame_1.class.getResource("/重置.png")));
		button_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetValue(e);
			}
		});
		button_reset.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		contentPane.setLayout(null);
		contentPane.add(label);
		contentPane.add(label_1_1);
		contentPane.add(comboBox_userType);
		contentPane.add(label_1);
		contentPane.add(passwordTextField);
		contentPane.add(button_login);
		contentPane.add(button_reset);
		contentPane.add(lblNewLabel);
		
		textField_userName = new JTextField();
		textField_userName.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_userName.setBounds(439, 227, 210, 29);
		contentPane.add(textField_userName);
		textField_userName.setColumns(10);
		textField_userName.setText(SSHService.TesterName());
	}

	protected void loginAction(ActionEvent e) {
		// TODO Auto-generated method stub
//	    String userName = comboBox_userName.getSelectedItem().toString();
		String userName = textField_userName.getText().toString();
	    String password = passwordTextField.getText().toString();
	    UserType selectedItem = (UserType)comboBox_userType.getSelectedItem();
	    
	    if(StringUtil.isEmpty(userName)){
	    	JOptionPane.showMessageDialog(this, "用户名不能为空");
	    	return;
	    }
	    if(StringUtil.isEmpty(password)){
	    	JOptionPane.showMessageDialog(this, "密码不能为空");
	    	return;
	    }
	    if("测试人员".equals(selectedItem.getName())) {
	    	TesterDao testerDao = new TesterDao();
	    	Tester tester = new Tester();
	    	tester.setName(userName);
	    	tester.setPassword(password);
	    	Tester test = testerDao.login(tester);
	    	if(test == null) {
	    		JOptionPane.showMessageDialog(this, "您的用户名与密码不匹配");
	    	}else {
	    		SSHService.writeProperties("TesterName", userName);
		    	SSHService.writeProperties("TesterPassword", password);
		    	this.dispose();
		    	JOptionPane.showMessageDialog(null, "欢迎【"+selectedItem.getName()+"】："+tester.getName());
		    	new MainFrame_2().setVisible(true);
	    	}
	    }else if("开发人员".equals(selectedItem.getName())) {
	    	//开发人员登录
		}else {
			//管理员登录
		}
	}

	protected void resetValue(ActionEvent e) {
		// TODO Auto-generated method stub
		textField_userName.setText("");
		passwordTextField.setText("");
		comboBox_userType.setSelectedIndex(0);
	}
}