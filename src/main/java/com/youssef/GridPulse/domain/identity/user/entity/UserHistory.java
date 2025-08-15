package com.youssef.GridPulse.domain.identity.user.entity;

import com.youssef.GridPulse.domain.base.BaseHistoryEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserHistory extends BaseHistoryEntity {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private boolean disabledRecord = false;

}
