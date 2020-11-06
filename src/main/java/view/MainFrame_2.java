package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.UserType;
import source.SSHService;
import view2.BugDialog_sendEmails;
import view2.CharDialog_findLine;
import view2.TimeDialog_transfer;
import view2.UniDialog_transfer;
import view3.ChoiceDialog_wms;

import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.SystemColor;

public class MainFrame_2 extends JFrame {

	private JPanel contentPane;
	private UserType userType;
	private Object userObject;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame_2 frame = new MainFrame_2();
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
	public MainFrame_2() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int whidth=getWidth();
				int height=getHeight();
			}
		});
		this.userType = userType;
		this.userObject = userObject;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame_2.class.getResource("/类目.png")));
		setTitle("测试系统选择");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 997, 628);
        setLocationRelativeTo(null);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("系统设置");
		setJMenuBar(menuBar);
	
		JMenu menu = new JMenu("系统设置");
		menu.setIcon(new ImageIcon(MainFrame_2.class.getResource("/系统设置 (1).png")));
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("修改密码");
		menuItem.setIcon(new ImageIcon(MainFrame_2.class.getResource("/修改密码 (1).png")));
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("当前版本");
		menuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getVersion();
			}
		});
		menuItem_1.setIcon(new ImageIcon(MainFrame_2.class.getResource("/当前版本 (2).png")));
		menu.add(menuItem_1);
		
		JMenu mnNewMenu = new JMenu("用户管理");
		mnNewMenu.setIcon(new ImageIcon(MainFrame_2.class.getResource("/用户管理.png")));
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem_2 = new JMenuItem("添加用户");
		menuItem_2.setIcon(new ImageIcon(MainFrame_2.class.getResource("/添加用户.png")));
		mnNewMenu.add(menuItem_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("删除用户");
		mntmNewMenuItem.setIcon(new ImageIcon(MainFrame_2.class.getResource("/删除用户.png")));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu menu_1 = new JMenu("帮助");
		menu_1.setIcon(new ImageIcon(MainFrame_2.class.getResource("/帮助 (1).png")));
		menuBar.add(menu_1);
		
		JMenuItem menuItem_3 = new JMenuItem("关于我们");
		menuItem_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutUs(e);
			}
		});
		menuItem_3.setIcon(new ImageIcon(MainFrame_2.class.getResource("/关于我们1.png")));
		menu_1.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("注意事项");
		menuItem_4.setIcon(new ImageIcon(MainFrame_2.class.getResource("/当前版本 (1).png")));
		menu_1.add(menuItem_4);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("天猫单");
		btnNewButton.setBounds(112, 93, 163, 146);
		btnNewButton.setBackground(new Color(253, 245, 230));
		btnNewButton.setSelectedIcon(new ImageIcon(MainFrame_2.class.getResource("/测试自动化.png")));
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 28));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OptionsFrame_3().setVisible(true);;
			}
		});
		
		JButton btnNewButton_1 = new JButton("WMS");
		btnNewButton_1.setBounds(702, 93, 163, 146);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popWindow();
			}
		});
		btnNewButton_1.setBackground(SystemColor.info);
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 28));
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBounds(421, 460, 131, 57);
		btnNewButton_2.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton_2.setIcon(new ImageIcon(MainFrame_2.class.getResource("/返回上一步.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	dispose();
		    	new LoginFrame_1().setVisible(true);
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 24));
		
		JButton btnNewButton_3 = new JButton("Unicode转码");
		btnNewButton_3.setBounds(424, 93, 128, 58);
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UniDialog_transfer().setVisible(true);
			}
		});
		btnNewButton_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		JButton btnNewButton_3_1 = new JButton("字符字数统计");
		btnNewButton_3_1.setBounds(424, 170, 128, 58);
		btnNewButton_3_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CharDialog_findLine().setVisible(true);
			}
		});
		btnNewButton_3_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(" ↓ 测试辅助小功能 ↓");
		lblNewLabel.setBounds(414, 5, 138, 91);
		lblNewLabel.setForeground(new Color(64, 224, 208));
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_2);
		contentPane.add(btnNewButton_3_1);
		contentPane.add(btnNewButton_3);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_3_1_1 = new JButton("需求缺陷提交");
		btnNewButton_3_1_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BugDialog_sendEmails().setVisible(true);
			}
		});
		btnNewButton_3_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnNewButton_3_1_1.setBounds(424, 376, 128, 58);
		contentPane.add(btnNewButton_3_1_1);
		
		JButton btnNewButton_4 = new JButton("OMS");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBrowser("http://39.98.231.61:30003/admin");
			}
		});
		btnNewButton_4.setFont(new Font("宋体", Font.PLAIN, 28));
		btnNewButton_4.setBackground(SystemColor.scrollbar);
		btnNewButton_4.setBounds(112, 288, 163, 146);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("时间戳获取转换");
		btnNewButton_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TimeDialog_transfer().setVisible(true);
			}
		});
		btnNewButton_5.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		btnNewButton_5.setBounds(421, 288, 131, 58);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_4_1 = new JButton("HUB");
		btnNewButton_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBrowser("http://47.92.244.122:30001/login");
			}
		});
		btnNewButton_4_1.setFont(new Font("宋体", Font.PLAIN, 28));
		btnNewButton_4_1.setBackground(SystemColor.scrollbar);
		btnNewButton_4_1.setBounds(702, 288, 163, 146);
		contentPane.add(btnNewButton_4_1);
	}

	protected void openBrowser(String url) {
		// TODO Auto-generated method stub
			if(Desktop.isDesktopSupported()) {
				try {
					// 创建一个URI实例
					java.net.URI uri = java.net.URI.create(url);
					// 获取当前系统桌面扩展
					Desktop dp = Desktop.getDesktop();
					// 判断系统桌面是否支持要执行的功能
					if(dp.isSupported(Desktop.Action.BROWSE)) {
					    //获取系统默认浏览器打开链接
						dp.browse(uri);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}
	


	protected void getVersion() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "当前系统版本为：1.0");
	}

	protected void popWindow() {
		// TODO Auto-generated method stub
		dispose();
		new ChoiceDialog_wms().setVisible(true);
//		JOptionPane.showMessageDialog(this, "当前系统暂未开发, 请选择OMS测试HUB的接口！");
	}

    	

	protected void aboutUs(ActionEvent e) {
		// TODO Auto-generated method stub
		String info = "【质量控制部】出品";
		JOptionPane.showMessageDialog(this, "目前仅支持OMS -> HUB的接口自动化, HUB -> 品牌商不支持！");
//		String[] buttons = {"你真好(●'◡'●), 爱你爱你~~","你真讨厌(ノω<。)ノ))☆., 不喜欢你了~~"};
//		int ret = JOptionPane.showOptionDialog(this, info, "关于我们", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, new ImageIcon(LoginFrame_1.class.getResource("/自动化 (1).png")), buttons, null);
//		if(ret == 0) {
//			// 采用java调用系统浏览器
//				try {
//					URL uri = new URL("http://192.168.1.198:4999/web/#/57?page_id=2236");
////					Desktop.getDesktop().browse(uri);
////					Runtime.getRuntime().exec("explorer http://192.168.1.198:4999/web/#/57?page_id=2236");
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//		}
	}
}
