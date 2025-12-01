package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.repository.MeterRepository;

import com.youssef.GridPulse.seeder.faker.MeterFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MeterSeeder {

    private final MeterRepository repo;
    private final MeterFaker faker;

    public List<Meter> seed(int count) {
        List<Meter> meters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Meter u = faker.generate();
            repo.save(u);
            meters.add(u);
        }
        return meters;

    }
}
