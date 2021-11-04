-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Ноя 04 2021 г., 22:15
-- Версия сервера: 5.7.34-log
-- Версия PHP: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `daniil`
--

-- --------------------------------------------------------

--
-- Структура таблицы `employee`
--

CREATE TABLE `employee`
(
    `id`           bigint(20) NOT NULL,
    `discord_name` varchar(255) DEFAULT NULL,
    `gold`         bigint(20)   DEFAULT NULL,
    `server_id`    bigint(20)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `for_sale`
--

CREATE TABLE `for_sale`
(
    `id`               bigint(20) NOT NULL,
    `dreaming_glory`   int(11)    DEFAULT NULL,
    `nightmare_vine`   int(11)    DEFAULT NULL,
    `mana_thistle`     int(11)    DEFAULT NULL,
    `nightmare_seed`   int(11)    DEFAULT NULL,
    `fel_lotus`        int(11)    DEFAULT NULL,
    `netherbloom`      int(11)    DEFAULT NULL,
    `terocone`         int(11)    DEFAULT NULL,

    `fel_iron_ore`     int(11)    DEFAULT NULL,
    `eternium_ore`     int(11)    DEFAULT NULL,
    `adamantite_ore`   int(11)    DEFAULT NULL,
    `khorium_ore`      int(11)    DEFAULT NULL,

    `blood_garnet`     int(11)    DEFAULT NULL,
    `flame_spessarite` int(11)    DEFAULT NULL,
    `golden_draenite`  int(11)    DEFAULT NULL,
    `shadow_draenite`  int(11)    DEFAULT NULL,
    `deep_peridot`     int(11)    DEFAULT NULL,
    `azure_moonstone`  int(11)    DEFAULT NULL,
    `dawnstone`        int(11)    DEFAULT NULL,
    `nightseye`        int(11)    DEFAULT NULL,
    `star_of_elune`    int(11)    DEFAULT NULL,
    `talasite`         int(11)    DEFAULT NULL,
    `living_ruby`      int(11)    DEFAULT NULL,
    `noble_topaz`      int(11)    DEFAULT NULL,


    `primal_air`       int(11)    DEFAULT NULL,
    `primal_earth`     int(11)    DEFAULT NULL,
    `primal_fire`      int(11)    DEFAULT NULL,
    `primal_life`      int(11)    DEFAULT NULL,
    `primal_mana`      int(11)    DEFAULT NULL,

    `price`            bigint(20) DEFAULT NULL,
    `date`             date       DEFAULT NULL,
    `employee_id`      bigint(20) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `resource`
--

CREATE TABLE `resource`
(
    `id`          bigint(20) NOT NULL,
    `icon`        varchar(255) DEFAULT NULL,
    `item_id`     bigint(20)   DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `unique_name` varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `resource_price`
--

CREATE TABLE `resource_price`
(
    `id`          bigint(20) NOT NULL,
    `price`       bigint(20)  DEFAULT NULL,
    `scanned_on`  datetime(6) DEFAULT NULL,
    `resource_id` bigint(20)  DEFAULT NULL,
    `server_id`   bigint(20)  DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `server`
--

CREATE TABLE `server`
(
    `id`              bigint(20) NOT NULL,
    `server_fraction` varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `employee`
--
ALTER TABLE `employee`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `UK_o713vnu3h8p41rw7bjybtqbm` (`discord_name`),
    ADD KEY `FKtkbe1prv4vo6xdlpl7sgw35ne` (`server_id`);

--
-- Индексы таблицы `for_sale`
--
ALTER TABLE `for_sale`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKeifkwgr7qi4q8r31voqrgfrww` (`employee_id`);

--
-- Индексы таблицы `resource`
--
ALTER TABLE `resource`
    ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `resource_price`
--
ALTER TABLE `resource_price`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKfae49bhrh8n5yjtsgu6i74t3l` (`resource_id`),
    ADD KEY `FK531yudr7vqdp2qx4shh463x9p` (`server_id`);

--
-- Индексы таблицы `server`
--
ALTER TABLE `server`
    ADD PRIMARY KEY (`id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `employee`
--
ALTER TABLE `employee`
    ADD CONSTRAINT `FKtkbe1prv4vo6xdlpl7sgw35ne` FOREIGN KEY (`server_id`) REFERENCES `server` (`id`);

--
-- Ограничения внешнего ключа таблицы `for_sale`
--
ALTER TABLE `for_sale`
    ADD CONSTRAINT `FKeifkwgr7qi4q8r31voqrgfrww` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Ограничения внешнего ключа таблицы `resource_price`
--
ALTER TABLE `resource_price`
    ADD CONSTRAINT `FK531yudr7vqdp2qx4shh463x9p` FOREIGN KEY (`server_id`) REFERENCES `server` (`id`),
    ADD CONSTRAINT `FKfae49bhrh8n5yjtsgu6i74t3l` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
