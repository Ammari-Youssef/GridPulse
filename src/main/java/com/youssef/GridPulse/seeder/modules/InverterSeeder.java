package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.seeder.faker.InverterFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InverterSeeder {

    private final InverterRepository repo;
    private final InverterFaker faker;

    public List<Inverter> seed(int count) {
        List<Inverter> inverters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Inverter inverter = faker.generate();
            repo.save(inverter);
            inverters.add(inverter);
        }
        return inverters;

    }
}