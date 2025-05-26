DELETE FROM User;
ALTER TABLE User AUTO_INCREMENT = 1;
DELETE FROM Authority;
ALTER TABLE Authority AUTO_INCREMENT = 1;
DELETE FROM Payment;
ALTER TABLE Payment AUTO_INCREMENT = 1;
DELETE FROM Plan;
ALTER TABLE Plan AUTO_INCREMENT = 1;
DELETE FROM Post;
ALTER TABLE Post AUTO_INCREMENT = 1;
DELETE FROM Attachment;
ALTER TABLE Attachment AUTO_INCREMENT = 1;
DELETE FROM BrandAttachment;
ALTER TABLE BrandAttachment AUTO_INCREMENT = 1;
DELETE FROM ItemAttachment;
ALTER TABLE ItemAttachment AUTO_INCREMENT = 1;
DELETE FROM Comment;
ALTER TABLE Comment AUTO_INCREMENT = 1;
DELETE FROM Item;
ALTER TABLE Item AUTO_INCREMENT = 1;
DELETE FROM Rental;
ALTER TABLE Rental AUTO_INCREMENT = 1;
DELETE FROM Brand;
ALTER TABLE Brand AUTO_INCREMENT = 1;

INSERT INTO Authority(grade)
VALUES ('USER'),
       ('BRAND'),
       ('ADMIN')
;

INSERT INTO Plan(type, price, count)
VALUES ('SILVER', 50000, 3),
       ('GOLD', 70000, 5),
       ('VIP', 100000, 10)
;

INSERT INTO User (user.auth_id, username, password, name, point, status_plan, status_account, paid_at, rental_cnt)
VALUES (3, 'admin', '$2a$10$.JN4oKC7Nr6oR8NgYxX3fOvtAn3OOURyYPNDf4Y/E5hfWKhblkKfe', '관리자', 0, 'ACTIVE', 'ACTIVE', '2025-4-24', 0),
       (1, 'user1', '$2a$10$AsdcGiiMWwG6sCu9IiNqvu5Z1G7krhWLhehijgfiqjRhHCODctw8a', '회원1', 30000, 'ACTIVE', 'INACTIVE', '2025-4-24', 2),
       (1, 'user2', '$2a$10$5e2fLl7OQKtTpGQyIlvbMuI8.eyKlVu1qfRuHlC/QyIcLvdPgh48O', '회원2', 0, 'ACTIVE', 'ACTIVE', '2025-5-1', 3),
       (1, 'user3', '$2a$10$zT51nN0ycpAvSg5aimPoUuOIyg94ktXJzLhWHGxeJQ8iDqXB4vqRm', '회원3', 57000, 'INACTIVE', 'ACTIVE', '2025-4-24', 0),
       (1, 'user4', '$2a$10$a5UM06TTPg.YOvkYiXEhkOvAqIqPV0fZkifaGJsBI9cAZKSYd/sLi', '회원4', 10000, 'INACTIVE', 'ACTIVE', '2025-3-24', 0)
;

INSERT INTO Brand (brand.auth_id, name, username, password, phone_num, is_actived, description)
VALUES (2, 'brand1', 'sample1', '$2a$10$ERbfOD26AaB.4gk0hadqy.AzoAMGGp8bJhO2EJ0ZZHERWOqTHfb4q', '031-1234-5678', true, 'brand1 입니다.'),
       (2, 'brand2', 'sample2', '$2a$10$giSgQ5AavEa1wHyqlKv9DOXm71fAuDY6gvxxgCTHzOMi9moeuV096', '02-1111-2222', true, 'brand2 입니다.')
;

INSERT INTO Brandattachment(brand_id, sourcename, filename)
VALUES (1, 'face01.png', '6737b830-9b99-468f-8541-db39519b6ddf_face01.png'),
       (2, 'face02.png', '462a2e1d-1c11-4608-bd64-a71bedb0ba17_face02.png')
;

-- 포인트 조회 기능 확인용 샘플 데이터
UPDATE user
SET point = 30000
WHERE username = 'user5';

UPDATE User
SET plan_id = 3
WHERE id = 2;

UPDATE User
SET plan_id = 1
WHERE id = 3;

UPDATE User SET point = 30000 WHERE id = 7;
