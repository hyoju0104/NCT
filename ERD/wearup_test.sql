show tables;

SELECT * FROM Authority;
SELECT * FROM Attachment;
SELECT * FROM BrandAttachment;
SELECT * FROM ItemAttachment;
SELECT * FROM Plan;
SELECT * FROM User;
SELECT * FROM Payment;
SELECT * FROM Post;
SELECT * FROM PostAttachment;
SELECT * FROM Comment;
SELECT * FROM Brand;
SELECT * FROM Item;
SELECT * FROM Rental;


-- 7일 전에 구독한 유저
UPDATE user SET paid_at = DATE_SUB(NOW(), INTERVAL 7 DAY) WHERE id = 2;
UPDATE user SET paid_at = CURDATE() - INTERVAL 7 DAY WHERE id = 2;

-- 28일 전에 구독한 유저
UPDATE user SET paid_at = DATE_SUB(NOW(), INTERVAL 28 DAY) WHERE id = 2;
