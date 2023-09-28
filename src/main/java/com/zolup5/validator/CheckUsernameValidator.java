package com.zolup5.validator;

import com.zolup5.DTO.UserFormDTO;
import com.zolup5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CheckUsernameValidator extends AbstractValidator<UserFormDTO> {
    private final UserRepository userRepository;
    @Override
    protected void doValidate(UserFormDTO dto, Errors errors) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            errors.rejectValue("username", "아이디 중복오류", "이미 사용중인 아이디 입니다");
        }
    }
}

