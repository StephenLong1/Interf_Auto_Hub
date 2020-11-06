package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.AutoLogger;
import source.SSHService;
import view2.DataDialog_inputText;
import view3.ChoiceDialog_wms_getOrder;
import view3.ChoiceDialog_returnOrder;
import view3.DataDialog_deliverBack;
import view3.DataDialog_delivery1;
import view3.DataDialog_matchReturnPush;
import view3.DataDialog_returnRemove;
import view3.DataDialog_shippingSales;
import view4.RunDialog_getOrder1;
import view4.RunDialog_getOrder2;
import view3.DataDialog_orderCancel1;
import view3.DataDialog_refundOnly;
import view3.DataDialog_returnFinishPush;
import view3.DataDialog_returnMatch;
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

public class OptionsFrame_3_timedTask extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					OptionsFrame_3_timedTask frame = new OptionsFrame_3_timedTask();
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
	public OptionsFrame_3_timedTask() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int whidth=getWidth();
				int height=getHeight();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptionsFrame_3_timedTask.class.getResource("/机器人、自动化、工业信息技术.png")));
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
				
				JButton btnNewButton_2 = new JButton("B A C K");
				btnNewButton_2.setBounds(594, 398, 117, 49);
				contentPanel.add(btnNewButton_2);
				btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 14));
				
				JButton btnNewButton_1_1_2_1_1 = new JButton("returnFinish");
				btnNewButton_1_1_2_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_returnFinishPush().setVisible(true);
					}
				});
				btnNewButton_1_1_2_1_1.setForeground(Color.RED);
				btnNewButton_1_1_2_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				btnNewButton_1_1_2_1_1.setBounds(187, 217, 131, 49);
				contentPanel.add(btnNewButton_1_1_2_1_1);
				
				JButton btnNewButton_1_1_2_3_1 = new JButton("returnMatch");
				btnNewButton_1_1_2_3_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_matchReturnPush().setVisible(true);
					}
				});
				btnNewButton_1_1_2_3_1.setForeground(Color.RED);
				btnNewButton_1_1_2_3_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				btnNewButton_1_1_2_3_1.setBounds(187, 122, 131, 49);
				contentPanel.add(btnNewButton_1_1_2_3_1);
				
				JLabel lblNewLabel_6_1 = new JLabel("匹配后触发推送：");
				lblNewLabel_6_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				lblNewLabel_6_1.setBounds(51, 124, 126, 44);
				contentPanel.add(lblNewLabel_6_1);
				
				JLabel lblNewLabel_6_1_1 = new JLabel("完结后触发推送：");
				lblNewLabel_6_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				lblNewLabel_6_1_1.setBounds(51, 219, 126, 44);
				contentPanel.add(lblNewLabel_6_1_1);
				
				JLabel lblNewLabel_6_1_2 = new JLabel("发货数据推送：");
				lblNewLabel_6_1_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				lblNewLabel_6_1_2.setBounds(51, 44, 126, 44);
				contentPanel.add(lblNewLabel_6_1_2);
				
				JButton btnNewButton_1_1_2_3_1_1 = new JButton("salesData");
				btnNewButton_1_1_2_3_1_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DataDialog_shippingSales().setVisible(true);;
					}
				});
				btnNewButton_1_1_2_3_1_1.setForeground(Color.RED);
				btnNewButton_1_1_2_3_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				btnNewButton_1_1_2_3_1_1.setBounds(187, 42, 131, 49);
				contentPanel.add(btnNewButton_1_1_2_3_1_1);
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
