export interface PageRequest {
  page: number;
  size: number;
  sortBy: string | null;
  desc: boolean;
}
