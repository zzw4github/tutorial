package me.zzw.app.nutz.bean;



/**
 * Created by Administrator on 2016-07-25.
 */
import org.nutz.dao.entity.annotation.*;

@Table("t_user")
public class User extends BasePojo {

    @Id
    private int id;
    @Name
    @Column
    private String name;
    @Column("passwd")
    private String password;
    @Column
    private String salt;
    @One(target=UserProfile.class, field="id", key="userId")
    protected UserProfile profile;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}