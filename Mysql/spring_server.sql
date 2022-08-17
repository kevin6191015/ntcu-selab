-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- 主機： mysql_server
-- 產生時間： 2022 年 08 月 17 日 13:36
-- 伺服器版本： 8.0.29
-- PHP 版本： 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫: `spring_server`
--

-- --------------------------------------------------------

--
-- 資料表結構 `User`
--

CREATE TABLE `User` (
  `ID` text NOT NULL,
  `GITLAB_ID` text,
  `USERNAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `PASSWORD` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `GITLAB_TOKEN` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `ROLE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `EMAIL` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CLASSES` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `User`
--

INSERT INTO `User` (`ID`, `GITLAB_ID`, `USERNAME`, `NAME`, `PASSWORD`, `GITLAB_TOKEN`, `ROLE`, `EMAIL`, `CLASSES`) VALUES
('acs108101', '17', '12345678', '冰鳥', '12345678', 'null', 'student', '1@example.com', '1,1'),
('acs108122', '18', '23456789', '閃電鳥', '23456789', 'null', 'student', '4@example.com', '1,1'),
('b1012', 'null', '32345678', '小智', '32345678', 'null', 'teacher', '3@example.com', ''),
('bcs108101', 'null', '42345678', '小剛', '42345678', 'null', 'TA', '2@example.com', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
