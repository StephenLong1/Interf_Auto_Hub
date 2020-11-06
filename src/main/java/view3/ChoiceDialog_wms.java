package view3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.OptionsFrame_3_timedTask;
import view.OptionsFrame_3_wms;
import view4.RunDialog_delivery1;
import view4.RunDialog_delivery2;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ChoiceDialog_wms extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChoiceDialog_wms dialog = new ChoiceDialog_wms();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChoiceDialog_wms() {
		setTitle("请选择测试类型");
		setBounds(100, 100, 384, 202);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 378, 175);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("WMS回传接口");
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new OptionsFrame_3_wms().setVisible(true);
				}
			});
			btnNewButton.setBounds(31, 62, 137, 42);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnNewButton = new JButton("发/退货数据推送");
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 13));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new OptionsFrame_3_timedTask().setVisible(true);
				}
			});
			btnNewButton.setBounds(206, 62, 137, 42);
			contentPanel.add(btnNewButton);
		}
	}

}
