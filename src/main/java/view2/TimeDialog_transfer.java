package view2;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.SSHService;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDialog_transfer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TimeDialog_transfer dialog = new TimeDialog_transfer();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TimeDialog_transfer() {
		setFont(new Font("微软雅黑", Font.BOLD, 14));
		setTitle("时间戳获取与转换");
		setBounds(100, 100, 505, 277);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 10, 502, 238);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(261, 33, 209, 36);
		textField_1.setEditable(false);
		contentPanel.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("  →");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel.setBounds(186, 30, 65, 36);
		contentPanel.add(lblNewLabel);
		
		JButton btnunicode = new JButton("获取当前时间戳");

		btnunicode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getNowStamp();
			}
		});
		btnunicode.setBounds(10, 31, 153, 39);
		contentPanel.add(btnunicode);
		
		textField_2 = new JTextField();

		textField_2.setColumns(10);
		textField_2.setBounds(10, 143, 153, 61);
		contentPanel.add(textField_2);
		
		JLabel lblNewLabel_1 = new JLabel("  →");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel_1.setBounds(186, 153, 65, 36);
		contentPanel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(261, 106, 209, 98);
		textField_3.setEditable(false);
		contentPanel.add(textField_3);
		
		JButton btnunicode_1 = new JButton("时间戳转时间格式");

		btnunicode_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stampToTime();
			}
		});
		btnunicode_1.setBounds(10, 106, 153, 39);
		contentPanel.add(btnunicode_1);
	}

    protected void stampToTime() {
		String str = textField_2.getText();
		Long st = Long.parseLong(str);
		if (str != null && !str.trim().isEmpty() || str.length() < 12) {
			//时间格式,HH是24小时制，hh是AM PM12小时制
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//比如timestamp=1449210225945；
			long date_temp = st;
			String date_string = sdf.format(new Date(date_temp * 1000L));
            //至于取10位或取13位，date_temp*1000L就是这种截取作用。如果是和服务器传值的，就和后台商量好就可以了

		    String sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(st);
			textField_3.setText(date_string);
		}else {
			String name = SSHService.TesterName();
			String info = "亲爱的"+"【"+name+"】"+"小姐姐/小哥哥 请输入时间戳, 再点击哦~";
			JOptionPane.showMessageDialog(this, info);
		}
	}

	protected void getNowStamp() {
    	int  a = (int)(System.currentTimeMillis() / 1000);
    	String timeStamp = String.valueOf(a);
    	textField_1.setText(timeStamp);
    }

	
}
