package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class CategoryBean implements Serializable{

    private int statusCode;
    private String categoryName;
    private Timestamp updateDatetime;
    
    public CategoryBean(){
    	
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
        CategoryBean other = (CategoryBean) obj;

        // フィールドごとに比較（Objects.equalsメソッドでNPEが起きない）
        return this.statusCode == other.statusCode
                && Objects.equals(this.categoryName, other.categoryName);
    }

    public int getCategoryId() {
        return statusCode;
    }

    public void setCategoryId(int categoryId) {
        this.statusCode = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}