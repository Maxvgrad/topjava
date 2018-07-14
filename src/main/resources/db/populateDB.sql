DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

--   id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--   name             VARCHAR                 NOT NULL,
--   email            VARCHAR                 NOT NULL,
--   password         VARCHAR                 NOT NULL,
--   reg_date         TIMESTAMP DEFAULT now() NOT NULL,
--   enabled          BOOL DEFAULT TRUE       NOT NULL,
--   calories_per_day INTEGER DEFAULT 2000    NOT NULL


INSERT INTO users (name, email, password) VALUES
  ('Max', 'user@yandex.ru', 'qwerty'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('USER', 100000),
  ('ADMIN', 100001);

INSERT INTO meals (user_id, description, meal_date, calories) VALUES
  (100000, 'pork', '12.07.2018 14:33:00', 1700),
  (100000, 'pancakes', '11.06.2018 09:12:00', 1500),
  (100000, 'banana', '01.01.2018 07:40:00', 150),
  (100000, 'milkshake', '24.11.2017 18:05:00', 500),
  (100000, 'breakfast', '24.11.2017 09:00:00', 777),
  (100001, 'admin brunch', '20.11.2017 19:00:00', 2002),
  (100001, 'berry', '20.11.2017 08:00:00', 180),
  (100001, 'snikers', '20.11.2017 14:00:00', 800);
