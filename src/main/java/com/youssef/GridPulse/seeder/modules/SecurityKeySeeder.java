package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.repository.SecurityKeyRepository;
import com.youssef.GridPulse.seeder.faker.SecurityKeyFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityKeySeeder {

    private final SecurityKeyRepository repo;
    private final SecurityKeyFaker faker;

    public List<SecurityKey> seed(User owner, Device device, int count) {
        List<SecurityKey> securityKeys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SecurityKey u = faker.generate(device , owner);
            repo.save(u);
            securityKeys.add(u);
        }
        return securityKeys;
    }

}
