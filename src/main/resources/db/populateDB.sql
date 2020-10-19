DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES (TO_TIMESTAMP('2020-01-30 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Завтрак', 500, 100000),
       (TO_TIMESTAMP('2020-01-30 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Обед', 1000, 100000),
       (TO_TIMESTAMP('2020-01-30 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Ужин', 500, 100000),
       (TO_TIMESTAMP('2020-01-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Еда на граничное значение', 100, 100000),
       (TO_TIMESTAMP('2020-01-31 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Завтрак', 1000, 100000),
       (TO_TIMESTAMP('2020-01-31 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Обед', 500, 100000),
       (TO_TIMESTAMP('2020-01-31 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Ужин', 410, 100000),
       (TO_TIMESTAMP('2015-06-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Админ ланч', 510, 100001),
       (TO_TIMESTAMP('2015-06-01 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Админ ужин', 1500, 100001);
