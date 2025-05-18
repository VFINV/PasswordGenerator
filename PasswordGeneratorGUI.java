import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGeneratorGUI extends JFrame {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:',.<>?/";

    private static final String ALL_CHARS = UPPER + LOWER + DIGITS + SPECIAL;
    private static final SecureRandom random = new SecureRandom();

    private JTextField lengthField;
    private JTextArea passwordArea;
    private JLabel strengthLabel;

    public PasswordGeneratorGUI() {
        setTitle("Password Generator 🔐");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GUI Components
        JLabel lengthLabel = new JLabel("Password Length:");
        lengthField = new JTextField("12", 5);
        JButton generateButton = new JButton("Generate Password");
        passwordArea = new JTextArea(2, 20);
        passwordArea.setLineWrap(true);
        passwordArea.setWrapStyleWord(true);
        passwordArea.setEditable(false);
        strengthLabel = new JLabel("Strength: ");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0; c.gridy = 0; panel.add(lengthLabel, c);
        c.gridx = 1; panel.add(lengthField, c);
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; panel.add(generateButton, c);
        c.gridy = 2; panel.add(new JScrollPane(passwordArea), c);
        c.gridy = 3; panel.add(strengthLabel, c);

        add(panel);

        // Action
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int length = Integer.parseInt(lengthField.getText());
                    String password = generatePassword(length);
                    passwordArea.setText(password);
                    strengthLabel.setText("Strength: " + evaluateStrength(password));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }
        });
    }

    private String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(ALL_CHARS.length());
            sb.append(ALL_CHARS.charAt(idx));
        }
        return sb.toString();
    }

    private String evaluateStrength(String password) {
        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*()].*")) score++;

        if (score >= 5) return "Strong";
        else if (score >= 3) return "Medium";
        else return "Weak";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PasswordGeneratorGUI().setVisible(true);
        });
    }
}
