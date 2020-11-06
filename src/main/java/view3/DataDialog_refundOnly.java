package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_getRefundOnly;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_refundOnly extends JDialog {
	private JTextField textField_refund_fee;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel_;
	private JTextField textField_oid;
	private JTextField textField_deal_code;
	private JLabel lblNewLabel_3;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_refundOnly dialog = new DataDialog_refundOnly();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_refundOnly() {
		setTitle("填写仅退款单号");
		setBounds(100, 100, 398, 265);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_refund_fee = new JTextField();
		textField_refund_fee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_refund_fee.setBounds(160, 86, 181, 24);
		textField_refund_fee.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_fee.setColumns(15);
		
		lblNewLabel_2 = new JLabel("refund_fee:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(46, 86, 92, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_refund_fee);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(73, 177, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(235, 177, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel_ = new JLabel("oid:");
		lblNewLabel_.setForeground(Color.RED);
		lblNewLabel_.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_.setBounds(89, 120, 48, 24);
		getContentPane().add(lblNewLabel_);
		
		textField_oid = new JTextField();
		textField_oid.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid.setColumns(15);
		textField_oid.setBounds(160, 120, 181, 24);
		getContentPane().add(textField_oid);
		
		textField_deal_code = new JTextField();
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		textField_deal_code.setBounds(160, 52, 181, 24);
		getContentPane().add(textField_deal_code);
		
		lblNewLabel_3 = new JLabel("deal_code：");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(46, 52, 92, 24);
		getContentPane().add(lblNewLabel_3);
		
		label = new JLabel("注意：退款的商品数取决于退款的金额");
		label.setBounds(89, 10, 215, 15);
		getContentPane().add(label);
	}


	protected void save() {
		// TODO Auto-generated method stub
		String oid1 = textField_oid.getText().toString();
		String refund_fee = textField_refund_fee.getText().toString();
		String deal_code = textField_deal_code.getText().toString();

//		AutoLogger.log.info("deal_code："+SSHService.deal_code());
//		AutoLogger.log.info("refund_fee1："+SSHService.refund_fee1());
//		AutoLogger.log.info("oid："+SSHService.oid());
		
		if(deal_code.trim().isEmpty() || oid1.trim().isEmpty() 
			|| refund_fee.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {

			this.dispose();
			SSHService.writeProperties("deal_code",deal_code);
			SSHService.writeProperties("refund_fee1",refund_fee);
			SSHService.writeProperties("oid1",oid1);
			new RunDialog_getRefundOnly().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String deal_code = SSHService.deal_code();
        String refund_fee1 = SSHService.refund_fee1();
        String oid1 = SSHService.oid1();

		textField_refund_fee.setText(refund_fee1);
		textField_deal_code.setText(deal_code);
		textField_oid.setText(oid1);

	}

}
