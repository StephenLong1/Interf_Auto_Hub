package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.AutoLogger;
import source.SSHService;
import view2.DataDialog_inputText;
import view2.DataDialog_inputText;
import view3.ChoiceDialog_getReturn;
import view3.ChoiceDialog_wms_getOrder;
import view3.ChoiceDialog_returnOrder;
import view3.DataDialog_deliverBack;
import view3.DataDialog_delivery1;
import view3.DataDialog_returnRemove;
import view3.DataDialog_wms_deliveryBack;
import view3.DataDialog_wms_deliveryOrder;
import view3.DataDialog_wms_entryOrderConfirm;
import view3.DataDialog_wms_getOrder;
import view3.DataDialog_wms_returnConfirm;
import view3.DataDialog_wms_returnOrder;
import view3.DataDialog_wms_stockOutConfirm;
import view4.RunDialog_getOrder1;
import view4.RunDialog_getOrder2;
import view4.RunDialog_wms_deliveryOrder1;
import view3.DataDialog_orderCancel1;
import view3.DataDialog_refundOnly;
import view3.DataDialog_returnMatch;
import view3.DataDialog_returnOrder;
import view3.DataDialog_returnRefund;
import view3.DataDialog_returnRemove;
import view3.DataDialog_refundBack;

import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Button;

public class OptionsFrame_3_wms extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					OptionsFrame_3_wms frame = new OptionsFrame_3_wms();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OptionsFrame_3_wms() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int whidth=getWidth();
				int height=getHeight();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptionsFrame_3_wms.class.getResource("/机器人、自动化、工业信息技术.png")));
		setTitle("WMS测试页面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 503);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBounds(0, 0, 740, 474);
		contentPane.add(contentPanel);
		
		JLabel lblNewLabel = new JLabel("发 货 回 写：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(65, 238, 112, 44);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("采购入仓回写：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(65, 313, 98, 44);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("采购退货回写：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(391, 313, 98, 44);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("退货入库回写：");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(391, 238, 98, 44);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("获取已审核订单：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(51, 101, 112, 44);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("通 知 发 货：");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(65, 167, 98, 44);
		contentPanel.add(lblNewLabel_5);
		
		JButton btnNewButton_1_1_2_2 = new JButton("entryConfirm");
		btnNewButton_1_1_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_wms_entryOrderConfirm().setVisible(true);

			}
		});
		btnNewButton_1_1_2_2.setForeground(Color.RED);
		btnNewButton_1_1_2_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton_1_1_2_2.setBounds(186, 311, 131, 49);
		contentPanel.add(btnNewButton_1_1_2_2);
		
		JButton btnNewButton_1_1_2_3 = new JButton("deliveryBack");
		btnNewButton_1_1_2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_wms_deliveryBack().setVisible(true);
			}
		});
		btnNewButton_1_1_2_3.setForeground(Color.RED);
		btnNewButton_1_1_2_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton_1_1_2_3.setBounds(186, 236, 131, 49);
		contentPanel.add(btnNewButton_1_1_2_3);
		
		JButton btnNewButton_1_1_2_4 = new JButton("getOrder");
		btnNewButton_1_1_2_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChoiceDialog_wms_getOrder().setVisible(true);
			}
		});
		btnNewButton_1_1_2_4.setForeground(Color.RED);
		btnNewButton_1_1_2_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton_1_1_2_4.setBounds(186, 99, 131, 49);
		contentPanel.add(btnNewButton_1_1_2_4);
		
		JButton btnNewButton_1_1_2_5 = new JButton("deliveryOrder");
		btnNewButton_1_1_2_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_wms_deliveryOrder().setVisible(true);
			}
		});
		btnNewButton_1_1_2_5.setForeground(Color.BLACK);
		btnNewButton_1_1_2_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnNewButton_1_1_2_5.setBounds(186, 165, 131, 49);
		contentPanel.add(btnNewButton_1_1_2_5);
		
		JButton btnNewButton_1_1_2_6 = new JButton("returnConfirm");
		btnNewButton_1_1_2_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_wms_returnConfirm().setVisible(true);
			}
		});
		btnNewButton_1_1_2_6.setForeground(Color.RED);
		btnNewButton_1_1_2_6.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnNewButton_1_1_2_6.setBounds(513, 236, 141, 49);
		contentPanel.add(btnNewButton_1_1_2_6);
		
		JButton btnNewButton_1_1_2_7 = new JButton("stockOutConfirm");
		btnNewButton_1_1_2_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_wms_stockOutConfirm().setVisible(true);
			}
		});
		btnNewButton_1_1_2_7.setForeground(Color.RED);
		btnNewButton_1_1_2_7.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		btnNewButton_1_1_2_7.setBounds(513, 312, 141, 49);
		contentPanel.add(btnNewButton_1_1_2_7);
				
				JButton btnNewButton_2 = new JButton("B A C K");
				btnNewButton_2.setBounds(594, 398, 117, 49);
				contentPanel.add(btnNewButton_2);
				btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 14));
				
				JButton btnNewButton = new JButton("请输入WMS测试数据");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_inputText().setVisible(true);
					}
				});
				btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				btnNewButton.setBounds(278, 10, 208, 49);
				contentPanel.add(btnNewButton);
				
				JButton btnNewButton_1_1_2_2_1 = new JButton("getReturn");
				btnNewButton_1_1_2_2_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_returnRefund().setVisible(true);

