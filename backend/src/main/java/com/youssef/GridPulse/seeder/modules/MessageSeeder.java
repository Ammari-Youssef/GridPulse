package com.youssef.GridPulse.seeder.modules;

import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.repository.MessageRepository;
import com.youssef.GridPulse.seeder.faker.MessageFaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageSeeder {

    private final MessageRepository repo;
    private final MessageFaker faker;

    /**
     * Seeds a given number of messages for a device.
     *
     * @param device the device to attach messages to
     * @param count  how many messages to generate
     * @return list of seeded messages
     */
    public List<Message> seed(Device device, int count) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Message msg = faker.generate(device);
            repo.save(msg);
            messages.add(msg);
        }
        return messages;
    }
}
