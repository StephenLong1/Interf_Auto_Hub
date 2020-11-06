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
import java.awt.SystemColor;

public class DataDialog_returnOrder extends JDialog {
	private JTextField textField_order_sn;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel_;
	private JTextField textField_deal_code;
	private JTextField textField_return_sn;
	private JLabel lblNewLabel_3;
	private JLabel lbloidoid;
	private JTextField textField_oid1;
	private JTextField textField_oid2;
	private JLabel lblNewLabel__2;
	private JLabel lblNewLabel__id;
	private JTextField textField_order_return_goods_id;
	private JLabel lblidordergoodsid;
	private JLabel lblNewLabel__4;
	private JTextField textField_ownerCode;
	private JLabel lblNewLabel__3;
	private JTextField textField_barcode1;
	private JLabel lblNewLabel__5;
	private JTextField textField_barcode2;
	private JTextField textField_num1;
	private JTextField textField_price1;
	private JLabel lblNewLabel__6;
	private JLabel lblNewLabel__7;
	private JTextField textField_price2;
	private JLabel lblNewLabel__8;
	private JTextField textField_num2;
	private JTextField textField_shopCode;
	private JLabel lblNewLabel__9;
	private JLabel lblNewLabel__10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_returnOrder dialog = new DataDialog_returnOrder();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_returnOrder() {
		setTitle("填写退货退款单号");
		setBounds(100, 100, 723, 388);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_order_sn = new JTextField();
		textField_order_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_order_sn.setBounds(160, 86, 181, 24);
		textField_order_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_sn.setColumns(15);
		
		lblNewLabel_2 = new JLabel("order_sn:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(46, 86, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_order_sn);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(e);
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(149, 284, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(451, 284, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel_ = new JLabel("deal_code：");
		lblNewLabel_.setForeground(Color.RED);
		lblNewLabel_.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_.setBounds(46, 121, 92, 24);
		getContentPane().add(lblNewLabel_);
		
		textField_deal_code = new JTextField();
		textField_deal_code.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_deal_code.setColumns(15);
		textField_deal_code.setBounds(160, 121, 181, 24);
		getContentPane().add(textField_deal_code);
		
		textField_return_sn = new JTextField();
		textField_return_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_sn.setColumns(15);
		textField_return_sn.setBounds(160, 52, 181, 24);
		getContentPane().add(textField_return_sn);
		
		lblNewLabel_3 = new JLabel("return_sn：");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(46, 52, 104, 24);
		getContentPane().add(lblNewLabel_3);
		
		lbloidoid = new JLabel("注意：如果你的是单订单,则包含2的字段可不输入,如果你的单子是多订单,字段包含1和2的都需要输入");
		lbloidoid.setBounds(69, 10, 566, 25);
		getContentPane().add(lbloidoid);
		
		JLabel lblNewLabel__1 = new JLabel("oid1：");
		lblNewLabel__1.setForeground(Color.RED);
		lblNewLabel__1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__1.setBounds(46, 150, 92, 24);
		getContentPane().add(lblNewLabel__1);
		
		textField_oid1 = new JTextField();
		textField_oid1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid1.setColumns(15);
		textField_oid1.setBounds(160, 155, 181, 24);
		getContentPane().add(textField_oid1);
		
		textField_oid2 = new JTextField();
		textField_oid2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_oid2.setColumns(15);
		textField_oid2.setBounds(160, 189, 181, 24);
		getContentPane().add(textField_oid2);
		
		lblNewLabel__2 = new JLabel("oid2：");
		lblNewLabel__2.setForeground(Color.BLACK);
		lblNewLabel__2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__2.setBounds(46, 184, 92, 24);
		getContentPane().add(lblNewLabel__2);
		
		lblNewLabel__id = new JLabel("id：");
		lblNewLabel__id.setForeground(Color.RED);
		lblNewLabel__id.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__id.setBounds(351, 52, 103, 24);
		getContentPane().add(lblNewLabel__id);
		
		textField_order_return_goods_id = new JTextField();
		textField_order_return_goods_id.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_order_return_goods_id.setColumns(15);
		textField_order_return_goods_id.setBounds(416, 52, 59, 24);
		getContentPane().add(textField_order_return_goods_id);
		
		lblidordergoodsid = new JLabel("order_return_goods表中的id");
		lblidordergoodsid.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblidordergoodsid.setBounds(485, 52, 184, 21);
		getContentPane().add(lblidordergoodsid);
		
		lblNewLabel__4 = new JLabel("ownerCode：");
		lblNewLabel__4.setForeground(Color.RED);
		lblNewLabel__4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel__4.setBounds(485, 86, 113, 24);
		getContentPane().add(lblNewLabel__4);
		
		textField_ownerCode = new JTextField();
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		textField_ownerCode.setBounds(579, 86, 104, 24);
		getContentPane().add(textField_ownerCode);
		
		lblNewLabel__3 = new JLabel("barcode1：");
		lblNewLabel__3.setForeground(Color.RED);
		lblNewLabel__3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel__3.setBounds(351, 155, 113, 24);
		getContentPane().add(lblNewLabel__3);
		
		textField_barcode1 = new JTextField();
		textField_barcode1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_barcode1.setColumns(15);
		textField_barcode1.setBounds(419, 155, 149, 24);
		getContentPane().add(textField_barcode1);
		
		lblNewLabel__5 = new JLabel("barcode2：");
		lblNewLabel__5.setForeground(Color.BLACK);
		lblNewLabel__5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel__5.setBounds(351, 189, 77, 24);
		getContentPane().add(lblNewLabel__5);
		
		textField_barcode2 = new JTextField();
		textField_barcode2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_barcode2.setColumns(15);
		textField_barcode2.setBounds(419, 189, 149, 24);
		getContentPane().add(textField_barcode2);
		
		textField_num1 = new JTextField();
		textField_num1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_num1.setColumns(15);
		textField_num1.setBounds(416, 86, 59, 24);
		getContentPane().add(textField_num1);
		
		textField_price1 = new JTextField();
		textField_price1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_price1.setColumns(15);
		textField_price1.setBounds(160, 223, 181, 24);
		getContentPane().add(textField_price1);
		
		lblNewLabel__6 = new JLabel("price1：");
		lblNewLabel__6.setForeground(Color.RED);
		lblNewLabel__6.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__6.setBounds(46, 218, 92, 24);
		getContentPane().add(lblNewLabel__6);
		
		lblNewLabel__7 = new JLabel("num2：");
		lblNewLabel__7.setForeground(Color.BLACK);
		lblNewLabel__7.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__7.setBounds(351, 121, 92, 24);
		getContentPane().add(lblNewLabel__7);
		
		textField_price2 = new JTextField();
		textField_price2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_price2.setColumns(15);
		textField_price2.setBounds(419, 223, 149, 24);
		getContentPane().add(textField_price2);
		
		lblNewLabel__8 = new JLabel("num1：");
		lblNewLabel__8.setBackground(Color.RED);
		lblNewLabel__8.setForeground(Color.RED);
		lblNewLabel__8.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__8.setBounds(351, 86, 65, 24);
		getContentPane().add(lblNewLabel__8);
		
		textField_num2 = new JTextField();
		textField_num2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_num2.setColumns(15);
		textField_num2.setBounds(416, 124, 59, 24);
		getContentPane().add(textField_num2);
		
		textField_shopCode = new JTextField();
		textField_shopCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_shopCode.setColumns(15);
		textField_shopCode.setBounds(579, 121, 104, 24);
		getContentPane().add(textField_shopCode);
		
		lblNewLabel__9 = new JLabel("shopCode：");
		lblNewLabel__9.setForeground(Color.RED);
		lblNewLabel__9.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel__9.setBounds(485, 121, 113, 24);
		getContentPane().add(lblNewLabel__9);
		
		lblNewLabel__10 = new JLabel("price2：");
		lblNewLabel__10.setForeground(Color.BLACK);
		lblNewLabel__10.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel__10.setBounds(351, 223, 92, 24);
		getContentPane().add(lblNewLabel__10);
	}


	protected void save(ActionEvent e) {
        String return_goods_id = textField_order_return_goods_id.getText().toString();
        String deal_code = textField_deal_code.getText().toString();
        String ownerCode = textField_ownerCode.getText().toString();
        String shopCode = textField_shopCode.getText().toString();
        String order_sn = textField_order_sn.getText().toString();
        String return_sn = textField_return_sn.getText().toString();


        String oid1 = textField_oid1.getText().toString();
        String oid2 = textField_oid2.getText().toString();
        String num1 = textField_num1.getText().toString();
        String num2 = textField_num2.getText().toString();
        String price1 = textField_price1.getText().toString();
        String price2 = textField_price2.getText().toString();
        String goods_barcode1 = textField_barcode1.getText().toString();
        String goods_barcode2 = textField_barcode2.getText().toString();


//		AutoLogger.log.info("deal_code："+SSHService.deal_code());
//		AutoLogger.log.info("refund_fee1："+SSHService.refund_fee1());
//		AutoLogger.log.info("oid："+SSHService.oid());
		
		if(return_goods_id.trim().isEmpty() || deal_code.trim().isEmpty() 
			|| ownerCode.trim().isEmpty()
					|| shopCode.trim().isEmpty()
					|| order_sn.trim().isEmpty()
					|| return_sn.trim().isEmpty()
					|| oid1.trim().isEmpty()
					|| num1.trim().isEmpty()
					|| price1.trim().isEmpty()
					|| goods_barcode1.trim().isEmpty()
					) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			this.dispose();
			SSHService.writeProperties("order_return_goods_id",return_goods_id);
			SSHService.writeProperties("deal_code",deal_code);
			SSHService.writeProperties("ownerCode",ownerCode);
			SSHService.writeProperties("shopCode",shopCode);
			SSHService.writeProperties("order_sn",order_sn);
			SSHService.writeProperties("return_sn",return_sn);
			SSHService.writeProperties("goods_number1",num1);
			SSHService.writeProperties("goods_number2",num2);
			SSHService.writeProperties("goods_price1",price1);
			SSHService.writeProperties("goods_price2",price2);
			SSHService.writeProperties("goods_barCode1",goods_barcode1);
			SSHService.writeProperties("goods_barCode2",goods_barcode2);
			new ChoiceDialog_returnOrder().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String return_goods_id = SSHService.order_return_goods_id();
        String deal_code = SSHService.deal_code();
        String ownerCode = SSHService.ownerCode();
        String shopCode = SSHService.shopCode();
        String order_sn = SSHService.order_sn();
        String return_sn = SSHService.oid1();
        
        String oid1 = SSHService.oid1();
        String oid2 = SSHService.oid2();
        String num1 = SSHService.goods_number1();
        String num2 = SSHService.goods_number2();
        String price1 = SSHService.goods_price1();
        String price2 = SSHService.goods_price2();
        String goods_barcode1 = SSHService.goods_barcode1();
        String goods_barcode2 = SSHService.goods_barcode2();


		textField_deal_code.setText(deal_code);
		textField_order_sn.setText(order_sn);
		textField_order_return_goods_id.setText(return_goods_id);
		textField_shopCode.setText(shopCode);
		textField_return_sn.setText(return_sn);
		textField_ownerCode.setText(ownerCode);
		
		textField_barcode1.setText(goods_barcode1);
		textField_barcode2.setText(goods_barcode2);
		textField_num1.setText(num1);
		textField_num2.setText(num2);
		textField_price1.setText(price1);
		textField_price2.setText(price2);
		textField_oid1.setText(oid1);
		textField_oid2.setText(oid2);
		
	}
}
