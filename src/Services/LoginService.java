package Services;
import Models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginService {
    private Map<String, User> usersDatabase = new HashMap<>();


    private List<User> registeredUsers = new ArrayList<>();


    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void register(User user) {
        if (!usersDatabase.containsKey(user.getUsername())) {
            usersDatabase.put(user.getUsername(), user);
            registeredUsers.add(user);
        } else {
            System.out.println("A user with this name already exists.");
        }
    }

    public boolean authenticate(String username, String password) {
        User user = usersDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("The user '" + username + "' has successfully logged in.");
            return true;
        } else {
            System.out.println("Incorrect username or password.");
            return false;

        }
    }

    public User getUserByUsername(String username) {
        return usersDatabase.get(username);
    }

}
