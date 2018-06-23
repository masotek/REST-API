package org.infoshare.model;

/**
 * Created by marcin on 07.05.2017.
 */
public class Credentials {
    private String username;
    private String password;
    private String id;

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
