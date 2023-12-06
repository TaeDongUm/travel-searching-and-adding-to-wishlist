package com.ssafy.vue.member.model;

import javax.security.auth.Subject;

public class Payload {
    private String user_id;
    private String user_name;
    private String type;

    public Payload(String user_id, String user_name, String type) {
        setUser_id(user_id);
        setUser_name(user_name);
        setType(type);
    }

    public static Payload atk(String user_id, String user_name, String type) {
        return new Payload(user_id, user_name,"ATK");
    }

    public static Payload rtk(String user_id, String user_name, String type) {
        return new Payload(user_id, user_name,"ATK");
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
