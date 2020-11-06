package view2;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.CaretListener;

public class CharDialog_findLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private JTextField textField_pickChar;
	private JTextField textField_cursor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CharDialog_findLine dialog = new CharDialog_findLine();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CharDialog_findLine() {
		setTitle("字符统计工具");
		setBounds(100, 100, 645, 404);
		setLocationRelativeTo(null);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 629, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
		);
		contentPanel.setLayout(null);
		scrollPane.setBounds(10, 10, 609, 292);
		contentPanel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textArea.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				int dot = e.getDot();
				int mark = e.getMark();
				textField_pickChar.setText(Math.abs(dot - mark) + "");
				textField_cursor.setText(dot + "");
			}
			
		});
		scrollPane.setViewportView(textArea);
		
		JLabel label = new JLabel("选中的字符数：");
		label.setFont(new Font("微软雅黑", Font.BOLD, 14));
		label.setBounds(74, 312, 98, 25);
		contentPanel.add(label);
		
		textField_pickChar = new JTextField();
		textField_pickChar.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_pickChar.setBounds(183, 312, 66, 24);
		textField_pickChar.setEditable(false);
		contentPanel.add(textField_pickChar);
		textField_pickChar.setColumns(10);
		
		JLabel label_1 = new JLabel("光标所在字符：");
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 14));
		label_1.setBounds(301, 312, 98, 25);
		contentPanel.add(label_1);
		
		textField_cursor = new JTextField();
		textField_cursor.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_cursor.setColumns(10);
		textField_cursor.setBounds(421, 312, 66, 24);
		textField_cursor.setEditable(false);
		contentPanel.add(textField_cursor);
		getContentPane().setLayout(groupLayout);
		

	}
}
