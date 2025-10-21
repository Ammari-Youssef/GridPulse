package com.youssef.GridPulse.domain.message.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.message.dto.MessageInput;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.enums.Severity;
import com.youssef.GridPulse.domain.message.mapper.MessageMapper;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;
import com.youssef.GridPulse.domain.message.payload.IdsPayload;
import com.youssef.GridPulse.domain.message.repository.MessageHistoryRepository;
import com.youssef.GridPulse.domain.message.repository.MessageRepository;
import com.youssef.GridPulse.domain.message.util.SeverityInterpreter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService extends BaseService<Message, MessageHistory, UUID, MessageInput> {

    private final DeviceRepository deviceRepository;
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository repository,
                          MessageHistoryRepository historyRepository,
                          MessageMapper mapper,

                          DeviceRepository deviceRepository,
                          MessageRepository messageRepository) {

        super(repository, historyRepository, mapper);
        this.deviceRepository = deviceRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Get messages by device ID
     *
     * @param deviceId UUID of the device
     * @return List of messages belonging to the specified device
     */
    public List<Message> getMessagesByDevice(UUID deviceId) {
        if(deviceId == null) {
            throw new IllegalArgumentException("Device ID cannot be null");
        }

        if(!deviceRepository.existsById(deviceId)) {
            throw new EntityNotFoundException("Device not found");
        }

        return messageRepository.findByDeviceId(deviceId);
    }

    /**
     * Ingest raw message payload from device
     * Currently unused, kept for future use
     * @param deviceId id of the device sending the message
     * @param rawPayload raw message payload
     * @return Saved Message entity
     */
    public Message ingestRawMessage(UUID deviceId, String rawPayload) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
        Message message = Message.fromDevicePayload(device, rawPayload);
        return repository.save(message);
    }

    /**
     * Hook to process MessageInput before saving Message entity (CREATE)
     * @param entity Message entity being created
     * @param input MessageInput DTO
     */
    @Override
    protected void beforeSave(Message entity, MessageInput input) {
        // Resolve relations
        Device device = deviceRepository.findById(input.deviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        entity.setDevice(device);
        // Encode raw JSON for parser
        String encodedPayload = Base64.getEncoder().encodeToString(
                input.messageText().getBytes(StandardCharsets.UTF_8)
        );

        // Parse and interpret
        Object parsedPayload = MessagePayloadParser.parse(input.messageType(), encodedPayload);
        Severity severity = SeverityInterpreter.calculate(input.messageType(), parsedPayload);
        // Handle IDS explanation with AttackType
        String explanation;

        if (input.messageType() == MessageType.IDS) {
            if (!(parsedPayload instanceof IdsPayload idsPayload)) {
                throw new IllegalArgumentException("Expected IdsPayload for IDS message type");
            }
            explanation = SeverityInterpreter.explain(input.messageType(), idsPayload.attackType());
        } else {
            explanation = SeverityInterpreter.explain(severity, input.messageType());
        }


        // Populate entity
        entity.setMessageText(input.messageText()); // store decoded for readability
        entity.setSeverity(severity);
        entity.setExplanation(explanation);

    }

    @Override
    protected void setRelationsInHistory(MessageHistory history, Message entity) {
        if (entity.getDevice() != null) {
            history.setDeviceId(entity.getDevice().getId());
        }
    }

    @Override
    @Transactional
    public Message update(UUID id, MessageInput input) {
        Message existing = super.update(id, input); // loads, saves history, applies mapper.updateEntity

        // Validate messageType consistency
        if (input.messageType() != existing.getMessageType()) {
            throw new IllegalArgumentException("Cannot change messageType during update");
        }

        // Re-parse messageText
        String encodedPayload = Base64.getEncoder().encodeToString(
                input.messageText().getBytes(StandardCharsets.UTF_8)
        );
        Object parsedPayload = MessagePayloadParser.parse(input.messageType(), encodedPayload);

        // Recalculate severity and explanation
        Severity severity = SeverityInterpreter.calculate(input.messageType(), parsedPayload);
        String explanation = (input.messageType() == MessageType.IDS && parsedPayload instanceof IdsPayload idsPayload)
                ? SeverityInterpreter.explain(input.messageType(), idsPayload.attackType())
                : SeverityInterpreter.explain(severity, input.messageType());

        existing.setMessageText(input.messageText());
        existing.setSeverity(severity);
        existing.setExplanation(explanation);
        existing.setUpdatedAt(Instant.now());

        return repository.save(existing); // persist your changes
    }



}
