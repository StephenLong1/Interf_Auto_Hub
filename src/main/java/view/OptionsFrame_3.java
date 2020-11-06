package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.AutoLogger;
import source.SSHService;
import view2.DataDialog_inputText;
import view3.ChoiceDialog_returnOrder;
import view3.DataDialog_deliverBack;
import view3.DataDialog_delivery1;
import view3.DataDialog_returnRemove;
import view4.RunDialog_getOrder1;
import view4.RunDialog_getOrder2;
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

public class OptionsFrame_3 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					OptionsFrame_3 frame = new OptionsFrame_3();
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
	public OptionsFrame_3() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int whidth=getWidth();
				int height=getHeight();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptionsFrame_3.class.getResource("/机器人、自动化、工业信息技术.png")));
		setTitle("天猫单测试页面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 915, 590);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		JButton Button_refundOnly1 = new JButton("推送退款-仅退款");
		Button_refundOnly1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				notice();
			}
		});
		Button_refundOnly1.setForeground(Color.BLACK);
		Button_refundOnly1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		JButton Button_deliver1 = new JButton("通知发货");
		Button_deliver1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_delivery1().setVisible(true);
			}
		});
		Button_deliver1.setForeground(Color.BLACK);
		Button_deliver1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_orderCancel1 = new JButton("订单取消");
		Button_orderCancel1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_orderCancel1().setVisible(true);
			}
		});
		Button_orderCancel1.setForeground(Color.BLACK);
		Button_orderCancel1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_returnRemove1 = new JButton("退货单作废");
		Button_returnRemove1.setForeground(Color.BLACK);
		Button_returnRemove1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_returnRemove().setVisible(true);
			}
		});
		Button_returnRemove1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_returnOrder1 = new JButton("推送退货单");
		Button_returnOrder1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_returnOrder().setVisible(true);

//				new ChoiceDialog_returnOrder().setVisible(true);
			}
		});
		Button_returnOrder1.setForeground(Color.BLACK);
		Button_returnOrder1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(OptionsFrame_3.class.getResource("/返回 (1).png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame_2().setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		JButton btnNewButton = new JButton("→  【测 试 数 据 输 入】 ←");
		btnNewButton.setForeground(new Color(128, 128, 0));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeProperties(e);
			}
		});
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		JButton btnNewButton_1_1 = new JButton("新建天猫单-单");
		btnNewButton_1_1.setForeground(Color.RED);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RunDialog_getOrder1().setVisible(true);
				getNowProperties();
			}
		});
		btnNewButton_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton btnNewButton_1_1_1 = new JButton("新建天猫单-多");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new RunDialog_getOrder2().setVisible(true);
			}
		});
		btnNewButton_1_1_1.setForeground(Color.RED);
		btnNewButton_1_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton btnNewButton_1_1_2 = new JButton("新建天猫仅退款单");
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DataDialog_refundOnly().setVisible(true);
			}
		});
		btnNewButton_1_1_2.setForeground(Color.RED);
		btnNewButton_1_1_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton btnNewButton_1_1_2_2 = new JButton("新建天猫退货退款单");
		btnNewButton_1_1_2_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_returnRefund().setVisible(true);
			}
		});
		btnNewButton_1_1_2_2.setForeground(Color.RED);
		btnNewButton_1_1_2_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_deliver1_2 = new JButton("发货回传");
		Button_deliver1_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDialog_deliverBack().setVisible(true);
			}
		});
		Button_deliver1_2.setForeground(Color.BLACK);
		Button_deliver1_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_deliver1_2_1 = new JButton("退款确认回传");
		Button_deliver1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_refundBack().setVisible(true);
			}
		});
		Button_deliver1_2_1.setForeground(Color.BLACK);
		Button_deliver1_2_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton Button_deliver1_2_1_1 = new JButton("退货入库回传");
		Button_deliver1_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DataDialog_returnMatch().setVisible(true);
			}
		});
		Button_deliver1_2_1_1.setForeground(Color.BLACK);
		Button_deliver1_2_1_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JButton btnNewButton_2 = new JButton("B A C K");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame_2().setVisible(true);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(127)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 636, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_1_1, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addComponent(Button_deliver1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(Button_deliver1_2, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(Button_orderCancel1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(980)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1_1_2_2, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_1_2, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(Button_returnOrder1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button_refundOnly1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(Button_deliver1_2_1_1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button_deliver1_2_1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addComponent(Button_returnRemove1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(744, Short.MAX_VALUE)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addGap(207))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(54)
							.addComponent(btnNewButton_1_1_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(Button_deliver1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(Button_deliver1_2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(Button_orderCancel1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1_1_2_2, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(153)
							.addComponent(btnNewButton_1_1_2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(Button_returnOrder1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGap(83)
							.addComponent(Button_refundOnly1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(Button_deliver1_2_1_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGap(83)
							.addComponent(Button_deliver1_2_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(Button_returnRemove1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
					.addGap(42)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(61)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	protected void notice() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "注意： 该接口仅支持官网、小程序,  暂时未开放");
	}

	protected void writeProperties(ActionEvent e) {
		new DataDialog_inputText().setVisible(true);

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
