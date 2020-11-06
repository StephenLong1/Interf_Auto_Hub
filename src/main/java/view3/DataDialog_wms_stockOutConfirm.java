package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_delivery1;
import view4.RunDialog_wms_deliveryBack;
import view4.RunDialog_wms_returnOrderConfirm;
import view4.RunDialog_wms_stockOutConfirm;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_wms_stockOutConfirm extends JDialog {
	private JTextField textField_ownerCode;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel;
	private JTextField textField_order_sn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_wms_stockOutConfirm dialog = new DataDialog_wms_stockOutConfirm();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_wms_stockOutConfirm() {
		setTitle("填写订单号");
		setBounds(100, 100, 372, 223);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_ownerCode = new JTextField();
		textField_ownerCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_ownerCode.setBounds(136, 43, 181, 24);
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		
		lblNewLabel_2 = new JLabel("ownerCode:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(22, 42, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_ownerCode);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(61, 138, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(220, 138, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel = new JLabel("order_sn：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(23, 92, 92, 24);
		getContentPane().add(lblNewLabel);
		
		textField_order_sn = new JTextField();
		textField_order_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_order_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_sn.setColumns(15);
		textField_order_sn.setBounds(136, 93, 181, 24);
		getContentPane().add(textField_order_sn);
	}

	protected void relatedToData() {
		SSHService.writeProperties("deal_code",textField_ownerCode.getText());
		SSHService.writeProperties("order_sn",textField_ownerCode.getText()+"a");

		AutoLogger.log.info("平台订单号："+SSHService.deal_code());
		AutoLogger.log.info("订单号："+SSHService.order_sn());

		
	}

	protected void save() {
		// TODO Auto-generated method stub
		String deal_code = textField_ownerCode.getText().toString();
		String order_sn = textField_order_sn.getText().toString();

		if(deal_code.trim().isEmpty() || order_sn.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("deal_code",deal_code);
			SSHService.writeProperties("order_sn",order_sn);
//			AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//			AutoLogger.log.info("订单号："+SSHService.order_sn());
			this.dispose();
//			relatedToData();   // 关联已输入的数据
//			new RunDialog_delivery1().setVisible(true);
			new RunDialog_wms_stockOutConfirm().setVisible(true);

		}
	}
	
	protected void getProperties(ActionEvent e) {
        String deal_code = SSHService.deal_code();
        String order_sn = SSHService.order_sn();

		textField_ownerCode.setText(deal_code);
		textField_order_sn.setText(order_sn);

	}

}
