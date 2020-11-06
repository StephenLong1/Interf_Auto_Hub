package view3;

import javax.swing.JButton;
import javax.swing.JDialog;
import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_delivery2;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_wms_getOrder extends JDialog {
	private JTextField textField_deal_code;
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
			DataDialog_wms_getOrder dialog = new DataDialog_wms_getOrder();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_wms_getOrder() {
		setTitle("填写订单号");
		setBounds(100, 100, 372, 223);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_deal_code = new JTextField();
		textField_deal_code.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_deal_code.setBounds(136, 43, 181, 24);
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		
		lblNewLabel_2 = new JLabel("平台订单号:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(22, 42, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_deal_code);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isNullToRunFrame();
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
		
		lblNewLabel = new JLabel("订 单 号：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
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
		SSHService.writeProperties("deal_code",textField_deal_code.getText());
		SSHService.writeProperties("order_sn",textField_deal_code.getText()+"a");

//		AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//		AutoLogger.log.info("订单号："+SSHService.order_sn());

		
	}

	protected void isNullToRunFrame() {
		// TODO Auto-generated method stub
		String deal_code = textField_deal_code.getText().toString();
		String order_sn = textField_order_sn.getText().toString();

		if(deal_code.trim().isEmpty() || order_sn.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			this.dispose();
			relatedToData();   // 关联已输入的数据
			new ChoiceDialog_wms_getOrder().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
		textField_deal_code.setText(SSHService.deal_code());
		textField_order_sn.setText(SSHService.order_sn());

	}

}
