-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2022 at 10:44 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ims`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, 'Watch'),
(2, 'Jewellery'),
(3, 'TV & Home Appliances'),
(4, 'Electronic Devices'),
(5, 'Groceries'),
(6, 'Automobiles');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `category_name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `quantity`, `price`, `description`, `category_name`) VALUES
(3, 'Apple Watch 3', 3, 39999, 'Watch Series 3 with pink gold color', 'Watch'),
(2, 'Cartier', 2, 150000, 'Evil Eye Inspired Bracelet', 'Jewellery'),
(1, 'Naviforce', 10, 3200, 'Luxury Stainless Steel Watch For Men', 'Watch'),
(5, 'Nokia Router', 15, 1500, 'Dual Band Router of both 2.4 and 5 GHz', 'Electronic Devices'),
(4, 'TVS 200 4V', 3, 415000, 'BS6 version with dual ABS', 'Automobiles');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `purchased_quantity` int(11) DEFAULT NULL,
  `sold_quantity` int(11) DEFAULT NULL,
  `remaining_quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`id`, `name`, `purchased_quantity`, `sold_quantity`, `remaining_quantity`) VALUES
(3, 'Naviforce', 10, NULL, 10),
(4, 'Cartier', 2, 1, 1),
(5, 'Apple Watch 3', 3, NULL, 3),
(6, 'TVS 200 4V', 3, 1, 2),
(7, 'Nokia Router', 15, 4, 11);

-- --------------------------------------------------------

--
-- Table structure for table `tblorder`
--

CREATE TABLE `tblorder` (
  `order_id` int(11) NOT NULL,
  `order_by` varchar(25) DEFAULT NULL,
  `ordered_product` varchar(20) DEFAULT NULL,
  `product_category` varchar(20) DEFAULT NULL,
  `ordered_qty` int(11) NOT NULL,
  `qty_price` float NOT NULL,
  `total_price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblorder`
--

INSERT INTO `tblorder` (`order_id`, `order_by`, `ordered_product`, `product_category`, `ordered_qty`, `qty_price`, `total_price`) VALUES
(7, 'demo2', 'Cartier', 'Jewellery', 1, 215000, 215000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `name` varchar(40) NOT NULL,
  `Address` varchar(40) NOT NULL,
  `telephone_no` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`name`, `Address`, `telephone_no`, `email`, `age`, `gender`, `username`, `password`) VALUES
('Dummy User1', 'Bhaktapur', 9873216540, 'demo1@gmail.com', 30, 'Female', 'demo1', '1111'),
('dummy User2', 'Kathmandu', 9876543210, 'demo2@gmail.com', 45, 'Female', 'demo2', '1111'),
('Saurav Lamichhane', 'Kathmandu-32', 9876543200, 'saurav.lamichhanesaurav@gmail.com', 22, 'Male', 'Saurav ', '12345678');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`name`),
  ADD UNIQUE KEY `category_id` (`category_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_name`),
  ADD KEY `category_name` (`category_name`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `tblorder`
--
ALTER TABLE `tblorder`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `order_by` (`order_by`),
  ADD KEY `ordered_product` (`ordered_product`),
  ADD KEY `product_category` (`product_category`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `telephone_no` (`telephone_no`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tblorder`
--
ALTER TABLE `tblorder`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_name`) REFERENCES `categories` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `name` FOREIGN KEY (`name`) REFERENCES `product` (`product_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblorder`
--
ALTER TABLE `tblorder`
  ADD CONSTRAINT `tblorder_ibfk_1` FOREIGN KEY (`order_by`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblorder_ibfk_2` FOREIGN KEY (`ordered_product`) REFERENCES `product` (`product_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblorder_ibfk_3` FOREIGN KEY (`product_category`) REFERENCES `categories` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
