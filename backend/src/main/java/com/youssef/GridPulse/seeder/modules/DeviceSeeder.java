package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.seeder.faker.DeviceFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceSeeder {

    private final DeviceRepository repo;
    private final DeviceFaker faker;


    public List<Device> seed(User user, User operator, Bms bms, Meter meter, Inverter inverter, Fleet fleet, int count) {
        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Device d = faker.generate(user, operator, bms, meter, inverter, fleet);
            repo.save(d);
            devices.add(d);
        }
        return devices;
    }

    public Device seedOne(User user, User operator, Bms bms, Meter meter, Inverter inverter, Fleet fleet) {
        Device device = faker.generate(user, operator, bms, meter, inverter, fleet);
        return repo.save(device);
    }
}
