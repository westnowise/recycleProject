package com.zolup5.repository;

import com.zolup5.DTO.UserResponseDTO;
import com.zolup5.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    @Modifying
    @Query("UPDATE Member m SET m.area = :newArea WHERE m.username = :username")
    void updateAreaByUsername(String username, String newArea);

    @Modifying
    @Query("UPDATE Member m SET m.point = m.point + :score WHERE m.username = :username")
    void updatePointByUsername(String username, String score);

    /**
     * 유효성 검사 - 중복 체크
     * @param username 회원 아이디
     * @return
     */
    boolean existsByUsername(String username);

}