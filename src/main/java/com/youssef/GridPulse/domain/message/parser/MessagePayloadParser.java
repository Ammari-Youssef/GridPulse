package com.youssef.GridPulse.domain.message.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.payload.*;

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

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static Object parse(MessageType type, String base64MessageText) {
        String json = decodeBase64(base64MessageText);

        try {
            return switch (type) {
                case INVERTER -> objectMapper.readValue(json, InverterPayload.class);
                case BMS -> objectMapper.readValue(json, BmsPayload.class);
                case METER -> objectMapper.readValue(json, MeterPayload.class);
                case HEARTBEAT -> objectMapper.readValue(json, HeartbeatPayload.class);
                case SYSTEM -> objectMapper.readValue(json, SystemPayload.class);
                case SOFTWARE -> objectMapper.readValue(json, SoftwarePayload.class);
                case IDS -> objectMapper.readValue(json, IdsPayload.class);
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