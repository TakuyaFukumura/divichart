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

DELETE FROM `authorities`
WHERE `username` = 'guest';

DELETE FROM `users`
WHERE `username` = 'guest';

INSERT INTO `users`
(`username`, `password`, `enabled`) VALUES
(   'guest', '{bcrypt}$2a$10$KY4iiHlRc8bfj.lQveu8DO.FgNLnCpon8lOPr4D3kHxwgISTUngTm', TRUE);

INSERT INTO `authorities`
(`username`, `authority`) VALUES
(   'guest', 'ROLE_USER');
