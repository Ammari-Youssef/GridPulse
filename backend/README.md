# GridPulse Backend

Spring Boot REST/GraphQL API for the GridPulse IoT monitoring platform.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 16+ (or use Docker)

### Installation & Run
```bash
# Install dependencies & run tests
mvn clean install

# Start application
mvn spring-boot:run

# Access endpoints
# GraphQL API: http://localhost:8080/graphql
# GraphiQL: http://localhost:8080/graphiql
# Actuator Health: http://localhost:8080/actuator/health
```

---

## 🛠️ Tech Stack

- **Spring Boot 3.4.5** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database access
- **GraphQL** - API query language
- **PostgreSQL 16** - Relational database
- **Liquibase** - Database migration management
- **JWT** - Token-based authentication
- **Maven** - Build & dependency management
- **Docker** - Containerization

---

## 📁 Project Structure
```
├───main
│   ├───java
│   │   └───com
│   │       └───youssef
│   │           └───GridPulse
│   │               │   GridPulseApplication.java
│   │               │   
│   │               ├───api
│   │               │       ApiResolver.java
│   │               │       
│   │               ├───common
│   │               │   └───base
│   │               │           BaseEntity.java
│   │               │           BaseHistoryEntity.java
│   │               │           BaseHistoryRepository.java
│   │               │           BaseMapper.java
│   │               │           BaseRepository.java
│   │               │           BaseResolver.java
│   │               │           BaseService.java
│   │               │           Source.java
│   │               │           
│   │               ├───configuration
│   │               │   │   ApplicationConfig.java
│   │               │   │   
│   │               │   ├───audit
│   │               │   │       ApplicationAuditAware.java
│   │               │   │       PersistenceConfig.java
│   │               │   │       
│   │               │   ├───graphql
│   │               │   │   │   GraphQLConfig.java
│   │               │   │   │   GraphQLExceptionHandler.java
│   │               │   │   │   MessagePayloadResolver.java
│   │               │   │   │   
│   │               │   │   └───pagination
│   │               │   │       ├───cursorBased
│   │               │   │       │       Connection.java
│   │               │   │       │       Edge.java
│   │               │   │       │       PageInfo.java
│   │               │   │       │       
│   │               │   │       └───offsetBased
│   │               │   │               PageRequestInput.java
│   │               │   │               PageResponse.java
│   │               │   │               
│   │               │   ├───mapping
│   │               │   │       BaseMappingConfig.java
│   │               │   │       InverterReferenceMapper.java
│   │               │   │       
│   │               │   ├───monitoring
│   │               │   │       GridPulseHealthIndicator.java
│   │               │   │       GridPulseInfoEndpoint.java
│   │               │   │       
│   │               │   ├───mqtt
│   │               │   │       MqttConfig.java
│   │               │   │       StubMqttClient.java
│   │               │   │       
│   │               │   └───security
│   │               │           ActuatorSecurityConfig.java
│   │               │           ApiSecurityConfig.java
│   │               │           AuthenticationCustomEntryPoint.java
│   │               │           JwtAuthFilter.java
│   │               │           JwtProperties.java
│   │               │           JwtService.java
│   │               │           
│   │               ├───domain
│   │               │   ├───bms
│   │               │   │   ├───dto
│   │               │   │   │       BmsInput.java
│   │               │   │   │       
│   │               │   │   ├───entity
│   │               │   │   │       Bms.java
│   │               │   │   │       BmsHistory.java
│   │               │   │   │       
│   │               │   │   ├───enums
│   │               │   │   │       BatteryChemistry.java
│   │               │   │   │       BatteryHealthStatus.java
│   │               │   │   │       
│   │               │   │   ├───mapper
│   │               │   │   │       BmsMapper.java
│   │               │   │   │       
│   │               │   │   ├───repository
│   │               │   │   │       BmsHistoryRepository.java
│   │               │   │   │       BmsRepository.java
│   │               │   │   │       
│   │               │   │   ├───resolver
│   │               │   │   │       BmsResolver.java
│   │               │   │   │       
│   │               │   │   └───service
│   │               │   │           BmsService.java
│   │               │   │           
│   │               │   ├───device
│   │               │   │   ├───dto
│   │               │   │   │       DeviceInput.java
│   │               │   │   │       DeviceStats.java
│   │               │   │   │       
│   │               │   │   ├───entity
│   │               │   │   │       Device.java
│   │               │   │   │       DeviceHistory.java
│   │               │   │   │       
│   │               │   │   ├───enums
│   │               │   │   │       DeviceStatus.java
│   │               │   │   │       
│   │               │   │   ├───mapper
│   │               │   │   │       DeviceMapper.java
│   │               │   │   │       
│   │               │   │   ├───repository
│   │               │   │   │       DeviceHistoryRepository.java
│   │               │   │   │       DeviceRepository.java
│   │               │   │   │       
│   │               │   │   ├───resolver
│   │               │   │   │       DeviceResolver.java
│   │               │   │   │       
│   │               │   │   └───service
│   │               │   │           DeviceService.java
│   │               │   │           
│   │               │   ├───fleet
│   │               │   │   ├───dto
│   │               │   │   │       FleetInput.java
│   │               │   │   │       
│   │               │   │   ├───entity
│   │               │   │   │       Fleet.java
│   │               │   │   │       FleetHistory.java
│   │               │   │   │       
│   │               │   │   ├───mapper
│   │               │   │   │       FleetMapper.java
│   │               │   │   │       
│   │               │   │   ├───repository
│   │               │   │   │       FleetHistoryRepository.java
│   │               │   │   │       FleetRepository.java
│   │               │   │   │       
│   │               │   │   ├───resolver
│   │               │   │   │       FleetResolver.java
│   │               │   │   │       
│   │               │   │   └───service
│   │               │   │           FleetService.java
│   │               │   │           
│   │               │   ├───identity
│   │               │   │   ├───auth
│   │               │   │   │   ├───dto
│   │               │   │   │   │       AuthenticationResponse.java
│   │               │   │   │   │       LoginInput.java
│   │               │   │   │   │       RegisterInput.java
│   │               │   │   │   │       
│   │               │   │   │   ├───resolver
│   │               │   │   │   │       AuthenticationResolver.java
│   │               │   │   │   │       
│   │               │   │   │   └───service
│   │               │   │   │           AuthenticationService.java
│   │               │   │   │           
│   │               │   │   ├───token
│   │               │   │   │       Token.java
│   │               │   │   │       TokenRepository.java
│   │               │   │   │       TokenType.java
│   │               │   │   │       
│   │               │   │   └───user
│   │               │   │       │   Role.java
│   │               │   │       │   
│   │               │   │       ├───dto
│   │               │   │       │       UpdateUserInput.java
│   │               │   │       │       
│   │               │   │       ├───entity
│   │               │   │       │       User.java
│   │               │   │       │       UserHistory.java
│   │               │   │       │       
│   │               │   │       ├───mapper
│   │               │   │       │       UserMapper.java
│   │               │   │       │       
│   │               │   │       ├───repository
│   │               │   │       │       UserHistoryRepository.java
│   │               │   │       │       UserRepository.java
│   │               │   │       │       
│   │               │   │       ├───resolver
│   │               │   │       │       UserResolver.java
│   │               │   │       │       
│   │               │   │       └───service
│   │               │   │               UserService.java
│   │               │   │               
│   │               │   ├───inverter
│   │               │   │   ├───base
│   │               │   │   │       SunSpecModelEntity.java
│   │               │   │   │       SunSpecModelEntityHistory.java
│   │               │   │   │       SunSpecModelHistoryRepository.java
│   │               │   │   │       SunSpecModelInput.java
│   │               │   │   │       SunSpecModelMapper.java
│   │               │   │   │       SunSpecModelRepository.java
│   │               │   │   │       SunSpecModelResolver.java
│   │               │   │   │       SunSpecModelService.java
│   │               │   │   │       
│   │               │   │   ├───common
│   │               │   │   │   ├───dto
│   │               │   │   │   │       InvCommonInput.java
│   │               │   │   │   │       
│   │               │   │   │   ├───entity
│   │               │   │   │   │       InvCommon.java
│   │               │   │   │   │       InvCommonHistory.java
│   │               │   │   │   │       
│   │               │   │   │   ├───mapper
│   │               │   │   │   │       InvCommonMapper.java
│   │               │   │   │   │       
│   │               │   │   │   ├───repository
│   │               │   │   │   │       InvCommonHistoryRepository.java
│   │               │   │   │   │       InvCommonRepository.java
│   │               │   │   │   │       
│   │               │   │   │   ├───resolver
│   │               │   │   │   │       InvCommonResolver.java
│   │               │   │   │   │       
│   │               │   │   │   └───service
│   │               │   │   │           InvCommonService.java
│   │               │   │   │           
│   │               │   │   ├───inverter
│   │               │   │   │   ├───dto
│   │               │   │   │   │       InverterInput.java
│   │               │   │   │   │       
│   │               │   │   │   ├───entity
│   │               │   │   │   │       Inverter.java
│   │               │   │   │   │       InverterHistory.java
│   │               │   │   │   │       
│   │               │   │   │   ├───mapper
│   │               │   │   │   │       InverterMapper.java
│   │               │   │   │   │       
│   │               │   │   │   ├───repository
│   │               │   │   │   │       InverterHistoryRepository.java
│   │               │   │   │   │       InverterRepository.java
│   │               │   │   │   │       
│   │               │   │   │   ├───resolver
│   │               │   │   │   │       InverterResolver.java
│   │               │   │   │   │       
│   │               │   │   │   └───service
│   │               │   │   │           InverterService.java
│   │               │   │   │           
│   │               │   │   ├───nameplate
│   │               │   │   │   ├───dto
│   │               │   │   │   │       InvNameplateInput.java
│   │               │   │   │   │       
│   │               │   │   │   ├───entity
│   │               │   │   │   │       InvNameplate.java
│   │               │   │   │   │       InvNameplateHistory.java
│   │               │   │   │   │       
│   │               │   │   │   ├───enums
│   │               │   │   │   │       DerType.java
│   │               │   │   │   │       
│   │               │   │   │   ├───mapper
│   │               │   │   │   │       InvNameplateMapper.java
│   │               │   │   │   │       
│   │               │   │   │   ├───repository
│   │               │   │   │   │       InvNameplateHistoryRepository.java
│   │               │   │   │   │       InvNameplateRepository.java
│   │               │   │   │   │       
│   │               │   │   │   ├───resolver
│   │               │   │   │   │       InvNameplateResolver.java
│   │               │   │   │   │       
│   │               │   │   │   └───service
│   │               │   │   │           InvNameplateService.java
│   │               │   │   │           
│   │               │   │   └───settings
│   │               │   │       ├───dto
│   │               │   │       │       InvSettingsInput.java
│   │               │   │       │       
│   │               │   │       ├───entity
│   │               │   │       │       InvSettings.java
│   │               │   │       │       InvSettingsHistory.java
│   │               │   │       │       
│   │               │   │       ├───enums
│   │               │   │       │       ClcTotVaMethod.java
│   │               │   │       │       ConnPhase.java
│   │               │   │       │       VarAction.java
│   │               │   │       │       
│   │               │   │       ├───mapper
│   │               │   │       │       InvSettingsMapper.java
│   │               │   │       │       
│   │               │   │       ├───repository
│   │               │   │       │       InvSettingsHistoryRepository.java
│   │               │   │       │       InvSettingsRepository.java
│   │               │   │       │       
│   │               │   │       ├───resolver
│   │               │   │       │       InvSettingsResolver.java
│   │               │   │       │       
│   │               │   │       └───service
│   │               │   │               InvSettingsService.java
│   │               │   │               
│   │               │   ├───message
│   │               │   │   ├───dto
│   │               │   │   │       DevicePayload.java
│   │               │   │   │       MessageInput.java
│   │               │   │   │       UpdateMessageInput.java
│   │               │   │   │       
│   │               │   │   ├───entity
│   │               │   │   │       Message.java
│   │               │   │   │       MessageHistory.java
│   │               │   │   │       
│   │               │   │   ├───enums
│   │               │   │   │       MessageFormat.java
│   │               │   │   │       MessagePriority.java
│   │               │   │   │       MessageStatus.java
│   │               │   │   │       MessageType.java
│   │               │   │   │       Severity.java
│   │               │   │   │       
│   │               │   │   ├───mapper
│   │               │   │   │       MessageMapper.java
│   │               │   │   │       
│   │               │   │   ├───parser
│   │               │   │   │       MessagePayloadParser.java
│   │               │   │   │       
│   │               │   │   ├───payload
│   │               │   │   │   │   BmsPayload.java
│   │               │   │   │   │   HeartbeatPayload.java
│   │               │   │   │   │   IdsPayload.java
│   │               │   │   │   │   InverterPayload.java
│   │               │   │   │   │   MeterPayload.java
│   │               │   │   │   │   SoftwarePayload.java
│   │               │   │   │   │   SystemPayload.java
│   │               │   │   │   │   
│   │               │   │   │   └───enums
│   │               │   │   │           AttackType.java
│   │               │   │   │           SoftwareMessageUpdateStatus.java
│   │               │   │   │           SoftwarePackageType.java
│   │               │   │   │           
│   │               │   │   ├───repository
│   │               │   │   │       MessageHistoryRepository.java
│   │               │   │   │       MessageRepository.java
│   │               │   │   │       
│   │               │   │   ├───resolver
│   │               │   │   │       MessageResolver.java
│   │               │   │   │       
│   │               │   │   ├───service
│   │               │   │   │       MessageService.java
│   │               │   │   │       
│   │               │   │   └───util
│   │               │   │           SeverityInterpreter.java
│   │               │   │           
│   │               │   ├───meter
│   │               │   │   ├───dto
│   │               │   │   │       MeterInput.java
│   │               │   │   │       
│   │               │   │   ├───entity
│   │               │   │   │       Meter.java
│   │               │   │   │       MeterHistory.java
│   │               │   │   │       
│   │               │   │   ├───mapper
│   │               │   │   │       MeterMapper.java
│   │               │   │   │       
│   │               │   │   ├───repository
│   │               │   │   │       MeterHistoryRepository.java
│   │               │   │   │       MeterRepository.java
│   │               │   │   │       
│   │               │   │   ├───resolver
│   │               │   │   │       MeterResolver.java
│   │               │   │   │       
│   │               │   │   └───service
│   │               │   │           MeterService.java
│   │               │   │           
│   │               │   └───security
│   │               │       ├───dto
│   │               │       │       ImportSecurityKeyInput.java
│   │               │       │       SecurityKeyInput.java
│   │               │       │       
│   │               │       ├───entity
│   │               │       │       SecurityKey.java
│   │               │       │       SecurityKeyHistory.java
│   │               │       │       
│   │               │       ├───enums
│   │               │       │       KeySource.java
│   │               │       │       KeyStatus.java
│   │               │       │       SecurityType.java
│   │               │       │       
│   │               │       ├───mapper
│   │               │       │       SecurityKeyMapper.java
│   │               │       │       
│   │               │       ├───repository
│   │               │       │       SecurityKeyHistoryRepository.java
│   │               │       │       SecurityKeyRepository.java
│   │               │       │       
│   │               │       ├───resolver
│   │               │       │       SecurityKeyResolver.java
│   │               │       │       
│   │               │       └───service
│   │               │               EncryptionService.java
│   │               │               SecurityKeyService.java
│   │               │               
│   │               └───seeder
│   │                   │   DatabaseSeeder.java
│   │                   │   
│   │                   ├───config
│   │                   │       SeedingConfig.java
│   │                   │       SeedingProperties.java
│   │                   │       
│   │                   ├───faker
│   │                   │       BmsFaker.java
│   │                   │       DeviceFaker.java
│   │                   │       FleetFaker.java
│   │                   │       InvCommonFaker.java
│   │                   │       InverterFaker.java
│   │                   │       InvNameplateFaker.java
│   │                   │       InvSettingsFaker.java
│   │                   │       MessageFaker.java
│   │                   │       MeterFaker.java
│   │                   │       SecurityKeyFaker.java
│   │                   │       UserFaker.java
│   │                   │       
│   │                   ├───modules
│   │                   │       BmsSeeder.java
│   │                   │       DeviceSeeder.java
│   │                   │       FleetSeeder.java
│   │                   │       InverterChildrenSeeder.java
│   │                   │       InverterSeeder.java
│   │                   │       MessageSeeder.java
│   │                   │       MeterSeeder.java
│   │                   │       SecurityKeySeeder.java
│   │                   │       UserSeeder.java
│   │                   │       
│   │                   └───util
│   │                           ResourcePool.java
│   │                           
│   └───resources
│       │   application-dev.yml
│       │   application-docker.yml
│       │   application-prod.yml
│       │   application-seed.yml
│       │   application.yml
│       │   liquibase.properties.template
│       │   logback-spring.xml
│       │   
│       ├───db
│       │   └───changelog
│       │       │   db.changelog-master.xml
│       │       │   
│       │       ├───domain
│       │       │       v2.0__create-bms-changelog.xml
│       │       │       v2.0__create-device-changelog.xml
│       │       │       v2.0__create-fleet-changelog.xml
│       │       │       v2.0__create-inv-common-changelog.xml
│       │       │       v2.0__create-inv-nameplate-changelog.xml
│       │       │       v2.0__create-inv-settings-changelog.xml
│       │       │       v2.0__create-inverter-changelog.xml
│       │       │       v2.0__create-message-changelog.xml
│       │       │       v2.0__create-meter-changelog.xml
│       │       │       v2.0__create-security-keys-changelog.xml
│       │       │       v2.0__create-token-changelog.xml
│       │       │       v2.0__create-user-changelog.xml
│       │       │       
│       │       └───system
│       │               v2.0__seed-baseline-entities.xml
│       │               
│       ├───graphiql
│       │       index.html
│       │       
│       └───graphql
│           │   README.md
│           │   schema.graphqls
│           │   
│           ├───api
│           │       api.graphqls
│           │       
│           ├───auth
│           │   ├───input
│           │   │       login_input.graphqls
│           │   │       register_input.graphqls
│           │   │       
│           │   ├───schema
│           │   │       mutations.graphqls
│           │   │       
│           │   └───types
│           │           authentication_response.graphqls
│           │           
│           ├───domain
│           │   ├───bms
│           │   │   ├───enum
│           │   │   │       battery_chemistery.graphqls
│           │   │   │       battery_health_status.graphqls
│           │   │   │       
│           │   │   ├───input
│           │   │   │       bms_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_bms.graphqls
│           │   │   │           page_response_bms_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           bms.graphqls
│           │   │           bms_history.graphqls
│           │   │           bms_page.graphqls
│           │   │           
│           │   ├───device
│           │   │   ├───enum
│           │   │   │       device_status.graphqls
│           │   │   │       
│           │   │   ├───input
│           │   │   │       device_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_device.graphqls
│           │   │   │           page_response_device_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           device.graphqls
│           │   │           device_history.graphqls
│           │   │           device_page.graphqls
│           │   │           device_stats.graphqls
│           │   │           
│           │   ├───fleet
│           │   │   ├───input
│           │   │   │       fleet_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_fleet.graphqls
│           │   │   │           page_response_fleet_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           fleet.graphqls
│           │   │           fleet_history.graphqls
│           │   │           fleet_page.graphqls
│           │   │           
│           │   ├───inverter
│           │   │   ├───input
│           │   │   │       inverter_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_inverter.graphqls
│           │   │   │           page_response_inverter_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           inverter.graphqls
│           │   │           inverter_history.graphqls
│           │   │           
│           │   ├───inv_common
│           │   │   ├───input
│           │   │   │       inv_common_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_inv_common.graphqls
│           │   │   │           page_response_inv_common_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           inv_common.graphqls
│           │   │           inv_common_history.graphqls
│           │   │           
│           │   ├───inv_nameplate
│           │   │   ├───input
│           │   │   │       inv_nameplate_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_inv_nameplate.graphqls
│           │   │   │           page_response_inv_nameplate_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           inv_nameplate.graphqls
│           │   │           inv_nameplate_history.graphqls
│           │   │           inv_nameplate_page.graphqls
│           │   │           
│           │   ├───inv_settings
│           │   │   ├───enum
│           │   │   │       clc_tot_va.graphqls
│           │   │   │       conn_phase.graphqls
│           │   │   │       var_action.graphqls
│           │   │   │       
│           │   │   ├───input
│           │   │   │       inv_settings_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_inv_settings.graphqls
│           │   │   │           page_response_inv_settings_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           inv_settings.graphqls
│           │   │           inv_settings_history.graphqls
│           │   │           inv_settings_page.graphqls
│           │   │           
│           │   ├───message
│           │   │   ├───enum
│           │   │   │       message_format.graphqls
│           │   │   │       message_priority.graphqls
│           │   │   │       message_status.graphqls
│           │   │   │       message_type.graphqls
│           │   │   │       severity.graphqls
│           │   │   │       
│           │   │   ├───input
│           │   │   │       message_input.graphqls
│           │   │   │       update_message_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_message.graphqls
│           │   │   │           page_response_message_history.graphqls
│           │   │   │           
│           │   │   ├───payloads
│           │   │   │   │   bms_payload.graphqls
│           │   │   │   │   heartbeat_payload.graphqls
│           │   │   │   │   ids_payload.graphqls
│           │   │   │   │   inverter_payloads.graphqls
│           │   │   │   │   message_payload_union.graphqls
│           │   │   │   │   meter_payloads.graphqls
│           │   │   │   │   software_payload.graphqls
│           │   │   │   │   system_payloads.graphqls
│           │   │   │   │   
│           │   │   │   └───enum
│           │   │   │           attack_type.graphqls
│           │   │   │           software_message_update_status.graphqls
│           │   │   │           software_package.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           message.graphqls
│           │   │           message_history.graphqls
│           │   │           message_page.graphqls
│           │   │           
│           │   ├───meter
│           │   │   ├───input
│           │   │   │       meter_input.graphqls
│           │   │   │       
│           │   │   ├───pagination
│           │   │   │   └───offset
│           │   │   │           page_response_meter.graphqls
│           │   │   │           page_response_meter_history.graphqls
│           │   │   │           
│           │   │   ├───schema
│           │   │   │       mutations.graphqls
│           │   │   │       queries.graphqls
│           │   │   │       
│           │   │   └───types
│           │   │           meter.graphqls
│           │   │           meter_history.graphqls
│           │   │           meter_page.graphqls
│           │   │           
│           │   └───security_key
│           │       ├───enum
│           │       │       security_key_enums.graphqls
│           │       │       
│           │       ├───input
│           │       │       import_security_key_input.graphqls
│           │       │       security_key_input.graphqls
│           │       │       
│           │       ├───pagination
│           │       │   └───offset
│           │       │           page_response_security_key.graphqls
│           │       │           page_response_security_key_history.graphqls
│           │       │           
│           │       ├───schema
│           │       │       mutations.graphqls
│           │       │       queries.graphqls
│           │       │       
│           │       └───types
│           │               security_key.graphqls
│           │               security_key_history.graphqls
│           │               security_key_page.graphqls
│           │               
│           ├───interfaces
│           │       auditable.graphqls
│           │       enums.graphqls
│           │       scalars.graphqls
│           │       sunspec.graphqls
│           │       
│           ├───pagination
│           │       page_request_input.graphqls
│           │       
│           ├───scripts
│           └───user
│               ├───enum
│               │       role.graphqls
│               │       
│               ├───input
│               │       user_input.graphqls
│               │       
│               ├───pagination
│               │   └───offset
│               │           page_response_user.graphqls
│               │           page_response_user_history.graphqls
│               │           
│               ├───schema
│               │       mutations.graphqls
│               │       queries.graphqls
│               │       
│               └───types
│                       user.graphqls
│                       user_history.graphqls
│                       
└───test
    ├───java
    │   └───com
    │       └───youssef
    │           └───GridPulse
    │               │   GridPulseApplicationTests.java
    │               │   
    │               ├───domain
    │               │   ├───base
    │               │   │       BaseHistoryRepositoryTest.java
    │               │   │       BaseMapperTest.java
    │               │   │       BaseResolverTest.java
    │               │   │       BaseServiceTest.java
    │               │   │       
    │               │   ├───identity
    │               │   │   ├───auth
    │               │   │   │   ├───resolver
    │               │   │   │   │       AuthenticationResolverTest.java
    │               │   │   │   │       
    │               │   │   │   └───service
    │               │   │   │           AuthenticationServiceTest.java
    │               │   │   │           
    │               │   │   └───user
    │               │   │       ├───mapper
    │               │   │       │       UserMapperTest.java
    │               │   │       │       
    │               │   │       ├───repository
    │               │   │       │       UserHistoryRepositoryTest.java
    │               │   │       │       UserRepositoryTest.java
    │               │   │       │       
    │               │   │       ├───resolver
    │               │   │       │       UserResolverTest.java
    │               │   │       │       
    │               │   │       └───service
    │               │   │               UserServiceTest.java
    │               │   │               
    │               │   ├───inverter
    │               │   │   ├───mapper
    │               │   │   │       InverterMapperTest.java
    │               │   │   │       InverterMapperTests.java
    │               │   │   │       
    │               │   │   ├───repository
    │               │   │   │       InverterHistoryRepositoryTest.java
    │               │   │   │       InverterRepositoryTest.java
    │               │   │   │       
    │               │   │   ├───resolver
    │               │   │   │       InverterResolverTest.java
    │               │   │   │       InverterResolverTests.java
    │               │   │   │       
    │               │   │   └───service
    │               │   │           InverterServiceTest.java
    │               │   │           
    │               │   └───liquibase
    │               │           LiquibaseMigrationTest.java
    │               │           
    │               └───utils
    │                       TestLogger.java
    │                       TestSuiteUtils.java
    │                       
    └───resources
        │   application-test.properties
        │   application.properties
        │   
        └───graphql-test
            ├───mutations
            │   ├───auth
            │   │       createUserWithRole.graphql
            │   │       login.graphql
            │   │       logout.graphql
            │   │       refreshToken.graphql
            │   │       register.graphql
            │   │       
            │   ├───inverter
            │   │       create.graphql
            │   │       deleteById.graphql
            │   │       markHistorySynced.graphql
            │   │       update.graphql
            │   │       
            │   └───user
            │           deleteUserById.graphql
            │           markUserHistorySynced.graphql
            │           toggleUserEnableStatus.graphql
            │           updateUser.graphql
            │           
            └───queries
                ├───inverter
                │       getAll.graphql
                │       getAllHistory.graphql
                │       getById.graphql
                │       getHistoryById.graphql
                │       getHistoryByOriginalId.graphql
                │       
                └───user
                        getAllUsers.graphql
                        getCurrentUser.graphql
                        getUserById.graphql
                        getUsersActivityHistory.graphql
```

