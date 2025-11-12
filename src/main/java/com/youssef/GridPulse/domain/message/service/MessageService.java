package com.youssef.GridPulse.domain.message.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.message.dto.MessageInput;
import com.youssef.GridPulse.domain.message.dto.UpdateMessageInput;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.mapper.MessageMapper;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;
import com.youssef.GridPulse.domain.message.payload.IdsPayload;
import com.youssef.GridPulse.domain.message.repository.MessageHistoryRepository;
import com.youssef.GridPulse.domain.message.repository.MessageRepository;
import com.youssef.GridPulse.domain.message.util.SeverityInterpreter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService extends BaseService<Message, MessageHistory, UUID, MessageInput> {

    private final DeviceRepository deviceRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper mapper2;

    public MessageService(MessageRepository repository,
                          MessageHistoryRepository historyRepository,
                          MessageMapper mapper,

                          DeviceRepository deviceRepository,
                          MessageRepository messageRepository,
                          MessageMapper mapper2) {

        super(repository, historyRepository, mapper);
        this.deviceRepository = deviceRepository;
        this.messageRepository = messageRepository;
        this.mapper2 = mapper2;
    }

    /**
     * Get messages by device ID
     *
     * @param deviceId UUID of the device
     * @return List of messages belonging to the specified device
     */
    public List<Message> getMessagesByDevice(UUID deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException("Device ID cannot be null");
        }

        if (!deviceRepository.existsById(deviceId)) {
            throw new EntityNotFoundException("Device not found");
        }

        return messageRepository.findByDeviceId(deviceId);
    }

    /**
     * Ingest raw message payload from device
     * Currently unused, kept for future use
     *
     * @param deviceId   id of the device sending the message
     * @param rawPayload raw message payload
     * @return Saved Message entity
     */
