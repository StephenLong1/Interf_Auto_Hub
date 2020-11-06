package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import view4.RunDialog_getRefundOnly;
import view4.RunDialog_orderCancel1;
import view4.RunDialog_refundBack;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_refundBack extends JDialog {
	private JTextField textField_refund_sn;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel;
	private JTextField textField_deal_code;
	private JButton btnNewButton_save_1;
	private JButton btnNewButton_getProperties_1;
	private JTextField textField_shopCode;
	private JTextField textField_refundStatus;
	private JTextField textField_oid;
	private JLabel lblShopcode;
	private JLabel lblNewLabel_refundStatus;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JTextField textField_oid1;
	private JLabel lblNewLabel_3;
	private JTextField textField_refundFee;
	private JLabel lblNewLabel_5;
	private JTextField textField_oid2;
	private JLabel lblNewLabel_6;
	private JTextField textField_refund_sn2;
	private JLabel lblNewLabel_7;
	private JTextField textField_refund_sn1;
	private JLabel lblNewLabel_8;
	private final JLabel lblNewLabel_9 = new JLabel("New label");
	private JLabel lblNewLabel_10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_refundBack dialog = new DataDialog_refundBack();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_refundBack() {
		setTitle("填写退款确认回传接口数据");
		setBounds(100, 100, 630, 304);
		setResizable(false);
		setLocationRelativeTo(null);

		
		textField_refund_sn = new JTextField();
		textField_refund_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_refund_sn.setBounds(118, 25, 181, 24);
		textField_refund_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_sn.setColumns(15);
		
		lblNewLabel_2 = new JLabel("refund_sn:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(24, 25, 84, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_refund_sn);
		
		lblNewLabel = new JLabel("deal_code：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(309, 63, 109, 24);
		getContentPane().add(lblNewLabel);
		
		textField_deal_code = new JTextField();
		textField_deal_code.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		textField_deal_code.setBounds(404, 63, 181, 24);
		getContentPane().add(textField_deal_code);
		

		
		btnNewButton_save_1 = new JButton(" 保 存");
		btnNewButton_save_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAndRun(e);
			}
		});
		btnNewButton_save_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save_1.setBounds(163, 221, 77, 30);
		getContentPane().add(btnNewButton_save_1);
		
		btnNewButton_getProperties_1 = new JButton("获 取");
		btnNewButton_getProperties_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String refund_sn = SSHService.refund_sn();
				String refund_sn1 = SSHService.return_sn1();
				String refund_sn2 = SSHService.return_sn2();

				String deal_code = SSHService.deal_code();
				String shopCode = SSHService.shopCode();
				String refundStatus = SSHService.refundStatus();
				String oid1 = SSHService.oid1();
				String oid2 = SSHService.oid2();
				String RefundFee1 = SSHService.refund_fee1();
				String RefundFee2 = SSHService.refund_fee2();

				textField_refund_sn.setText("");
				textField_refund_sn1.setText(refund_sn1);
				textField_refund_sn2.setText(refund_sn2);
				textField_refund_sn1.setEditable(false);
				textField_refund_sn2.setEditable(false);
				
				textField_deal_code.setText(deal_code);
				textField_shopCode.setText(shopCode);
				textField_refundStatus.setText(refundStatus);
				textField_oid.setText("");
				textField_oid1.setText(oid1);
				textField_oid1.setEditable(false);
				textField_oid2.setText(oid2);
				textField_oid2.setEditable(false);
				textField_refundFee.setText("");
			}
		});
		btnNewButton_getProperties_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties_1.setBounds(394, 221, 77, 30);
		getContentPane().add(btnNewButton_getProperties_1);
		
		textField_shopCode = new JTextField();
		textField_shopCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_shopCode.setColumns(15);
		textField_shopCode.setBounds(118, 138, 181, 24);
		getContentPane().add(textField_shopCode);
		
		textField_refundStatus = new JTextField();
		textField_refundStatus.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refundStatus.setColumns(15);
		textField_refundStatus.setBounds(404, 25, 53, 24);
		getContentPane().add(textField_refundStatus);
		
		textField_oid = new JTextField();
		textField_oid.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid.setColumns(15);
		textField_oid.setBounds(404, 104, 181, 24);
		getContentPane().add(textField_oid);
		
		lblShopcode = new JLabel("shopCode：");
		lblShopcode.setForeground(Color.RED);
		lblShopcode.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblShopcode.setBounds(24, 138, 99, 24);
		getContentPane().add(lblShopcode);
		
		lblNewLabel_refundStatus = new JLabel("refundStatus：");
		lblNewLabel_refundStatus.setForeground(Color.RED);
		lblNewLabel_refundStatus.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_refundStatus.setBounds(309, 26, 99, 24);
		getContentPane().add(lblNewLabel_refundStatus);
		
		lblNewLabel_4 = new JLabel("oid1：");
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(337, 138, 84, 24);
		getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("refundFee：");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(24, 172, 99, 24);
		getContentPane().add(lblNewLabel_1);
		
		textField_oid1 = new JTextField();
		textField_oid1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid1.setColumns(15);
		textField_oid1.setBounds(404, 138, 181, 24);
		getContentPane().add(textField_oid1);
		
		lblNewLabel_3 = new JLabel("1：仅退款  2：退货退款");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(366, 21, 230, 30);
		getContentPane().add(lblNewLabel_3);
		
		textField_refundFee = new JTextField();
		textField_refundFee.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refundFee.setColumns(15);
		textField_refundFee.setBounds(118, 172, 181, 24);
		getContentPane().add(textField_refundFee);
		
		lblNewLabel_5 = new JLabel("oid2：");
		lblNewLabel_5.setForeground(Color.BLACK);
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(337, 172, 84, 24);
		getContentPane().add(lblNewLabel_5);
		
		textField_oid2 = new JTextField();
		textField_oid2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid2.setColumns(15);
		textField_oid2.setBounds(404, 172, 181, 24);
		getContentPane().add(textField_oid2);
		
		lblNewLabel_6 = new JLabel("oid：");
		lblNewLabel_6.setForeground(Color.RED);
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(337, 104, 84, 24);
		getContentPane().add(lblNewLabel_6);
		
		textField_refund_sn2 = new JTextField();
		textField_refund_sn2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_sn2.setColumns(15);
		textField_refund_sn2.setBounds(118, 104, 181, 24);
		getContentPane().add(textField_refund_sn2);
		
		lblNewLabel_7 = new JLabel("refund_sn2:");
		lblNewLabel_7.setForeground(Color.BLACK);
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(24, 104, 99, 24);
		getContentPane().add(lblNewLabel_7);
		
		textField_refund_sn1 = new JTextField();
		textField_refund_sn1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_refund_sn1.setColumns(15);
		textField_refund_sn1.setBounds(118, 63, 181, 24);
		getContentPane().add(textField_refund_sn1);
		
		lblNewLabel_8 = new JLabel("refund_sn1:");
		lblNewLabel_8.setForeground(Color.BLACK);
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(24, 63, 99, 24);
		getContentPane().add(lblNewLabel_8);
		lblNewLabel_9.setBounds(198, -18, 99, 24);
		getContentPane().add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("注意：红色字体为必填字段,黑色字体为已生成的退款单号和子订单号");
		lblNewLabel_10.setFont(new Font("楷体", Font.PLAIN, 14));
		lblNewLabel_10.setForeground(Color.BLACK);
		lblNewLabel_10.setBounds(98, 0, 543, 25);
		getContentPane().add(lblNewLabel_10);
	}

	
	protected void saveAndRun(ActionEvent e) {
		String refund_sn = textField_refund_sn.getText().toString();
		String deal_code = textField_deal_code.getText().toString();
		String shopCode = textField_shopCode.getText().toString();
		String refundStatus = textField_refundStatus.getText().toString();
		String oid = textField_oid.getText().toString();
		String RefundFee = textField_refundFee.getText().toString();
		
		if(refund_sn.trim().isEmpty() 
				|| deal_code.trim().isEmpty() 
				|| shopCode.trim().isEmpty()
				|| refundStatus.trim().isEmpty()
				|| oid.trim().isEmpty()
				|| RefundFee.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
			}else {
				SSHService.writeProperties("refund_sn", refund_sn);
				SSHService.writeProperties("deal_code", deal_code);
				SSHService.writeProperties("shopCode", shopCode);
				SSHService.writeProperties("refundStatus", refundStatus);
				SSHService.writeProperties("oid", oid);
				SSHService.writeProperties("RefundFee", RefundFee);
				this.dispose();
				new RunDialog_refundBack().setVisible(true);
			}

	}
}