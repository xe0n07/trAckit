package View;

import DAO.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ForgetPass extends JFrame {
    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JButton resetButton;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel newPasswordLabel;
    private UserDAO userDAO;

    public ForgetPass() {
        userDAO = new UserDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reset Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainPanel.setLayout(null);

        titleLabel = new JLabel("RESET PASSWORD");
        titleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 32));
        titleLabel.setBounds(130, 20, 300, 40);
        mainPanel.add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameLabel.setBounds(70, 80, 100, 30);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 80, 200, 30);
        mainPanel.add(usernameField);

        newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newPasswordLabel.setBounds(70, 130, 120, 30);
        mainPanel.add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(180, 130, 200, 30);
        mainPanel.add(newPasswordField);

        resetButton = new JButton("Reset Password");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBounds(180, 190, 200, 35);
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(this::handleReset);
        mainPanel.add(resetButton);

        setContentPane(mainPanel);
    }

    private void handleReset(ActionEvent e) {
        String username = usernameField.getText();
        String newPassword = new String(newPasswordField.getPassword());

        if (username.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = userDAO.resetPassword(username, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Password reset successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}