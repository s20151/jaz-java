package pl.edu.pjwstk.jaz;

import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class UserRepository {
    private HashMap<String, User> userMap = new HashMap<>();

    public HashMap<String, User> getUsers() {
        return userMap;
    }

    public void addUser(User user){
        userMap.put (user.getUsername (),user);
    }

    public boolean doesUserExist (String username){
        return userMap.containsKey (username);
    }

    public User getUser(String username){
        return userMap.get (username);
    }

    public String getPassword(String username){
        User user = userMap.get (username);
        return user.getPassword ();
    }

    public boolean isEmpty(){
        return userMap.isEmpty ();
    }
}
