package view3;

import javax.swing.JButton;
import javax.swing.JDialog;
import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_delivery2;
import view4.RunDialog_wms_returnOrder;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog_wms_returnOrder extends JDialog {
	private JTextField textField_return_sn;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel;
	private JTextField textField_ownerCode;
	private JTextField textField_shopCode;
	private JLabel lblShopcode;
	private JTextField textField_return_sn2;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_wms_returnOrder dialog = new DataDialog_wms_returnOrder();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_wms_returnOrder() {
		setTitle("填写订单号");
		setBounds(100, 100, 356, 265);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_return_sn = new JTextField();
		textField_return_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_return_sn.setBounds(136, 27, 181, 24);
		textField_return_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_sn.setColumns(15);
		
		lblNewLabel_2 = new JLabel("return_sn:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(10, 26, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_return_sn);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(52, 178, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(212, 178, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel = new JLabel("ownerCode：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 99, 104, 24);
		getContentPane().add(lblNewLabel);
		
		textField_ownerCode = new JTextField();
		textField_ownerCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		textField_ownerCode.setBounds(136, 99, 181, 24);
		getContentPane().add(textField_ownerCode);
		
		textField_shopCode = new JTextField();
		textField_shopCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_shopCode.setColumns(15);
		textField_shopCode.setBounds(136, 133, 181, 24);
		getContentPane().add(textField_shopCode);
		
		lblShopcode = new JLabel("shopCode：");
		lblShopcode.setForeground(Color.RED);
		lblShopcode.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblShopcode.setBounds(35, 133, 92, 24);
		getContentPane().add(lblShopcode);
		
		textField_return_sn2 = new JTextField();
		textField_return_sn2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_sn2.setColumns(15);
		textField_return_sn2.setBounds(136, 65, 181, 24);
		textField_return_sn2.setEditable(false);
		getContentPane().add(textField_return_sn2);
		
		lblNewLabel_1 = new JLabel("return_sn2:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(10, 65, 104, 24);
		getContentPane().add(lblNewLabel_1);
	}

	protected void relatedToData() {
		SSHService.writeProperties("return_sn",textField_return_sn.getText());
		SSHService.writeProperties("ShopCode",textField_shopCode.getText());
		SSHService.writeProperties("ownerCode",textField_ownerCode.getText());
		
	}

	protected void save() {
		// TODO Auto-generated method stub
		String return_sn = textField_return_sn.getText().toString();
		String return_sn2 = textField_return_sn2.getText().toString();

		String ownerCode = textField_ownerCode.getText().toString();
		String shopCode = textField_shopCode.getText().toString();

		if(return_sn.trim().isEmpty() || ownerCode.trim().isEmpty()
				|| shopCode.trim().isEmpty() ) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			this.dispose();
			relatedToData();   // 关联已输入的数据
			new RunDialog_wms_returnOrder().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
		textField_return_sn.setText(SSHService.return_sn1());
		textField_return_sn2.setText(SSHService.return_sn2());
		textField_return_sn2.setEditable(false);
		textField_ownerCode.setText(SSHService.ownerCode());
		textField_shopCode.setText(SSHService.shopCode());

	}

}
