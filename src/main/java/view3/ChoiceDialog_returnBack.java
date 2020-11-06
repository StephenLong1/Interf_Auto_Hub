package view3;

import view4.RunDialog_delivery1;
import view4.RunDialog_delivery2;
import view4.RunDialog_returnBack1;
import view4.RunDialog_returnBack2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class ChoiceDialog_returnBack extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChoiceDialog_returnBack dialog = new ChoiceDialog_returnBack();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChoiceDialog_returnBack() {
		setTitle("退货入库回写OMS");
		setBounds(100, 100, 384, 214);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 378, 175);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("oid1");
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new RunDialog_returnBack1().setVisible(true);
				}
			});
			btnNewButton.setBounds(53, 93, 104, 42);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnNewButton = new JButton("oid2");
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new RunDialog_returnBack2().setVisible(true);
				}
			});
			btnNewButton.setBounds(219, 93, 104, 42);
			contentPanel.add(btnNewButton);
		}
		{
			JLabel label = new JLabel("**以下按钮与你创建的天猫单类型一一对应**");
			label.setBounds(44, 15, 309, 42);
			contentPanel.add(label);
		}
	}

}