//    public Message ingestRawMessage(UUID deviceId, String rawPayload) throws IOException {
//        Device device = deviceRepository.findById(deviceId)
//                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
//
//        Message message = fromDevicePayload(device, rawPayload);
//        MessageInput input = MessageInput.builder()
//                .deviceId(deviceId)
//                .cloudMessageId(message.getCloudMessageId())
//                .messageText(message.getMessageText())
//                .messageType(message.getMessageType())
//                .format(message.getFormat())
//                .status(message.getStatus())
//                .priority(message.getPriority())
//                .sentAt(message.getSentAt())
//                .receivedAt(message.getReceivedAt())
//                .build();
//
//        setMessageValues(input, message);
//        return repository.save(message);
//    }
//
//    public Message ingestMessage(UUID deviceId, DevicePayload input) throws IOException {
//        Device device = deviceRepository.findById(deviceId)
//                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
//
//        // Convert input to DevicePayload
//        DevicePayload dto = DevicePayload.fromInput(input);
//
//        Object parsedPayload = MessagePayloadParser.parse(
//                dto.type(),
//                MessagePayloadParser.getObjectMapper().valueToTree(dto.messageText())
//        );
//
//        Severity severity = SeverityInterpreter.calculate(dto.type(), parsedPayload);
//        String explanation = SeverityInterpreter.explain(severity, dto.type());
//
//        return Message.builder()
//                .device(device)
//                .cloudMessageId(dto.messageId())
//                .messageText(dto.messageText())
//                .sentAt(OffsetDateTime.now())
//                .receivedAt(dto.date())
//                .severity(severity)
//                .explanation(explanation)
//                .priority(dto.priority())
//                .format(dto.format())
//                .messageType(dto.type())
//                .status(dto.status())
//                .build();
//    }


    /**
     * Hook to process MessageInput before saving Message entity (CREATE)
     * @param entity Message entity being created
     * @param input MessageInput DTO
     */
    @Override
    protected void beforeSave(Message entity, MessageInput input) {
        Device device = deviceRepository.findById(input.deviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        entity.setDevice(device);

        setMessageValues(input, entity);
    }


    @Override
    protected void setRelationsInHistory(MessageHistory history, Message entity) {
        if (entity.getDevice() != null) {
            history.setDeviceId(entity.getDevice().getId());
        }
    }


    @Transactional
    public Message update(UUID id, MessageInput input) {
        Message existing = super.update(id, input);

        if (input.messageType() != existing.getMessageType()) {
            throw new IllegalArgumentException("Cannot change messageType during update");
        }

        setMessageValues(input, existing);

        return repository.save(existing);
    }

    @Transactional
    public Message update(UUID id, UpdateMessageInput input) {
        Message existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));

        MessageHistory history = mapper.toHistory(existing);
        setUpdated(history, id);
        historyRepository.save(history);

        setMessageValues(input, existing);
        mapper2.updateEntity(input, existing);
        return repository.save(existing);
    }

    // Pagination specials
    public PageResponse<Message> getByDeviceIdOffsetBased(UUID deviceId, PageRequestInput pageRequest) {
        Pageable pageable = setPageRequestFields(pageRequest);
        Page<Message> result = ((MessageRepository) repository).findByDeviceId(deviceId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }


    // Helper methods
    private void setMessageValues(MessageInput input, Message existing) {
        JsonNode jsonNode = MessagePayloadParser.getObjectMapper().valueToTree(input.messageText());

        Object parsedPayload = MessagePayloadParser.parse(input.messageType(), jsonNode);
        Severity severity = SeverityInterpreter.calculate(input.messageType(), parsedPayload);
        String explanation = resolveExplanation(input.messageType(), severity, parsedPayload);

        existing.setMessageText(parsedPayload);
        existing.setSeverity(severity);
        existing.setExplanation(explanation);
    }

    private void setMessageValues(UpdateMessageInput input, Message existing) {
        JsonNode jsonNode = MessagePayloadParser.getObjectMapper().valueToTree(input.messageText());

        Object parsedPayload = MessagePayloadParser.parse(existing.getMessageType(), jsonNode);
        Severity severity = SeverityInterpreter.calculate(existing.getMessageType(), parsedPayload);
        String explanation = resolveExplanation(existing.getMessageType(), severity, parsedPayload);

        existing.setMessageText(parsedPayload);
        existing.setSeverity(severity);
        existing.setExplanation(explanation);
    }

    private String resolveExplanation(MessageType type, Severity severity, Object payload) {
        return (type == MessageType.IDS && payload instanceof IdsPayload ids)
                ? SeverityInterpreter.explain(type, ids.attackType())
                : SeverityInterpreter.explain(severity, type);
    }

    /**
     * Factory method to transform a raw, encoded payload string received from an IoT device into a {@link Message} entity.
     * <p>
     * Expected payload format (pipe-delimited):<br>
     * {@code Message_ID|Base64EncodedMessage|Date|Priority|Format|Status|MessageType}
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Parses the raw payload string into a {@link DevicePayload} DTO</li>
     *   <li>Decodes the base64-encoded JSON message content</li>
     *   <li>Parses the decoded JSON into a typed payload object using {@link MessagePayloadParser}</li>
     *   <li>Calculates {@link Severity} based on the parsed payload</li>
     *   <li>Generates a human-readable explanation using {@link SeverityInterpreter}</li>
     *   <li>Builds and returns a fully populated {@link Message} entity with decoded JSON stored in {@code messageText}</li>
     * </ol>
     *
     * @param device  the associated {@link Device} entity
     * @param rawJson the raw json payload received from the device
     * @return a constructed {@link Message} entity with resolved severity and explanation
     * @throws RuntimeException if JSON decoding or parsing fails
     */
//    public Message fromDevicePayload(Device device, String rawJson) throws IOException {
//        DevicePayload dto = DevicePayload.fromJson(rawJson);
//
//        Object parsedPayload = MessagePayloadParser.parse(dto.type(), MessagePayloadParser.getObjectMapper().valueToTree(dto.messageText()));
//
//        Severity severity = SeverityInterpreter.calculate(dto.type(), parsedPayload);
//        String explanation = SeverityInterpreter.explain(severity, dto.type());
//
//        return Message.builder()
//                .device(device)
//                .cloudMessageId(dto.messageId())
//                .messageText(dto.messageText()) // already parsed
//                .sentAt(OffsetDateTime.now())
//                .receivedAt(dto.date())
//                .severity(severity)
//                .explanation(explanation)
//                .priority(dto.priority())
//                .format(dto.format())
//                .messageType(dto.type())
//                .status(dto.status())
//                .build();
//    }


}
