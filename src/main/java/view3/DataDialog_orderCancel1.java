package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
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

public class DataDialog_orderCancel1 extends JDialog {
	private JTextField textField_deal_code;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel;
	private JTextField textField_order_sn;
	private JTextField textField_shopCode;
	private JTextField textField_cancelReason;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_orderCancel1 dialog = new DataDialog_orderCancel1();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_orderCancel1() {
		setTitle("填写订单取消接口数据");
		setBounds(100, 100, 441, 306);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_deal_code = new JTextField();
		textField_deal_code.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_deal_code.setBounds(179, 30, 181, 24);
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		
		lblNewLabel_2 = new JLabel("deal code:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(65, 30, 104, 24);
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
		lblNewLabel.setBounds(65, 76, 114, 24);
		getContentPane().add(lblNewLabel);
		
		textField_order_sn = new JTextField();
		textField_order_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_order_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_sn.setColumns(15);
		textField_order_sn.setBounds(179, 76, 181, 24);
		getContentPane().add(textField_order_sn);
		
		JButton btnNewButton_clear = new JButton("清 除");
		btnNewButton_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clear(arg0);
			}
		});
		btnNewButton_clear.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_clear.setBounds(283, 223, 77, 30);
		getContentPane().add(btnNewButton_clear);
		
		JLabel lblShopCode = new JLabel("shop code：");
		lblShopCode.setForeground(Color.RED);
		lblShopCode.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblShopCode.setBounds(65, 116, 114, 24);
		getContentPane().add(lblShopCode);
		
		JLabel lblNewLabel_1_1 = new JLabel("cancelReson：");
		lblNewLabel_1_1.setForeground(Color.RED);
		lblNewLabel_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(65, 162, 114, 24);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_shopCode = new JTextField();
		textField_shopCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_shopCode.setColumns(15);
		textField_shopCode.setBounds(179, 119, 181, 24);
		getContentPane().add(textField_shopCode);
		
		textField_cancelReason = new JTextField();
		textField_cancelReason.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_cancelReason.setColumns(15);
		textField_cancelReason.setBounds(179, 162, 181, 24);
		getContentPane().add(textField_cancelReason);
		
		lblNewLabel_1 = new JLabel("确保订单已过仓");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(186, 0, 218, 25);
		getContentPane().add(lblNewLabel_1);
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
		String order_sn = textField_order_sn.getText().toString();
		String ShopCode = textField_shopCode.getText().toString();
		String cancelReason = textField_cancelReason.getText().toString();

		if(deal_code.trim().isEmpty() || order_sn.trim().isEmpty()
				|| ShopCode.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			SSHService.writeProperties("deal_code",deal_code);
			SSHService.writeProperties("order_sn",order_sn);
			SSHService.writeProperties("ShopCode",ShopCode);
			SSHService.writeProperties("cancelReason",cancelReason);

//			AutoLogger.log.info("平台订单号："+SSHService.deal_code());
//			AutoLogger.log.info("订单号："+SSHService.order_sn());
//			AutoLogger.log.info("ShopCode："+SSHService.shopCode());
//			AutoLogger.log.info("取消原因："+SSHService.cancelReason());

			this.dispose();
//			relatedToData();   // 关联已输入的数据
//			new RunDialog_orderCancel1().setVisible(true);
			new ChoiceDialog_orderCancel().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String deal_code = SSHService.deal_code();
        String order_sn = SSHService.order_sn();
        String ShopCode = SSHService.shopCode();
        String cancelReason = SSHService.cancelReason();
        
		textField_deal_code.setText(deal_code);
		textField_order_sn.setText(order_sn);
		textField_shopCode.setText(ShopCode);
		textField_cancelReason.setText(cancelReason);
	}
	
	protected void clear(ActionEvent e) {
		textField_deal_code.setText("");
		textField_order_sn.setText("");
		textField_shopCode.setText("");
		textField_cancelReason.setText("");
	}
}
