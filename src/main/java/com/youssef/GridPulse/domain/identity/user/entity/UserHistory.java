package com.youssef.GridPulse.domain.identity.user.entity;

import com.youssef.GridPulse.domain.base.BaseHistoryEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

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

    @ElementCollection
    private List<UUID> usedDeviceIds;

    @ElementCollection
    private List<UUID> operatedDeviceIds;

}
