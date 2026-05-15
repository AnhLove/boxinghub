package com.boxinghub.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetLink);
}