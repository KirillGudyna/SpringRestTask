-- Дамп данных таблицы gift.gift_certificate: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`
                               , `create_date`, `last_update_date`)
VALUES (1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100, 60
       , STR_TO_DATE('2020-12-16T14:48Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-16T14:49Z','%Y-%m-%dT%H:%iZ')),
       (3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125, 180
       , STR_TO_DATE('2020-12-16T14:51Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-16T14:52Z','%Y-%m-%dT%H:%iZ')),
       (5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125, 180
       , STR_TO_DATE('2020-12-17T11:49Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-17T11:51Z','%Y-%m-%dT%H:%iZ')),
       (6, 'Programming courses ''Java Web development''', 'Become good programmer for short period', 400, 90
       , STR_TO_DATE('2020-12-17T12:00Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-17T12:01Z','%Y-%m-%dT%H:%iZ')),
       (7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60, 14
       , STR_TO_DATE('2020-12-18T07:11Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-18T10:05Z','%Y-%m-%dT%H:%iZ')),
       (12, 'SilverScreen', 'Просмотр любого кинофильма', 15, 45
       , STR_TO_DATE('2020-12-18T09:22Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-18T09:25Z','%Y-%m-%dT%H:%iZ')),
       (15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150, 100
       , STR_TO_DATE('2020-12-18T10:22Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-18T10:37Z','%Y-%m-%dT%H:%iZ')),
       (16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250, 90
       , STR_TO_DATE('2020-12-21T12:21Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-21T12:21Z','%Y-%m-%dT%H:%iZ')),
       (19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222, 120
       , STR_TO_DATE('2020-12-22T12:33Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-22T12:57Z','%Y-%m-%dT%H:%iZ')),
       (20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900, 90
       , STR_TO_DATE('2020-12-23T08:22Z','%Y-%m-%dT%H:%iZ'), STR_TO_DATE('2020-12-23T08:22Z','%Y-%m-%dT%H:%iZ'));
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;
-- Дамп данных таблицы gift.order: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `name`)
VALUES (14, 'IT'),
       (13, 'Programming'),
       (1, 'Активность'),
       (10, 'Искусство'),
       (7, 'Кино'),
       (2, 'Красота'),
       (3, 'Образование'),
       (8, 'Отдых'),
       (15, 'Программирование'),
       (6, 'Театр');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
INSERT INTO gift_to_tag (`gift_certificate_id`, `tag_id`)
VALUES (1, 8),
       (1, 1),
       (3, 2),
       (5, 2),
       (5, 1),
       (5, 8),
       (6, 3),
       (12, 7),
       (7, 6),
       (15, 3),
       (15, 10),
       (16, 2),
       (19, 13),
       (19, 14),
       (20, 14),
       (20, 15);
/*!40000 ALTER TABLE gift_to_tag ENABLE KEYS */;
