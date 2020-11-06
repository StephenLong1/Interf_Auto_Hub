package view2;

import javax.swing.JButton;
import javax.swing.JDialog;
import common.SendMailTest;
import source.SSHService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BugDialog_sendEmails extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BugDialog_sendEmails dialog = new BugDialog_sendEmails();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BugDialog_sendEmails() {
		setTitle("测试需求缺陷报告提交");
		setBounds(100, 100, 564, 432);
		setResizable(false);
		setLocationRelativeTo(null);

		getContentPane().setLayout(null);
		{
			JButton btnNewButton = new JButton("发 送");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					isSend();
				}
			});
			btnNewButton.setForeground(new Color(255, 0, 0));
			btnNewButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
			btnNewButton.setBounds(225, 322, 99, 46);
			getContentPane().add(btnNewButton);
		}
		{
			JLabel lblNewLabel = new JLabel("需改善的功能：");
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			lblNewLabel.setBounds(22, 95, 99, 40);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel = new JLabel("需增加的功能：");
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			lblNewLabel.setBounds(22, 172, 99, 40);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel = new JLabel("创新小建议：");
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			lblNewLabel.setBounds(22, 242, 99, 40);
			getContentPane().add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setBounds(118, 101, 401, 32);
			getContentPane().add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(118, 178, 401, 32);
			getContentPane().add(textField_1);
		}
		{
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(118, 248, 401, 32);
			getContentPane().add(textField_2);
		}
		{
			JLabel label = new JLabel("");
			label.setBounds(184, 109, 54, 15);
			getContentPane().add(label);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("测 试 反 馈");
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
			lblNewLabel_1.setBounds(221, 23, 199, 40);
			getContentPane().add(lblNewLabel_1);
		}
	}

	protected void isSend() {
		// TODO Auto-generated method stub
		if(textField.getText().trim().isEmpty() && textField_1.getText().trim().isEmpty()
				&& textField_2.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "亲亲, 输一下下, 为了不断完善,  测试小工具一定会满足你的一切能实现的需求~");
		}else {
			SendMailTest email = new SendMailTest();
			String content1 = "【需改善的功能】："+textField.getText() +  "\n" ;
			String content2 = "【需增加的功能】："+textField_1.getText() +  "\n" ;
			String content3 = "【创新的小建议】："+textField_2.getText() +  "\n" ;
			String name     = "------------------------------------发件人:  " + SSHService.TesterName()+"---------------------------------------- " ;
			String info = content1 + content2 + content3 + name;
			email.sendEmail(info);
			JOptionPane.showMessageDialog(this, "发送成功");
			dispose();
		}
	}
}