package com.youssef.GridPulse.configuration.graphql;

import com.youssef.GridPulse.domain.message.payload.*;
import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessagePayloadResolver implements TypeResolver {

    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        Object payload = env.getObject();

//        log.warn("Resolving payload of type: {}", payload.getClass().getName());
//        log.debug("Payload content: {}", payload);

        if (payload instanceof SoftwarePayload) {
            return env.getSchema().getObjectType("SoftwarePayload");
        } else if (payload instanceof BmsPayload) {
            return env.getSchema().getObjectType("BmsPayload");
        } else if (payload instanceof HeartbeatPayload) {
            return env.getSchema().getObjectType("HeartbeatPayload");
        } else if (payload instanceof SystemPayload) {
            return env.getSchema().getObjectType("SystemPayload");
        } else if (payload instanceof MeterPayload) {
            return env.getSchema().getObjectType("MeterPayload");
        } else if (payload instanceof InverterPayload) {
            return env.getSchema().getObjectType("InverterPayload");
        } else if (payload instanceof IdsPayload) {
            return env.getSchema().getObjectType("IdsPayload");
        } else {
//            log.error("Unknown payload type: {}", payload.getClass().getName());
            throw new IllegalArgumentException("Unknown payload type: " + payload.getClass());
        }
    }
}