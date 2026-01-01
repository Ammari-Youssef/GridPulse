import { Role } from "@models/enums/role.enum";

export interface User {
    id: string;
    firstname: string;
    lastname: string;
    email: string;
    role: Role
}