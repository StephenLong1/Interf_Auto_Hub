package view2;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.HttpClientKw;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UniDialog_transfer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_0;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UniDialog_transfer dialog = new UniDialog_transfer();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UniDialog_transfer() {
		setFont(new Font("微软雅黑", Font.BOLD, 14));
		setTitle("Unice编码转换");
		setBounds(100, 100, 518, 277);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 10, 502, 238);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		textField_0 = new JTextField();
		textField_0.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				textField_1.setText(HttpClientKw.Encode(textField_0.getText()));
			}
		});
		textField_0.setBounds(10, 49, 209, 36);
		contentPanel.add(textField_0);
		textField_0.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(283, 49, 209, 36);
		textField_1.setEditable(false);
		contentPanel.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("  →");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel.setBounds(219, 49, 65, 36);
		contentPanel.add(lblNewLabel);
		
		JButton btnunicode = new JButton("转换为Unicode编码");

		btnunicode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField_1.setText(HttpClientKw.Encode(textField_0.getText()));
			}
		});
		btnunicode.setBounds(10, 0, 153, 39);
		contentPanel.add(btnunicode);
		
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				textField_3.setText(HttpClientKw.DeCode(textField_2.getText()));
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(10, 168, 209, 36);
		contentPanel.add(textField_2);
		
		JLabel lblNewLabel_1 = new JLabel("  →");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel_1.setBounds(219, 165, 65, 36);
		contentPanel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(283, 168, 209, 36);
		textField_3.setEditable(false);
		contentPanel.add(textField_3);
		
		JButton btnunicode_1 = new JButton("转换为中文或字符");

		btnunicode_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField_3.setText(HttpClientKw.DeCode(textField_2.getText()));
			}
		});
		btnunicode_1.setBounds(10, 108, 153, 39);
		contentPanel.add(btnunicode_1);
	}

}
