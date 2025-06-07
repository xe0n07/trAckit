package View;

import DAO.UserDAO;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
    // Swing components matching Login.form
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckbox;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel notRegisteredLabel;
    private JLabel iconLabel;
    private UserDAO userDAO;

    public Login() {
        userDAO = new UserDAO();
        initializeUI();
    }
    private void openForgetPass() {
        ForgetPass forgetPass = new ForgetPass();
        forgetPass.setVisible(true);
    }


    private void initializeUI() {
        setTitle("Inventory Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);

        // Main panel with border and background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainPanel.setLayout(null);  

        // Title label
        titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 40));
        titleLabel.setBounds(240, 30, 200, 50);
        mainPanel.add(titleLabel);

        // Icon label (optional, if image exists)
        iconLabel = new JLabel();
        try {
            iconLabel.setIcon(new ImageIcon(getClass().getResource("/View/Untitled design (1).png")));
        } catch (Exception e) {
            // If image not found, ignore
        }
        iconLabel.setBounds(400, 100, 190, 64);
        mainPanel.add(iconLabel);

        // Username label and field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameLabel.setBounds(100, 110, 100, 30);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 110, 180, 30);
        mainPanel.add(usernameField);

        // Password label and field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setBounds(100, 160, 100, 30);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 160, 180, 30);
        mainPanel.add(passwordField);

        // Remember Me checkbox
        rememberMeCheckbox = new JCheckBox("Remember Me");
        rememberMeCheckbox.setBounds(200, 200, 150, 25);
        rememberMeCheckbox.setBackground(Color.WHITE);
        mainPanel.add(rememberMeCheckbox);

        // Login button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(200, 240, 180, 35);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this::handleLogin);
        mainPanel.add(loginButton);

        // Forgot password button
        forgotPasswordButton = new JButton("Forget password?");
        forgotPasswordButton.setForeground(new Color(255, 51, 51));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setBounds(200, 280, 180, 25);
        forgotPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.addActionListener(e -> openForgetPass());
        // Add action if needed
        mainPanel.add(forgotPasswordButton);

        // Not registered label
        notRegisteredLabel = new JLabel("Not registered yet?");
        notRegisteredLabel.setBounds(170, 320, 150, 25);
        mainPanel.add(notRegisteredLabel);

        // Register button
        registerButton = new JButton("Create an account");
        registerButton.setForeground(new Color(0, 51, 255));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBounds(250, 320, 180, 25);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> openRegister());
        mainPanel.add(registerButton);

        setContentPane(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty");
            return;
        }

        User user = userDAO.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Welcome, " + user.getFullName() + "!",
                "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            // Open main application window here
        } else {
            showError("Invalid username or password");
        }
    }

    private void openRegister() {
        Register register = new Register();
        register.setVisible(true);
        this.dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        Database.DatabaseConnection.testConnection();
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}