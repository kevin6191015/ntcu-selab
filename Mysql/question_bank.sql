-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- 主機： mysql_server
-- 產生時間： 2022 年 05 月 03 日 04:16
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
-- 資料庫: `project`
--

-- --------------------------------------------------------

--
-- 資料表結構 `question_bank`
--

CREATE TABLE `question_bank` (
  `num` int NOT NULL,
  `question_name` text NOT NULL,
  `describe` text NOT NULL,
  `category` text NOT NULL,
  `image1` text NOT NULL,
  `image2` text NOT NULL,
  `input1` text NOT NULL,
  `input2` text NOT NULL,
  `input3` text NOT NULL,
  `input4` text NOT NULL,
  `input5` text NOT NULL,
  `input6` text NOT NULL,
  `input7` text NOT NULL,
  `input8` text NOT NULL,
  `input9` text NOT NULL,
  `input10` text NOT NULL,
  `output1` text NOT NULL,
  `output2` text NOT NULL,
  `output3` text NOT NULL,
  `output4` text NOT NULL,
  `output5` text NOT NULL,
  `output6` text NOT NULL,
  `output7` text NOT NULL,
  `output8` text NOT NULL,
  `output9` text NOT NULL,
  `output10` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `question_bank`
--

INSERT INTO `question_bank` (`num`, `question_name`, `describe`, `category`, `image1`, `image2`, `input1`, `input2`, `input3`, `input4`, `input5`, `input6`, `input7`, `input8`, `input9`, `input10`, `output1`, `output2`, `output3`, `output4`, `output5`, `output6`, `output7`, `output8`, `output9`, `output10`) VALUES
(1, 'Fibonacci Number', 'Write a program which prints \r\nn\r\n-th fibonacci number for a given integer \r\nn\r\n. The \r\nn\r\n-th fibonacci number is defined by the following recursive formula:', 'math', 'https://imgur.com/zkJGJJM', '', '3', '3', '3', '3', '4', '4', '5', '5', '4', '45', '4', '54', '454', '54', '5', '45', '45', '4', '54', '5');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `question_bank`
--
ALTER TABLE `question_bank`
  ADD PRIMARY KEY (`num`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `question_bank`
--
ALTER TABLE `question_bank`
  MODIFY `num` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
