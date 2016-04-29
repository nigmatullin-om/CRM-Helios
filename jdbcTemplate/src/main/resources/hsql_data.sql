
insert into role (id,role_name ) values (1, 'Пользователь');
insert into role (id,role_name ) values (2, 'Администратор');
COMMIT;

insert into phone_type values (1, 'рабочий'),
  (2, 'рабочий прямой'),
  (3, 'мобильный'),
  (4, 'факс'),
  (5, 'домашний'),
  (6, 'другой');
COMMIT;

insert into lang values (1, 'Украинский'),
  (2, 'Русский'),
  (3, 'English');
COMMIT;

insert into currencies values (1, 'гривна', TRUE),
  (2, 'доллар', FALSE),
  (3, 'рубль', FALSE),
  (4, 'тенге', FALSE),
  (5, 'лей', FALSE),
  (6, 'манат', FALSE),
  (7, 'драма', FALSE),
  (8, 'сума', FALSE),
  (9, 'сомонна', FALSE),
  (10, 'сома', FALSE);
COMMIT;


insert into stage values (1, 'первичный контакт'),
  (2, 'переговоры'),
  (3, 'принимают решение'),
  (4, 'согласование договора'),
  (5, 'успешно реализовано'),
  (6, 'закрыто и не реализовано');
COMMIT;

insert into task_type VALUES (1, 'Follow-up');
insert into task_type VALUES (2, 'Meeting');
COMMIT;