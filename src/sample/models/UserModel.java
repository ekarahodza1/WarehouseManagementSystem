package sample.models;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    private Map<String, String> users = new HashMap<>();
    private static UserModel instance;

    public UserModel() {
        users.put("admin", "1234");
        users.put("user", "0000");
    }

    public static UserModel getInstance() {
        if (instance == null) instance = new UserModel();
        return instance;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
