import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class loginpage extends JFrame{
    ArrayList<String[]> users = new ArrayList<>();
    JLabel usernameLabel = new JLabel("Username :");
    JLabel passwordLabel = new JLabel("Password  :");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField(); //Sifre girilirken nokta olarak gozukmesi icin JPasswordField
    JButton submitButton = new JButton("Submit");
    JButton quitButton = new JButton("Quit");
    loginpage() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(340, 180);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.getHSBColor(123, 50, 70));
        panel.setLayout(null);

        usernameLabel.setBounds(20, 15, 100, 30);
        passwordLabel.setBounds(20, 55, 100, 30);
        usernameField.setBounds(100, 20, 200, 20);
        passwordField.setBounds(100, 60, 200, 20);
        submitButton.setBounds(20, 95, 80, 30);
        quitButton.setBounds(110, 95, 80, 30);

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String line;
                int counter = 0;
                try {
                    File file = new File("src/users.txt");
                    FileReader control = new FileReader(file);
                    BufferedReader reader = new BufferedReader(control);

                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    while ((line = reader.readLine()) != null) {
                        if (!(line.isEmpty())) {
                            String[] adding;
                            adding = line.split("/");
                            users.add(adding);
                        }
                    }
                    if (username.equals("admin") && password.equals("admin")) {
                        JOptionPane.showMessageDialog(null, "Welcome " + username);
                        loginpage.this.dispose();
                        new adminframe();
                    } else {
                        for (String[] str : users) {
                            if (str[0].equals(username) && str[1].equals(password)) {
                                counter = 1;
                                break;
                            }
                        }
                        if (counter == 1) {
                            JOptionPane.showMessageDialog(null, "Welcome " + username);
                            loginpage.this.dispose(); // Use loginpage.this to refer to the outer class
                            new ChoosePage();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid entry!");
                        }
                    }
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Users file can not be found!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "IO Exception");
                }
            }
        });

        usernameField.setLayout(null);
        passwordField.setLayout(null);

        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(submitButton);
        panel.add(quitButton);

        setLocationRelativeTo(null);
        this.add(panel);
        this.setVisible(true);
    }
}
