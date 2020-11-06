package view4;

import javax.swing.JButton;
import javax.swing.JDialog;
import org.testng.TestNG;

import controller.RefundCreate1;
import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class RunDialog_getRefundOnly extends JDialog {
	private JButton btnNewButton;
	JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RunDialog_getRefundOnly dialog = new RunDialog_getRefundOnly();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RunDialog_getRefundOnly() {
		setTitle("生成天猫仅退款单");
		setBounds(100, 100, 854, 527);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);

		{
			btnNewButton = new JButton("R U N");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TestNG testSuite = new TestNG();
					testSuite.setTestClasses(new Class[] {RefundCreate1.class});
					testSuite.run();
					Properties props = new Properties();
					String path = System.getProperty("user.dir")+"\\log\\AutoTest.log";  // 获取项目路径
					try {
						BufferedReader br = new BufferedReader(new FileReader(path));
						String line = null;
						StringBuffer b = new StringBuffer();
						while ((line = br.readLine()) != null) {
							b.append(line.toString()).append("\n");
							}
						textArea.setText(b.toString());
						br.close();
						String text = textArea.getText();

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			btnNewButton.setBounds(11, 10, 93, 37);
			btnNewButton.setForeground(new Color(255, 0, 0));
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		}
		getContentPane().setLayout(null);
		getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 826, 441);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton_1 = new JButton("进入oms");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()) {
					String url = "http://39.98.231.61:30003/admin";
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
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
			}
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnNewButton_1.setBounds(152, 10, 93, 37);
		getContentPane().add(btnNewButton_1);
	}
}
