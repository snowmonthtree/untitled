import org.example.EmailSender;
import org.example.QQEmailSender;
import org.junit.jupiter.api.Test;

public class EmailTest {
    EmailSender emailSender=new QQEmailSender();

    @Test
    public void Test() {
        emailSender.init("2680340431@qq.com");
        emailSender.sendmail();
    }
}
