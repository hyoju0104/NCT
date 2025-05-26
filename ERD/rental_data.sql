-- 2023년
-- Q1
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 1, '2023-01-05 10:00:00', '2023-01-12 10:00:00', '2023-01-10 16:00:00', 'RETURNED'),
       (3, 2, '2023-02-14 14:30:00', '2023-02-21 14:30:00', '2023-02-20 11:00:00', 'RETURNED'),
       (4, 3, '2023-03-20 09:15:00', '2023-03-27 09:15:00', NULL, 'RENTED');

-- Q2
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 4, '2023-04-02 13:45:00', '2023-04-09 13:45:00', '2023-04-09 12:00:00', 'RETURNED'),
       (3, 5, '2023-05-18 16:20:00', '2023-05-25 16:20:00', '2023-05-24 09:30:00', 'RETURNED'),
       (4, 6, '2023-06-10 11:00:00', '2023-06-17 11:00:00', NULL, 'RENTED');

-- Q3
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 2, '2023-07-05 10:15:00', '2023-07-12 10:15:00', '2023-07-12 09:00:00', 'RETURNED'),
       (3, 3, '2023-08-22 14:00:00', '2023-08-29 14:00:00', '2023-08-28 13:45:00', 'RETURNED'),
       (4, 1, '2023-09-30 08:30:00', '2023-10-07 08:30:00', NULL, 'RENTED');

-- Q4
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 5, '2023-10-11 09:50:00', '2023-10-18 09:50:00', '2023-10-18 08:00:00', 'RETURNED'),
       (3, 6, '2023-11-19 15:30:00', '2023-11-26 15:30:00', '2023-11-25 10:15:00', 'RETURNED'),
       (4, 2, '2023-12-05 12:10:00', '2023-12-12 12:10:00', NULL, 'RENTED');


-- 2024년
-- Q1
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 3, '2024-01-08 11:20:00', '2024-01-15 11:20:00', '2024-01-15 10:00:00', 'RETURNED'),
       (3, 4, '2024-02-17 13:10:00', '2024-02-24 13:10:00', '2024-02-24 12:30:00', 'RETURNED'),
       (4, 5, '2024-03-29 09:00:00', '2024-04-05 09:00:00', NULL, 'RENTED');

-- Q2
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 6, '2024-04-12 10:40:00', '2024-04-19 10:40:00', '2024-04-19 09:15:00', 'RETURNED'),
       (3, 1, '2024-05-27 16:55:00', '2024-06-03 16:55:00', '2024-06-02 15:00:00', 'RETURNED'),
       (4, 2, '2024-06-08 08:20:00', '2024-06-15 08:20:00', NULL, 'RENTED');

-- Q3
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 3, '2024-07-14 14:05:00', '2024-07-21 14:05:00', '2024-07-21 13:50:00', 'RETURNED'),
       (3, 4, '2024-08-23 09:30:00', '2024-08-30 09:30:00', '2024-08-29 10:10:00', 'RETURNED'),
       (4, 5, '2024-09-30 11:45:00', '2024-10-07 11:45:00', NULL, 'RENTED');

-- Q4
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 6, '2024-10-11 10:25:00', '2024-10-18 10:25:00', '2024-10-18 09:20:00', 'RETURNED'),
       (3, 1, '2024-11-19 15:00:00', '2024-11-26 15:00:00', '2024-11-25 14:00:00', 'RETURNED'),
       (4, 2, '2024-12-05 13:15:00', '2024-12-12 13:15:00', NULL, 'RENTED');


-- 2025년 Q1
INSERT INTO Rental (user_id, item_id, rented_at, return_due_at, returned_at, status)
VALUES (2, 3, '2025-01-07 12:00:00', '2025-01-14 12:00:00', '2025-01-14 11:00:00', 'RETURNED'),
       (3, 4, '2025-02-16 09:45:00', '2025-02-23 09:45:00', '2025-02-22 16:30:00', 'RETURNED'),
       (4, 5, '2025-03-28 14:20:00', '2025-04-04 14:20:00', NULL, 'RENTED');
