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
DELETE FROM Comment;
ALTER TABLE Comment AUTO_INCREMENT = 1;
DELETE FROM Item;
ALTER TABLE Item AUTO_INCREMENT = 1;
DELETE FROM Rental;
ALTER TABLE Rental AUTO_INCREMENT = 1;
DELETE FROM Brand;
ALTER TABLE Brand AUTO_INCREMENT = 1;

INSERT INTO Authority(grade) VALUES
        ('USER'), ('BRAND'), ('ADMIN')
;

INSERT INTO Plan(type, price, count) VALUES
        ('SILVER', 50000, 3),
        ('GOLD', 70000,5),
        ('VIP', 100000,10)
;

INSERT INTO User (user.auth_id, username, password, name, point, status_plan) VALUES
        (1, 'user1', '1111', '안주경', 1000, 'ACTIVE'),
        (1, 'user3', '1111', '이현정', 2000, 'INACTIVE'),
        (3, 'admin', '1111', '관리자', 0, 'ACTIVE')
;

INSERT INTO Payment (Payment.user_id, Payment.plan_id, price) VALUES
        (1, 1, 50000),
        (2, 2, 70000),
        (3, 3, 100000)
;

INSERT INTO Post(Post.user_id, content, items) VALUES
        (1, '가나다라', '나이키바지'),
        (2, '가나다다라', '나이키티셔츠'),
        (3, '가나다라라', '아디다스바지')
;

INSERT INTO Comment (Comment.user_id, Comment.post_id, content) VALUES
        (1, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂ'),
        (2, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂㅇㄹㄹ'),
        (3, 1, 'ㄱㄱㄱㄱㄱㅂㅂㅂㄴㅇㅁㄹ'),
        (1, 2, 'ㄱㄱㄱㄱㄱㅂㅇㄴㄹㅂㅂ'),
        (2, 2, 'ㄱㄱㄱㄱㄱㅂㅁㄴㅇㄹㅂㅂ'),
        (3, 2, 'ㄱㄱㄱㄱㄱㅂㅁㄴㅇㄹㅂㅂ'),
        (1, 3, 'ㄱㄱㄱㄱㄱㅂㄴㅇㅁㅁㅂㅂ'),
        (2, 3, 'ㄱㄱㅁㄴㅇㄱㄱㄱㅂㅂㅂ'),
        (3, 3, 'ㄱㅁㅇㄴㄹㄱㄱㄱㄱㅂㅂㅂ')
;

INSERT INTO Brand (brand.auth_id, name, username, password, phone_num, is_actived, description) VALUES
        (2, '나이키', 'nike', 'nike123', '031-1234-4321', true, '1111'),
        (2, '아디다스', 'adidas', 'adidas123', '031-1234-5342', true, 'aaaaaa'),
        (2, '뉴발란스', 'newbalance', 'newbalance123', '031-1234-6757', false, 'bbbbbbbb')
;

INSERT INTO Item(Item.brand_id, name, image_sourcename, image_filename, category, is_available, item_status, is_exist, description) VALUES
        (1, 'brand1-바지1', 'brand1_1', 'brand1_1', '하의', true, 'A', true, '"brand1" 브랜드의 "바지1" 상품입니다.'),
        (1, 'brand1-긴팔1', 'brand1_2', 'brand1_2', '상의', false, 'B', true, '"brand1" 브랜드의 "긴팔1" 상품입니다.'),
        (1, 'brand1-신발1', 'brand1_3', 'brand1_3', '신발', true, 'A', true,'"brand1" 브랜드의 "신발1" 상품입니다.'),
        (1, 'brand1-신발2', 'brand1_4', 'brand1_4', '신발', true, 'C', true, '"brand1" 브랜드의 "신발2" 상품입니다'),
        (1, 'brand1-신발3', 'brand1_5', 'brand1_5', '신발', true, 'B', true, '"brand1" 브랜드의 "신발3" 상품입니다'),
        (1, 'brand1-잡화1', 'brand1_6', 'brand1_6', '잡화', true, 'C', true, '"brand1" 브랜드의 "잡화1" 상품입니다'),
        (2, 'brand2-잡화1', 'brand2_1', 'brand2_1', '잡화', false, 'B', true,'"brand2" 브랜드의 "잡화1" 상품입니다'),
        (2, 'brand2-바지1', 'brand2_2', 'brand2_2', '하의', false, 'A', true, '"brand2" 브랜드의 "바지1" 상품입니다'),
        (2, 'brand2-아우터1', 'brand2_3', 'brand2_3', '아우터', true, 'C', true,'"brand2" 브랜드의 "아우터1" 상품입니다.'),
        (2, 'brand2-긴팔1', 'brand2_4', 'brand2_4', '상의', false, 'B', true, '"brand2" 브랜드의 "긴팔1" 상품입니다'),
        (3, 'brand3-아우터1', 'brand3_1', 'brand3_1', '아우터', true, 'B', false, '"brand3" 브랜드의 "아우터1" 상품입니다'),
        (3, 'brand3-잡화1', 'brand3_2', 'brand3_2', '잡화', true, 'B', false, '"brand3" 브랜드의 "잡화1" 상품입니다')
;

INSERT INTO Rental(Rental.user_id, Rental.item_id, return_due_at, status) VALUES
        (1, 1, '2025-05-10 17:40:00', 'RENTED'),
        (2, 2, '2025-05-13 17:40:00', 'RETURNED'),
        (3, 3, '2025-05-15 17:40:00', 'OVERDUE')
;

-- 포인트 조회 기능 확인용 샘플 데이터
UPDATE user
SET point = 30000
WHERE username = 'user5';
