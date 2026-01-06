import { PagedResponse } from "@graphql/pagination/page.response";
import { Message } from "@models/classes/message.model";

export interface GetAllMessagesPagedResponse {
  getAllMessagePaged: PagedResponse<Message>;
}