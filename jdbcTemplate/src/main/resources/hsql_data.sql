
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

insert into person values (1, 'Андрей Ярмоленко', 'yar', NULL, 'yar@ukr.net', '+380963451234', '+380440912938', 1, 1, 'сильные стороны: общение с клиентом', '2016-03-08 04:05:06', FALSE );
COMMIT;
insert into company values (1, 'Елки', 1, 'elki.com.ua', 'info@elki.com.ua', 'Украина, Киев, Институтская-3', '+380440313936', 1, 1, '2016-03-10 08:10:00', FALSE, '2016-03-10 08:10:00', 1);
COMMIT;
insert into contact values (1, 'Андрей Пятов', '+380442343976', 'pyatov@ukr.net', 'pyatov84', 'менеджер', 1, 1, 1, 1, '2016-03-10 08:50:20', FALSE, '2016-03-10 08:50:20', 1),
                           (2, 'Тарас Степаненко', '+3804825353956', 'step@ukr.net', 'step89', 'маркетолог', 1, 1, 1, 1, '2016-03-11 13:52:21', FALSE, '2016-03-10 08:50:20', 1);
COMMIT;
insert into deal values (1, 'поставка сумок', 25000.00, 1, 1, 1, 1, '2016-03-10 17:00:00', FALSE),
                        (2, 'поставка кошельков', 15000.00, 1, 1, 1, 1, '2016-03-10 16:00:00', FALSE );
insert into task values (1, 'Познакомиться', '2016-03-12 18:00:00', 1, 'лично встретиться с клиентом', 1, 2, 1, 1, 1, '2016-03-11 17:50:00', FALSE, FALSE, 1)
COMMIT;
insert into deal_contact values (2, 1);
COMMIT;