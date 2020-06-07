public class TestMain {
    public static void main(String[] args) throws Exception {
        MailSender mailSender = new MailSender();
        String subject = "test mail";
        String content = "<h1>title</h1><br><div>this is a massage</div>";
        mailSender.SendMail(subject, content);
    }
}
