insert into role (id, name)
values (1, 'CLIENT'),
       (2, 'DIETITIAN');

-- username: dietitian123, password: dietitian123
-- username: client123, password: client123
insert into "user" (id, username, password, role_id)
values (1, 'dietitian123', '$2a$10$yy.ltCDSA2L1.3KAwmVeAeFxrZwxNf2sfLY3Kue/8bPJ4P9gpk5iG', 2),
       (2, 'client123', '$2a$10$S91gR8EWTTINPl2dHt/VkOhFWAHZ9jDlI5cJG9tAGc7oaRJJwIIcm', 1);


insert into user_info (user_id, first_name, last_name, email, phone)
values (1, 'Dietitian', 'Dietitian', 'dietitian@email.com', '1234567890'),
       (2, 'Client', 'Client', 'client@email.com', '0987654321');