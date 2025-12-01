package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.repository.BmsRepository;
import com.youssef.GridPulse.seeder.faker.BmsFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BmsSeeder {
    private final BmsRepository repo;
    private final BmsFaker faker;

    public List<Bms> seed(int count) {
        List<Bms> bmsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Bms u = faker.generate();
            repo.save(u);
            bmsList.add(u);
        }
        return bmsList;
    }


}
