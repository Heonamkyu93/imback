package com.refore.our.member.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
public class MailSend {

    private final JavaMailSender mailSender;


    public void sendNewPwd(String to, String subject, String text) {
        String mail = "변경된 비밀번호입니다." + text + "비밀번호를 변경해주세요.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

//    public void sendEmail(String to, String subject, String code) throws MessagingException {
//        // Thymeleaf 컨텍스트 생성 및 변수 설정
//        Context context = new Context();
//        context.setVariable("code", code);
//
//        // Thymeleaf를 사용하여 HTML 컨텐츠 생성
//        String htmlContent = templateEngine.process("mail/mail", context);
//
//        // MimeMessage 및 Helper 생성
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");
//
//        try {
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(htmlContent, true); // true는 HTML 이메일임을 나타냅니다.
//
//            // MimeMessage 전송
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            throw new MailParseException(e);
//        }
//    }




    public String createCode() {
        int random = (int) (Math.random() * 1000000);
        String code = String.valueOf(random);
        return code;
    }
}
