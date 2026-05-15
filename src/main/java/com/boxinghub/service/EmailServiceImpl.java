package com.boxinghub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[BoxingHub] Yêu cầu đặt lại mật khẩu");
        message.setText(
                "Xin chào!\n\n" +
                        "Bạn vừa yêu cầu đặt lại mật khẩu tại BoxingHub.\n" +
                        "Nhấn vào link dưới đây để tiến hành (hiệu lực trong 30 phút):\n\n" +
                        resetLink + "\n\n" +
                        "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.\n\n" +
                        "Trân trọng,\nĐội ngũ BoxingHub"
        );
        mailSender.send(message);
    }
}