import { MessageStatus } from '../enums/message/MessageStatus.enum';
import { MessageType } from '../enums/message/MessageType.enum';
import { Severity } from '../enums/message/Severity.enum';

export interface Message {
  id: string;
  cloudMessageId: string;
  messageType: MessageType;
  messageText: JSON;
  status: MessageStatus;
  severity: Severity;
  format: string;
  priority: string;
  explanation: string;
  sentAt: string;
  receivedAt: string;
  createdAt: string;
  device: {
    id: string;
    name: string;
    serialNumber: string;
    lastSeen: string;
    fleet: {
      id: string;
      name: string;
    };
  };
}
