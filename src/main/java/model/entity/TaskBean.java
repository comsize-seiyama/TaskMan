package model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class TaskBean implements Serializable {
	
	//フィールド
	private int taskId;//タスクID
	private String taskName;//タスク名
	private String categoryName;//カテゴリ名
	private Date limitDate;//期限
	private String userName;//ユーザ名
	private String statusName;//ステータス名
	private String memo;//メモ
	private Timestamp	create_datetime	;//登録日時
	private Timestamp updateDatetime;//更新日時
	

	//コンストラクタ
	public TaskBean() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	//アクセサメソッド
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Timestamp getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(Timestamp create_datetime) {
		this.create_datetime = create_datetime;
	}

	public Timestamp getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	
}
