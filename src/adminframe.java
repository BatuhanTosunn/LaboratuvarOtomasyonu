import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class adminframe extends JFrame {
    ArrayList<String[]> users = new ArrayList<>();
    JLabel userlabel = new JLabel("Username | Password | User ID");
    DefaultListModel<String> usermodel = new DefaultListModel<>();
    JList<String> userslist = new JList(usermodel);
    JTextField addusername = new JTextField("Username");
    JTextField adduserpass = new JTextField("User Password");
    JButton addButton = new JButton("Add User");
    JButton removeButton = new JButton("Remove User");
    JButton signout = new JButton("Sign Out");
    adminframe(){
        this.setTitle("Admin Panel");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel adminpanel = new JPanel();
        adminpanel.setBackground(Color.getHSBColor(123, 50, 70));
        adminpanel.setLayout(null);

        userlabel.setBounds(10, 0, 300, 30);
        userslist.setBounds(10, 28, 250, 425);
        addusername.setBounds(265, 28, 217, 30);
        adduserpass.setBounds(265, 62, 217, 30);
        addButton.setBounds(265, 96, 217, 30);
        removeButton.setBounds(265, 130, 217, 30);
        signout.setBounds(265, 164, 217, 30);

        try{
            File file = new File("src/users.txt");
            FileReader reader1 = new FileReader(file);
            BufferedReader reader2 = new BufferedReader(reader1);

            String line;
            while ((line = reader2.readLine()) != null) {
                if (!(line.isEmpty())) {
                    String[] adding;
                    adding = line.split("/");
                    users.add(adding);
                }
            }
            reader2.close();
            reader1.close();
        } catch (FileNotFoundException a){
            JOptionPane.showMessageDialog(null, "File can not be found!");
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "IO Exception");
        }
        for (String[] str : users){
            String row = str[0] + "/" + str[1];
            usermodel.addElement(row);
        }

        adminpanel.add(userlabel);
        adminpanel.add(userslist);
        adminpanel.add(addusername);
        adminpanel.add(adduserpass);
        adminpanel.add(addButton);
        adminpanel.add(removeButton);
        adminpanel.add(signout);

        signout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminframe.this.dispose();
                new loginpage();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = userslist.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Remove from the ArrayList
                    users.remove(selectedIndex);

                    // Remove from the DefaultListModel
                    usermodel.remove(selectedIndex);

                    // Update the file
                    try {
                        FileWriter writer = new FileWriter("src/users.txt");
                        BufferedWriter writer2 = new BufferedWriter(writer);

                        for (String[] equipData : users) {
                            String row = equipData[0] + "/" + equipData[1];
                            writer2.write(row + "\n");
                        }

                        writer2.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "IO Exception while updating the file");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to remove");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("src/users.txt");
                    FileWriter writer = new FileWriter(file, true);
                    BufferedWriter writer2 = new BufferedWriter(writer);
                    String username = addusername.getText();
                    String password = adduserpass.getText();

                    if (username.isEmpty() || password.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Empty texts!");
                    }
                    else {
                        String row = username + "/" + password;
                        String[] row2 = row.split("/");
                        users.add(row2);
                        usermodel.addElement(row);
                        writer2.write(row + "\n");
                    }

                    writer2.close();
                } catch (FileNotFoundException p){
                    JOptionPane.showMessageDialog(null, "Equips file can not be found!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a number!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "IO Exception");
                }
                addusername.setText("");
                adduserpass.setText("");
            }
        });

        addusername.setLayout(null);
        adduserpass.setLayout(null);

        setLocationRelativeTo(null);
        this.add(adminpanel);
        this.setVisible(true);
    }
}