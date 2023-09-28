package com.zolup5.service.Impl;

import com.zolup5.repository.UserRepository;
import com.zolup5.service.EmailService;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendTemporaryPasswordEmail(String toEmail, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("임시 비밀번호가 발급되었습니다.");
        message.setText("임시 비밀번호가 발급되었습니다. 발급 후 마이페이지 비밀번호 변경에서 비밀번호를 변경해주세요" +
                "임시비밀번호 : " + temporaryPassword);
        mailSender.send(message);
    }

}
