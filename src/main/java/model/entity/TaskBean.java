package model.entity;

import java.io.Serializable;
import java.sql.Date;

public class TaskBean implements Serializable {
	
	//フィールド
	private int taskId;//タスクID
	private String taskName;//タスク名
	private String categoryName;//カテゴリ名
	private Date limitDate;//期限
	private String userName;//ユーザ名
	private String statusName;//ステータス名
	private String memo;//メモ

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
}
