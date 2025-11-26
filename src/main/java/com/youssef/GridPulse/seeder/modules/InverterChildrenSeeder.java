package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.common.repository.InvCommonRepository;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.repository.InvNameplateRepository;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.repository.InvSettingsRepository;
import com.youssef.GridPulse.seeder.faker.InvCommonFaker;
import com.youssef.GridPulse.seeder.faker.InvNameplateFaker;
import com.youssef.GridPulse.seeder.faker.InvSettingsFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InverterChildrenSeeder {

    private final InvCommonRepository commonRepo;
    private final InvSettingsRepository settingsRepo;
    private final InvNameplateRepository nameplateRepo;

    private final InvCommonFaker commonFaker;
    private final InvSettingsFaker settingsFaker;
    private final InvNameplateFaker nameplateFaker;

    public void seedForInverter(Inverter inv) {

        InvCommon c = commonFaker.generate(inv);
        commonRepo.save(c);

        InvSettings s = settingsFaker.generate(inv);
        settingsRepo.save(s);

        InvNameplate n = nameplateFaker.generate(inv);
        nameplateRepo.save(n);
    }
}

