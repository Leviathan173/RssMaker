package per.leviathan173.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static per.leviathan173.entity.Options.ENABLE_MAIL;

/*
* 这个类使用了https://blog.csdn.net/xuemengrui12/article/details/78530594的代码
*
* */
public class MailSender{
    public void sendMail(String subject, String content) throws Exception {
        if (!ENABLE_MAIL)return;
        String myEmailAccount = "xxxxxxxxxxx@163.com"; // 更改发件人邮箱
        String myEmailPassword = "asdfg"; // 你需要自己更改密码，注意不是邮箱登录密码，是SMTP服务的授权密码
        String myEmailSMTPHost = "smtp.163.com"; // 这里是163的smtp服务器，如果使用其他邮箱需要更改
        String receiveMailAccount = "xxxxxxxxxx@qq.com"; // 更改收件人邮箱

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(props);
        session.setDebug(true);

        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, subject, content);

        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);

        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);

        // From: 发件人
        message.setFrom(new InternetAddress(sendMail, "服务器", "UTF-8"));

        // To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "我自己", "UTF-8"));

        // Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 设置发件时间
        message.setSentDate(new Date());

        message.saveChanges();

        return message;
    }

    public static void sendErrMail(String subject, String content) throws Exception {
        if (!ENABLE_MAIL)return;
        MailSender mailSender = new MailSender();
        mailSender.sendMail("Rss服务器出现" + subject,
                "<h1>详细信息</h1><br><div>" +
                        "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                        "错误类型：" + subject +
                        "错误信息：" + content + "<br>");
    }
}