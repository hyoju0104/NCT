
CREATE TABLE authority
(
  id    INT         NOT NULL AUTO_INCREMENT,
  grade VARCHAR(10) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE authority
  ADD CONSTRAINT UQ_grade UNIQUE (grade);

CREATE TABLE comment
(
  id      INT      NOT NULL AUTO_INCREMENT,
  user_id INT      NOT NULL,
  post_id INT      NOT NULL,
  content TEXT     NOT NULL,
  regdate DATETIME NULL     DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE item
(
  id           INT          NOT NULL AUTO_INCREMENT,
  name         VARCHAR(100) NOT NULL,
  brand        VARCHAR(100) NOT NULL,
  desc         TEXT         NULL    ,
  is_available BOOLEAN      NULL     DEFAULT true COMMENT '대여 여부 (아닌 경우 true)',
  condition    ENUM         NOT NULL DEFAULT B COMMENT 'A, B, C',
  createdAt    DATETIME     NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

CREATE TABLE like
(
  id      INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  item_id INT NOT NULL,
  PRIMARY KEY (id)
) COMMENT '찜';

CREATE TABLE post
(
  id          INT          NOT NULL AUTO_INCREMENT,
  user_id     INT          NOT NULL,
  content     LONGTEXT     NOT NULL,
  createdAt   DATETIME     NULL     DEFAULT now(),
  link        VARCHAR(100) NULL    ,
  media_type  VARCHAR(100) NULL    ,
  media_url   VARCHAR(100) NOT NULL,
  item        VARCHAR(100) NULL    ,
  is_approved BOOLEAN      NOT NULL DEFAULT false,
  approved_at DATETIME     NOT NULL DEFAULT now(),
  point_given BOOLEAN      NOT NULL DEFAULT false,
  PRIMARY KEY (id)
);

CREATE TABLE subscription
(
  id        INT      NOT NULL AUTO_INCREMENT,
  user_id   INT      NOT NULL,
  type      ENUM     NOT NULL DEFAULT FREE COMMENT 'SELECT(SILVER, GOLD, VIP, INFLUENCER)',
  startAt   DATETIME NOT NULL DEFAULT now(),
  expiredAt DATETIME NULL    ,
  price     INT      NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user
(
  id          INT          NOT NULL AUTO_INCREMENT,
  auth_id     INT          NOT NULL,
  username    VARCHAR(100) NOT NULL,
  password    VARCHAR(100) NOT NULL,
  name        VARCHAR(80)  NOT NULL,
  age         INT          NULL     DEFAULT 0 COMMENT 'CHECK(age >= 0)',
  gender      ENUM         NULL     DEFAULT MALE COMMENT 'MALE, FEMALE',
  authority   VARCHAR(10)  NOT NULL DEFAULT MEMBER,
  point       INT          NOT NULL DEFAULT 0 COMMENT 'CHECK(point >= 0)',
  signedAt    DATETIME     NULL     DEFAULT now(),
  account     VARCHAR(100) NULL    ,
  provider    VARCHAR(100) NOT NULL,
  provider_id VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE user
  ADD CONSTRAINT UQ_username UNIQUE (username);

ALTER TABLE post
  ADD CONSTRAINT FK_user_TO_post
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE;

ALTER TABLE comment
  ADD CONSTRAINT FK_post_TO_comment
    FOREIGN KEY (post_id)
    REFERENCES post (id)
        ON DELETE CASCADE;

ALTER TABLE comment
  ADD CONSTRAINT FK_user_TO_comment
    FOREIGN KEY (user_id)
    REFERENCES user (id)
        ON DELETE CASCADE;

ALTER TABLE subscription
  ADD CONSTRAINT FK_user_TO_subscription
    FOREIGN KEY (user_id)
    REFERENCES user (id)
        ON DELETE CASCADE;

ALTER TABLE user
  ADD CONSTRAINT FK_authority_TO_user
    FOREIGN KEY (auth_id)
    REFERENCES authority (id)
        ON DELETE CASCADE;

ALTER TABLE like
  ADD CONSTRAINT FK_user_TO_like
    FOREIGN KEY (user_id)
    REFERENCES user (id)
        ON DELETE CASCADE;

ALTER TABLE like
  ADD CONSTRAINT FK_item_TO_like
    FOREIGN KEY (item_id)
    REFERENCES item (id)
        ON DELETE CASCADE;
