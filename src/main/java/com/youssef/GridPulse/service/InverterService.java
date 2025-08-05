package com.youssef.GridPulse.service;

import com.youssef.GridPulse.domain.dto.InverterInput;
import com.youssef.GridPulse.domain.entity.Inverter;
import com.youssef.GridPulse.repository.InverterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InverterService {

    private final InverterRepository inverterRepository;

    public Inverter createInverter(InverterInput input) {
        Inverter inverter = Inverter.builder()
                .name(input.name())
                .model(input.model())
                .version(input.version())
                .manufacturer(input.manufacturer())
                .build();
        return inverterRepository.save(inverter);
    }

    public List<Inverter> getAllInverters() {
        return inverterRepository.findAll();
    }

    public Inverter getInverterById(UUID id) {
        return inverterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inverter not found"));
    }

    public Inverter updateInverter(UUID id, InverterInput input) {
        Inverter inverter = inverterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inverter not found"));
        inverter.setName(input.name());
        inverter.setModel(input.model());
        inverter.setVersion(input.version());
        return inverterRepository.save(inverter);
    }

    public void deleteInverter(UUID id) {
        if (!inverterRepository.existsById(id)) {
            throw new RuntimeException("Inverter not found");
        }
        inverterRepository.deleteById(id);
    }
}
