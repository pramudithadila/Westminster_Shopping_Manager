public class User {
    // Fields for user credentials
    private String username;
    private String password;

    // Constructor for creating a new User instance.
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter and setter methods for user credentials.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
