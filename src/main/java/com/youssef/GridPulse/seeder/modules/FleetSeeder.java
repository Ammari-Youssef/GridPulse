package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.repository.FleetRepository;
import com.youssef.GridPulse.seeder.faker.FleetFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FleetSeeder {

    private final FleetRepository repo;
    private final FleetFaker faker;

    public List<Fleet> seed(int count) {
        List<Fleet> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Fleet u = faker.generate();
            repo.save(u);
            users.add(u);
        }
        return users;
    }

}
