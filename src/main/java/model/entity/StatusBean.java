package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


public class StatusBean implements Serializable{
	
	    private String statusCode;
	    private String statusName;
	    private Timestamp updateDatetime;
	    
	    public StatusBean() {
	    	
	    }
	    
	    @Override
	    public boolean equals(Object obj) {
	        // 同一参照なら即 true
	        if (this == obj) {
	            return true;
	        }

	        // null または型が違うなら false
	        if (obj == null || getClass() != obj.getClass()) {
	            return false;
	        }

	        // 比較対象をキャストして取り出す
	        StatusBean other = (StatusBean) obj;

	        // フィールドごとに比較（Objects.equalsメソッドでNPEが起きない）
	        return Objects.equals(this.statusCode, other.statusCode)
	                && Objects.equals(this.statusName, other.statusName);
	    }

	    public String getStatusCode() {
	        return statusCode;
	    }

	    public void setStatusCode(String statusCode) {
	        this.statusCode = statusCode;
	    }

	    public String getStatusName() {
	        return statusName;
	    }

	    public void setStatusName(String statusName) {
	        this.statusName = statusName;
	    }

	    public Timestamp getUpdateDatetime() {
	        return updateDatetime;
	    }

	    public void setUpdateDatetime(Timestamp updateDatetime) {
	        this.updateDatetime = updateDatetime;
	    }
	}

