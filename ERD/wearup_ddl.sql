SET SESSION FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Attachment;
DROP TABLE IF EXISTS Authority;
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Rental;
DROP TABLE IF EXISTS User;


CREATE TABLE Attachment
(
  id         INT          NOT NULL AUTO_INCREMENT,
  post_id    INT          NOT NULL,
  sourcename VARCHAR(100) NULL    ,
  filename   VARCHAR(100) NOT NULL,
  sequence   INT          NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE Authority
(
  id    INT  NOT NULL AUTO_INCREMENT,
  grade ENUM('USER', 'BRAND', 'ADMIN') NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE Brand
(
  id              INT           NOT NULL AUTO_INCREMENT,
  name            VARCHAR(100)  NOT NULL,
  phone_num       VARCHAR(13)   NOT NULL,
  logo_sourcename VARCHAR(100)  NULL    ,
  logo_filename   VARCHAR(100)  NULL    ,
  description     VARCHAR(1000) NULL    ,
  PRIMARY KEY (id)
);

CREATE TABLE Comment
(
  id      INT          NOT NULL AUTO_INCREMENT,
  user_id INT          NOT NULL,
  post_id INT          NOT NULL,
  content VARCHAR(200) NOT NULL,
  regdate DATETIME     NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE Item
(
  id               INT           NOT NULL AUTO_INCREMENT,
  brand_id         INT           NOT NULL,
  name             VARCHAR(100)  NOT NULL,
  image_sourcename VARCHAR(100)  NOT NULL,
  image_filename   VARCHAR(100)  NOT NULL,
  category         VARCHAR(20)   NOT NULL,
  description      VARCHAR(1000) NULL    ,
  is_available     BOOLEAN       NOT NULL   DEFAULT true,
  status           ENUM('A', 'B', 'C')  NOT NULL,
  created_at       DATETIME      NOT NULL   DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE Payment
(
  id      INT  NOT NULL AUTO_INCREMENT,
  user_id INT  NOT NULL,
  plan_id INT  NOT NULL,
  price   INT  NOT NULL,
  paid_at DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
) COMMENT '결제 히스토리';

CREATE TABLE Plan
(
  id    INT         NOT NULL AUTO_INCREMENT,
  type  ENUM('SILVER', 'GOLD', 'VIP') NOT NULL,
  price INT         NOT NULL COMMENT '구독 비용',
  count INT         NOT NULL COMMENT '대여 가능 총 횟수',
  PRIMARY KEY (id)
);

CREATE TABLE Post
(
  id         INT           NOT NULL AUTO_INCREMENT,
  user_id    INT           NOT NULL,
  content    VARCHAR(1000) NOT NULL,
  items      VARCHAR(1000) NOT NULL,
  created_at DATETIME      NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE Rental
(
  id            INT         NOT NULL AUTO_INCREMENT,
  user_id       INT         NOT NULL,
  item_id       INT         NOT NULL,
  rented_at     DATETIME    NOT NULL    DEFAULT now(),
  return_due_at DATETIME    NOT NULL,
  returned_at   DATETIME    NULL    ,
  status        ENUM('RENTED', 'RETURNED', 'OVERDUE') NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE User
(
  id             INT          NOT NULL AUTO_INCREMENT,
  auth_id        INT          NOT NULL,
  plan_id        INT          NULL,
  username       VARCHAR(100) NOT NULL,
  password       VARCHAR(100) NOT NULL,
  name           VARCHAR(30)  NOT NULL,
  phone_num      INT          NULL    ,
  email          VARCHAR(100) NULL    ,
  point          INT          NOT NULL DEFAULT 0,
  address        VARCHAR(100) NULL    ,
  provider       VARCHAR(100) NULL    ,
  provider_id    VARCHAR(100) NULL    ,
  signed_at      DATETIME     NOT NULL DEFAULT now(),
  paid_at        DATETIME     NULL    ,
  status_plan    ENUM('ACTIVE', 'INACTIVE')         NOT NULL,
  status_account ENUM('ACTIVE', 'INACTIVE', 'DELETED')         NOT NULL DEFAULT 'active' COMMENT '계정 상태',
  rental_cnt     INT          NOT NULL DEFAULT 0 COMMENT '이번주에 대여한 횟수',
  PRIMARY KEY (id)
);

ALTER TABLE Attachment
    ADD CONSTRAINT FK_Post_TO_Attachment
        FOREIGN KEY (post_id)
            REFERENCES Post (id)
ON DELETE CASCADE
;

ALTER TABLE Attachment
    ADD CONSTRAINT UQ_attachment UNIQUE (post_id, sequence);

ALTER TABLE Brand
    ADD CONSTRAINT UQ_brand UNIQUE (name);

ALTER TABLE Comment
    ADD CONSTRAINT FK_User_TO_Comment
        FOREIGN KEY (user_id)
            REFERENCES User (id)
ON DELETE CASCADE
;

ALTER TABLE Comment
    ADD CONSTRAINT FK_Post_TO_Comment
        FOREIGN KEY (post_id)
            REFERENCES Post (id)
ON DELETE CASCADE
;

ALTER TABLE Item
    ADD CONSTRAINT FK_Brand_TO_Item
        FOREIGN KEY (brand_id)
            REFERENCES Brand (id)
ON DELETE CASCADE
;

ALTER TABLE Payment
    ADD CONSTRAINT FK_User_TO_Payment
        FOREIGN KEY (user_id)
            REFERENCES User (id)
ON DELETE CASCADE
;

ALTER TABLE Payment
    ADD CONSTRAINT FK_Plan_TO_Payment
        FOREIGN KEY (plan_id)
            REFERENCES Plan (id)
            ON DELETE CASCADE
;

ALTER TABLE Post
    ADD CONSTRAINT FK_User_TO_Post
        FOREIGN KEY (user_id)
            REFERENCES User (id)
            ON DELETE CASCADE
;


ALTER TABLE Rental
    ADD CONSTRAINT FK_User_TO_Rental
        FOREIGN KEY (user_id)
            REFERENCES User (id)
ON DELETE CASCADE
;

ALTER TABLE Rental
    ADD CONSTRAINT FK_Item_TO_Rental
        FOREIGN KEY (item_id)
            REFERENCES Item (id)
ON DELETE CASCADE
;

ALTER TABLE User
  ADD CONSTRAINT FK_Authority_TO_User
    FOREIGN KEY (auth_id)
    REFERENCES Authority (id)
ON DELETE CASCADE
;

ALTER TABLE User
    ADD CONSTRAINT FK_Plan_TO_User
        FOREIGN KEY (plan_id)
            REFERENCES Plan (id)
ON DELETE CASCADE
;