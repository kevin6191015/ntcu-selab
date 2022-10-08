-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： mysql_server
-- 產生時間： 2022 年 10 月 04 日 13:50
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
-- 資料庫： `system`
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
(2, '109-1', '程式設計A班', 'James', 'Robert'),
(3, '109-1', '程式設計B班', 'Michael', 'David'),
(4, '109-1', '程式設計C班', 'Daniel', 'Mark');

-- --------------------------------------------------------

--
-- 資料表結構 `class_001_questions`
--

CREATE TABLE `class_001_questions` (
  `question_id` text,
  `assignment_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `question_name` text,
  `release_time` text,
  `deadline` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_001_questions`
--

INSERT INTO `class_001_questions` (`question_id`, `assignment_name`, `question_name`, `release_time`, `deadline`) VALUES
('a0001', 'HW_1', 'Fibonacci_Number', '20221001', '20221101'),
('a0002', 'HW_1', 'Matrix-chain_Multiplication', '20221001', '20221101'),
('a0003', 'HW_1', 'Longest_Common_Subsequence', '20221001', '20221101'),
('a0004', 'HW_2', 'Optimal_Binary_Search_Tree', '20220930', '20221201');

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
('acs109102', '周二二'),
('acs109101', '陳一一'),
('acs108122', '閃電鳥');

-- --------------------------------------------------------

--
-- 資料表結構 `class_002_questions`
--

CREATE TABLE `class_002_questions` (
  `assignment_name` text,
  `question_id` text,
  `question_name` text,
  `release_time` text,
  `deadline` text
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
('acs109101', '陳一一'),
('acs108101', '冰鳥');

-- --------------------------------------------------------

--
-- 資料表結構 `class_003_questions`
--

CREATE TABLE `class_003_questions` (
  `assignment_name` text,
  `question_id` text,
  `question_name` text,
  `release_time` text,
  `deadline` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `class_003_student`
--

CREATE TABLE `class_003_student` (
  `student_id` text,
  `student_name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `class_003_student`
--

INSERT INTO `class_003_student` (`student_id`, `student_name`) VALUES
('acs109102', '周二二'),
('acs108122', '閃電鳥');

-- --------------------------------------------------------

--
-- 資料表結構 `class_004_questions`
--

CREATE TABLE `class_004_questions` (
  `assignment_name` text,
  `question_id` text,
  `question_name` text,
  `release_time` text,
  `deadline` text
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
('acs109103', '李三三');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
