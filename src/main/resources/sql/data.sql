DELETE FROM `user`
WHERE `id` = 1;

INSERT INTO `user`
(`id`,   `name`,  `password`) VALUES
(1, 'admin', 'pass');
