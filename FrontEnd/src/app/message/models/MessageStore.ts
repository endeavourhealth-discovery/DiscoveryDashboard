export class MessageStore {
  id: number;
  source: number;
  receivedDateTime: Date;
  status: number;
  sentDateTime: string;
  messagePayload: string;
  errorMessage: string;
}