//						new ChoiceDialog_getReturn().setVisible(true);
					}
				});
				btnNewButton_1_1_2_2_1.setForeground(Color.RED);
				btnNewButton_1_1_2_2_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				btnNewButton_1_1_2_2_1.setBounds(513, 99, 141, 49);
				contentPanel.add(btnNewButton_1_1_2_2_1);
				
				JLabel lblNewLabel_5_1 = new JLabel("获取退货单：");
				lblNewLabel_5_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				lblNewLabel_5_1.setBounds(391, 101, 98, 44);
				contentPanel.add(lblNewLabel_5_1);
				
				JLabel lblNewLabel_5_1_1 = new JLabel("退货单推送：");
				lblNewLabel_5_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				lblNewLabel_5_1_1.setBounds(391, 167, 98, 44);
				contentPanel.add(lblNewLabel_5_1_1);
				
				JButton Button_returnOrder1 = new JButton("returnOrder");
				Button_returnOrder1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_wms_returnOrder().setVisible(true);
					}
				});
				Button_returnOrder1.setForeground(Color.BLACK);
				Button_returnOrder1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				Button_returnOrder1.setBounds(512, 167, 141, 50);
				contentPanel.add(Button_returnOrder1);
				
				JLabel lblNewLabel_6 = new JLabel("注意：在获取退货单之前,请确保【订单状态：已发货】,否则会报错");
				lblNewLabel_6.setFont(new Font("等线", Font.PLAIN, 14));
				lblNewLabel_6.setBounds(174, 59, 427, 30);
				contentPanel.add(lblNewLabel_6);
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						new MainFrame_2().setVisible(true);
					}
				});
	}
	
	protected void getNowProperties() {
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
		
		AutoLogger.log.info("shopName："+shopName);
		AutoLogger.log.info("shopCode："+ShopCode);
		AutoLogger.log.info("ownerCode："+ownerCode);
		
		AutoLogger.log.info("商品单价1："+goods_price1);
		AutoLogger.log.info("商品单价2："+goods_price2);
		AutoLogger.log.info("商品SKU1："+goods_barcode1);
		AutoLogger.log.info("商品SKU2："+goods_barcode2);
		AutoLogger.log.info("商品1数量："+goods_number1);
		AutoLogger.log.info("商品2数量："+goods_number2);
		AutoLogger.log.info("商品折扣1："+discount1);
		AutoLogger.log.info("商品折扣1："+discount2);
	}
}
