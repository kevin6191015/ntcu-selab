-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- 主機： mysql_server
-- 產生時間： 2022 年 08 月 31 日 13:04
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
-- 資料庫: `system`
--

-- --------------------------------------------------------

--
-- 資料表結構 `classes_list`
--

CREATE TABLE `classes_list` (
  `class_id` int NOT NULL,
  `semester` tinytext NOT NULL,
  `class_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher` text NOT NULL,
  `TA` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `classes_list`
--

INSERT INTO `classes_list` (`class_id`, `semester`, `class_name`, `teacher`, `TA`) VALUES
(1, '108-1', '程式設計B班', 'Kevin', 'John'),
(2, '101-1', '程式設計C班', 'Kevin', 'murloc'),
(3, '109-2', '程式設計A班', 'ABC', 'murloc'),
(4, '108-1', '程設A班', '魯夫', '凱多');

-- --------------------------------------------------------

--
-- 資料表結構 `class_001_questions`
--

CREATE TABLE `class_001_questions` (
  `question_id` text,
  `question_name` text,
  `release_time` date DEFAULT NULL,
  `deadline` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_001_questions`
--

INSERT INTO `class_001_questions` (`question_id`, `question_name`, `release_time`, `deadline`) VALUES
('a001', 'test4', '2002-10-30', '2022-12-31'),
('a0001', 'Fibonacci_Number', '2022-07-26', '2022-07-31');

-- --------------------------------------------------------

--
-- 資料表結構 `class_001_student`
--

CREATE TABLE `class_001_student` (
  `student_id` text,
  `student_name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_001_student`
--

INSERT INTO `class_001_student` (`student_id`, `student_name`) VALUES
('acs108122', '閃電鳥');

-- --------------------------------------------------------

--
-- 資料表結構 `class_002_questions`
--

CREATE TABLE `class_002_questions` (
  `question_id` text,
  `question_name` text,
  `release_time` datetime DEFAULT NULL,
  `deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `class_002_student`
--

CREATE TABLE `class_002_student` (
  `student_id` text,
  `student_name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_002_student`
--

INSERT INTO `class_002_student` (`student_id`, `student_name`) VALUES
('acs108122', '閃電鳥'),
('acs108101', '冰鳥2');

-- --------------------------------------------------------

--
-- 資料表結構 `class_003_questions`
--

CREATE TABLE `class_003_questions` (
  `question_id` text,
  `question_name` text,
  `release_time` datetime DEFAULT NULL,
  `deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `class_003_student`
--

CREATE TABLE `class_003_student` (
  `student_id` text,
  `student_name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `class_004_questions`
--

CREATE TABLE `class_004_questions` (
  `question_id` text,
  `question_name` text,
  `release_time` datetime DEFAULT NULL,
  `deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `class_004_student`
--

CREATE TABLE `class_004_student` (
  `student_id` text,
  `student_name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_004_student`
--

INSERT INTO `class_004_student` (`student_id`, `student_name`) VALUES
('acs108122', '閃電鳥');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
