package com.ssafy.vue.member.service;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    void createCode();
    MimeMessage createEmailForm(String email) throws Exception;

    public String sendEmail(String email) throws Exception;
}
