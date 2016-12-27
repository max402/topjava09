DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100002, 100000, '2015-05-30 10:00:00.000000', 'Завтрак', 500);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100003, 100000, '2015-05-30 13:00:00.000000', 'Обед', 1000);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100004, 100000, '2015-05-30 20:00:00.000000', 'Ужин', 500);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100005, 100000, '2015-05-31 10:00:00.000000', 'Завтрак', 500);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100006, 100000, '2015-05-31 13:00:00.000000', 'Обед', 1000);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100008, 100001, '2015-06-01 14:00:00.000000', 'Админ ланч', 510);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100009, 100001, '2015-06-01 21:00:00.000000', 'Админ ужин', 1500);
INSERT INTO meals (id, user_id, date_time, description, calories) VALUES (100007, 100000, '2015-05-31 20:00:00.000000', 'Ужин', 511);