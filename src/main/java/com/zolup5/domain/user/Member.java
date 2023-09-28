package com.zolup5.domain.user;

import com.zolup5.domain.role.Role;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Long Accounts_id;
    @Column(unique = true, length = 30)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = true, length = 30)
    private String password;
    @Column(nullable = false, length = 30)
    private String area;
    @Column(nullable = true, length = 30)
    private String point;
    @Column(nullable = true, length = 999)
    private String trash;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

}