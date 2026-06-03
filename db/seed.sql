
INSERT INTO m_user (user_id,password,user_name)
VALUES ('1','1234','test');

INSERT INTO m_category (category_name)
VALUES ('新商品A:開発プロジェクト'),('既存商品B:改良プロジェクト');

INSERT INTO m_status
(status_code, status_name)
VALUES
('01', '未着手'),
('02', '対応中'),
('03', '完了');

INSERT INTO t_task (task_name,category_id,limit_date,user_id,status_code,memo)
VALUES ('在庫管理システム','2','2026-6-30','1','02','棚卸機能を追加予定'),
('ECサイト開発','1','2026-9-30','1','01','スポーツ用品のECサイト');
