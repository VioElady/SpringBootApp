package com.projectSpring.controllerMail;

import com.projectSpring.classModel.MimeMessage;
import com.projectSpring.classModel.Mail;
import com.projectSpring.serviceApp.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Controller()
public class ConnectController {
    private final MailService mailService;

    public ConnectController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    public String newMessage(Model model) {
        model.addAttribute("mail", new Mail());
        return "sendMessage";
    }

    @GetMapping("inbox")
    public String showInbox(Model model) throws Exception {

        List<MimeMessage> messageList = mailService.getListOfMessages();
        model.addAttribute("messageList", messageList);
        return "inbox";
    }

    @GetMapping("inbox/message/{id}")
    public String showMessage(@PathVariable(value = "id") int id, Model model) throws Exception {
        List<MimeMessage> messageList = mailService.getListOfMessages();
        MimeMessage message = messageList.get(id);
        model.addAttribute("message", message);
        return "show";
    }


    @PostMapping
    public String sendMessage(@ModelAttribute("mail") Mail mail) throws MessagingException, IOException {
        System.out.println(mail.getSubject() + " " + mail.getMessage() + " " + mail.getAttachment());
        mailService.setMail(mail);

        if (mail.getAttachment().equals("")) {
            mailService.sendEmail();
        } else {
            mailService.sendEmailWithAttachment();
        }

        return "redirect:/sendMessage";
    }
}
