package view3;

import javax.swing.JButton;
import javax.swing.JDialog;

import common.AutoLogger;
import source.SSHService;
import view4.RunDialog_returnRemove;
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

public class DataDialog_returnRemove extends JDialog {
	private JTextField textField_return_sn;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_save;
	private JButton btnNewButton_getProperties;
	private JLabel lblNewLabel_;
	private JTextField textField_cancelReason;
	private JTextField textField_ownerCode;
	private JLabel lblNewLabel_3;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDialog_returnRemove dialog = new DataDialog_returnRemove();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDialog_returnRemove() {
		setTitle("退货单作废");
		setBounds(100, 100, 402, 236);
		setResizable(false);
		setLocationRelativeTo(null);

		textField_return_sn = new JTextField();
		textField_return_sn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField_return_sn.setBounds(160, 69, 181, 24);
		textField_return_sn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_return_sn.setColumns(15);
		
		lblNewLabel_2 = new JLabel("return_sn:");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(46, 69, 104, 24);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(textField_return_sn);
		
		btnNewButton_save = new JButton(" 保 存");
		btnNewButton_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(e);
			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_save.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_save.setBounds(66, 149, 77, 30);
		getContentPane().add(btnNewButton_save);
		
		btnNewButton_getProperties = new JButton("获 取");
		btnNewButton_getProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getProperties(e);
			}
		});
		btnNewButton_getProperties.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton_getProperties.setBounds(248, 149, 77, 30);
		getContentPane().add(btnNewButton_getProperties);
		
		lblNewLabel_ = new JLabel("cancelReason：");
		lblNewLabel_.setForeground(Color.RED);
		lblNewLabel_.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_.setBounds(46, 103, 119, 24);
		getContentPane().add(lblNewLabel_);
		
		textField_cancelReason = new JTextField();
		textField_cancelReason.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_cancelReason.setColumns(15);
		textField_cancelReason.setBounds(160, 103, 181, 24);
		getContentPane().add(textField_cancelReason);
		
		textField_ownerCode = new JTextField();
		textField_ownerCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textField_ownerCode.setColumns(15);
		textField_ownerCode.setBounds(160, 35, 181, 24);
		getContentPane().add(textField_ownerCode);
		
		lblNewLabel_3 = new JLabel("ownerCode：");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(46, 35, 104, 24);
		getContentPane().add(lblNewLabel_3);
		
		label = new JLabel("");
		label.setBounds(88, 10, 229, 15);
		getContentPane().add(label);
	}


	protected void save(ActionEvent e) {
		// TODO Auto-generated method stub
		String cancelReason = textField_cancelReason.getText().toString();
		String return_sn = textField_return_sn.getText().toString();
		String ownerCode = textField_ownerCode.getText().toString();

//		AutoLogger.log.info("ownerCode："+SSHService.ownerCode());
//		AutoLogger.log.info("return_sn："+SSHService.return_sn());
//		AutoLogger.log.info("cancelReason："+SSHService.cancelReason());
		
		if(ownerCode.trim().isEmpty() || return_sn.trim().isEmpty() 
			|| cancelReason.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "不能为空格,  请检查是否输入正确！！！");
		}else {
			this.dispose();
			SSHService.writeProperties("ownerCode",ownerCode);
			SSHService.writeProperties("return_sn",return_sn);
			SSHService.writeProperties("cancelReason",cancelReason);
			new RunDialog_returnRemove().setVisible(true);
		}
	}
	
	protected void getProperties(ActionEvent e) {
        String ownerCode = SSHService.ownerCode();
        String return_sn = SSHService.return_sn();
        String cancelReason = SSHService.cancelReason();

		textField_return_sn.setText(return_sn);
		textField_ownerCode.setText(ownerCode);
		textField_cancelReason.setText(cancelReason);


	}
}
