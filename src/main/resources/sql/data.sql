DELETE FROM `account`
WHERE `id` = 1;

INSERT INTO `account`
(`id`,   `name`,  `password`) VALUES
(1, 'admin', 'pass');

DELETE FROM `dividend_history`
WHERE `id` = 1;

INSERT INTO `dividend_history`
(`id`, `amount_received`, `receipt_date`) VALUES
(   1,               120,   '2022-11-14');
