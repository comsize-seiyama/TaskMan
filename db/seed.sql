
INSERT INTO m_category (category_name)
VALUES ('新商品A:開発プロジェクト'),('既存商品B:改良プロジェクト');

INSERT INTO m_status
(status_code, status_name)
VALUES
('01', '未着手'),
('02', '対応中'),
('03', '完了');

--m_userのサンプル用レコード @by林
INSERT INTO m_user (user_id,password,user_name)
VALUES('admin','admin','アドミン太郎');
