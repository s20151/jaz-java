package pl.edu.pjwstk.jaz.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String authorities;



    public String getAuthorities () {
        return authorities;
    }

    public void setAuthorities (String authorities) {
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

}