---

## 🔨 Available Commands

| Command | Description |
|---------|-------------|
| `mvn spring-boot:run` | Start application |
| `mvn clean install` | Build & run all tests |
| `mvn test` | Run unit tests only |
| `mvn verify` | Run tests + integration tests |
| `mvn clean package` | Build JAR (skip tests) |
| `mvn clean package -DskipTests` | Build JAR (skip tests) |
| `mvn liquibase:update` | Apply database migrations |
| `mvn liquibase:rollback` | Rollback last migration |

---

## 🏗️ Building

### Development Build
```bash
mvn clean install
```

### Production JAR
```bash
mvn clean package -DskipTests
```
Output: `target/gridpulse-backend-*.jar`

### Run JAR
```bash
java -jar target/gridpulse-backend-*.jar
```

---

## 🧪 Testing

### Unit Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run specific test method
mvn test -Dtest=UserServiceTest#testCreateUser
```

### Integration Tests
```bash
# Run all tests including integration tests
mvn verify
```

**Test configuration:**
- Framework: JUnit 5
- Mocking: Mockito
- Integration: Testcontainers (PostgreSQL)

### Code Coverage
```bash
# Generate coverage report
mvn clean verify

# View report
open target/site/jacoco/index.html
```

**Coverage tool:** JaCoCo

---

## 🗄️ Database

### Local PostgreSQL (Docker)
```bash
docker run -d \
  --name gridpulse-db \
  -e POSTGRES_DB=gridpulse \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=root \
  -p 5432:5432 \
  postgres:16
