package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_delivery1;
import view4.RunDialog_shippingData;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_shippingSales extends JDialog {
	private JTextField textField_deal_code;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_shippingSales dialog = new DataDialog_shippingSales();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_shippingSales() {
		setTitle("填写订单号");
		setBounds(100, 100, 365, 196);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_deal_code = new JTextField();
		textField_deal_code.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_deal_code.setBounds(136, 45, 194, 24);
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		
		lblNewLabel_2 = new JLabel("平台订单号:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(22, 45, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_deal_code);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(65, 100, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(208, 100, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
	}

	protected void relatedToData() {
		SSHService.writeProperties("deal_code",textField_deal_code.getText());
		SSHService.writeProperties("order_sn",textField_deal_code.getText()+"a");

		AutoLogger.log.info("平台订单号："+SSHService.deal_code());
		AutoLogger.log.info("订单号："+SSHService.order_sn());
	}

	protected void save() {
		// TODO Auto-generated method stub
		String deal_code = textField_deal_code.getText().toString();

		if(deal_code.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("deal_code",deal_code);
			AutoLogger.log.info("平台订单号："+SSHService.deal_code());
			this.dispose();
//			relatedToData();   // 关联已输入的数据
//			new RunDialog_delivery1().setVisible(true);
			new RunDialog_shippingData().setVisible(true);

		}
	}
	
	protected void getProperties(ActionEvent e) {
        String deal_code = SSHService.deal_code();

		textField_deal_code.setText(deal_code);

	}

}
