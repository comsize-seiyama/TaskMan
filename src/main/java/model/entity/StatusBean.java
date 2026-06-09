package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class StatusBean implements Serializable{
	
	    private String statusCode;
	    private String statusName;
	    private Timestamp updateDatetime;

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

