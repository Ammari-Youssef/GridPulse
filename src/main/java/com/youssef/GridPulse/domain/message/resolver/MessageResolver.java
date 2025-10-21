package com.youssef.GridPulse.domain.message.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;

import com.youssef.GridPulse.domain.message.dto.MessageInput;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import com.youssef.GridPulse.domain.message.service.MessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class MessageResolver extends BaseResolver<Message, MessageHistory, UUID, MessageInput> {

    private final MessageService service;

    public MessageResolver(MessageService service) {
        super(service);
        this.service = service;
    }

    @QueryMapping
    public List<Message> getMessagesByDevice(@Argument UUID deviceId) {
        return service.getMessagesByDevice(deviceId);
    }

    @MutationMapping
    public Message ingestMessage(@Argument UUID deviceId, @Argument(name = "payload") String rawPayload) {
        return service.ingestRawMessage(deviceId, rawPayload);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Message> getAllMessages() {
        return super.getAll();
    }

    @QueryMapping
    public Message getMessageById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Message createMessage(@Argument MessageInput input) {
        return service.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Message updateMessage(@Argument UUID id, @Argument MessageInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteMessage(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MessageHistory> getMessageHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MessageHistory> getAllMessageHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MessageHistory getMessageHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markMessageHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }


//    @MutationMapping
//    public Message ingestRawMessage(@Argument UUID deviceId, @Argument String rawPayload) {
//        var device = service.getDeviceRepository.findById(deviceId)
//                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
//        Message message = Message.fromDevicePayload(deviceId, rawPayload); // Add an overloaded version of method with deviceId
//        return messageRepository.save(message);
//    }


}
