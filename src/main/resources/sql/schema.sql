CREATE TABLE IF NOT EXISTS `users`(
	`username` VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
	`password` VARCHAR_IGNORECASE(500) NOT NULL,
	`enabled`  BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS `authorities` (
	`username`  VARCHAR_IGNORECASE(50) NOT NULL,
	`authority` VARCHAR_IGNORECASE(50) NOT NULL,
	CONSTRAINT `fk_authorities_users` FOREIGN KEY(`username`) REFERENCES `users`(`username`)
);
CREATE UNIQUE INDEX IF NOT EXISTS `ix_auth_username` ON `authorities` (`username`, `authority`);

CREATE TABLE IF NOT EXISTS `dividend_history` (
    `id`              INT AUTO_INCREMENT, -- H2のデータ型とJavaのデータ型の対応 INT java.lang.Integer
    `ticker_symbol`   VARCHAR(5) NOT NULL,
    `amount_received` NUMERIC(20, 2) NOT NULL, -- DECIMAL java.math.BigDecimal
    `receipt_date`    DATE NOT NULL, -- DATE java.sql.Date
    PRIMARY KEY (`id`)
);
