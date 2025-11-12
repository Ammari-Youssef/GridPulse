package com.youssef.GridPulse.domain.message.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vladmihalcea.hibernate.type.json.JsonType;
import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class MessageHistory extends BaseHistoryEntity {

    @Column(nullable = false)
    private UUID deviceId;

    private String cloudMessageId;

    @Type(JsonType.class)
    @JdbcTypeCode(SqlTypes.JSON) // For Hibernate 6+
    @Column(columnDefinition = "jsonb")
    private Object messageText;

    private OffsetDateTime receivedAt;

    private OffsetDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private MessageFormat format;

    @Enumerated(EnumType.STRING)
    private MessagePriority priority;

    private String explanation;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @PostLoad
    public void hydrateMessageText() {
        if (messageText instanceof LinkedHashMap && messageType != null) {
            JsonNode jsonNode = objectMapper.valueToTree(messageText);
            this.messageText = MessagePayloadParser.parse(messageType, jsonNode);
        }
    }
}
