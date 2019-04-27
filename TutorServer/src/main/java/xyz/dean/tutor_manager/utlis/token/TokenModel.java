package xyz.dean.tutor_manager.utlis.token;

import java.util.Date;

public class TokenModel {
    private String username;
    private String token;
    private Long createTime;
    private Long duration;

    public TokenModel(String username, String token, Long createTime, Long duration) {
        this.username = username;
        this.token = token;
        this.createTime = createTime;
        this.duration = duration;
    }

    public String getUsername() {
        return username;
    }
    public void setId(String username) {
        this.username = username;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
