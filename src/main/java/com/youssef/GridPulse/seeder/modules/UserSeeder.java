package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.seeder.faker.UserFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSeeder {

    private final UserRepository repo;
    private final UserFaker faker;

    public List<User> seed(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User u = faker.generate();
            repo.save(u);
            users.add(u);
        }
        return users;
    }

    public List<User> seedAdmins(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User u = faker.generate(Role.ADMIN);
            repo.save(u);
            users.add(u);
        }
        return users;
    }


}
