package common;

public class SendMailTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		SendMail mail=new SendMail();
//		mail.initMail();
//		try {
//			mail.send("龙张立测试");
////			mail.send("......you！", "D:\\Stephen.jpg");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void sendEmail(String content) {
		SendMail mail=new SendMail();
		mail.initMail();
		try {
			mail.send(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
