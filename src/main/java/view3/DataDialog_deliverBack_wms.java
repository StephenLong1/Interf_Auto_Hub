package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import view4.RunDialog_orderCancel1;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_deliverBack_wms extends JDialog {
	private JTextField textField_database;
	private JLabel lblNewLabel_2;
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
			DataDialog_deliverBack_wms dialog = new DataDialog_deliverBack_wms();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_deliverBack_wms() {
		setTitle("填写订单取消接口数据");
		setBounds(100, 100, 396, 241);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_database = new JTextField();
		textField_database.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_database.setBounds(159, 40, 181, 24);
		textField_database.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_database.setColumns(15);
		
		lblNewLabel_2 = new JLabel("dataBase:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(58, 40, 91, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_database);
		
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
		lblNewLabel.setBounds(58, 92, 91, 24);
		getContentPane().add(lblNewLabel);
		
		textField_order_sn = new JTextField();
		textField_order_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_order_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_sn.setColumns(15);
		textField_order_sn.setBounds(159, 92, 181, 24);
		getContentPane().add(textField_order_sn);
		

		
		btnNewButton_save_1 = new JButton(" 保 存");
		btnNewButton_save_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAndRun(e);
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
				String database = SSHService.dataBase();
				String order_sn = SSHService.order_sn();
				textField_database.setText(database);
				textField_order_sn.setText(order_sn);
			}
		});
		btnNewButton_getProperties_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties_1.setBounds(236, 158, 77, 30);
		getContentPane().add(btnNewButton_getProperties_1);
	}

	protected void relatedToData() {
		SSHService.writeProperties("deal_code",textField_database.getText());
		SSHService.writeProperties("order_sn",textField_database.getText()+"a");

//		AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//		AutoLogger.log.info("订单号："+SSHService.order_sn());

		
	}

	protected void save() {
		// TODO Auto-generated method stub
		String database = textField_database.getText().toString();
		String order_sn = textField_order_sn.getText().toString();

		if(database.trim().isEmpty() || order_sn.trim().isEmpty()
			) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("database",database);
			SSHService.writeProperties("order_sn",order_sn);

//			AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//			AutoLogger.log.info("订单号："+SSHService.order_sn());

			this.dispose();
//			relatedToData();   // 关联已输入的数据
			new RunDialog_orderCancel1().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String database = SSHService.dataBase();
        String order_sn = SSHService.order_sn();

		textField_database.setText(database);
		textField_order_sn.setText(order_sn);

	}
	
	protected void saveAndRun(ActionEvent e) {
		String database = textField_database.getText();
		String order_sn = textField_order_sn.getText();
		SSHService.writeProperties("database", database);
		SSHService.writeProperties("order_sn", order_sn);
		HttpClientKw.doGet("192.168.11.111:8080/deliveryOrder?","database="+database+"&order_sn="+order_sn+"", "", "", "", "", "");
		String a = HttpClientKw.result;
		JOptionPane.showMessageDialog(this,a);
	}
}