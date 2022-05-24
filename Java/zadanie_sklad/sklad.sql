-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2020 at 11:48 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sklad`
--

-- --------------------------------------------------------

--
-- Table structure for table `dodavatelia`
--

CREATE TABLE `dodavatelia` (
  `idDodavatelia` int(11) NOT NULL,
  `nazov` varchar(45) CHARACTER SET utf8 COLLATE utf8_slovak_ci NOT NULL,
  `ico` int(11) NOT NULL,
  `adresa` varchar(45) CHARACTER SET utf8 COLLATE utf8_slovak_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dodavatelia`
--

INSERT INTO `dodavatelia` (`idDodavatelia`, `nazov`, `ico`, `adresa`) VALUES
(1, 'DODAVATEL', 55555, 'Test 76'),
(14, 'DODAVATEL2', 44846642, 'Test 223'),
(15, 'DODAVATEL3', 44128475, 'Test 6663');

-- --------------------------------------------------------

--
-- Table structure for table `tovar`
--

CREATE TABLE `tovar` (
  `idTovar` int(11) NOT NULL,
  `nazov` varchar(45) CHARACTER SET utf8 COLLATE utf8_slovak_ci NOT NULL,
  `cena` float NOT NULL,
  `idDodavatelia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tovar`
--

INSERT INTO `tovar` (`idTovar`, `nazov`, `cena`, `idDodavatelia`) VALUES
(14, 'marhule', 1.12, 1),
(27, 'banány', 2.4, 15),
(31, 'mango', 55, 14);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dodavatelia`
--
ALTER TABLE `dodavatelia`
  ADD PRIMARY KEY (`idDodavatelia`);

--
-- Indexes for table `tovar`
--
ALTER TABLE `tovar`
  ADD PRIMARY KEY (`idTovar`),
  ADD KEY `idDodavatelia` (`idDodavatelia`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dodavatelia`
--
ALTER TABLE `dodavatelia`
  MODIFY `idDodavatelia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `tovar`
--
ALTER TABLE `tovar`
  MODIFY `idTovar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tovar`
--
ALTER TABLE `tovar`
  ADD CONSTRAINT `dodavatelia` FOREIGN KEY (`idDodavatelia`) REFERENCES `dodavatelia` (`idDodavatelia`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
