package view3;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_delivery1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataDialog_wms_deliveryOrder extends JDialog {
	private JTextField textField_ownerCode;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_wms_deliveryOrder dialog = new DataDialog_wms_deliveryOrder();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_wms_deliveryOrder() {
		setTitle("填写订单号");
		setBounds(100, 100, 373, 193);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_ownerCode = new JTextField();
		textField_ownerCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_ownerCode.setBounds(151, 46, 181, 24);
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		
		lblNewLabel_2 = new JLabel("ownerCode:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(37, 45, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 17));
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
		btnNewButton_save.setBounds(64, 101, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(213, 101, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
	}

	protected void relatedToData() {
		SSHService.writeProperties("deal_code",textField_ownerCode.getText());
		SSHService.writeProperties("order_sn",textField_ownerCode.getText()+"a");

//		AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//		AutoLogger.log.info("订单号："+SSHService.order_sn());
	}

	protected void save() {
		// TODO Auto-generated method stub
		String ownerCode = textField_ownerCode.getText().toString();

		if(ownerCode.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("ownerCode",ownerCode);
			AutoLogger.log.info("ownerCode："+SSHService.ownerCode());
			this.dispose();
//			relatedToData();   // 关联已输入的数据
			new RunDialog_delivery1().setVisible(true);
//			new ChoiceDialog_wms_delivery().setVisible(true);

		}
	}
	
	protected void getProperties(ActionEvent e) {
        String ownerCode = SSHService.ownerCode();

		textField_ownerCode.setText(ownerCode);

	}

}
