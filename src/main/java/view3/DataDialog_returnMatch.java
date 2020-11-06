package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import view4.RunDialog_getRefundOnly;
import view4.RunDialog_orderCancel1;
import view4.RunDialog_refundBack;
import view4.RunDialog_returnBack1;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_returnMatch extends JDialog {
	private JTextField textField_return_sn;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel;
	private JTextField textField_ownerCode;
	private JButton btnNewButton_save_1;
	private JButton btnNewButton_getProperties_1;
	private JTextField textField_return_goods_id1;
	private JTextField textField_orderType;
	private JTextField textField_oid1;
	private JLabel lblShopcode;
	private JLabel lblNewLabel_refundStatus;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JTextField textField_deal_code;
	private JLabel lblNewLabel_3;
	private JTextField textField_returnReason;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JTextField textField_oid2;
	private JTextField textField_return_goods_id2;
	private JLabel lblReturngoodsid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_returnMatch dialog = new DataDialog_returnMatch();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_returnMatch() {
		setTitle("填写退货入库回传接口数据");
		setBounds(100, 100, 573, 276);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_return_sn = new JTextField();
		textField_return_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_return_sn.setBounds(139, 25, 138, 24);
		textField_return_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_sn.setColumns(15);
		
		lblNewLabel_2 = new JLabel("return_sn:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(28, 25, 91, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_return_sn);
		
		lblNewLabel = new JLabel("ownerCode：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(28, 64, 104, 24);
		getContentPane().add(lblNewLabel);
		
		textField_ownerCode = new JTextField();
		textField_ownerCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		textField_ownerCode.setBounds(139, 59, 138, 24);
		getContentPane().add(textField_ownerCode);
		

		
		btnNewButton_save_1 = new JButton(" 保 存");
		btnNewButton_save_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAndRun(e);
			}
		});
		btnNewButton_save_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save_1.setBounds(126, 193, 77, 30);
		getContentPane().add(btnNewButton_save_1);
		
		btnNewButton_getProperties_1 = new JButton("获 取");
		btnNewButton_getProperties_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String return_sn = SSHService.return_sn();
				String deal_code = SSHService.deal_code();
				String oid1 = SSHService.oid1();
				String oid2 = SSHService.oid2();

				String ownerCode = SSHService.ownerCode();
				String order_return_goods_id1 = SSHService.order_return_goods_id();
				String order_return_goods_id2 = SSHService.order_return_goods_id2();

				String orderType = SSHService.orderType();
				String returnReason = SSHService.returnReason();
				textField_return_sn.setText(return_sn);
				textField_deal_code.setText(deal_code);
				textField_return_goods_id1.setText(order_return_goods_id1);
				textField_return_goods_id2.setText(order_return_goods_id2);

				textField_orderType.setText(orderType);
				textField_oid1.setText(oid1);
				textField_oid2.setText(oid2);

				textField_ownerCode.setText(ownerCode);
				textField_returnReason.setText(returnReason);
			}
		});
		btnNewButton_getProperties_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties_1.setBounds(360, 193, 77, 30);
		getContentPane().add(btnNewButton_getProperties_1);
		
		textField_return_goods_id1 = new JTextField();
		textField_return_goods_id1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_goods_id1.setColumns(15);
		textField_return_goods_id1.setBounds(139, 98, 138, 24);
		getContentPane().add(textField_return_goods_id1);
		
		textField_orderType = new JTextField();
		textField_orderType.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_orderType.setColumns(15);
		textField_orderType.setBounds(381, 132, 155, 24);
		getContentPane().add(textField_orderType);
		
		textField_oid1 = new JTextField();
		textField_oid1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid1.setColumns(15);
		textField_oid1.setBounds(381, 25, 155, 24);
		getContentPane().add(textField_oid1);
		
		lblShopcode = new JLabel("return_goods_id1：");
		lblShopcode.setForeground(Color.RED);
		lblShopcode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblShopcode.setBounds(10, 98, 139, 24);
		getContentPane().add(lblShopcode);
		
		lblNewLabel_refundStatus = new JLabel("orderType：");
		lblNewLabel_refundStatus.setForeground(Color.RED);
		lblNewLabel_refundStatus.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_refundStatus.setBounds(287, 132, 121, 24);
		getContentPane().add(lblNewLabel_refundStatus);
		
		lblNewLabel_4 = new JLabel("oid1：");
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(287, 25, 66, 24);
		getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("returnReason：");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 159, 121, 24);
		getContentPane().add(lblNewLabel_1);
		
		textField_deal_code = new JTextField();
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		textField_deal_code.setBounds(381, 98, 155, 24);
		getContentPane().add(textField_deal_code);
		
		lblNewLabel_3 = new JLabel("注意：THRK=退货入库;HHRK=换货入库");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(275, 155, 261, 35);
		getContentPane().add(lblNewLabel_3);
		
		textField_returnReason = new JTextField();
		textField_returnReason.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_returnReason.setColumns(15);
		textField_returnReason.setBounds(139, 159, 138, 24);
		getContentPane().add(textField_returnReason);
		
		lblNewLabel_5 = new JLabel("deal_code：");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(287, 98, 99, 24);
		getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("oid2：");
		lblNewLabel_6.setForeground(Color.BLACK);
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(287, 59, 66, 24);
		getContentPane().add(lblNewLabel_6);
		
		textField_oid2 = new JTextField();
		textField_oid2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid2.setColumns(15);
		textField_oid2.setBounds(381, 59, 155, 24);
		getContentPane().add(textField_oid2);
		
		textField_return_goods_id2 = new JTextField();
		textField_return_goods_id2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_goods_id2.setColumns(15);
		textField_return_goods_id2.setBounds(139, 132, 138, 24);
		getContentPane().add(textField_return_goods_id2);
		
		lblReturngoodsid = new JLabel("return_goods_id2：");
		lblReturngoodsid.setForeground(Color.BLACK);
		lblReturngoodsid.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblReturngoodsid.setBounds(10, 132, 139, 24);
		getContentPane().add(lblReturngoodsid);
	}

	
	protected void saveAndRun(ActionEvent e) {
		String return_sn = textField_return_sn.getText().toString();
		String deal_code = textField_deal_code.getText().toString();
		String oid1 = textField_oid1.getText().toString();
		String oid2 = textField_oid2.getText().toString();
		String ownerCode = textField_ownerCode.getText().toString();

		String order_return_goods_id1 = textField_return_goods_id1.getText().toString();
		String order_return_goods_id2 = textField_return_goods_id2.getText().toString();

		String orderType = textField_orderType.getText().toString();
		String returnReason = textField_returnReason.getText().toString();

		if(return_sn.trim().isEmpty() 
				|| deal_code.trim().isEmpty() 
				|| oid1.trim().isEmpty()
				|| ownerCode.trim().isEmpty()
				|| order_return_goods_id1.trim().isEmpty()
				|| orderType.trim().isEmpty()
				) {
				JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
			}else {
				SSHService.writeProperties("return_sn", return_sn);
				SSHService.writeProperties("deal_code", deal_code);
				SSHService.writeProperties("oid1", oid1);
				SSHService.writeProperties("ownerCode", ownerCode);
				SSHService.writeProperties("order_return_goods_id", order_return_goods_id1);
				SSHService.writeProperties("order_return_goods_id2", order_return_goods_id2);
				SSHService.writeProperties("orderType", orderType);
				SSHService.writeProperties("returnReason", returnReason);
				this.dispose();
				new ChoiceDialog_returnBack().setVisible(true);
			}

	}
}