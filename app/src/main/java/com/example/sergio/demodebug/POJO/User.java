package com.example.sergio.demodebug.POJO;

public class User {
    private Integer id;
    private String name;
    private String username;

    /*public User(Integer id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
