package com.ssafy.vue.member.model;

public class KeyInfo {
    private String user_id, pw_salt, pw_key;

    public KeyInfo() {
        super();
    }

    public KeyInfo(String user_id, String pw_salt, String pw_key) {
        super();
        setUser_id(user_id);
        setPw_salt(pw_salt);
        setPw_key(pw_key);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        if(user_id != null)
            this.user_id = user_id;
    }

    public String getPw_salt() {
        return pw_salt;
    }

    public void setPw_salt(String pw_salt) {
        if(pw_salt != null)
            this.pw_salt = pw_salt;
    }

    public String getPw_key() {
        return pw_key;
    }

    public void setPw_key(String pw_key) {
        if(pw_key != null)
            this.pw_key = pw_key;
    }

    @Override
    public String toString() {
        return "KeyInfoDto [user_id=" + user_id + ", pw_salt=" + pw_salt + ", pw_key=" + pw_key + "]";
    }

}
