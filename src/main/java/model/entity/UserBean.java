package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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