```

### Connect to Database
```bash
# Using psql
psql -h localhost -U postgres -d gridpulse

# List tables
\dt

# View users
SELECT * FROM _user;
```

---

## 🔄 Database Migrations (Liquibase)

### How It Works
1. On startup, Spring Boot runs Liquibase
2. Liquibase checks `databasechangelog` table
3. Applies pending changesets from `db/changelog/`
4. Records applied migrations

### Migration Structure
```
resources/db/changelog/
├── db.changelog-master.xml         # Master changelog
├── v1.0/
│   ├── 01-create-users-table.xml
│   ├── 02-create-devices-table.xml
│   └── 03-create-bms-table.xml
└── seed/
    └── 01-seed-demo-data.xml       # Demo user, devices
```

### Create New Migration
```bash
# 1. Create XML file
touch src/main/resources/db/changelog/v2.0/01-add-new-feature.xml
```
```xml
<!-- 2. Add changeset -->
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog">
    <changeSet id="add-new-column" author="yourname">
        <addColumn tableName="device">
            <column name="new_field" type="VARCHAR(255)"/>
        </addColumn>
    </changeSet>
</databaseChangeLog>
```
```xml
<!-- 3. Include in master changelog -->
<include file="db/changelog/v2.0/01-add-new-feature.xml"/>
```

### Useful Liquibase Commands
```bash
# Apply pending migrations
mvn liquibase:update

