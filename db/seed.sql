
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

INSERT INTO m_user (user_id,password,user_name)
VALUES('admiko','admiko','アドミン娘');

INSERT INTO t_task ( task_name,category_id,limit_date,user_id,status_code,memo)
VALUES('結合テスト',2,'2026-06-29','admin','02','手こずっています');
