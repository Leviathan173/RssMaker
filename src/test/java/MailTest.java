import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailTest {
    public boolean Test() throws Exception {
        Print.Println("开始邮件测试");
        String subject = "test mail";
        String content = "<h1>title</h1><br><div>this is a massage</div>";
        if(!send(subject, content)){
            return false;
        }
        Print.Println("邮件测试成功");
        return true;
    }
    boolean send(String subject, String content){
        try {
            String myEmailAccount = "Lolitadaisukidesu@163.com";
            String myEmailPassword = "FZZQQZEUMNQLIEKW";
            String myEmailSMTPHost = "smtp.163.com";
            String receiveMailAccount = "506535370@qq.com";

            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", myEmailSMTPHost);
            props.setProperty("mail.smtp.auth", "true");

            final String smtpPort = "465";
            props.setProperty("mail.smtp.port", smtpPort);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", smtpPort);

            Session session = Session.getInstance(props);
            session.setDebug(false);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmailAccount, "服务器", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, "我自己", "UTF-8"));
            message.setSubject(subject, "UTF-8");
            message.setContent(content, "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            message.saveChanges();
            Transport transport = session.getTransport();
            transport.connect(myEmailAccount, myEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (Exception e){
            Print.Println("邮件测试失败");
            Print.Println("错误信息:"+e.getMessage());
            return false;
        }
        return true;
    }
}
