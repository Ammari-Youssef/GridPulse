import { MessageStatus } from '@models/enums/message/MessageStatus.enum';
import { MessageType } from '@models/enums/message/MessageType.enum';
import { Severity } from '@models/enums/message/Severity.enum';

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
