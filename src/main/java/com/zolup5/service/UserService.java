package com.zolup5.service;

import com.zolup5.DTO.UserFormDTO;
import com.zolup5.domain.user.Member;
import com.zolup5.repository.UserRepository;
import org.springframework.validation.Errors;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

public interface UserService {
    Long join(UserFormDTO userFormDTO);

    /**
     * 회원가입 시, 유효성 중복 검사
     * @param errors
     * @return
     */
    Map<String, String> validateHandling(Errors errors);

    @Transactional
    void updateUserArea(String username, String newArea);

    @Transactional
    boolean changePassword(String username, String currentPassword, String newPassword, String confirmPassword);

    @Transactional
    void updateUserPoint(String username, String score);

    void emailchangePassword(String username, String newPassword);

}