package com.dzytsiuk.onlinestore.entity;

import java.util.Objects;

public class User {
    private long id;
    private String login;
    private int password;
    private String salt;

    public User() {
    }

    public User(String login, int password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                password == user.password &&
                Objects.equals(login, user.login) &&
                Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, password, salt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
