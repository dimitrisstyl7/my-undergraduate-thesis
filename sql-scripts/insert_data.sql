insert into role (name)
values ('CLIENT'),
       ('DIETITIAN');

-- DIETITIAN -> username: aliceWhite95, password: aliceWhite95
-- CLIENT -> username: johnBrown90, password: johnBrown90
insert into "user" (username, password, enabled, role_id)
values ('aliceWhite95', '$2a$10$6cZ5L3DqviiLHWV.8ci8leDRKd9nk1nv0xAz/B.Qm2/E6.uQEmaHO', true, 2),
       ('johnBrown90', '$2a$10$d/./JcM59U/3vh4eDy/.9uRb7zA.tJmD267s17BJufVjXN0ZfkKDa', true, 1);

insert into user_info (user_id, first_name, last_name, gender, date_of_birth, email, phone)
values (1, 'Alice', 'White', 'F', '1995-06-16', 'aliceWhite95@email.com', '1234567890'),
       (2, 'John', 'Brown', 'M', '1990-01-11', 'johnbrown90@email.com', '0987654321');