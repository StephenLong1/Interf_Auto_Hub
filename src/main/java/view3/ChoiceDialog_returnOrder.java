package view3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view4.RunDialog_delivery1;
import view4.RunDialog_delivery2;
import view4.RunDialog_returnOrder1;
import view4.RunDialog_returnOrder2;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChoiceDialog_returnOrder extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChoiceDialog_returnOrder dialog = new ChoiceDialog_returnOrder();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChoiceDialog_returnOrder() {
		setTitle("创建退货单");
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
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new RunDialog_returnOrder1().setVisible(true);
				}
			});
			btnNewButton.setBounds(44, 93, 104, 42);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnNewButton = new JButton("oid2");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new RunDialog_returnOrder2().setVisible(true);
				}
			});
			btnNewButton.setBounds(212, 93, 104, 42);
			contentPanel.add(btnNewButton);
		}
		{
			JLabel label = new JLabel("**以下按钮与你创建的天猫单类型一一对应**");
			label.setBounds(66, 15, 287, 42);
			contentPanel.add(label);
		}
	}

}
