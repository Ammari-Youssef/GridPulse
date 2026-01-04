import { PagedResponse } from "@graphql/pagination/page.response";
import { Device } from "@core/models/classes/device.model";

export interface GetAllDevicePagedResponse {
  getAllDevicePaged: PagedResponse<Device>;
}