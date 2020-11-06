package view2;

import javax.swing.JButton;
import javax.swing.JDialog;
import common.AutoLogger;
import source.SSHService;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Button;

public class DataDialog_inputText extends JDialog {
	private JTextField textField_shopName;
	private JTextField textField_number1;
	private JTextField textField_number2;
	private JTextField textField_barcode1;
	private JTextField textField_barcode2;
	private JTextField textField_price1;
	private JTextField textField_price2;
	private JTextField textField_discount1;
	private JTextField textField_discount2;
	private JTextField textField_ShopCode;
	private JComboBox comboBox_ownerCode;
	int count;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_inputText dialog = new DataDialog_inputText();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_inputText() {
		setTitle("接口测试数据参数填写界面");
		setBounds(100, 100, 620, 359);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnNewButton = new JButton("注意事项");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alert();
			}
		});
		btnNewButton.setBounds(10, 10, 123, 30);
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		
		textField_shopName = new JTextField();
		textField_shopName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField_shopName.setBounds(140, 57, 411, 25);
		textField_shopName.setText("");
		textField_shopName.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_shopName.setColumns(15);

		JLabel lblNewLabel_2 = new JLabel("shopName:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(49, 57, 77, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		JLabel lblNewLabel_1 = new JLabel("number1:");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setBounds(49, 158, 81, 24);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		textField_number1 = new JTextField();
		textField_number1.setBounds(140, 158, 146, 25);
		textField_number1.setText("");
		textField_number1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_number1.setColumns(10);

		JLabel lblNewLabel_4_1 = new JLabel("number2:");
		lblNewLabel_4_1.setBounds(311, 158, 81, 24);
		lblNewLabel_4_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		textField_number2 = new JTextField();
		textField_number2.setBounds(413, 159, 138, 23);
		textField_number2.setText("");
		textField_number2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_number2.setColumns(15);

		JLabel lblGoodsbarcode = new JLabel("barCode1:");
		lblGoodsbarcode.setForeground(new Color(255, 0, 0));
		lblGoodsbarcode.setBounds(49, 191, 81, 24);
		lblGoodsbarcode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		textField_barcode1 = new JTextField();
		textField_barcode1.setBounds(140, 192, 146, 25);
		textField_barcode1.setText("");
		textField_barcode1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		textField_barcode1.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("barCode2:");
		lblNewLabel_3.setBounds(311, 191, 78, 24);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		textField_barcode2 = new JTextField();
		textField_barcode2.setBounds(412, 192, 139, 25);
		textField_barcode2.setText("");
		textField_barcode2.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		textField_barcode2.setColumns(10);
		getContentPane().setLayout(null);
		getContentPane().add(btnNewButton);
		getContentPane().add(lblNewLabel_1);
		getContentPane().add(textField_number1);
		getContentPane().add(lblNewLabel_4_1);
		getContentPane().add(textField_number2);
		getContentPane().add(lblGoodsbarcode);
		getContentPane().add(textField_barcode1);
		getContentPane().add(lblNewLabel_3);
		getContentPane().add(textField_barcode2);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_shopName);

		JLabel lblNewLabel = new JLabel("price1:");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(49, 124, 65, 24);
		getContentPane().add(lblNewLabel);
		
		textField_price1 = new JTextField();
		textField_price1.setText("");
		textField_price1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_price1.setColumns(15);
		textField_price1.setBounds(140, 125, 146, 23);
		getContentPane().add(textField_price1);

		JLabel lblNewLabel_2_2 = new JLabel("price2:");
		lblNewLabel_2_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_2_2.setBounds(311, 124, 78, 24);
		getContentPane().add(lblNewLabel_2_2);
		
		textField_price2 = new JTextField();
		textField_price2.setText("");
		textField_price2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_price2.setColumns(10);
		textField_price2.setBounds(413, 125, 138, 24);
		getContentPane().add(textField_price2);

		JLabel lblNewLabel_4 = new JLabel("discount1:");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(49, 225, 81, 25);
		getContentPane().add(lblNewLabel_4);
		
		textField_discount1 = new JTextField();
		textField_discount1.setText("");
		textField_discount1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_discount1.setColumns(10);
		textField_discount1.setBounds(140, 225, 146, 24);
		getContentPane().add(textField_discount1);

		JLabel lblNewLabel_1_1 = new JLabel("discount2:");
		lblNewLabel_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(311, 225, 78, 24);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_discount2 = new JTextField();
		textField_discount2.setText("");
		textField_discount2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_discount2.setColumns(10);
		textField_discount2.setBounds(413, 227, 138, 21);
		getContentPane().add(textField_discount2);

		JButton btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				showSavedProperties();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(117, 260, 77, 30);
		getContentPane().add(btnNewButton_save);

		JButton btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				get();
		}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(266, 260, 77, 30);
		getContentPane().add(btnNewButton_getProperties);

		JButton btnNewButton_getProperties_1 = new JButton("清 除");
		btnNewButton_getProperties_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});
		btnNewButton_getProperties_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties_1.setBounds(423, 260, 77, 30);
		getContentPane().add(btnNewButton_getProperties_1);
		
		JLabel lblNewLabel_ = new JLabel("ownerCode:");
		lblNewLabel_.setForeground(Color.RED);
		lblNewLabel_.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_.setBounds(49, 90, 81, 24);
		getContentPane().add(lblNewLabel_);
		
		textField_ShopCode = new JTextField();
		textField_ShopCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ShopCode.setColumns(15);
		textField_ShopCode.setBounds(413, 92, 138, 23);
		getContentPane().add(textField_ShopCode);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("shopCode:");
		lblNewLabel_2_2_1.setForeground(Color.RED);
		lblNewLabel_2_2_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_2_2_1.setBounds(311, 90, 78, 24);
		getContentPane().add(lblNewLabel_2_2_1);
		
	    comboBox_ownerCode = new JComboBox();
		comboBox_ownerCode.setEditable(true);
		comboBox_ownerCode.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox_ownerCode.setModel(new DefaultComboBoxModel(new String[] {"clarks", "cl", "oakley_tm", "swatch", "rimowa", "art", "bally", "bash", "buying", "cartier", "clarks_bq", "clarks_bqxl", "clubmonaco", "cp", "demo", "dvf", "flikflak", "fossil", "gabor", "herschel", "hugoboss", "isabel_marant", "jimmychoo", "kenzo", "lenscrafters", "mackage", "maje", "maje_sea", "margiela", "marni", "mcm", "mcm_outlets", "mk", "montblanc", "mooseknuckles", "mooseknuckles_offline", "mulberry", "ot", "patrizia_pepe", "pinko", "puma", "rayban", "rayban_fx", "rayban_jd", "rayban_tm", "rayban_vip", "sandro", "smcp_vip", "sw", "pandora", "templates", "thekooples", "theory", "theory_outlets", "tissot", "tous", "tumi", "versace", "cp_sea", "sandro_sea", "juicycouture"}));
		comboBox_ownerCode.setToolTipText("");
		comboBox_ownerCode.setBounds(140, 87, 146, 28);
		getContentPane().add(comboBox_ownerCode);
		
		Button button = new Button("GET BRAND");
		button.setBackground(Color.ORANGE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count++;
				if(count == 1) {
					getClarks();
				}if (count == 2) {
					getSwatch();
				}if (count == 3) {
					getTissot();
				}if (count == 4) {
					getDiesel();;
				}if (count == 5){
					getMulberry();
			}if(count == 6) {
				getCp();
			}if(count == 7) {
				getPandora();
			}if(count > 7) {
				count = 0;
			}
			}
		});
		button.setBounds(243, 10, 126, 34);
		getContentPane().add(button);
	}
	
	protected void alert() {
		// TODO Auto-generated method stub
		String info  =  "注意事项"+"\n";
		String info1  =  "一. 请配置天猫订单测试数据<确保输入的参数为有效值,可在sys_shop/spkcb_shop查找>"+"\n"; 
		String info2 =  "二. 需全部将所有的参数输入, 且金额不要输入小数点,但是discount输入为小数点, 暂且为0.00"+"\n";
		String info3 =  "三. 如果打印的日志出现-数据不存在的字样,或转单未成功, 这个原因则是因为有其他天猫单转单失败,"+"\n"+"导致无法读取json,需前往sys_info/oms_tb_trade将其删除";
		String a = info + info1 + info2 + info3;
		JOptionPane.showMessageDialog(this, a);
	}

	protected void clearAll() {
		// TODO Auto-generated method stub
		textField_shopName.setText("");
		
		textField_ShopCode.setText("");
		textField_number1.setText("");
		textField_number2.setText("");
		textField_barcode1.setText("");
		textField_barcode2.setText("");
		textField_price1.setText("");
		textField_price2.setText("");
		textField_discount1.setText("");
		textField_discount2.setText("");
		comboBox_ownerCode.setSelectedItem("");
	}

	protected void relatedToData() {
		SSHService.writeProperties("shopName",textField_shopName.getText());
		SSHService.writeProperties("ownerCode",comboBox_ownerCode.getSelectedItem().toString());
		SSHService.writeProperties("ShopCode",textField_ShopCode.getText());

		SSHService.writeProperties("goods_number1",textField_number1.getText());
		SSHService.writeProperties("goods_number2",textField_number2.getText());

		SSHService.writeProperties("goods_barCode1",textField_barcode1.getText());
		SSHService.writeProperties("goods_barCode2",textField_barcode2.getText());

		SSHService.writeProperties("goods_price1",textField_price1.getText());
		SSHService.writeProperties("goods_price2",textField_price2.getText());

		SSHService.writeProperties("discount1",textField_discount1.getText());
		SSHService.writeProperties("discount2",textField_discount2.getText());
	
	}

	protected void save() {
		// TODO Auto-generated method stub
		String shopName = textField_shopName.getText().toString();
		
		String ownerCode = comboBox_ownerCode.getSelectedItem().toString();
		String shopCode = textField_ShopCode.getText().toString();
		
		String num1 = textField_number1.getText().toString();
		String num2 = textField_number2.getText().toString();

		String barCode1 = textField_barcode1.getText().toString();
		String barCode2 = textField_barcode2.getText().toString();
		
		String price1 = textField_price1.getText().toString();
		String price2 = textField_price2.getText().toString();
		
		String discount1 = textField_discount1.getText().toString();
		String discount2 = textField_discount2.getText().toString();
		if(shopName.trim().isEmpty()
				||   num1.trim().isEmpty() || num2.trim().isEmpty()||   barCode1.trim().isEmpty()
				||  price1.trim().isEmpty()|| price2.trim().isEmpty() || barCode2.trim().isEmpty()
				||  discount1.trim().isEmpty()|| discount2.trim().isEmpty() 
			    ||  ownerCode.trim().isEmpty()|| shopCode.trim().isEmpty()
				) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			relatedToData();   // 关联已输入的数据
			dispose();
		}
	}

	protected void get(){
		String shopName = SSHService.shopName();
		String ownerCode = SSHService.ownerCode();
		String ShopCode = SSHService.shopCode();
		
		String goods_number1 = SSHService.goods_number1();
		String goods_number2 = SSHService.goods_number2();
		String goods_price1 = SSHService.goods_price1();
		String goods_price2 = SSHService.goods_price2();
		String goods_barcode1 = SSHService.goods_barcode1();
		String goods_barcode2 = SSHService.goods_barcode2();
		String discount1 = SSHService.discount1();
		String discount2 = SSHService.discount2();
		
		textField_shopName.setText(shopName);
		comboBox_ownerCode.setSelectedItem(ownerCode);
		textField_ShopCode.setText(ShopCode);

		textField_number1.setText(goods_number1);
		textField_number2.setText(goods_number2);
		textField_price1.setText(goods_price1);
		textField_price2.setText(goods_price2);
		textField_barcode1.setText(goods_barcode1);
		textField_barcode2.setText(goods_barcode2);
		textField_discount1.setText(String.valueOf(discount1));
		textField_discount2.setText(String.valueOf(discount2));
		
	
//		textField_discount1.setText(SSHService.discount1());
//		textField_discount2.setText(SSHService.discount1());
//		
//		textField_number1.setText(SSHService.goods_number1());
//		textField_number2.setText(SSHService.goods_number2());
//		textField_price1.setText(SSHService.goods_price1());
//		textField_price2.setText(SSHService.goods_price2());
//		textField_barcode1.setText(SSHService.goods_barcode1());
//		textField_barcode2.setText(SSHService.goods_barcode2());
//		textField_discount1.setText(SSHService.discount1());
//		textField_discount2.setText(SSHService.discount2());
	}
	protected void showSavedProperties(){
		String shopName = SSHService.shopName();
		String ownerCode = SSHService.ownerCode();
		String ShopCode = SSHService.shopCode();
		String goods_number1 = SSHService.goods_number1();
		String goods_number2 = SSHService.goods_number2();
		String goods_price1 = SSHService.goods_price1();
		String goods_price2 = SSHService.goods_price2();
		String goods_barcode1 = SSHService.goods_barcode1();
		String goods_barcode2 = SSHService.goods_barcode2();
		String discount1 = SSHService.discount1();
		String discount2 = SSHService.discount2();
//		AutoLogger.log.info("shopName："+shopName);
//		AutoLogger.log.info("shopCode："+ShopCode);
//		AutoLogger.log.info("ownerCode："+ownerCode);
//		AutoLogger.log.info("商品单价1："+goods_price1);
//		AutoLogger.log.info("商品单价2："+goods_price2);
//		AutoLogger.log.info("商品SKU1："+goods_barcode1);
//		AutoLogger.log.info("商品SKU2："+goods_barcode2);
//
//		AutoLogger.log.info("商品1数量："+goods_number1);
//		AutoLogger.log.info("商品2数量："+goods_number2);
//
//		AutoLogger.log.info("商品折扣1："+discount1);
//		AutoLogger.log.info("商品折扣1："+discount2);
	}
	
	protected void getClarks() {
		textField_shopName.setText("clarks女鞋旗舰店");
		comboBox_ownerCode.setSelectedItem("clarks");
		textField_ShopCode.setText("clarks_women");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("800");
		textField_price2.setText("600");
		textField_barcode1.setText("203506744040");
		textField_barcode2.setText("261224405025");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");		
	}	
	
	protected void getPandora(){
		textField_shopName.setText("pandora潘多拉官方旗舰店");
		comboBox_ownerCode.setSelectedItem("pandora");
		textField_ShopCode.setText("pandora");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("800");
		textField_price2.setText("700");
		textField_barcode1.setText("190947GCZ-54");
		textField_barcode2.setText("190947GCZ-52");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}
	
	protected void getDiesel(){
		textField_shopName.setText("diesel官方旗舰店");
		comboBox_ownerCode.setSelectedItem("diesel");
		textField_ShopCode.setText("diesel");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("500");
		textField_price2.setText("400");
		textField_barcode1.setText("8053837022891");
		textField_barcode2.setText("8053837022907");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}

	protected void getMulberry(){
		textField_shopName.setText("mulberry天猫旗舰店");
		comboBox_ownerCode.setSelectedItem("mulberry");
		textField_ShopCode.setText("mulberry");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("500");
		textField_price2.setText("400");
		textField_barcode1.setText("HH4706/205Z698OS");
		textField_barcode2.setText("HH5059/205A100OS");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}

	protected void getTissot(){
		textField_shopName.setText("tissot天梭官方旗舰店");
		comboBox_ownerCode.setSelectedItem("tissot");
		textField_ShopCode.setText("tissot");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("500");
		textField_price2.setText("400");
		textField_barcode1.setText("8522550144");
		textField_barcode2.setText("8522550145");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}

	protected void getCp(){
		textField_shopName.setText("claudiepierlot官方旗舰店");
		comboBox_ownerCode.setSelectedItem("cp");
		textField_ShopCode.setText("cp");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("500");
		textField_price2.setText("400");
		textField_barcode1.setText("3609140205206");
		textField_barcode2.setText("3609140205244");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}

	protected void getSwatch(){
		textField_shopName.setText("swatch斯沃琪官方旗舰店");
		comboBox_ownerCode.setSelectedItem("swatch");
		textField_ShopCode.setText("swatch");

		textField_number1.setText("1");
		textField_number2.setText("1");
		textField_price1.setText("200");
		textField_price2.setText("300");
		textField_barcode1.setText("7610522823730");
		textField_barcode2.setText("7610522828841");
		textField_discount1.setText("0.00");
		textField_discount2.setText("0.00");
	}

	
}