package pl.edu.pjwstk.jaz;

import java.util.Set;

public class User {
    private String username;
    private String password;
    Set<String> authorities;

    public User (String username, String password, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public Set<String> getAuthorities () {
        return authorities;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setAuthorities (Set<String> authorities) {
        this.authorities = authorities;
    }
}
