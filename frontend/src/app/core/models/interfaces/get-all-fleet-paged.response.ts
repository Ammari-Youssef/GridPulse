import { PagedResponse } from "@graphql/pagination/page.response";
import { Fleet } from "@core/models/classes/fleet.model";

export interface GetAllFleetPagedResponse {
  getAllFleetPaged: PagedResponse<Fleet>;
}