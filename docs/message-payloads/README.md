# Message Payload Templates

This folder contains raw JSON payloads for each `MessageType` used in the `rawPayloadJson` field of the GraphQL API. Each payload is crafted to trigger a specific `Severity` level based on backend logic.

## 📦 Folder Structure

Folders are named using the enum ordinal and type name for clarity and ordering:

| Enum Value | Message Type | Folder Name     |
|------------|--------------|-----------------|
| 0          | IDS          | `0-ids/`        |
| 1          | HEARTBEAT    | `1-heartbeat/`  |
| 2          | SYSTEM       | `2-system/`     |
| 3          | SOFTWARE     | `3-software/`   |
| 4          | BMS          | `4-bms/`        |
| 5          | METER        | `5-meter/`      |
| 6          | INVERTER     | `6-inverter/`   |

Each folder contains one or more JSON files named by severity level:
- `critical.json`
- `medium.json`
- `low.json`
- `info.json`

## 🎯 Severity Mapping

Each payload is designed to trigger a specific severity level based on the backend logic in:
```
SeverityInterpreter.calculate(MessageType type, Object payload);
```
## 🧪 Usage
To simulate a message via GraphQL:

Open the desired JSON file.

Copy its contents as-is (raw JSON).

Paste it into the messageText field of your MessageInput.

✅ No need to base64-encode the payload manually — the backend handles encoding and parsing internally.

Example GraphQL Mutation
````graphql
mutation {
  createMessage(input: {
    deviceId: "your-device-id",
    cloudMessageId: "msg-123",
    messageText: "{ \"cpuUsage\": 95, \"memoryUsage\": 92, \"diskUsage\": 98, \"temperature\": 70 }",
    messageType: SYSTEM,
    format: HEX,
    status: NEW,
    priority: HIGH,
    sentAt: "2025-10-21T10:00:00Z",
    receivedAt: "2025-10-21T10:01:00Z"
  }) {
    id
    severity
    explanation
  }
}
````
## 🔁 External Ingestion Strategy
For external systems (e.g. MQTT, file-based), use:

````java
Message.fromDevicePayload(Device device, String rawPayload)
````
This method expects a pipe-delimited string with a base64-encoded payload and handles decoding and parsing internally.
