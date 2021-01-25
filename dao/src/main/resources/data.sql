-- Дамп данных таблицы gift.gift_certificate: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`
                               , `create_date`, `last_update_date`)
VALUES (1, 'Sauna Triton', 'Certificate for free admission to the Triton sauna on Mayakovsky, 16', 100, 60
       , '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
       (3, 'Tattoo parlor "Fox"', 'Free tattoo 10x10cm', 125, 180
       ,'2020-12-16T14:51Z', '2020-12-16T14:52Z'),
       (5, 'SPA center on Starovilenskaya, 15', 'The whole range of SPA procedures', 125, 180
       , '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
       (6, 'Programming courses ''Java Web development''', 'Become good programmer for short period', 400, 90
       , '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
       (7, 'Theater named after Yanka Kupala', 'Attending any performance', 60, 14
       , '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
       (12, 'SilverScreen', 'Watching any movie', 15, 45
       , '2020-12-18T09:22Z','2020-12-18T09:25Z'),
       (15, 'Watching any movie', 'English courses at SkyEng online school', 150, 100
       , '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
       (16, 'Tattoo parlor "Imagine Dragon"', 'Free tattoo 12x12, + design', 250, 90
       , '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
       (19, 'Online C # courses', 'Free C # Course at Litrex Programming School', 1222, 120
       , '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
       (20, 'Course Python Web development', 'Take a Free Python Web Development Course', 900, 90
       , '2020-12-23T08:22Z', '2020-12-23T08:22Z');
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;
-- Дамп данных таблицы gift.order: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `name`)
VALUES (14, 'IT'),
       (13, 'Programming'),
       (1, 'Activity'),
       (10, 'Art'),
       (7, 'Movie'),
       (2, 'Beauty'),
       (3, 'Education'),
       (8, 'Relaxation'),
       (15, 'Programming'),
       (6, 'theater');
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
