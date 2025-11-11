package com.youssef.GridPulse.domain.message.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.payload.*;
import lombok.Getter;

import java.util.Base64;

/**
 * Parser for different message payloads.
 * Used by the frontend to decode and parse the base64-encoded JSON message payloads.
 * Supports various message types defined in {@link MessageType}.
 * Utilizes Jackson's ObjectMapper for JSON deserialization.
 * Each message type is mapped to its corresponding payload class:
 * - INVERTER -> {@link InverterPayload}
 * - BMS -> {@link BmsPayload}
 * - METER -> {@link MeterPayload}
 * - HEARTBEAT -> {@link HeartbeatPayload}
 * - SYSTEM -> {@link SystemPayload}
 * - SOFTWARE -> {@link SoftwarePayload}
 * - IDS -> {@link IdsPayload}
 * If an unsupported message type is provided, an IllegalArgumentException is thrown.
 * If JSON parsing fails, a RuntimeException is thrown with the error details.
 */
public class MessagePayloadParser {

    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static Object parse(MessageType type, JsonNode jsonNode) {
        try {
            // Si la valeur reçue est un TextNode contenant du JSON brut, la parser d'abord.
            JsonNode nodeToUse = jsonNode;
            if (nodeToUse != null && nodeToUse.isTextual()) {
                nodeToUse = objectMapper.readTree(nodeToUse.asText());
            }

            return switch (type) {
                case IDS -> objectMapper.treeToValue(nodeToUse, IdsPayload.class);
                case HEARTBEAT -> objectMapper.treeToValue(nodeToUse, HeartbeatPayload.class);
                case BMS -> objectMapper.treeToValue(nodeToUse, BmsPayload.class);
                case METER -> objectMapper.treeToValue(nodeToUse, MeterPayload.class);
                case SYSTEM -> objectMapper.treeToValue(nodeToUse, SystemPayload.class);
                case SOFTWARE -> objectMapper.treeToValue(nodeToUse, SoftwarePayload.class);
                case INVERTER -> objectMapper.treeToValue(nodeToUse, InverterPayload.class);
                default -> throw new IllegalArgumentException("Unsupported message type: " + type);
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message payload: " + e.getMessage(), e);
        }
    }

    private static String decodeBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }
}