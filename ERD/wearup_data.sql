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
        (2, 'user2', '1111', '최해훈', 3000, 'INACTIVE'),
        (3, 'user3', '1111', '이현정', 2000, 'ACTIVE')
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

INSERT INTO Brand (name, username, password, phone_num, is_actived, description) VALUES
        ('나이키', 'nike', 'nike123', '031-1234-4321', true, '1111'),
        ('아디다스', 'adidas', 'adidas123', '031-1234-5342', true, 'aaaaaa'),
        ('뉴발란스', 'newbalance', 'newbalance123', '031-1234-6757', false, 'bbbbbbbb')
;

INSERT INTO Item(Item.brand_id, name, image_sourcename, image_filename, category, is_available, item_status, is_exist) VALUES
        (1, '나이키바지', 'ffdddf', 'dddsadr', '하의', true, 'A', true),
        (1, '나이키상의', 'sdf', 'asdf', '상의', false, 'B', false),
        (2, '아디다스바지', 'qerwq', 'asdfdsa', '하의', true, 'C', true)
;

INSERT INTO Rental(Rental.user_id, Rental.item_id, return_due_at, status) VALUES
        (1, 1, '2025-05-10 17:40:00', 'RENTED'),
        (2, 2, '2025-05-13 17:40:00', 'RETURNED'),
        (3, 3, '2025-05-15 17:40:00', 'OVERDUE')
;

INSERT INTO Attachment(post_id, sourcename, filename, sequence)
VALUES (1, 'face20.png', 'face20.png', 1)
;