-- DIETITIAN -> username: aliceWhite95, password: aliceWhite95
-- CLIENT -> username: johnBrown90, password: johnBrown90
insert into "user" (username, password, enabled, role)
values ('aliceWhite95', '$2a$10$6cZ5L3DqviiLHWV.8ci8leDRKd9nk1nv0xAz/B.Qm2/E6.uQEmaHO', true, 'DIETITIAN'),
       ('johnBrown90', '$2a$10$d/./JcM59U/3vh4eDy/.9uRb7zA.tJmD267s17BJufVjXN0ZfkKDa', true, 'CLIENT');

insert into user_info (user_id, first_name, last_name, gender, date_of_birth, email, phone)
values (1, 'Alice', 'White', 'FEMALE', '1995-06-16', 'aliceWhite95@email.com', '1234567890'),
       (2, 'John', 'Brown', 'MALE', '1990-01-11', 'johnbrown90@email.com', '0987654321');

insert into tag_category (name)
values ('General Dietary Needs'),
       ('Digestive Conditions'),
       ('Eating Disorders'),
       ('Other Conditions'),
       ('Lifestyle Factors');

insert into tag (tag_category_id, name)
values (1, 'Food allergies'),
       (1, 'Food intolerances'),
       (1, 'Vegetarian'),
       (1, 'Vegan'),
       (1, 'Renal (kidney) disease'),
       (1, 'Liver disease'),
       (1, 'Cardiovascular disease'),
       (1, 'Hypertension'),
       (1, 'Hyperlipidemia'),
       (1, 'Metabolic syndrome');

insert into tag (tag_category_id, name)
values (2, 'Celiac disease'),
       (2, 'Irritable bowel disease'),
       (2, 'Inflammatory bowel disease'),
       (2, 'Ulcerative colitis'),
       (2, 'Crohn''s disease'),
       (2, 'Gastroesophageal reflux disease');

insert into tag (tag_category_id, name)
values (3, 'Bulimia nervosa'),
       (3, 'Anorexia nervosa'),
       (3, 'Binge eating disorder');

insert into tag (tag_category_id, name)
values (4, 'Cancer'),
       (4, 'Autoimmune diseases'),
       (4, 'Polycystic ovary syndrome'),
       (4, 'Pregnancy and lactation'),
       (4, 'Sports nutrition');

insert into tag (tag_category_id, name)
values (5, 'Weight loss'),
       (5, 'Weight management'),
       (5, 'Muscle building'),
       (5, 'Healthy aging');

insert into client_tag_association (user_info_id, tag_id)
values (2, 24),
       (2, 27),
       (2, 1);