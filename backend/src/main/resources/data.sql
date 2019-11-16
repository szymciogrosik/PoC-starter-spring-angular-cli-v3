-- INSERT ROLES
INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');

-- INSERT USERS
INSERT INTO users (id, first_name, last_name, email, password, last_modification_date, creation_date)
VALUES (1, 'Andrzej', 'Abacki', 'admin@gmail.com', '$2a$10$uCDnmQ7VTqXWKym5TCcsr.upvC67eO2SMWzLVa/.ag9OqKw.w/p4S', '2019-09-16 20:00:00', '2019-09-16 20:00:00');

-- INSERT USER_ROLES
INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);