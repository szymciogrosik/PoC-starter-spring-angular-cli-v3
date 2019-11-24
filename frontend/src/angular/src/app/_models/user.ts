import {Role} from './role';

export class User {
    id: number;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    creationDate: Date;
    lastModificationDate: Date;
    roles: Role[];
    token: string;
}
