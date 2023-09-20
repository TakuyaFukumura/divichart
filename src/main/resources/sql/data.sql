DELETE FROM `dividend_history`
WHERE `id` = 1;

INSERT INTO `dividend_history`
(`id`, `ticker_symbol`, `amount_received`, `receipt_date`) VALUES
(   1,            'VT',               120,   '2022-11-14');

DELETE FROM `authorities`
WHERE `username` = 'admin';

DELETE FROM `users`
WHERE `username` = 'admin';

INSERT INTO `users`
(`username`, `password`, `enabled`) VALUES
(   'admin', '{bcrypt}$2a$10$kxhJnySfXtAL6xjlVks36e.NkqIiXCSUHy2Z2zT8HO8jETJ/t6YwK', TRUE);

INSERT INTO `authorities`
(`username`, `authority`) VALUES
(   'admin', 'ROLE_USER');
