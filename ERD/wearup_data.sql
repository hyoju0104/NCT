DELETE
FROM User;
ALTER TABLE User
    AUTO_INCREMENT = 1;
DELETE
FROM Authority;
ALTER TABLE Authority
    AUTO_INCREMENT = 1;
DELETE
FROM Payment;
ALTER TABLE Payment
    AUTO_INCREMENT = 1;
DELETE
FROM Plan;
ALTER TABLE Plan
    AUTO_INCREMENT = 1;
DELETE
FROM Post;
ALTER TABLE Post
    AUTO_INCREMENT = 1;
DELETE
FROM Attachment;
ALTER TABLE Attachment
    AUTO_INCREMENT = 1;
DELETE
FROM Comment;
ALTER TABLE Comment
    AUTO_INCREMENT = 1;
DELETE
FROM Item;
ALTER TABLE Item
    AUTO_INCREMENT = 1;
DELETE
FROM Rental;
ALTER TABLE Rental
    AUTO_INCREMENT = 1;
DELETE
FROM Brand;
ALTER TABLE Brand
    AUTO_INCREMENT = 1;

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

INSERT INTO User (user.auth_id, username, password, name, point, status_plan)
VALUES (3, 'admin', '$2a$10$.JN4oKC7Nr6oR8NgYxX3fOvtAn3OOURyYPNDf4Y/E5hfWKhblkKfe', '관리자', 0, 'ACTIVE'),
       (1, 'user1', '$2a$10$AsdcGiiMWwG6sCu9IiNqvu5Z1G7krhWLhehijgfiqjRhHCODctw8a', '회원1', 30000, 'ACTIVE'),
       (1, 'user2', '$2a$10$5e2fLl7OQKtTpGQyIlvbMuI8.eyKlVu1qfRuHlC/QyIcLvdPgh48O', '회원2', 0, 'INACTIVE')
;

INSERT INTO Payment (Payment.user_id, Payment.plan_id, price)
VALUES (1, 1, 50000),
       (2, 2, 70000),
       (3, 3, 100000)
;

INSERT INTO Post(Post.user_id, content, items)
VALUES (1, '가나다라', '나이키바지'),
       (2, '가나다다라', '나이키티셔츠'),
       (3, '가나다라라', '아디다스바지')
;

INSERT INTO Comment (Comment.user_id, Comment.post_id, content)
VALUES (1, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂ'),
       (2, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂㅇㄹㄹ'),
       (3, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂㄴㅇㅁㄹ'),
       (1, 2, 'ㄱㄱㄱㄱㄱㅂㅇㄴㄹㅂㅂ'),
       (2, 2, 'ㄱㄱㄱㄱㄱㅂㅁㄴㅇㄹㅂㅂ'),
       (3, 2, 'ㄱㄱㄱㄱㄱㅂㅁㄴㅇㄹㅂㅂ'),
       (1, 3, 'ㄱㄱㄱㄱㄱㅂㄴㅇㅁㅁㅂㅂ'),
       (2, 3, 'ㄱㄱㅁㄴㅇㄱㄱㄱㅂㅂㅂ'),
       (3, 3, 'ㄱㅁㅇㄴㄹㄱㄱㄱㄱㅂㅂㅂ')
;

INSERT INTO Brand (brand.auth_id, name, username, password, phone_num, is_actived, description)
VALUES (2, '나이키', 'nike', 'nike123', '031-1234-4321', true, '1111'),
       (2, '아디다스', 'adidas', 'adidas123', '031-1234-5342', true, 'aaaaaa'),
       (2, '뉴발란스', 'newbalance', 'newbalance123', '031-1234-6757', false, 'bbbbbbbb'),
       (2, '브랜드1', 'brand1', '$2a$10$.PNEhmDp8ns/piR3D4XfL.OdEbDj.i2Sxf9rnOZogm6YsJU2N0XcS', '1234567890', true,
        'test1'),
       (2, '브랜드2', 'brand2', '$2a$10$aHXnS/n5WQKbQ2rGzXtt2eFYzpING1w62o7AZzwhj4yKmUBsUQMfi', '1234567890', true, '')
;

INSERT INTO Item(Item.brand_id, name, category, is_available, item_status, is_exist, description)
VALUES (1, 'brand1-바지1', '하의', true, 'A', true, '"brand1" 브랜드의 "바지1" 상품입니다.'),
       (1, 'brand1-긴팔1', '상의', false, 'B', true, '"brand1" 브랜드의 "긴팔1" 상품입니다.'),
       (1, 'brand1-신발1', '신발', true, 'A', true, '"brand1" 브랜드의 "신발1" 상품입니다.'),
       (1, 'brand1-신발2', '신발', true, 'C', true, '"brand1" 브랜드의 "신발2" 상품입니다'),
       (1, 'brand1-신발3', '신발', true, 'B', true, '"brand1" 브랜드의 "신발3" 상품입니다'),
       (1, 'brand1-잡화1', '잡화', true, 'C', true, '"brand1" 브랜드의 "잡화1" 상품입니다'),
       (2, 'brand2-잡화1', '잡화', false, 'B', true, '"brand2" 브랜드의 "잡화1" 상품입니다'),
       (2, 'brand2-바지1', '하의', false, 'A', true, '"brand2" 브랜드의 "바지1" 상품입니다'),
       (2, 'brand2-아우터1', '아우터', true, 'C', true, '"brand2" 브랜드의 "아우터1" 상품입니다.'),
       (2, 'brand2-긴팔1', '상의', false, 'B', true, '"brand2" 브랜드의 "긴팔1" 상품입니다'),
       (3, 'brand3-아우터1', '아우터', true, 'B', false, '"brand3" 브랜드의 "아우터1" 상품입니다'),
       (3, 'brand3-잡화1', '잡화', true, 'B', false, '"brand3" 브랜드의 "잡화1" 상품입니다')
;

INSERT INTO Rental(Rental.user_id, Rental.item_id, return_due_at, status)
VALUES (2, 1, '2025-05-10 17:40:00', 'RENTED'),
       (2, 2, '2025-05-13 17:40:00', 'RETURNED'),
       (2, 3, '2025-05-15 17:40:00', 'OVERDUE'),
       (3, 5, '2025-02-10 17:40:00', 'RENTED'),
       (3, 6, '2025-03-13 17:40:00', 'RETURNED'),
       (3, 7, '2025-04-15 17:40:00', 'OVERDUE')
;

UPDATE User
SET plan_id = 3
WHERE id = 2;

UPDATE User
SET plan_id = 1
WHERE id = 3;
