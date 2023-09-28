package com.zolup5.DTO;

import com.zolup5.domain.role.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO {
    private String username;
    private String email;
    private String password;
    private String checkpassword;
    private String area;
    private String point;
    private String trash;
    private Role role;
}