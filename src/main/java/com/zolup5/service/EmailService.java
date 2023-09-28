package com.zolup5.service;

import com.zolup5.domain.user.Member;
import com.zolup5.repository.UserRepository;

public interface EmailService {
    public void sendTemporaryPasswordEmail(String toEmail, String temporaryPassword);
}
