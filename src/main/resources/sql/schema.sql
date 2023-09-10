CREATE TABLE IF NOT EXISTS `account` (
    `id` int GENERATED BY DEFAULT AS IDENTITY,
    `name` varchar(255) not null,
    `password` varchar(255) not null,
    PRIMARY KEY (`id`)
);
create table IF NOT EXISTS users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(500) not null,
	enabled boolean not null
);

create table IF NOT EXISTS authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index IF NOT EXISTS ix_auth_username on authorities (username,authority);
CREATE TABLE IF NOT EXISTS `dividend_history` (
    `id` INT AUTO_INCREMENT, -- H2のデータ型とJavaのデータ型の対応 INT java.lang.Integer
    `amount_received` DECIMAL NOT NULL, -- DECIMAL java.math.BigDecimal
    `receipt_date` DATE NOT NULL, -- DATE java.sql.Date
    PRIMARY KEY (`id`)
);
