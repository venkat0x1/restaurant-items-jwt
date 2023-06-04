package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {


    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String toEmail,String body,String subject){

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("makkavenkataramana0506@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setText(body);
        mailMessage.setSubject(subject);

        javaMailSender.send(mailMessage);
        System.out.println("mail send...!");
    }

    public void emailSending(String email,int id){
        sendEmail(email,
                "This is From Spring Restaurant_Food_Items Application Invitation --- Venkat click this link and conform your verification    http://localhost:8080/users/verify?id=" + id,
                "conformation mail");
    }

}

