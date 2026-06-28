package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
/*＊
 *  ログインユーザー情報を保持するBeanクラスです。
 *  
 *  @author 林
 */
public class UserBean implements Serializable {

    private String userId;
    private String password;
    private String userName;
    private Timestamp updateDatetime;

    public UserBean() {
        super();
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
        UserBean other = (UserBean) obj;

        // フィールドごとに比較（Objects.equalsメソッドでNPEが起きない）
        return Objects.equals(this.userId, other.userId)
                && Objects.equals(this.userName, other.userName);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}