package xyz.dean.tutor_manager.utlis.token;

import java.util.Date;

public class TokenModel {
    private Integer id;
    private String token;
    private Date createTime;
    private long duration;

    public TokenModel(Integer id, String token, Date createTime, long duration) {
        this.id = id;
        this.token = token;
        this.createTime = createTime;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
