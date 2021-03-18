package com.projectSpring.serviceApp;

import com.projectSpring.classModel.MimeMessage;
import com.projectSpring.classModel.Mail;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {
    private Mail mail;

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Autowired
    private JavaMailSender javaMailSender;
    //Send a normal text email.
    public void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getTo());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());

        javaMailSender.send(msg);

    }
    //Send an HTML email and a file attachment.

    public void sendEmailWithAttachment() throws MessagingException, IOException {

        javax.mail.internet.MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getMessage());
        helper.addAttachment(mail.getAttachment(), new ClassPathResource(mail.getAttachment()));
        javaMailSender.send(msg);
    }


    public List<MimeMessage> getListOfMessages() throws MessagingException {
        Folder folder = null;
        Store store = null;
        List<MimeMessage> messageList = new ArrayList<>();

        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com","networkprogramming2@gmail.com", "Password_2");
            folder = store.getFolder("Inbox");

            folder.open(Folder.READ_WRITE);
            Message messages[] = folder.getMessages();
            System.out.println("No of Messages : " + folder.getMessageCount());
            System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
            for (int i=0; i < messages.length; ++i) {
                Message message = messages[i];
                messageList.add(new MimeMessage(i,message.getSubject(),""+message.getFrom()[0],readPlainContent((javax.mail.internet.MimeMessage) message)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (folder != null) { folder.close(true); }
            if (store != null) { store.close(); }
        }
        return messageList;
    }

    public String readPlainContent(javax.mail.internet.MimeMessage message) throws Exception {
        return new MimeMessageParser(message).parse().getPlainContent();
    }
}
