package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_getReturnRefund1;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_returnRefund extends JDialog {
	private JTextField textField_refund_fee1;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel_;
	private JTextField textField_oid1;
	private JTextField textField_deal_code;
	private JLabel lblNewLabel_3;
	private JLabel label;
	private JTextField textField_oid2;
	private JLabel lblNewLabel;
	private JTextField textField_refund_fee2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_returnRefund dialog = new DataDialog_returnRefund();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_returnRefund() {
		setTitle("填写退货退款单号");
		setBounds(100, 100, 433, 297);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_refund_fee1 = new JTextField();
		textField_refund_fee1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_refund_fee1.setBounds(160, 86, 181, 24);
		textField_refund_fee1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_fee1.setColumns(15);
		
		lblNewLabel_2 = new JLabel("refund_fee1:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(46, 86, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_refund_fee1);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(e);
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(73, 215, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(264, 215, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel_ = new JLabel("oid1：");
		lblNewLabel_.setForeground(Color.RED);
		lblNewLabel_.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_.setBounds(58, 147, 92, 24);
		getContentPane().add(lblNewLabel_);
		
		textField_oid1 = new JTextField();
		textField_oid1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid1.setColumns(15);
		textField_oid1.setBounds(160, 147, 181, 24);
		getContentPane().add(textField_oid1);
		
		textField_deal_code = new JTextField();
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		textField_deal_code.setBounds(160, 52, 181, 24);
		getContentPane().add(textField_deal_code);
		
		lblNewLabel_3 = new JLabel("deal_code：");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(46, 52, 104, 24);
		getContentPane().add(lblNewLabel_3);
		
		label = new JLabel("注意：退款的商品数取决于退款的金额");
		label.setBounds(88, 10, 229, 15);
		getContentPane().add(label);
		
		JLabel lblNewLabel__1 = new JLabel("oid2：");
		lblNewLabel__1.setForeground(Color.BLACK);
		lblNewLabel__1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__1.setBounds(58, 181, 92, 24);
		getContentPane().add(lblNewLabel__1);
		
		textField_oid2 = new JTextField();
		textField_oid2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid2.setColumns(15);
		textField_oid2.setBounds(160, 181, 181, 24);
		getContentPane().add(textField_oid2);
		
		lblNewLabel = new JLabel("refund_fee2:");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(46, 113, 104, 24);
		getContentPane().add(lblNewLabel);
		
		textField_refund_fee2 = new JTextField();
		textField_refund_fee2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_fee2.setColumns(15);
		textField_refund_fee2.setBounds(160, 116, 181, 24);
		getContentPane().add(textField_refund_fee2);
	}


	protected void save(ActionEvent e) {
		// TODO Auto-generated method stub
		String oid1 = textField_oid1.getText().toString();
		String oid2 = textField_oid2.getText().toString();

		String refund_fee1 = textField_refund_fee1.getText().toString();
		String refund_fee2 = textField_refund_fee2.getText().toString();

		String deal_code = textField_deal_code.getText().toString();
		if(deal_code.trim().isEmpty() || oid1.trim().isEmpty() 
				|| refund_fee1.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			this.dispose();
			SSHService.writeProperties("deal_code",deal_code);
			SSHService.writeProperties("refund_fee1",refund_fee1);
			SSHService.writeProperties("refund_fee2",refund_fee2);

			SSHService.writeProperties("oid1",oid1);
			SSHService.writeProperties("oid2",oid2);

//			AutoLogger.log.info("deal_code："+SSHService.deal_code());


			new ChoiceDialog_getReturn().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String deal_code = SSHService.deal_code();
        String refund_fee1 = SSHService.refund_fee1();
        String refund_fee2 = SSHService.refund_fee2();
        String oid1 = SSHService.oid1();
        String oid2 = SSHService.oid2();

		textField_refund_fee1.setText(refund_fee1);
		textField_refund_fee2.setText(refund_fee2);
		textField_deal_code.setText(deal_code);
		textField_oid1.setText(oid1);
		textField_oid2.setText(oid2);

	}
}
