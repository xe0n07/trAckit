package Model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String companyName;
    private String email;
    private String role;
    
    // Constructor
    public User(String username, String password, String fullName, 
                String companyName, String email, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.companyName = companyName;
        this.email = email;
        this.role = role;
    }
    
    // Getters
    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getCompanyName() { return companyName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}