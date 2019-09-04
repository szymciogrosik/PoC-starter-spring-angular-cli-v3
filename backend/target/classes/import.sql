-- insert --
insert into "Role" ("id", "name") values ( 1, 'ROLE_ADMIN');
insert into "Role" ("id", "name") values ( 2, 'ROLE_USER');

insert into "User" ("id", "username", "password", "firstName", "lastName", "email", "accountCreationDate", "lastModificationDate", "banned") values ( 1, 'admin', '$2a$10$O6p/ftbC7LbrhpZsIFk9SegNDSk62BXq2ctKF9kqMVxCGYDwQt8n6', 'Andrzej', 'Abacki', 'a.abacki@gmail.com', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false);
insert into "User" ("id", "username", "password", "firstName", "lastName", "email", "accountCreationDate", "lastModificationDate", "banned") values ( 2, 'user', '$2a$10$O6p/ftbC7LbrhpZsIFk9SegNDSk62BXq2ctKF9kqMVxCGYDwQt8n6', 'Bogdan', 'Babacki', 'b.babackii@gmail.com', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false);

insert into "User_Role" ("idUser", "idRole") values ( 1, 1);
insert into "User_Role" ("idUser", "idRole") values ( 1, 2);
insert into "User_Role" ("idUser", "idRole") values ( 2, 2);