package com.smattotest.lib.model;

/**
 * Created by pasqualini on 11/18/16.
 */

public class Question {

    private int created = 0;
    private String type = null;
    private Data data = null;
    private User user = null;

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
