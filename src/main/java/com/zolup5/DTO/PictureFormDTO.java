package com.zolup5.DTO;

import com.zolup5.domain.user.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureFormDTO {
    private Long accountId;
    @Builder
    public PictureFormDTO(Member user) {
        this.accountId = user.getAccounts_id();
    }

}
