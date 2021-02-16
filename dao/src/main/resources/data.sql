DELETE FROM gift_to_tag;
delete FROM `user_order`;
delete FROM `user`;
DELETE from gift_certificate;
delete from tag;
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
INSERT INTO `tag` (`id`, `name`,`operation`,`operation_date`)
VALUES (14, 'IT','INSERT', '2021-02-04T11:47:51.8445638'),
       (13, 'Programming', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (1, 'Activity', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (10, 'Art', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (7, 'Movie', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (2, 'Beauty', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (3, 'Education', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (8, 'Relaxation', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (15, 'Web Programming', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (6, 'Theater', 'INSERT', '2021-02-04T11:47:51.8445638');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
INSERT INTO `user` (`id`, `name`) VALUES
(1, 'Alex'),
(2, 'Petr'),
(3, 'Valeriy'),
(4, 'Mihail'),
(5, 'Artem'),
(6, 'Kirill');
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
INSERT INTO `user_order` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`) VALUES
(1, 1, 6, '2020-12-24T12:21Z', 250.00),
(2, 6, 15, '2020-12-24T13:21Z', 400.00),
(3, 3, 19, '2020-12-24T14:21Z', 999.00),
(4, 4, 7, '2020-12-24T10:19Z', 60.00),
(5, 3, 1, '2020-12-24T10:20Z', 100.00),
(6, 3, 15, '2020-12-24T10:26Z', 150.00),
(7, 3, 15, '2020-12-24T10:26Z', 150.00),
(8, 5, 5, '2020-12-24T10:27Z', 125.00),
(9, 5, 5, '2020-12-24T10:27Z', 125.00),
(10, 2, 16, '2020-12-24T10:28Z', 250.00),
(14, 1, 25, '2021-01-12T07:43Z', 100.00),
(15, 1, 19, '2021-01-12T08:04Z', 222.00),
(16, 1, 30, '2021-01-13T08:58Z', 55.00),
(17, 1, 1, '2021-01-21T14:08:14.9825138', 100.00),
(18, 1, 3, '2021-01-21T14:08:36.2681671', 125.00),
(19, 1, 12, '2021-01-21T14:08:43.5779579', 15.00);
