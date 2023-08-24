DELETE FROM `account`
WHERE `id` = 1;

INSERT INTO `account`
(`id`,   `name`,  `password`) VALUES
(1, 'admin', 'pass');
