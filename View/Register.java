package View;

import DAO.UserDAO;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Register extends JFrame {
    private JTextField fullNameField;
    private JTextField companyField;
    private JTextField emailField;
    private JTextField roleField;
    private JPasswordField passwordField;
    private JCheckBox termsCheckbox;
    private JButton registerButton;
    private JLabel iconLabel;
    private JLabel titleLabel;
    private UserDAO userDAO;

    public Register() {
        userDAO = new UserDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Inventory Management System - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 530);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);

        // Icon
        iconLabel = new JLabel();
        try {
            iconLabel.setIcon(new ImageIcon(getClass().getResource("/View/Untitled design (1).png")));
        } catch (Exception e) {
            // ignore if not found
        }
        iconLabel.setBounds(520, 200, 200, 64);
        mainPanel.add(iconLabel);

        // Title
        titleLabel = new JLabel("REGISTER");
        titleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 40));
        titleLabel.setBounds(320, 30, 300, 50);
        mainPanel.add(titleLabel);

        // Subtitle
        JLabel subtitle = new JLabel("MANAGE ALL YOUR INVENTORY EFFICIENTLY !");
        subtitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 12));
        subtitle.setBounds(290, 70, 350, 20);
        mainPanel.add(subtitle);

        // Full Name
        JLabel fullNameLabel = new JLabel("FULL NAME:");
        fullNameLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
        fullNameLabel.setBounds(100, 120, 120, 30);
        mainPanel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(230, 120, 200, 30);
        mainPanel.add(fullNameField);

        // Company's Name
        JLabel companyLabel = new JLabel("COMPANY'S NAME:");
        companyLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
        companyLabel.setBounds(100, 170, 160, 30);
        mainPanel.add(companyLabel);

        companyField = new JTextField();
        companyField.setBounds(240, 170, 200, 30);
        mainPanel.add(companyField);

        // Email
        JLabel emailLabel = new JLabel("EMAIL:");
        emailLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
        emailLabel.setBounds(100, 220, 120, 30);
        mainPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(230, 220, 200, 30);
        mainPanel.add(emailField);

        // Role
        JLabel roleLabel = new JLabel("ROLE:");
        roleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
        roleLabel.setBounds(100, 270, 120, 30);
        mainPanel.add(roleLabel);

        roleField = new JTextField();
        roleField.setBounds(230, 270, 200, 30);
        mainPanel.add(roleField);

        // Password
        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
        passwordLabel.setBounds(100, 320, 120, 30);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(230, 320, 200, 30);
        mainPanel.add(passwordField);

        // Terms Checkbox
        termsCheckbox = new JCheckBox("I Agree to all Terms & Conditions");
        termsCheckbox.setBackground(Color.WHITE);
        termsCheckbox.setBounds(100, 370, 250, 30);
        mainPanel.add(termsCheckbox);

        // Register Button
        registerButton = new JButton("SIGN UP");
        registerButton.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 18));
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBounds(230, 420, 200, 40);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this::handleRegistration);
        mainPanel.add(registerButton);

        setContentPane(mainPanel);
    }

    private void handleRegistration(ActionEvent e) {
        String fullName = fullNameField.getText();
        String company = companyField.getText();
        String email = emailField.getText();
        String role = roleField.getText();
        String password = new String(passwordField.getPassword());

        if (!termsCheckbox.isSelected()) {
            showError("You must agree to the terms and conditions");
            return;
        }

        if (fullName.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty()) {
            showError("All fields except company are required");
            return;
        }

        if (userDAO.emailExists(email)) {
            showError("Email already registered");
            return;
        }

        User newUser = new User(email, password, fullName, company, email, role);
        if (userDAO.registerUser(newUser)) {
            JOptionPane.showMessageDialog(this, 
                "Registration successful! Please login.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            new Login().setVisible(true);
            this.dispose();
        } else {
            showError("Registration failed. Please try again.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, 
            message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void dispose() {
        super.dispose();
        new Login().setVisible(true);
    }
}