package com.youssef.GridPulse.domain.inverter.resolver;

import com.youssef.GridPulse.domain.base.BaseResolverTest;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.resolver.InverterResolver;
import com.youssef.GridPulse.domain.inverter.inverter.service.InverterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.ZoneId;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@GraphQlTest(InverterResolver.class)
@EnableMethodSecurity
@AutoConfigureGraphQlTester
class InverterResolverTests extends BaseResolverTest<InverterResolver, Inverter, InverterHistory, UUID, InverterInput, InverterService> {

    @MockitoBean
    InverterService service;

    @Autowired
    public InverterResolverTests(InverterService service) {
        super(service);
    }

    @Override
    protected InverterResolver getResolver() {
        return new InverterResolver(service);
    }

    @Override
    protected Inverter createTestEntity(UUID id) {
        return Inverter.builder()
                .id(id)
                .name("Test Inverter")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .version("Test Version")
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    @Override
    protected InverterHistory createTestHistoryEntity(UUID originalId) {
        return InverterHistory.builder()
                .id(UUID.randomUUID())
                .originalId(originalId)
                .name("Test Inverter")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .version("Test Version")
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    @Override
    protected InverterInput createTestInput() {
        return new InverterInput("Test Inverter", "TX5000", "v2.1", "SolarEdge");
    }

    @Override
    protected UUID createTestId() {
        return UUID.randomUUID();
    }

    @Override
    protected Class<Inverter> getEntityClass() {
        return Inverter.class;
    }

    @Override
    protected Class<InverterHistory> getHistoryClass() {
        return InverterHistory.class;
    }

    @Override
    protected void assertions(Inverter inverter) {
        assertThat(inverter.getName()).isEqualTo("Test Inverter");
        assertThat(inverter.getModel()).isEqualTo("Test Model");
        assertThat(inverter.getVersion()).isEqualTo("Test Version");
        assertThat(inverter.getManufacturer()).isEqualTo("Test Manufacturer");
        assertThat(inverter.getSource()).isEqualTo("app");
    }

    @Override
    protected void assertionsHistory(InverterHistory history) {
        assertThat(history.getName()).isEqualTo("Test Inverter");
        assertThat(history.getModel()).isEqualTo("Test Model");
        assertThat(history.getVersion()).isEqualTo("Test Version");
        assertThat(history.getManufacturer()).isEqualTo("Test Manufacturer");
        assertThat(history.getSource()).isEqualTo("app");
    }

    @Override
    protected Map<String, Object> convertInputToMap(InverterInput input) {
        return Map.of(
                "name", input.name(),
                "model", input.model(),
                "version", input.version(),
                "manufacturer", input.manufacturer()
        );
    }

    // Now you can use the inherited test methods or override them
    @Test
    @WithMockUser(roles = "ADMIN")
    void canCreateEntity() {

    }

    @Test
    @WithMockUser(roles = "USER")
    void userCannotCreateEntity() {
        testCreateEntity_Unauthorized("USER");
    }

    // Add entity-specific tests that need custom behavior
    @Test
    @WithMockUser(roles = "ADMIN")
    void canUpdateEntity() {
        testUpdateEntity_AdminSuccess(testEntityId, testEntity);
        assertions(testEntity);
    }
}