# Rollback last migration
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# Generate SQL (dry run)
mvn liquibase:updateSQL

# Clear checksums (if migration files changed)
mvn liquibase:clearCheckSums
```

---

## 🔐 Security & Authentication

### JWT Authentication Flow
1. User sends credentials to `/graphql` (login mutation)
2. Backend validates credentials
3. Backend generates JWT access token + refresh token
4. Frontend stores tokens in memory (not localStorage)
5. All subsequent requests include JWT in `Authorization` header
6. `JwtAuthFilter` validates token on each request
7. Refresh token used to get new access token when expired

### Default Users (Seeded by Liquibase)
```
Admin User:
Email: demo@gridpulse.io
Password: demo123
Role: ADMIN

Regular User:
Email: youssef@gridpulse.io
Password: (bcrypt hash in seed data)
Role: ADMIN
```

### Endpoints Security
- **Public:** `/graphql`, `/graphiql` (authentication mutations)
- **Protected:** All other GraphQL queries/mutations
- **Admin only:** User management, system settings

---

## 🌍 Environment Configuration

### Development (`application-dev.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gridpulse
    username: postgres
    password: root
```

### Production (`application-prod.yml`)
```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASS}
```

**Activate profile:**
```bash
# Via command line
java -jar app.jar --spring.profiles.active=prod

