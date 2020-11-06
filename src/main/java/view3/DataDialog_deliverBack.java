package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_deliveryBack;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_deliverBack extends JDialog {
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel;
	private JTextField textField_order_sn;
	private JButton btnNewButton_save_1;
	private JButton btnNewButton_getProperties_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_deliverBack dialog = new DataDialog_deliverBack();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_deliverBack() {
		setTitle("填写订单取消接口数据");
		setBounds(100, 100, 396, 241);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(64, 223, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(174, 223, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel = new JLabel("order sn：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(64, 68, 79, 24);
		getContentPane().add(lblNewLabel);
		
		textField_order_sn = new JTextField();
		textField_order_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_order_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_sn.setColumns(15);
		textField_order_sn.setBounds(151, 68, 172, 24);
		getContentPane().add(textField_order_sn);
		

		
		btnNewButton_save_1 = new JButton(" 保 存");
		btnNewButton_save_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save_1.setBounds(72, 158, 77, 30);
		getContentPane().add(btnNewButton_save_1);
		
		btnNewButton_getProperties_1 = new JButton("获 取");
		btnNewButton_getProperties_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties_1.setBounds(236, 158, 77, 30);
		getContentPane().add(btnNewButton_getProperties_1);
	}



	protected void save() {
		// TODO Auto-generated method stub
		String order_sn = textField_order_sn.getText().toString();
		if( order_sn.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("order_sn",order_sn);
			AutoLogger.log.info("订单号："+SSHService.order_sn());
			this.dispose();
			new RunDialog_deliveryBack().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String order_sn = SSHService.order_sn();
		textField_order_sn.setText(order_sn);
	}
	
}