import { Fleet } from "@core/models/classes/fleet.model";

export interface PagedResponse {
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
  content: Fleet[];
}