# Via environment variable
export SPRING_PROFILES_ACTIVE=prod
```

---

## 📊 GraphQL API

### GraphiQL Interface
Open browser: `http://localhost:8080/graphiql`

### Sample Queries

**Login:**
```graphql
mutation Login {
  login(email: "demo@gridpulse.io", password: "demo123") {
    accessToken
    refreshToken
    user {
      id
      email
      firstname
      lastname
      role
    }
  }
}
```

**Get Devices:**
```graphql
query GetDevices {
  devices {
    id
    name
    status
    serialNumber
    bms {
      soc
      soh
      temperature
    }
  }
}
```

**Create Device:**
```graphql
mutation CreateDevice($input: DeviceInput!) {
  createDevice(input: $input) {
    id
    name
    status
  }
}
```

---

## 📦 Key Dependencies

### Spring Framework
- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - Database access
- `spring-boot-starter-security` - Security
- `spring-boot-starter-actuator` - Monitoring

### GraphQL
- `spring-boot-starter-graphql` - GraphQL support

### Database
- `postgresql` - PostgreSQL driver
- `liquibase-core` - Database migrations

### Security
- `jjwt-api` - JWT token handling
- `jjwt-impl` - JWT implementation
- `jjwt-jackson` - JWT JSON processing

### Testing
- `spring-boot-starter-test` - Testing framework
- `testcontainers` - Integration testing
- `mockito` - Mocking

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Kill process on port 8080 (Linux/Mac)
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Database Connection Errors
1. Verify PostgreSQL is running
2. Check credentials in `application.yml`
3. Ensure database `gridpulse` exists
4. Check firewall settings

### Liquibase Errors
```bash
# Clear checksums
mvn liquibase:clearCheckSums

# Drop all tables and reapply
mvn liquibase:dropAll
mvn liquibase:update
```

### Build Errors
```bash
# Clean Maven cache
mvn clean

# Update dependencies
mvn clean install -U
```

---

## 🚀 Deployment

See [docs/DEPLOYMENT.md](../docs/DEPLOYMENT.md) for Railway deployment instructions.

**Docker build:**
```bash
# Build image
docker build -t gridpulse-backend .

# Run container
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=jdbc:postgresql://host:5432/db \
  gridpulse-backend
```

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring GraphQL](https://spring.io/projects/spring-graphql)
- [Liquibase Documentation](https://docs.liquibase.com)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 👤 Author

**Youssef Ammari**
- GitHub: [@Ammari-Youssef](https://github.com/Ammari-Youssef)

---

**Last Updated:** January 2026