-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： mysql_server
-- 產生時間： 2022 年 10 月 24 日 16:26
-- 伺服器版本： 8.0.30
-- PHP 版本： 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `spring_server`
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
('acs108101', '2', '12345678', '冰鳥', 'MTIzNDU2Nzg=', 'null', 'student', '1@example.com', '2'),
('acs108122', '3', '23456789', '閃電鳥', 'MjM0NTY3ODk=', 'null', 'student', '4@example.com', '1,3'),
('b1012', 'null', '32345678', '小智', 'MzIzNDU2Nzg=', 'null', 'teacher', '3@example.com', ''),
('bcs108101', 'null', '42345678', '小剛', 'NDIzNDU2Nzg=', 'null', 'TA', '2@example.com', ''),
('ROOT', '1', '123', 'ROOT', 'MTIz', 'ROOT', 'ROOT', '2@example.com', ''),
('acs109101', '4', '11111111', '陳一一', 'MTExMTExMTE=', 'null', 'student', 'acs109101@example.com', '2'),
('acs109102', '5', '22222222', '周二二', 'MjIyMjIyMjI=', 'null', 'student', 'acs109102@example.com', ''),
('acs109103', '6', '33333333', '李三三', 'MzMzMzMzMzM=', 'null', 'student', 'acs109103@example.com', '3,4'),
('acs110101', '9', '00000000', '源氏', 'MDAwMDAwMDA=', 'null', 'student', '0@example.com', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
