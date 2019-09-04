import {Role} from './role';

export class User {
    userId: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    banned: boolean;
    accountCreation: Date;
    lastModification: Date;
    token: string;
    roles: Role[];
}
